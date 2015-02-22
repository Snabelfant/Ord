package dag.ord.wordsource.nob;

import dag.ord.searchresult.WordDescriptor;
import dag.ord.util.Log;
import dag.ord.wordsource.HtmlWordSource;
import dag.ord.wordsource.WordSource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dag on 14.08.2014.
 */
abstract public class NoWordSource extends HtmlWordSource {

    private final String htmlTableId;

    NoWordSource(String shortName, String htmlTableId) {
        super(shortName);
        this.htmlTableId = htmlTableId;
    }

    @Override
    protected List<WordDescriptor> toWordDescriptors(String word, Document document, int maxWordDescriptorLength) {
        List<WordDescriptor> wordDescriptors = new ArrayList<WordDescriptor>();
        String displayUrl = getLookupUrl(word);

        Elements tables = document.select("table[id=" + htmlTableId + "]");
        if (tables.size() > 0) {
            Elements artikler = document.select("div[class=artikkelinnhold]");
            for (Element artikkel : artikler) {
                removeUnwantedTags(artikkel);
                StringBuilder artikkelHtmlSb = new StringBuilder();
                traverse(artikkel, artikkelHtmlSb);
                String artikkelHtml = doReplacements(artikkelHtmlSb);
//                Log.i("'" + artikkelHtml + "'");
                WordDescriptor wordDescriptor = new WordDescriptor(displayUrl, artikkelHtml,maxWordDescriptorLength);
                wordDescriptors.add(wordDescriptor);
            }
        }

        return wordDescriptors;
    }

    protected abstract void removeUnwantedTags(Element artikkel);

    private String doReplacements(StringBuilder artikkelHtml) {
        return artikkelHtml.toString().replaceAll("([^ ])<i>", "$1 <i>");
    }


    private void traverse(Node node, StringBuilder sb) {
        if (node instanceof Element) {
            for (Node child : node.childNodes()) {
                if (child instanceof TextNode) {
                    sb.append(((TextNode) child).text());
                } else {
                    if (child instanceof Element) {
                        Element e = (Element) child;
                        if (tagToBeProcessed(e.tagName())) {
                            String tag = findMarkup(e);

                            if (tag != null) {
                                sb.append("<").append(tag).append(">");
                            }
                            traverse(child, sb);
                            if (tag != null) {
                                sb.append("</").append(tag).append(">");
                            }
                        }
                    }
                }
            }
        } else {
            sb.append("<b>XXX</b>");
        }
    }

    protected abstract String findMarkup(Element element);

    protected abstract boolean tagToBeProcessed(String tagName);

    public static List<WordSource> createWordSources() {
        List<WordSource> wordSources = new ArrayList<WordSource>();

        wordSources.add(new NoBmWordSource());
        wordSources.add(new NoNnWordSource());
        return wordSources;
    }

}
