package dag.ord.source

import dag.ord.search.Result
import dag.ord.util.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class HtmlSource protected constructor(sourceId: String) : Source(sourceId) {
    override fun toResults(query: String, urlContent: String, maxResultLength: Int): List<Result> {
        val htmlDocument = Jsoup.parse(urlContent)
        return toResults(query, htmlDocument, maxResultLength)
    }

    protected abstract fun toResults(query: String, document: Document, maxResultLength: Int): List<Result>
}