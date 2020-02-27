package dag.ord.source.naob

import dag.ord.search.Result
import dag.ord.source.HtmlSource
import dag.ord.source.wiki.WiktionarySource
import dag.ord.util.Logger
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

private const val BASEURL = "https://www.naob.no/søk/[query]"

class NaobSource(sourceId: String) : HtmlSource(sourceId) {
    override fun toResults(query: String, document: Document, maxResultLength: Int): List<Result> {
        val jsonElements: List<Element> = document
                .getElementsByTag("script")
                .filter { "__NEXT_DATA__" in it.html() }
                .also { Logger.info("ND" + it) }


        return listOf(Result("url", jsonElements[0].html(), 200))
     }

    override fun getLookupUrl(urlEncodedQueryWord: String) = BASEURL.replace("[query]", urlEncodedQueryWord)

    companion object {
        fun createWordSources() = listOf(
                NaobSource("NAOB")
        )
    }

}