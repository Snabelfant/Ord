package dag.ord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dag.ord.search.AllSourceResults
import dag.ord.search.SearchEngine

class OrdViewModel
constructor(application: Application) : AndroidViewModel(application) {
    val liveAllSourceResults: LiveData<AllSourceResults>

    init {
        liveAllSourceResults = SearchEngine.liveAllSourceResults
    }

    fun search(searchTerm: String) {
        SearchEngine.search(searchTerm)
    }
}
