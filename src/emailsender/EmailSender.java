package emailsender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = new Runnable() {
            public void run() {
                sendEmails();
            }
        };
        
        //scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }

    private static void sendEmails() {
        System.out.println("Preparing to send message....");
        // Get the current time

        LocalDateTime currentTime = LocalDateTime.now();

        // Format the current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        // Create the email message with HTML formatting
        String message = "<html><body><h2 style=\"color:blue;\">Dear valued subscriber,</h2>"
                + "<p>We hope this message finds you well.</p>"
                + "<p>We are pleased to bring you the latest updates from RDX India.</p>"
                + "<p>Our team has been working diligently to enhance our services and provide you with an even better experience.</p>"
                + "<p>We are excited to announce some upcoming changes:</p>"
                + "<ul>"
                + "<li>New features and improvements to our platform.</li>"
                + "<li>Enhanced customer support services to better assist you.</li>"
                + "<li>Exciting promotions and offers exclusive to our subscribers.</li>"
                + "</ul>"
                + "<p>We are committed to continually improving our services and delivering value to our customers.</p>"
                + "<p><span style=\"font-size:20px;color:red;\">Current Time: " + formattedTime + "</span></p>"
                + "<p>Thank you for choosing RDX India. We appreciate your continued support.</p>"
                + "<p>Best regards,<br>RDX India Team</p></body></html>";

        String subject = "🚀 Scheduled Update from RDX India: Exciting Changes Ahead! 🎉";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String from = "";
        String fromPass = "";
        String to = "";
        List<String> cc = new ArrayList<>();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/emails", "root", "");
            String query = "SELECT t.email as toemail, f.email as fromemail, f.pass as frompass, c.email as ccemail FROM fromtable f, totable t, cctable c";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                from = rs.getString("fromemail");
                fromPass = rs.getString("frompass");
                to = rs.getString("toemail");
                do {
                    cc.add(rs.getString("ccemail"));
                } while (rs.next());

                System.out.println(from);
                System.out.println(fromPass);
                System.out.println(to);
                System.out.println(cc);
            } else {
                System.out.println("No data found in the database.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        sendEmail(message, subject, to, from, fromPass, cc);
    }

    private static void sendEmail(String message, String subject, String to, String from, String fromPass, List<String> cc) {
        //gmail variable
        String host = "smtp.gmail.com";

        //Get the system properties
        Properties properties = System.getProperties();
        System.out.println("Properties " + properties);

        //Setting imp info to properties obj..
        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //enable TLSv1.2
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        //STEP 1. to get session obj...
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, fromPass);
            }

        });
        session.setDebug(true);

        //STEP 2. compose the message[text, multimedia]
        MimeMessage msg = new MimeMessage(session);

        try {
            //from email
            msg.setFrom(new InternetAddress(from));

            //adding recipient
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Adding cc emails
            if (!cc.isEmpty()) {
                InternetAddress[] ccAddresses = new InternetAddress[cc.size()];
                for (int i = 0; i < cc.size(); i++) {
                    ccAddresses[i] = new InternetAddress(cc.get(i));
                }
                msg.addRecipients(Message.RecipientType.CC, ccAddresses);
            }

            //adding subject
            msg.setSubject(subject);

            // Set the message content as HTML
            msg.setContent(message, "text/html");

//            //adding text to msg
//            msg.setText(message);
            //send
            //Step 3.. sending messege using transport class
            Transport.send(msg);

            System.out.println("msg has been sent..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
