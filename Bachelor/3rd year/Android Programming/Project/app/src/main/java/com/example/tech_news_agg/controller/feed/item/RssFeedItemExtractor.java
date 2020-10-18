package com.example.tech_news_agg.controller.feed.item;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tech_news_agg.controller.user.UserManager;
import com.example.tech_news_agg.model.RssFeedItem;
import com.example.tech_news_agg.model.RssFeedMetadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RssFeedItemExtractor extends AsyncTask<RssFeedMetadata, Void, List<RssFeedItem>> {

    private static String TAG_CHANNEL = "channel";
    private static String TAG_TITLE = "title";
    private static String TAG_LINK = "link";
    private static String TAG_DESRIPTION = "description";
    private static String TAG_ITEM = "item";
    private static String TAG_PUB_DATE = "pubDate";

    public List<RssFeedItem> doInBackground(RssFeedMetadata... feedMetadatas) {
        List<RssFeedItem> itemsList = new ArrayList<RssFeedItem>();
        String rssFeedXML;
        RssFeedMetadata feedMetadata = feedMetadatas[0];
        rssFeedXML = getXmlFromUrl(feedMetadata.getUrl());
        if (rssFeedXML != null) {
            try {
                Document doc = getDomElement(rssFeedXML);
                NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
                Element e = (Element) nodeList.item(0);

                NodeList items = e.getElementsByTagName(TAG_ITEM);
                for (int i = 0; i < items.getLength(); i++) {
                    Element e1 = (Element) items.item(i);

                    String title = getValue(e1, TAG_TITLE);
                    String link = getValue(e1, TAG_LINK);
                    String description = getValue(e1, TAG_DESRIPTION);
                    String pubdate = getValue(e1, TAG_PUB_DATE);

                    RssFeedItem rssItem = new RssFeedItem(title, link, description, pubdate);

                    // adding item to list
                    itemsList.add(rssItem);
                }
            } catch (Exception e) {
                // Check log for errors
                e.printStackTrace();
            }
        }
        return itemsList;
    }


    private static String getXmlFromUrl(String url)  {
        String xml = "", inputLine = "";

        try {
            URLConnection conn = new URL(url).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((inputLine = reader.readLine()) != null)
                xml = xml.concat(inputLine + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }


    private static Document getDomElement(String xml) {
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

    private static String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


    private static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }
}
