package dag.ord.source.wiktionary

import dag.ord.search.Result
import dag.ord.source.HtmlSource
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.*

private const val BASESEARCHURL = "http://[lang].m.[wiki].org/wiki/Special:Search?search=[query]"
// "http://[lang].[wiki].org/w/api.php?format=json&action=query&titles=[query]&prop=revisions&rvprop=content";
private const val BASEDISPLAYURL = "https://[lang].m.[wiki].org/wiki/[query]"

abstract class WikiHtmlSource internal constructor(sourceId: String, private val wiki: String, private val languageCode: String) : HtmlSource(sourceId) {
    private fun getDisplayUrl(query: String)=
        BASEDISPLAYURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncode(query))

    override fun getLookupUrl(urlEncodedQueryWord: String) =
        BASEDISPLAYURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncodedQueryWord)

    override fun toResults(word: String, document: Document, maxResultLength: Int)=
            document
                    .select("div[id=content]")
                    .mapNotNull { transform(it)?.let { it2 -> Result(getDisplayUrl(word), it2, maxResultLength) } }

    protected abstract fun transform(top: Element): String?
}