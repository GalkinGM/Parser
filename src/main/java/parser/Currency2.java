package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Currency2 {

    public static void main(String[] args) throws Exception {
        getRates();
    }

    private static String[][] getRates() throws Exception{
        HashMap <String, NodeList> result = new HashMap();
        String[][] rates = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
        Date date = new Date ();
        String url = "http://www.cbr.ru/scripts/XML_daily.asp?date_req="+ dateFormat.format(date);
        Document doc = loadDocument(url);

        NodeList nl = doc.getElementsByTagName("Valute");
        for (int i = 0; i< nl.getLength(); i++) {
            Node c = nl.item(i);
            NodeList nlChilds = c.getChildNodes();
            for (int j = 0; j< nlChilds.getLength(); j++) {
                if (nlChilds.item(j).getNodeName().equals("CharCode")) {
                    result.put(nlChilds.item(j).getTextContent(), nlChilds);
                }
            }
        }

        int k = 0;
        rates = new String[result.size()][2];

        for (Map.Entry<String, NodeList> entry : result.entrySet()) {
            NodeList temp = entry.getValue();
            double value = 0;
            int nominal = 0;

            for (int i = 0; i < temp.getLength(); i++) {
                if (temp.item(i).getNodeName().equals("Value")) {
                    value = Double.parseDouble(temp.item(i).getTextContent().replace(',' ,'.'));
                } else if (temp.item(i).getNodeName().equals("Nominal")) {
                    nominal = Integer.parseInt(temp.item(i).getTextContent());
                }
            }
            double amount = value / nominal;

            rates [k][0] = entry.getKey();
            rates [k][1] = Double.toString(((double) Math.round(amount*10000))/10000);
            k++;
        }

        for (int i = 0; i < rates.length; i++) {
            System.out.println(rates[i][0] +" "+ rates[i][1]);

        }

        return rates;
    }

    private static Document loadDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }
}