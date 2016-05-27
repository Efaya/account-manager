package fr.efaya.register;

import fr.efaya.database.UsersRepository;
import fr.efaya.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

/**
 * Created by KTIFA FAMILY on 15/05/2016.
 */
@RestController
@RequestMapping("/registration")
public class RegisterController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @RequestMapping(value = "{userId}/{token}", method = RequestMethod.GET)
    public void confirmRegistration(@PathVariable String userId, @PathVariable String token) {
        String tokenDecoded = new String(Base64.getDecoder().decode(token.getBytes()));
        if ("EFAYA".equals(tokenDecoded)) {
            User user = usersRepository.findOne(userId);
            user.setValid(true);
            usersRepository.save(user);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<User> listUsers() {
        /*if (principal != null) {
            User user = usersRepository.findByUsername(principal.getName());
            if (!"ADMIN".equals(user.getRole())) {
                throw new RuntimeException("Insufficient rights");
            }
        */
        List<User> users = usersRepository.findAll();
        users.stream().forEach(u -> u.setPassword(null));
        return users;
        /*}
        return null;*/
    }

    public void sendEmail(User user, String contextPath) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        String htmlMsg = String.format("<p>Bonjour,<br/><br/>Merci de votre enregistrement, " +
                "veuillez cliquez sur le <a target=\"_blank\" href=\"%s\">lien</a> suivant pour le valider.</p>",
                contextPath + "/" + user.getId() + "/" + Base64.getEncoder().encodeToString("EFAYA".getBytes()));
        mimeMessage.setContent(htmlMsg, "text/html");
        helper.setTo(user.getEmail());
        helper.setSubject("Confirmation de l'inscription");
        helper.setFrom(sender);
        mailSender.send(mimeMessage);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void registration(@RequestBody @Valid Registration registration, HttpServletRequest request) throws AlreadyExistingUserException, MessagingException {
        User alreadyExist = usersRepository.findByUsername(registration.getUsername());
        if (alreadyExist != null) {
            throw new AlreadyExistingUserException();
        }
        User user = new User(registration.getUsername(), registration.getPassword());
        user.setEmail(registration.getEmail());
        usersRepository.save(user);
        sendEmail(user, request.getRequestURL().toString());
    }

    @ResponseStatus(value= HttpStatus.CONFLICT, reason="Un utilisateur avec ce nom existe déjà.")
    private class AlreadyExistingUserException extends RuntimeException  {
    }
}
