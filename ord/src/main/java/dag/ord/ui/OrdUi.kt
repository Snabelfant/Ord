package dag.ord.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dag.ord.OrdViewModel
import dag.ord.R
import dag.ord.search.AllSourceResults
import dag.podkast.R
import dag.podkast.model.ChannelOverview
import dag.podkast.viewmodel.ChannelOverviewViewModel

object OrdUi {

    fun build(activity: AppCompatActivity, viewModel: OrdViewModel) {
        val channelOverviewView = activity.findViewById<ListView>(R.id.channeloverviewactivity_channels)
        val adapter = ChannelOverviewAdapter(activity, CheckboxChangeListener(viewModel), viewModel)
        channelOverviewView.adapter = adapter

        viewModel.liveAllSourceResults.observe(activity, Observer { allSourceResults: AllSourceResults ->
            adapter.allSourceResults = allSourceResults
            adapter.notifyDataSetChanged()
        })

        val searchTermView = activity.findViewById<View>(R.id.searchterm) as EditText
        searchTermView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchTermView.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchTerm = textView.text.toString().trim { it <= ' ' }
                if (!searchTerm.isBlank()) {
                    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS)
                    adapter.search(searchTerm)
                }
                return@OnEditorActionListener true
            }
            false

        })

    }
}
