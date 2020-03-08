package dag.ord.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import dag.ord.R
import dag.ord.WebPageActivity
import dag.ord.search.AllSourceResults
import dag.ord.search.SourceResult
import dag.ord.util.Logger
import dag.ord.util.YesNoCancel.Companion.EMPTY
import dag.ord.util.YesNoCancel.Companion.show
import java.net.MalformedURLException

class SourceResultAdapter(private val context: Context) : RecyclerView.Adapter<SourceResultAdapter.MyViewHolder>() {
    var allSourceResults = AllSourceResults()
    private val BG_WD1 = -0x219
    private val BG_WD2 = -0x63c
    private val BG_TOOMANY = -0xcd
    private val BG_NOTFOUND = -0x3334
    private val BG_FOUND = Color.WHITE

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceIdView: TextView = itemView.findViewById(R.id.sourceid)
        val sourceResultsView: LinearLayout = itemView.findViewById(R.id.sourceresults)
    }

    private fun populateResultView(resultView: TextView, sourceResult: SourceResult, resultIndex: Int, backgroundColor: Int) =
            if (sourceResult.size > resultIndex) {
                resultView.visibility = View.VISIBLE
                resultView.setBackgroundColor(backgroundColor)
                val result = sourceResult.results[resultIndex]
                resultView.text = Html.fromHtml(result.summary, Html.FROM_HTML_MODE_LEGACY)
                resultView.setOnClickListener {
                    try {
                        context.startActivity(Intent(context, WebPageActivity::class.java).putExtra("url", result.displayUrl))
                    } catch (e: MalformedURLException) {
                        show(context, "Ugyldig url", "'" + result.displayUrl + "'", EMPTY, null, null)
                    }
                }
            } else {
                resultView.text=null
                resultView.visibility = View.GONE
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.sourceresult, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, listPosition: Int) {
        val sourceResult = allSourceResults.sourceResults[listPosition]
        holder.sourceIdView.text = sourceResult.displayName

        val resultsLayoutManager = holder.sourceResultsView
        val resultView1 = resultsLayoutManager.findViewById<View>(R.id.searchresult1) as TextView
        val resultView2 = resultsLayoutManager.findViewById<View>(R.id.searchresult2) as TextView
        val resultView3 = resultsLayoutManager.findViewById<View>(R.id.searchresult3) as TextView
        val resultView4 = resultsLayoutManager.findViewById<View>(R.id.searchresult4) as TextView
        val resultView5 = resultsLayoutManager.findViewById<View>(R.id.searchresult5) as TextView
        val resultView6 = resultsLayoutManager.findViewById<View>(R.id.searchresult6) as TextView
        val resultView7 = resultsLayoutManager.findViewById<View>(R.id.searchresult7) as TextView
        val resultView8 = resultsLayoutManager.findViewById<View>(R.id.searchresult8) as TextView
        val resultView9 = resultsLayoutManager.findViewById<View>(R.id.searchresult9) as TextView
        val resultView10 = resultsLayoutManager.findViewById<View>(R.id.searchresult10) as TextView
        val resultView11 = resultsLayoutManager.findViewById<View>(R.id.searchresult11) as TextView
        val resultView12 = resultsLayoutManager.findViewById<View>(R.id.searchresult12) as TextView
        if (sourceResult.isEmpty) {
            holder.sourceIdView.setBackgroundColor(Color.LTGRAY)
        } else {
            val resultCount = sourceResult.size
            if (resultCount == 0) {
                holder.sourceIdView.setBackgroundColor(BG_NOTFOUND)
            } else {
                if (resultCount > 12) {
                    holder.sourceIdView.setBackgroundColor(BG_TOOMANY)
                } else {
                    holder.sourceIdView.setBackgroundColor(BG_FOUND)
                }
            }
        }
        populateResultView(resultView1, sourceResult, 0, BG_WD1)
        populateResultView(resultView2, sourceResult, 1, BG_WD2)
        populateResultView(resultView3, sourceResult, 2, BG_WD1)
        populateResultView(resultView4, sourceResult, 3, BG_WD2)
        populateResultView(resultView5, sourceResult, 4, BG_WD1)
        populateResultView(resultView6, sourceResult, 5, BG_WD2)
        populateResultView(resultView7, sourceResult, 6, BG_WD1)
        populateResultView(resultView8, sourceResult, 7, BG_WD2)
        populateResultView(resultView9, sourceResult, 8, BG_WD1)
        populateResultView(resultView10, sourceResult, 9, BG_WD2)
        populateResultView(resultView11, sourceResult, 10, BG_WD1)
        populateResultView(resultView12, sourceResult, 11, BG_WD2)
    }

    override fun getItemCount() = allSourceResults.size
}