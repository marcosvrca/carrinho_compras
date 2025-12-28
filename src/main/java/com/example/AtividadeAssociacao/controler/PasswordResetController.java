import com.example.AtividadeAssociacao.model.Pessoa.Pessoa;
import com.example.AtividadeAssociacao.repository.PessoaRepository;
import com.example.AtividadeAssociacao.service.PessoaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PasswordResetController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private JavaMailSender mailSender; // Need to configure this bean

    // Display "forgot password" form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password"; // This will be the name of the HTML template
    }

    // Process "forgot password" request
    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, HttpServletRequest request, Model model) {
        Pessoa pessoa = pessoaRepository.findByEmail(userEmail);
        if (pessoa == null) {
            model.addAttribute("error", "Não existe uma conta com esse e-mail.");
            return "forgot-password";
        }

        pessoaService.generateResetPasswordToken(pessoa);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("no-reply@yourdomain.com"); // Configure your email sender
        passwordResetEmail.setTo(pessoa.getEmail());
        passwordResetEmail.setSubject("Redefinição de Senha");
        passwordResetEmail.setText("Para redefinir sua senha, clique no link abaixo:\n" + appUrl + "/reset-password?token=" + pessoa.getResetPasswordToken());

        mailSender.send(passwordResetEmail);

        model.addAttribute("message", "Um link de redefinição de senha foi enviado para o seu e-mail.");
        return "forgot-password";
    }

    // Display "reset password" form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Pessoa pessoa = pessoaService.findByResetPasswordToken(token);
        if (pessoa == null || pessoa.getTokenCreationDate().plusHours(1).isBefore(LocalDateTime.now())) { // Token valid for 1 hour
            model.addAttribute("error", "O token é inválido ou expirou.");
            return "redirect:/login";
        }

        model.addAttribute("token", token);
        return "reset-password"; // This will be the name of the HTML template
    }

    // Process "reset password" request
    @PostMapping("/reset-password")
    public String processResetPasswordForm(@RequestParam("token") String token,
                                           @RequestParam("password") String newPassword,
                                           @RequestParam("confirmPassword") String confirmPassword,
                                           Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "As senhas não coincidem.");
            model.addAttribute("token", token); // Keep token in model to re-display form
            return "reset-password";
        }

        Pessoa pessoa = pessoaService.findByResetPasswordToken(token);
        if (pessoa == null || pessoa.getTokenCreationDate().plusHours(1).isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "O token é inválido ou expirou.");
            return "redirect:/login";
        }

        pessoaService.updatePassword(pessoa, newPassword);
        model.addAttribute("message", "Sua senha foi redefinida com sucesso. Por favor, faça login.");
        return "redirect:/login";
    }
}
