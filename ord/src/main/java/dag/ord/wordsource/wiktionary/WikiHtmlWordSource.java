package dag.ord.wordsource.wiktionary;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import dag.ord.searchresult.WordDescriptor;
import dag.ord.util.Log;
import dag.ord.wordsource.HtmlWordSource;

/**
 * Created by Dag on 19.08.2014.
 */
public abstract class WikiHtmlWordSource extends HtmlWordSource {
    private static final String BASESEARCHURL = "http://[lang].m.[wiki].org/wiki/Special:Search?search=[query]";
    // "http://[lang].[wiki].org/w/api.php?format=json&action=query&titles=[query]&prop=revisions&rvprop=content";
    private static final String BASEDISPLAYURL = "http://[lang].m.[wiki].org/wiki/[query]";
    private String wiki;
    private String languageCode;


    WikiHtmlWordSource(String shortName, String wiki, String languageCode) {
        super(shortName);
        this.wiki = wiki;
        this.languageCode = languageCode;

    }

    private String getDisplayUrl(String query) {
        return BASEDISPLAYURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncode(query));
    }

    @Override
    protected String getLookupUrl(String urlEncodedQueryWord) {
        return BASESEARCHURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncodedQueryWord);
    }


    @Override
    protected List<WordDescriptor> toWordDescriptors(String word, Document document, int maxWordDescriptorLength) {
        List<WordDescriptor> wordDescriptors = new ArrayList<WordDescriptor>();
        Elements contentElements = document.select("div[id=content");

        if (contentElements.size() == 0) {
            return wordDescriptors;
        }
        String transformedHtml = transform(contentElements.get(0));
        if (transformedHtml == null) {
            return wordDescriptors;
        }
        String displayUrl = getLookupUrl(word);
        WordDescriptor wordDescriptor = new WordDescriptor(displayUrl, transformedHtml, maxWordDescriptorLength);
        wordDescriptors.add(wordDescriptor);
        return wordDescriptors;
    }

    abstract protected String transform(Element top);


}
