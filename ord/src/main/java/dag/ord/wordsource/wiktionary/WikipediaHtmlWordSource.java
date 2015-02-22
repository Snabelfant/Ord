package dag.ord.wordsource.wiktionary;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import dag.ord.wordsource.WordSource;

public class WikipediaHtmlWordSource extends WikiHtmlWordSource {
    public WikipediaHtmlWordSource(String shortName, String languageCode) {
        super(shortName, "wikipedia", languageCode);
    }

    public static List<WordSource> createWordSources() {
        List<WordSource> wordSources = new ArrayList<WordSource>();

        wordSources.add(new WikipediaHtmlWordSource("WPNO", "no"));
        wordSources.add(new WikipediaHtmlWordSource("WPSV", "sv"));
        wordSources.add(new WikipediaHtmlWordSource("WPDA", "da"));
        wordSources.add(new WikipediaHtmlWordSource("WPDE", "de"));
        wordSources.add(new WikipediaHtmlWordSource("WPFR", "fr"));
        wordSources.add(new WikipediaHtmlWordSource("WPEN", "en"));
        return wordSources;
    }

    @Override
    protected String transform(Element top) {
        final String tagsToRemove =
                "table,sup,h2,h3,noscript,img,div[class~=thumb.*], " +
                        "div[class~=references.*],div[id=page-secondary-actions]," +
                        "div[class=noprint],div[class=floatleft]," +
                        "div[class=vedlikehold], div[class~=stubb.*]," +
                        "div[style~=clear: both.*],div[id=catlinks]";

        Elements noMatchFound = top.select("p[class=mw-search-createlink]");
        if (!noMatchFound.isEmpty()) {
            return null;
        }
        top.select(tagsToRemove).remove();
        Elements as = top.select("a");
        for (Element a : as) {
            a.replaceWith(new TextNode(a.text(), null));
        }

        Elements ps = top.select("p");
        for (Element p : ps) {
            p.tagName("div");
        }

        Elements uls = top.select("ul");
        for (Element ul : uls) {
            ul.tagName("div").before(new Element(Tag.valueOf("br"), ""));
        }

        Elements ols = top.select("ol");
        for (Element ol : ols) {
            ol.tagName("div").before(new Element(Tag.valueOf("br"), ""));
        }

        Elements lis = top.select("li");
        for (Element li : lis) {
            Element liDiv = new Element(Tag.valueOf("div"), "");
            liDiv.append("&bull;&nbsp;");
            liDiv.append(li.html());
            liDiv.appendElement("br");
            li.replaceWith(liDiv);
        }

        String html = top.toString();
        html = html.replaceAll("<div.*?>", "").replace("</div>", "");
//        Log.i( getShortName() + " '" + html + "'");
        return html;
    }


}
