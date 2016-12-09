package com.dingmouren.easymvp.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by dingzi on 2016/12/8.
 */

public class HtmlFormat {
    public static String formatHtmlSource(String htmText){
        Document doc = Jsoup.parse(htmText);
        Elements elements = doc.getElementsByTag("img");
        for (Element element: elements ) {
            element.attr("width","100%").attr("height","auto");
        }
        return doc.toString();
    }
}
