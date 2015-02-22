package dag.ord.wordsource;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

import dag.ord.R;
import dag.ord.searchresult.SearchResult;
import dag.ord.searchresult.WordDescriptor;
import dag.ord.util.YesNoCancel;

public class WordSourceAdapter extends ArrayAdapter<WordSource> {
    private static final int BG_FOUND = 0xFFCCFF99;
    private final int BG_WD1 = 0xfffffde7;
    private final int BG_WD2 = 0xfffff9c4;
    private final int BG_TOOMANY = 0xFFFFFF33;
    private final int BG_NOTFOUND = 0xFFFFCCCC;

    private WordSources wordSources;
    private Context context;

    public WordSourceAdapter(Context context, WordSources wordSources) {
        super(context, R.layout.wordsource, R.id.wordsource_source, wordSources);
        this.wordSources = wordSources;
        wordSources.setAdapter(this);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) super.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WordSource wordSource = wordSources.get(position);

        View rowView = inflater.inflate(R.layout.wordsource, parent, false);

        TextView sourceView = (TextView) rowView.findViewById(R.id.wordsource_source);
        sourceView.setTextColor(Color.BLACK);
        sourceView.setText(wordSource.getDisplayName());

        TextView resultView1 = (TextView) rowView.findViewById(R.id.searchresult1);
        TextView resultView2 = (TextView) rowView.findViewById(R.id.searchresult2);
        TextView resultView3 = (TextView) rowView.findViewById(R.id.searchresult3);
        TextView resultView4 = (TextView) rowView.findViewById(R.id.searchresult4);
        TextView resultView5 = (TextView) rowView.findViewById(R.id.searchresult5);
        TextView resultView6 = (TextView) rowView.findViewById(R.id.searchresult6);
        TextView resultView7 = (TextView) rowView.findViewById(R.id.searchresult7);

        SearchResult searchResult = wordSource.getLastSearchResult();

        if (searchResult == null) {
            sourceView.setBackgroundColor(Color.LTGRAY);
        } else {
            int resultCount = searchResult.getResultCount();

            if (resultCount == 0) {
                sourceView.setBackgroundColor(BG_NOTFOUND);
            } else {
                if (resultCount > 7) {
                    sourceView.setBackgroundColor(BG_TOOMANY);
                } else {
                    sourceView.setBackgroundColor(BG_FOUND);
                }
            }
        }

        populateResultView(resultView1, searchResult, 0, BG_WD1);
        populateResultView(resultView2, searchResult, 1, BG_WD2);
        populateResultView(resultView3, searchResult, 2, BG_WD1);
        populateResultView(resultView4, searchResult, 3, BG_WD2);
        populateResultView(resultView5, searchResult, 4, BG_WD1);
        populateResultView(resultView6, searchResult, 5, BG_WD2);
        populateResultView(resultView7, searchResult, 6, BG_WD1);

        return rowView;
    }

    private void populateResultView(final TextView resultView, SearchResult searchResult, int resultIndex, int backgroundColor) {
        if (searchResult != null && searchResult.getResultCount() > resultIndex) {
            resultView.setBackgroundColor(backgroundColor);
            final WordDescriptor wordDescriptor = searchResult.getWordDescriptor(resultIndex);

            resultView.setText(Html.fromHtml(wordDescriptor.getSummary()));
//            resultView.setText(wordDescriptor.getSummary());
            resultView.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new URL(wordDescriptor.getDisplayUrl());
                        Uri uri = Uri.parse(wordDescriptor.getDisplayUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    } catch (MalformedURLException e) {
                        YesNoCancel.show(context, "Ugyldig url", "'" + wordDescriptor.getDisplayUrl() + "'", YesNoCancel.EMPTY, null, null);
                    }
                }
            });

        } else {
            resultView.setVisibility(View.GONE);
        }
    }

    @Override
    public void add(WordSource wordSource) {
        super.add(wordSource);
    }

    public void resetSearchResults() {
        wordSources.resetSearchResults();
        notifyDataSetChanged();
    }

    public void search(String searchTerm) {
        wordSources.search(searchTerm);
    }

    public void searchCompletedForWordSource() {
        notifyDataSetChanged();
    }
}

