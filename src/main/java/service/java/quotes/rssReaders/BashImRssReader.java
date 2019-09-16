package service.java.quotes.rssReaders;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import service.java.quotes.models.Quote;
import service.java.quotes.repository.QuotesRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public abstract class BashImRssReader implements QuotesRepository {

    private static final String URL = "https://bash.im/rss/";
    private static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    private static final String ITEM_TAG = "item";
    private static final String GUID_TAG = "guid";
    private static final String LINK_TAG = "link";
    private static final String TITLE_TAG = "title";
    private static final String DESCRIPTION_TAG = "description";
    private static final String PUB_DATE_TAG = "pubDate";


    static private String getTag(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    static private Document getDocument(){
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc =  builder.parse(URL);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            log.info("can't load models from " + URL + ", " + e);
        }
        return doc;
    }

    static public List<Quote> readQuotes() {
        Document doc = getDocument();
        if (doc == null) {
            return Collections.emptyList();
        }

        NodeList itemsNodeList = doc.getElementsByTagName(ITEM_TAG);
        List<Quote> quotes = new ArrayList<>(itemsNodeList.getLength());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        for (int i = 0; i < itemsNodeList.getLength(); i++) {
            Node node = itemsNodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String guid = getTag(element, GUID_TAG);
                String link = getTag(element, LINK_TAG);
                String title = getTag(element, TITLE_TAG);
                String description = getTag(element, DESCRIPTION_TAG);
                String pubDateStr = getTag(element, PUB_DATE_TAG);
                Date pubDate = null;
                try {
                    pubDate = dateFormat.parse(pubDateStr);
                } catch (ParseException e) {
                    log.info("can't parse pubDate: " + e);
                }
                Quote quote = new Quote(guid, link, title, description, pubDate);
                quotes.add(quote);
            }
        }
        return quotes;
    }
}
