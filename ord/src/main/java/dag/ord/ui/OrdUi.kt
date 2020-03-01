package dag.ord.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dag.ord.OrdViewModel
import dag.ord.R
import dag.ord.search.AllSourceResults


object OrdUi {

    fun build(activity: AppCompatActivity, viewModel: OrdViewModel) {
        val sourceResultsView = activity.findViewById<RecyclerView>(R.id.sourceresults)
        sourceResultsView.layoutManager = LinearLayoutManager(activity);
//       sourceResultsView.layoutManager = object: LinearLayoutManager(activity) {
//            override fun canScrollVertically()=false
//        }

        sourceResultsView.setHasFixedSize(true)
        sourceResultsView.addItemDecoration(DividerItemDecoration(sourceResultsView.context, DividerItemDecoration.VERTICAL))

        val adapter = SourceResultAdapter(activity)
        sourceResultsView.adapter = adapter

        viewModel.liveAllSourceResults.observe(activity, Observer { allSourceResults: AllSourceResults ->
            adapter.allSourceResults = allSourceResults
            adapter.notifyDataSetChanged()
        })

        val searchTermView = activity.findViewById<View>(R.id.searchterm) as EditText
        searchTermView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchTermView.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
            if (actionId in listOf(EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_UNSPECIFIED)) {
                val searchTerm = textView.text.toString().trim { it <= ' ' }
                if (!searchTerm.isBlank()) {
                    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS)
                    viewModel.search(searchTerm)
                }
                return@OnEditorActionListener true
            }
            false

        })

    }
}
