package parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrencyJsoup {
    private Document document;
    private Thread secThread;
    private Runnable runnable;
    private List list;
    private HashMap <String, List> map = new HashMap<>();

    public static void main(String[] args) {
        CurrencyJsoup currencyJsoup = new CurrencyJsoup();
        currencyJsoup.init();
    }


    void init(){
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
//            Подключаемся к web-странице
            document = Jsoup.connect("https://minfin.com.ua/currency/").get();

//            Выбираем элементы которые нам нужны по тегу, в нашем случае tbody
            Elements table = document.getElementsByTag("tbody");
//            если хотим посмотреть, что находится в первой таблице на сайте
//            System.out.println(table.get(0).text());
//           это как раз та таблица которая нам нужна
            Element our_table = table.get(0);
//            берем элементы из выбранной таблицы
            Elements elements_from_tabel = our_table.children();
//            берем первую строку таблицы
            Element USD =elements_from_tabel.get(0);
//            берем элементы из выбранной строки
            Elements USD_elements = USD.children();

            System.out.println(USD_elements.get(0).text());

            for (int i = 0; i < elements_from_tabel.size(); i++) {
                list = new ArrayList();
                for (int j = 0; j < elements_from_tabel.size(); j++) {
                    list.add(elements_from_tabel.get(i).children().get(j).text());
                }
                System.out.println(list);
                map.put(list.get(0).toString(),list);
            }
            System.out.println(map.get("RUB"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
