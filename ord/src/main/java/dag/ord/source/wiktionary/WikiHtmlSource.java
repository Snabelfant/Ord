package dag.ord.source.wiktionary;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import dag.ord.search.Result;
import dag.ord.source.HtmlSource;

/**
 * Created by Dag on 19.08.2014.
 */
public abstract class WikiHtmlSource extends HtmlSource {
    private static final String BASESEARCHURL = "http://[lang].m.[wiki].org/wiki/Special:Search?search=[query]";
    // "http://[lang].[wiki].org/w/api.php?format=json&action=query&titles=[query]&prop=revisions&rvprop=content";
    private static final String BASEDISPLAYURL = "https://[lang].m.[wiki].org/wiki/[query]";
    private String wiki;
    private String languageCode;


    WikiHtmlSource(String shortName, String wiki, String languageCode) {
        super(shortName);
        this.wiki = wiki;
        this.languageCode = languageCode;

    }

    private String getDisplayUrl(String query) {
        return BASEDISPLAYURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncode(query));
    }

    @Override
    protected String getLookupUrl(String urlEncodedQueryWord) {
             return BASEDISPLAYURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncodedQueryWord);
    }

    @Override
    protected List<Result> toWordDescriptors(String word, Document document, int maxWordDescriptorLength) {
        List<Result> Results = new ArrayList<Result>();
        Elements contentElements = document.select("div[id=content]");
        if (contentElements.size() == 0) {
            return Results;
        }
        String transformedHtml = transform(contentElements.get(0));
        if (transformedHtml == null) {
            return Results;
        }
        String displayUrl = getLookupUrl(word);
        Result result = new Result(displayUrl, transformedHtml, maxWordDescriptorLength);
        Results.add(result);
        return Results;
    }

    abstract protected String transform(Element top);


}
