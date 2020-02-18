package dag.ord.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import dag.ord.R
import dag.ord.search.SourceResult
import dag.ord.util.YesNoCancel.Companion.EMPTY
import dag.ord.util.YesNoCancel.Companion.show
import dag.ord.source.Source
import dag.ord.source.WordSources
import java.net.MalformedURLException
import java.net.URL

class AllSourceResultsAdapter(context: Context) : ArrayAdapter<Source>(context, R.layout.sourceresult, R.id.wordsource_source, wordSources) {
    private val BG_WD1 = -0x219
    private val BG_WD2 = -0x63c
    private val BG_TOOMANY = -0xcd
    private val BG_NOTFOUND = -0x3334

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = super.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val wordSource = wordSources[position]
        val rowView = inflater.inflate(R.layout.sourceresult, parent, false)
        val sourceView = rowView.findViewById<View>(R.id.wordsource_source) as TextView
        sourceView.setTextColor(Color.BLACK)
        sourceView.text = wordSource.displayName
        val resultView1 = rowView.findViewById<View>(R.id.searchresult1) as TextView
        val resultView2 = rowView.findViewById<View>(R.id.searchresult2) as TextView
        val resultView3 = rowView.findViewById<View>(R.id.searchresult3) as TextView
        val resultView4 = rowView.findViewById<View>(R.id.searchresult4) as TextView
        val resultView5 = rowView.findViewById<View>(R.id.searchresult5) as TextView
        val resultView6 = rowView.findViewById<View>(R.id.searchresult6) as TextView
        val resultView7 = rowView.findViewById<View>(R.id.searchresult7) as TextView
        val searchResult = wordSource.lastSourceResult
        if (searchResult == null) {
            sourceView.setBackgroundColor(Color.LTGRAY)
        } else {
            val resultCount = searchResult.count
            if (resultCount == 0) {
                sourceView.setBackgroundColor(BG_NOTFOUND)
            } else {
                if (resultCount > 7) {
                    sourceView.setBackgroundColor(BG_TOOMANY)
                } else {
                    sourceView.setBackgroundColor(BG_FOUND)
                }
            }
        }
        populateResultView(resultView1, searchResult, 0, BG_WD1)
        populateResultView(resultView2, searchResult, 1, BG_WD2)
        populateResultView(resultView3, searchResult, 2, BG_WD1)
        populateResultView(resultView4, searchResult, 3, BG_WD2)
        populateResultView(resultView5, searchResult, 4, BG_WD1)
        populateResultView(resultView6, searchResult, 5, BG_WD2)
        populateResultView(resultView7, searchResult, 6, BG_WD1)
        return rowView
    }

    private fun populateResultView(resultView: TextView, sourceResult: SourceResult?, resultIndex: Int, backgroundColor: Int) {
        if (sourceResult != null && sourceResult.count > resultIndex) {
            resultView.setBackgroundColor(backgroundColor)
            val wordDescriptor = sourceResult.getWordDescriptor(resultIndex)
            resultView.text = Html.fromHtml(wordDescriptor.summary)
            //            resultView.setText(wordDescriptor.getSummary());
            resultView.setOnClickListener {
                try {
                    URL(wordDescriptor.displayUrl)
                    val uri = Uri.parse(wordDescriptor.displayUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                } catch (e: MalformedURLException) {
                    show(context, "Ugyldig url", "'" + wordDescriptor.displayUrl + "'", EMPTY, null, null)
                }
            }
        } else {
            resultView.visibility = View.GONE
        }
    }

    override fun add(source: Source?) {
        super.add(source)
    }

    fun resetSearchResults() {
        wordSources.resetSearchResults()
        notifyDataSetChanged()
    }

    fun search(searchTerm: String) {
        wordSources.search(searchTerm)
    }

    fun searchCompletedForWordSource() {
        notifyDataSetChanged()
    }

    companion object {
        private const val BG_FOUND = -0x330067
    }

    init {
        wordSources.setAdapter(this)
    }
}