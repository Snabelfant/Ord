package dag.ord.wordsource.wiktionary;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import dag.ord.wordsource.WordSource;

/**
 * Created by Dag on 17.08.2014.
 */
public class WiktionaryHtmlWordSource extends WikiHtmlWordSource {

    private WiktionaryHtmlWordSource(String shortName, String languageCode) {
        super(shortName, "wiktionary", languageCode);
    }

    public static List<WordSource> createWordSources() {
        List<WordSource> wordSources = new ArrayList<WordSource>();

        wordSources.add(new WiktionaryHtmlWordSource("WKNO", "no"));
        wordSources.add(new WiktionaryHtmlWordSource("WKSV", "sv"));
        wordSources.add(new WiktionaryHtmlWordSource("WKDA", "da"));
        wordSources.add(new WiktionaryHtmlWordSource("WKEN", "en"));
        wordSources.add(new WiktionaryHtmlWordSource("WKDE", "de"));
        wordSources.add(new WiktionaryHtmlWordSource("WKFR", "fr"));
        return wordSources;
    }

    @Override
    protected String transform(Element top) {
        final String tagsToRemove =
                "table,sup,noscript,script,img,div[id=page-secondary-actions]," +
                 "a[class*=edit-page], div[class=thumbcaption],h1,"+
                        "h2:has(span[class=mw-headline]), div[class=NavFrame]"
//                        "div[class~=references.*]," +
//                        "div[class=noprint],div[class=floatleft]," +
//                        "div[class=vedlikehold], div[class~=stubb.*]," +
//                        "div[style~=clear: both.*],div[id=catlinks]";
                ;

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

        Elements dls = top.select("dl");
        for (Element dl : dls) {
            dl.tagName("div");
        }

        Elements dds = top.select("dd");
        for (Element dd : dds) {
            Element newDd = new Element(Tag.valueOf("i"), "");
            newDd.append("//");
            newDd.appendText(dd.text());
            dd.replaceWith(newDd);
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

        Elements h3s = top.select("h3");
        for (Element h3 : h3s) {
            Element newH3 = new Element(Tag.valueOf("u"),"");
            Element newI = new Element(Tag.valueOf("i"), "");
            newI.appendText(h3.text());
            newI.appendElement("br");
            newH3.appendChild(newI);
            h3.replaceWith(newH3);
        }

        Elements h4s = top.select("h4");
        for (Element h4 : h4s) {
            Element newH4 = new Element(Tag.valueOf("u"),"");
            newH4.appendText(h4.text());
            newH4.append("&nbsp;");
            h4.replaceWith(newH4);
        }

        String html = top.toString();
        html = html.replaceAll("<div.*?>", "").replace("</div>", "");
//        Log.i( getShortName() + " '" + html + "'");
        return html;
    }
}
