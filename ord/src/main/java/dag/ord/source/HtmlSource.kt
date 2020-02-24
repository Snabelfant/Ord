package dag.ord.source

import dag.ord.search.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class HtmlSource protected constructor(sourceId: String) : Source(sourceId) {
    override fun toResults(queryWord: String, urlContent: String, maxResultLength: Int): List<Result> {
        val htmlDocument = Jsoup.parse(urlContent)
        return toResults(queryWord, htmlDocument, maxResultLength)
    }

    protected abstract fun toResults(word: String, document: Document, maxResultLength: Int): List<Result>
}