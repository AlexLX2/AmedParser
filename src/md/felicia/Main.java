package md.felicia;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    private static final String FILE_NAME = "D:\\Catalog\\";

    public static void main(String[] args) {
        String catalog = "";
        String decoded = "";
        Elements cataloglinks = null;
        Document doc;
        try{
            doc = Jsoup.connect("http://amed.md/ro/catalogul-national").get();

            cataloglinks = doc.body().getElementsByClass("field-item even");
        } catch (IOException e){
            e.printStackTrace();
        }

        for(Element link : cataloglinks) {
            catalog = link.getElementsByTag("a").attr("href");
        }

        try {
            decoded = URLDecoder.decode(catalog,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        decoded = decoded.substring(decoded.lastIndexOf(" "));
        decoded = decoded.substring(0,decoded.lastIndexOf("."));

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        try {
            date = sdf.parse(decoded);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(catalog);
        System.out.println(decoded);
        System.out.println(date);


        downloadCatalog(catalog,decoded);



    }

    public  static  void downloadCatalog(String url, String file){
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream  = new FileOutputStream(FILE_NAME + file + ".xls")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}
