function atualizarContadorCarrinho() {
    fetch('/api/cart/item-count')
        .then(response => response.json())
        .then(data => {
            const span = document.getElementById('cart-item-count');
            if (span) {
                span.innerText = data.count;
            }
        })
        .catch(err => console.error('Erro ao atualizar carrinho', err));
}

document.addEventListener('DOMContentLoaded', function() {
    atualizarContadorCarrinho();

    // Toast notification function
    function showToast(message, type = 'success') {
        const toastContainer = document.getElementById('toast-container');
        if (!toastContainer) return;

        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.textContent = message;

        toastContainer.appendChild(toast);

        // Show toast
        setTimeout(() => {
            toast.classList.add('show');
        }, 100);

        // Hide toast after 3 seconds
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => {
                toast.remove();
            }, 300);
        }, 3000);
    }

    // Add to cart functionality
    document.querySelectorAll('.add-to-cart-form').forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent default form submission

            const formData = new FormData(this);

            fetch(this.action, {
                method: 'POST',
                body: new URLSearchParams(formData),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
            .then(response => {
                if (response.ok) {
                    // Try to parse the response as JSON
                    response.json().then(data => {
                        showToast(data.message || 'Item adicionado ao carrinho!', 'success');
                    }).catch(() => {
                        // If parsing as JSON fails, just show a generic success message
                        showToast('Item adicionado ao carrinho!', 'success');
                    });

                    atualizarContadorCarrinho(); // Update the cart count
                } else {
                    response.json().then(data => {
                        showToast(data.message || 'Erro ao adicionar item ao carrinho.', 'error');
                    }).catch(() => {
                        showToast('Erro ao adicionar item ao carrinho.', 'error');
                    });
                }
            })
            .catch(error => {
                console.error('Error adding to cart:', error)
                showToast('Erro de rede. Tente novamente.', 'error');
            });
        });
    });

    // Update quantity functionality
    function updateCartQuantity(produtoId, quantidade) {
        const formData = new FormData();
        formData.append('produtoId', produtoId);
        formData.append('quantidade', quantidade);

        fetch('/produtos/atualizar-carrinho', {
            method: 'POST',
            body: new URLSearchParams(formData),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                if (quantidade === 0) {
                    // Remove the item row if quantity is 0
                    const widget = document.querySelector(`.quantity-widget[data-produto-id='${produtoId}']`);
                    if (widget) {
                        widget.closest('tr').remove();
                    }
                } else {
                    // Update the quantity input
                    const input = document.querySelector(`.quantity-widget[data-produto-id='${produtoId}'] .quantity`);
                    if (input) {
                        input.value = quantidade;
                    }
                }

                // Recalculate and update totals
                let total = 0;
                document.querySelectorAll('.quantity-widget').forEach(widget => {
                    const priceString = widget.closest('tr').querySelector('td:nth-child(3)').textContent;
                    // Remove R$, remove thousands separator '.', then replace decimal ',' with '.'
                    const price = parseFloat(priceString.replace('R$', '').replace(/\./g, '').replace(',', '.'));
                    const quantity = parseInt(widget.querySelector('.quantity').value);
                    const itemTotal = price * quantity;
                    widget.closest('tr').querySelector('td:nth-child(4)').textContent = 'R$ ' + itemTotal.toFixed(2).replace('.', ',');
                    total += itemTotal;
                });

                document.querySelector('.text-warning').textContent = 'R$ ' + total.toFixed(2).replace('.', ',');

                if (document.querySelectorAll('.quantity-widget').length === 0) {
                    const main = document.querySelector('main');
                    main.innerHTML = `
                        <h2 class="mb-4">🛒 Carrinho de Compras</h2>
                        <div class="alert alert-warning" role="alert">
                            Seu carrinho está vazio.
                        </div>
                        <a href="/produtos" class="btn btn-secondary mt-3">Continuar Comprando</a>
                    `;
                }

                atualizarContadorCarrinho();
                showToast(data.message, 'success');
            } else {
                showToast(data.message, 'error');
            }
        })
        .catch(error => {
            console.error('Error updating quantity:', error);
            showToast('Erro de rede. Tente novamente.', 'error');
        });
    }

    document.querySelectorAll('.quantity-plus').forEach(button => {
        button.addEventListener('click', function() {
            const widget = this.closest('.quantity-widget');
            const produtoId = widget.dataset.produtoId;
            const input = widget.querySelector('.quantity');
            const newQuantity = parseInt(input.value) + 1;
            updateCartQuantity(produtoId, newQuantity);
        });
    });

    document.querySelectorAll('.quantity-minus').forEach(button => {
        button.addEventListener('click', function() {
            const widget = this.closest('.quantity-widget');
            const produtoId = widget.dataset.produtoId;
            const input = widget.querySelector('.quantity');
            const newQuantity = parseInt(input.value) - 1;
            if (newQuantity >= 0) {
                updateCartQuantity(produtoId, newQuantity);
            }
        });
    });
});
