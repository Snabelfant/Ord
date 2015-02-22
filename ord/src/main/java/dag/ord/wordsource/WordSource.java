package dag.ord.wordsource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dag.ord.searchresult.SearchResult;
import dag.ord.searchresult.WordDescriptor;
import dag.ord.util.Log;
import dag.ord.util.UrlReader;

public abstract class WordSource {
    private String shortName;

    public String getDisplayName() {
        return displayName;
    }

    private String displayName;
    private SearchResult lastSearchResult;
    private int order;
    private int maxWordDescriptorLength;

    WordSource(String shortName) {
        this.shortName = shortName;
        displayName = shortName;

        if (shortName.length() > 3) {
            displayName = String.format("%s\n%s", shortName.substring(0,2), shortName.substring(2));
        }
        order = -5;
        maxWordDescriptorLength = -1;
    }

    public void setMaxWordDescriptorLength(int maxWordDescriptorLength) {
        this.maxWordDescriptorLength = maxWordDescriptorLength;
    }

    String getShortName() {
        return shortName;
    }

    public SearchResult getLastSearchResult() {
        return lastSearchResult;
    }

    private boolean hasSearchResult() {
        return lastSearchResult != null && lastSearchResult.isNotEmpty();
    }

    public SearchResult search(String queryWord) {
        long start = System.currentTimeMillis();
        SearchResult searchResult = new SearchResult();
        String lookupUrl = getLookupUrl(urlEncode(queryWord));
        List<WordDescriptor> wordDescriptors = null;

        try {
            String urlContent = UrlReader.read(lookupUrl);
            wordDescriptors = toWordDescriptors(queryWord, urlContent, maxWordDescriptorLength);
        } catch (IOException e) {
            wordDescriptors = new ArrayList<WordDescriptor>(1);
            wordDescriptors.add(new WordDescriptor(null, e.toString(), maxWordDescriptorLength));
        } finally {
            searchResult.add(wordDescriptors);
        }

        searchResult.setLookupTimeInMillis(System.currentTimeMillis() - start);
        lastSearchResult = searchResult;
        return searchResult;
    }

    protected abstract List<WordDescriptor> toWordDescriptors(String queryWord, String urlContent, int maxWordDescriptorLength);

    protected abstract String getLookupUrl(String urlEncodedQueryWord);

    protected String urlEncode(String queryWord) {
        try {
            return URLEncoder.encode(queryWord, "UTF-8"); // StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "UnsupportedEncodingException " + e.getMessage();
        }
    }

    public void resetSearchResult() {
        lastSearchResult = null;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static class WordSourceComparator implements Comparator<WordSource> {
        @Override
        public int compare(WordSource wordSource1, WordSource wordSource2) {
            boolean hasSearchResult1 = wordSource1.hasSearchResult();
            boolean hasSearchResult2 = wordSource2.hasSearchResult();

            if (hasSearchResult1 && !hasSearchResult2) {
                return -1;
            }

            if (!hasSearchResult1 && hasSearchResult2) {
                return 1;
            }

            if (wordSource1.order < wordSource2.order) {
                return -1;
            }

            if (wordSource1.order > wordSource2.order) {
                return 1;
            }

            return 0;
        }
    }
}
