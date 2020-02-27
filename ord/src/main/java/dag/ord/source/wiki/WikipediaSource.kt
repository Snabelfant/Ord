package dag.ord.source.wiki

class WikipediaSource(sourceId: String, languageCode: String) : WikiSource(sourceId, "wikipedia", languageCode, 500) {
    override fun getSummary(page: WikiSource.Page) = "<b>${page.title}</b> ${page.extract}"

    companion object {
        fun createWordSources() = listOf(
                WikipediaSource("WPNO", "no"),
                WikipediaSource("WPSV", "sv"),
                WikipediaSource("WPDA", "da"),
                WikipediaSource("WPDE", "de"),
                WikipediaSource("WPFR", "fr"),
                WikipediaSource("WPEN", "en"),
                WikipediaSource("WPES", "es")
        )
    }
}