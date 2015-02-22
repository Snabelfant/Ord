package dag.ord;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import dag.ord.util.YesNoCancel;
import dag.ord.wordsource.WordSourceAdapter;
import dag.ord.wordsource.WordSources;
import dag.ord.wordsource.wiktionary.WikipediaHtmlWordSource;


public class Dictionary extends Activity {
    private WordSourceAdapter wordSourceAdapter;

    public static void main(String... args) {
        new WikipediaHtmlWordSource("TEST", "no").search("elg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        try {
            ListView wordSourcesView = (ListView) findViewById(R.id.wordsources);

            WordSources wordSources = new WordSources();

            wordSourceAdapter = new WordSourceAdapter(this, wordSources);

            wordSourcesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    YesNoCancel.show(Dictionary.this, null, "OnItemLongClickListener", YesNoCancel.EMPTY, null, YesNoCancel.EMPTY);
                    return true;
                }
            });

            wordSourcesView.setAdapter(wordSourceAdapter);

            EditText searchTermView = (EditText) findViewById(R.id.searchterm);
            searchTermView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

            searchTermView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String searchTerm = textView.getText().toString().trim();

                        if (!"".equals(searchTerm)) {
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            wordSourceAdapter.resetSearchResults();
                            wordSourceAdapter.search(searchTerm);
                        }

                        return true;
                    }

                    return false;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            YesNoCancel.show(Dictionary.this, null, "E: " + e.getClass().getName() + "=" + e.getMessage(), YesNoCancel.EMPTY, null, YesNoCancel.EMPTY);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
