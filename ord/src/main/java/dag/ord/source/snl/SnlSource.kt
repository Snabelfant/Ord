package dag.ord.source.snl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import dag.ord.search.Result
import dag.ord.source.Source
import dag.ord.util.JsonMapper
import java.util.*

private const val BASEURL = "https://[subdomene]snl.no/api/v1/search?query=[query]"

class SnlSource private constructor(sourceId: String, private val subDomain: SubDomain) : Source(sourceId) {
    override fun getLookupUrl(urlEncodedQueryWord: String): String {
        return BASEURL.replace("[subdomene]", subDomain.subDomain).replace("[query]", urlEncodedQueryWord)
    }

    override fun toResults(queryWord: String, urlContent: String, maxResultLength: Int) =
            JsonMapper()
                    .read(urlContent, object : TypeReference<List<Article>>() {})
                    .map {
                        val summary = "<b>${it.headword}</b> <i>(${it.taxonomyTitle})</i> ${it.snippet}"
                        Result(it.articleUrl, summary, maxResultLength)
                    }

    enum class SubDomain(val subDomain: String) {
        SNL(""), NKL("nkl."), NBL("nbl."), SML("sml.")
    }

    companion object {
        fun createWordSources() = listOf(
            SnlSource("SNL", SubDomain.SNL),
            SnlSource("NKL", SubDomain.NKL),
            SnlSource("NBL", SubDomain.NBL),
            SnlSource("SML", SubDomain.SML))
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