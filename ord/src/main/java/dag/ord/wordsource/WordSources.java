package dag.ord.wordsource;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dag.ord.searchresult.SearchResult;
import dag.ord.wordsource.nob.NoWordSource;
import dag.ord.wordsource.snl.SnlWordSource;
import dag.ord.wordsource.wiktionary.WikipediaHtmlWordSource;
import dag.ord.wordsource.wiktionary.WiktionaryHtmlWordSource;

public class WordSources extends ArrayList<WordSource> {
    private ExecutorService executorService;
    private WordSourceAdapter adapter;

    public WordSources() {
        super();
        createSources();
        this.executorService = Executors.newFixedThreadPool(size());
    }

    public void search(final String searchTerm) {
        for (int i = 0; i < size(); i++) {
            final WordSource wordSource = get(i);
            AsyncTask<WordSource, Integer, SearchResult> task = new AsyncTask<WordSource, Integer, SearchResult>() {

                @Override
                protected SearchResult doInBackground(WordSource... wordSources) {
                    return wordSources[0].search(searchTerm);
                }

                @Override
                protected void onPostExecute(SearchResult result) {
                    sort();
                    adapter.searchCompletedForWordSource();
                }

            };

            task.executeOnExecutor(executorService, wordSource);
        }

    }

    public void resetSearchResults() {
        for (WordSource wordSource : this) {
            wordSource.resetSearchResult();
        }


    }

    public void setAdapter(WordSourceAdapter adapter) {
        this.adapter = adapter;
    }

    private void createSources() {
        addAll(SnlWordSource.createWordSources());
        addAll(NoWordSource.createWordSources());
        addAll(WikipediaHtmlWordSource.createWordSources());
        addAll(WiktionaryHtmlWordSource.createWordSources());

        String[] dictionaryOrder = new String[]{
                "NOBM",
                "NONN",
                "SNL",
                "WPNO",
                "WKNO",
                "WPSV",
                "WPDA",
                "WPEN",
                "WPDE",
                "WPFR",
                "SML",
                "WKSV",
                "WKDA",
                "WKEN",
                "WKDE",
                "WKFR",
                "NKL",
                "NBL"};

        for (int index = 0; index < dictionaryOrder.length; index++) {
            WordSource wordSource = findWordSource(dictionaryOrder[index]);
            wordSource.setOrder(index);
        }

        sort();

        findWordSource("WPNO").setMaxWordDescriptorLength(500);
        findWordSource("WPEN").setMaxWordDescriptorLength(500);
        findWordSource("WPDE").setMaxWordDescriptorLength(500);
        findWordSource("WPFR").setMaxWordDescriptorLength(500);
        findWordSource("WPSV").setMaxWordDescriptorLength(500);
        findWordSource("WPDA").setMaxWordDescriptorLength(500);
        findWordSource("WKEN").setMaxWordDescriptorLength(500);
        findWordSource("WKDE").setMaxWordDescriptorLength(500);
        findWordSource("WKFR").setMaxWordDescriptorLength(500);
    }

    private WordSource findWordSource(String shortName) {
        for (WordSource wordSource : this) {
            if (wordSource.getShortName().equals(shortName)) {
                return wordSource;
            }
        }
        return null;
    }

    void sort() {
        Collections.sort(this, new WordSource.WordSourceComparator());
    }
}