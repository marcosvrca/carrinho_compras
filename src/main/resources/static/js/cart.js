document.addEventListener('DOMContentLoaded', function() {
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

                    if (typeof window.atualizarContadorCarrinho === 'function') {
                        window.atualizarContadorCarrinho(); // Update the cart count
                    }
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
});
