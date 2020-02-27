package dag.ord.source.wiki

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import dag.ord.search.Result
import dag.ord.source.Source
import dag.ord.util.JsonMapper
import dag.ord.util.UrlReader

private const val SEARCHURL = "https://[lang].[wiki].org/w/api.php?action=query&prop=extracts&titles=[query]&exintro=&exsentences=5&explaintext=&redirects=&format=json&formatversion=2"
private const val FINDPAGEURL = "https://[lang].[wiki].org/w/api.php?action=query&prop=info&pageids=[pageid]&inprop=url&formatversion=2&format=json"

abstract class WikiSource(sourceId: String, val wiki : String, val languageCode: String, maxResultLen: Int) : Source(sourceId, maxResultLen) {
    private val mapper = JsonMapper()
    override fun toResults(query: String, urlContent: String, maxResultLength: Int) =
            mapper
                    .read(urlContent, object : TypeReference<SearchResult>() {})
                    .query
                    .pages
                    .mapNotNull {
                        if (it.missing != true) {
                            Result(getPageUrl(it.pageId), getSummary(it), maxResultLength)
                        } else {
                            null
                        }
                    }

    protected abstract fun getSummary(page: Page) : String

    private fun getPageUrl(pageId: Int?): String? {
        val findPageUrl = FINDPAGEURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[pageid]", pageId.toString())
        val urlContent = UrlReader.read(findPageUrl) ?: return null
        val searchResult = mapper.read(urlContent, object : TypeReference<SearchResult>() {})

        return searchResult.query.pages[0].canonicalUrl
    }

    override fun getLookupUrl(urlEncodedQueryWord: String) =
            SEARCHURL.replace("[lang]", languageCode).replace("[wiki]", wiki).replace("[query]", urlEncodedQueryWord)

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected class SearchResult {
        lateinit var query: Query
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected class Query {
        lateinit var pages: List<Page>
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected class Page {
        @JsonProperty("pageid")
        var pageId: Int? = null
        var title: String? = null
        var extract: String? = null
        var missing: Boolean? = null
        @JsonProperty("canonicalurl")
        var canonicalUrl: String? = null
    }

}