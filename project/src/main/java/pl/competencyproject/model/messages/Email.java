package pl.competencyproject.model.messages;

import pl.competencyproject.model.DAO.SessionLogon;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {
    private static final String host = "smtp.gmail.com";
    private static final String from = "commpetencyproject@gmail.com";
    private static final boolean sessionDebug = false;
    private static final String pass = "pr0jektk0mpetencyjny";

    public static void mailRejestration(String to) {
        try {
            String subject = "Kod potwierdzający rejestrację";
            String messageText = "Twój kod to: " + String.valueOf(SessionLogon.generateCode());
            createMail(to,subject,messageText);
            System.out.println("message send successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void mailChangePassword(String to) {
        try {
            String subject = "Kod potwierdzający zmianę hasła";
            String messageText = "Twój kod to: " + String.valueOf(SessionLogon.generateCode());
            createMail(to,subject,messageText);
            System.out.println("message send successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Properties setProperties() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
        return props;
    }

    private static Session createMailSession(Properties props) {
        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        return mailSession;
    }

    private static Message createMessage(Session mailSession, String to, String subject, String messageText) {
        Message msg = null;
        try {
            msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private static void createTransport(Session mailSession, Message msg) {
        Transport transport = null;
        try {
            transport = mailSession.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void createMail(String to, String subject, String messageText){
        Properties props = setProperties();
        Session mailSession = createMailSession(props);
        Message msg = createMessage(mailSession, to, subject, messageText);
        createTransport(mailSession,msg);
    }
}