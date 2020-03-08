package dag.ord.source

import dag.ord.search.Result
import dag.ord.search.SourceResult
import dag.ord.util.Logger
import dag.ord.util.UrlReader.read
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

abstract class Source(val sourceId: String, private val maxResultLength: Int = 500) {
    fun search(queryWord: String): SourceResult {
        val results =
                try {
                    val lookupUrl = getLookupUrl(urlEncode(queryWord))
                    val urlContent = read(lookupUrl).orEmpty()
                    toResults(queryWord, urlContent, maxResultLength)
                } catch (e: Exception) {
                    listOf(Result(null, e.toString(), maxResultLength))
                }

        return SourceResult(sourceId, results)
    }

    abstract fun toResults(query: String, urlContent: String, maxResultLength: Int): List<Result>

    protected abstract fun getLookupUrl(urlEncodedQueryWord: String): String

    protected fun urlEncode(queryWord: String) : String =
            try {
                URLEncoder.encode(queryWord, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                "UnsupportedEncodingException ${e.message}"
            }
}