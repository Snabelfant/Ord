package dag.ord.search

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import dag.ord.source.Source
import dag.ord.source.Sources
import dag.ord.util.Logger
import java.util.concurrent.Executors

object SearchEngine {
    val liveAllSourceResults = MutableLiveData<AllSourceResults>()

    fun search(searchTerm: String) {
        val executorService = Executors.newFixedThreadPool(Sources.size)
        val allSourceResults = AllSourceResults()

        Sources.sources.forEach {
            SearchTask(allSourceResults, searchTerm).executeOnExecutor(executorService, it)
        }
    }

    private class SearchTask(private val allSourceResults : AllSourceResults, private val searchTerm: String) : AsyncTask<Source, Int, SourceResult>() {
        override fun doInBackground(vararg sources: Source) = sources.first().search(searchTerm)
        override fun onPostExecute(sourceResult: SourceResult) {
            allSourceResults += sourceResult
            liveAllSourceResults.postValue(allSourceResults)
        }
    }

}