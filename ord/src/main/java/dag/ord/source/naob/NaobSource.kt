package dag.ord.source.naob

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import dag.ord.search.Result
import dag.ord.source.HtmlSource
import dag.ord.util.JsonMapper
import dag.ord.util.Logger
import org.jsoup.nodes.Document

private const val SEARCHURL = "https://www.naob.no/søk/[query]"
private const val PAGEURL = "https://www.naob.no/ordbok/[slug]"

class NaobSource(sourceId: String) : HtmlSource(sourceId) {
    override fun toResults(query: String, document: Document, maxResultLength: Int): List<Result> {
        val jsonElements: List<String> = document
                .getElementsByTag("script")
                .filter { "__NEXT_DATA__" in it.html() }
                .distinct()
                .also { Logger.info("ND1 " + it) }
                .map { it.html().replace("__NEXT_DATA__ = ", "").replace("</script>", "") }
                .also { Logger.info("ND2 " + it) }

        val results = mutableListOf<Result>()
        jsonElements.forEach { jsonElement ->
            val naobJson = JsonMapper().read(jsonElement, object : TypeReference<NaobJson>() {})
            val pageProps = naobJson.props?.pageProps

            if (!pageProps?.validationError.isNullOrEmpty()) {
                results += Result(null, "<i>${pageProps?.validationError}</i>}", 200 )
            } else {
                val articles = pageProps?.searchResult?.articles

                articles?.forEach {
                    val displayUrl = if (it.slug != null) PAGEURL.replace("[slug]", it.slug!!) else null
                    Logger.info("Tekst=${it.text}")
                    val filteredText = it.text
                            ?.replace("<div class=\"shortform\">", "")
                            ?.replace("<span class=\"ordklasse-shortform\">", "<i>")
                            ?.replace("</span>", "</i>")
                            ?.replace("</div>", "")
                    val summary = "<b>${it.headWord?.capitalize()}</b> $filteredText"
                    Logger.info("ND3 " + summary)

                    results += Result(displayUrl, summary, maxResultLength)
                }
            }
        }

        return results
    }

    override fun getLookupUrl(urlEncodedQueryWord: String) = SEARCHURL.replace("[query]", urlEncodedQueryWord)

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class NaobJson {
        var props: Props? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Props {
        var pageProps: PageProps? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class PageProps {
        var validationError: String? = null
        var searchResult: SearchResult? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class SearchResult {
        var articles: List<Article>? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Article {
        @JsonProperty("headword")
        var headWord: String? = null
        var slug: String? = null
        @JsonProperty("shortformL")
        var text: String? = null
    }

    companion object {
        fun createWordSources() = listOf(
                NaobSource("NAOB")
        )
    }

}