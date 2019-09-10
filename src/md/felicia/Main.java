package md.felicia;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

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



    }
}
