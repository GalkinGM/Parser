package parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CurrencyJsoup2 {
    private Document document;
    private Thread secThread;
    private Runnable runnable;
    private List list;
    private HashMap <String, List> map = new HashMap<>();

    public static void main(String[] args) {
        CurrencyJsoup2 currencyJsoup = new CurrencyJsoup2();
        currencyJsoup.init();
    }


    private void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }

    private void getWeb(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
            Date date = new Date (2021-1900,7,10);
            System.out.println(dateFormat.format(date));
            String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="+ dateFormat.format(date);
            document = Jsoup.connect(url).get();
            Elements table = document.getElementsByTag("Valute");
            Element our_table = table.get(0);
            Elements elements_from_tabel = our_table.children();
            Element USD =elements_from_tabel.get(1);
            System.out.println(USD.text());
            for (int i = 0; i < table.size(); i++) {
                list = new ArrayList();
                for (int j = 0; j < elements_from_tabel.size(); j++) {
                    list.add(table.get(i).children().get(j).text());
                }
                System.out.println(list);
                map.put(list.get(1).toString(),list);
            }
            System.out.println(map.get("AUD").get(1));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
