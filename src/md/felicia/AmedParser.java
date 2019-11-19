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
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;


public class AmedParser {

    private static final String FILE_NAME = "D:\\Catalog\\";

    public static void main(String[] args) {
        String catalog = "";
        String decoded = "";
        Elements cataloglinks = null;
        Document doc;
        try {
            doc = Jsoup.connect("https://amdm.gov.md/ro/catalogul-national").get();

            cataloglinks = doc.body().getElementsByClass("field-item even");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert cataloglinks != null;
        for (Element link : cataloglinks) {
            catalog = link.getElementsByTag("a").attr("href");
        }

        try {
            decoded = URLDecoder.decode(catalog, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        decoded = decoded.substring(decoded.lastIndexOf(" "));
        decoded = decoded.substring(0, decoded.lastIndexOf("."));

        String fileStringPath = FILE_NAME + decoded + ".xls";
        File checkFile = new File(fileStringPath);
        if (!checkFile.exists()) {
            downloadCatalog(catalog, fileStringPath);
            sendMail(fileStringPath);
        } else
            System.out.println("Catalog already downloaded");

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

    private static void sendMail(String file) {
        String to = "ak@felicia.md";         // sender email
        String from = "ak@felicia.md";       // receiver email
        String host = "10.50.0.21";            // mail server host

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
            mimeBodyPart.attachFile(file);

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
}
