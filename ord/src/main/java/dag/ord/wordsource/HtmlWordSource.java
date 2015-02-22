package dag.ord.wordsource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import dag.ord.searchresult.WordDescriptor;

public abstract class HtmlWordSource extends WordSource {
    protected HtmlWordSource(String shortName) {
        super(shortName);
    }


    @Override
    protected List<WordDescriptor> toWordDescriptors(String queryWord, String htmlAsString, int maxWordDescriptorLength) {
        Document htmlDocument = Jsoup.parse(htmlAsString);
        return toWordDescriptors(queryWord, htmlDocument, maxWordDescriptorLength);
    }

    protected abstract List<WordDescriptor> toWordDescriptors(String word, Document document, int maxWordDescriptorLength);

 }
