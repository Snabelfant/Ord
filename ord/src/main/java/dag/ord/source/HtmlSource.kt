package dag.ord.source

import dag.ord.search.Result
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class HtmlSource protected constructor(shortName: String) : Source(shortName) {
    override fun toResults(queryWord: String, urlContent: String, maxWordDescriptorLength: Int): List<Result> {
        val htmlDocument = Jsoup.parse(urlContent)
        return toWordDescriptors(queryWord, htmlDocument, maxWordDescriptorLength)
    }

    protected abstract fun toWordDescriptors(word: String, document: Document, maxWordDescriptorLength: Int): List<Result>
}