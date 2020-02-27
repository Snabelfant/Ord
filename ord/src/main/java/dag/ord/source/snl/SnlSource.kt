package dag.ord.source.snl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import dag.ord.search.Result
import dag.ord.source.Source
import dag.ord.util.JsonMapper
import java.util.*

private const val BASEURL = "https://[subdomene]snl.no/api/v1/search?query=[query]"

class SnlSource private constructor(sourceId: String, private val subDomain: String) : Source(sourceId) {
    override fun getLookupUrl(urlEncodedQueryWord: String): String {
        return BASEURL.replace("[subdomene]", subDomain).replace("[query]", urlEncodedQueryWord)
    }

    override fun toResults(queryWord: String, urlContent: String, maxResultLength: Int) =
            JsonMapper()
                    .read(urlContent, object : TypeReference<List<Article>>() {})
                    .map {
                        val summary = "<b>${it.headword}</b> <i>(${it.taxonomyTitle})</i> ${it.snippet}"
                        Result(it.articleUrl, summary, maxResultLength)
                    }

    companion object {
        fun createWordSources() = listOf(
            SnlSource("SNL", ""),
            SnlSource("NKL", "nkl."),
            SnlSource("NBL", "nbl."),
            SnlSource("SML", "sml."))
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Article {
        @JsonProperty("article_url")
        lateinit var articleUrl: String
        lateinit var headword: String
        lateinit var snippet: String
        @JsonProperty("taxonomy_title")
        lateinit var taxonomyTitle: String
    }
}