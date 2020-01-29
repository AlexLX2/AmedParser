package md.felicia;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class AmedParser {

    private static String FILE_NAME;
    private static String CATALOG_URI;
    private static String MAIL_TO;
    private static String MAIL_FROM;
    private static String MAIL_SERVER;

    static {
        Properties properties = new Properties();
        try {
            properties.load(AmedParser.class.getResourceAsStream("config.ini"));
            CATALOG_URI = properties.getProperty("CATALOG_URI");
            FILE_NAME = properties.getProperty("LOCAL_CATALOG_PATH");
            MAIL_TO = properties.getProperty("MAIL_TO");
            MAIL_FROM = properties.getProperty("MAIL_FROM");
            MAIL_SERVER = properties.getProperty("MAIL_SERVER");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Initialization failed! Verify the config file!");
        }

    }

    public static void main(String[] args) {
        checkCatalog();
    }

    private static void downloadCatalog(String url, String fileStringPath) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileStringPath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMail(String fileStringPath, String to, String from, String host) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(from)); // setting header fields

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("New catalog has arrived"); // subject line

            // actual mail body
            message.setText("Вышел новый каталог");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(fileStringPath);

            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    private static void checkCatalog() {
        String catalog = "";
        String decoded;
        Elements cataloglinks = null;
        Document doc;

        try {
            doc = Jsoup.connect(CATALOG_URI).get();
            cataloglinks = doc.body().getElementsByClass("field-item even");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert cataloglinks != null;
        for (Element link : cataloglinks) {
            catalog = link.getElementsByTag("a").attr("href");
        }

        decoded = URLDecoder.decode(catalog, StandardCharsets.UTF_8);
        decoded = decoded.substring(decoded.lastIndexOf(" "));
        decoded = decoded.substring(0, decoded.lastIndexOf("."));

        String fileStringPath = FILE_NAME + decoded + ".xls";
        File checkFile = new File(fileStringPath);
        if (!checkFile.exists()) {
            downloadCatalog(catalog, fileStringPath);
            sendMail(fileStringPath, MAIL_TO, MAIL_FROM, MAIL_SERVER);
            CatalogParser.parse(fileStringPath);
        } else {
            System.out.println("Catalog already downloaded");
        }
    }
}
