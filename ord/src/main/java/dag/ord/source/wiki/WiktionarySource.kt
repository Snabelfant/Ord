package dag.ord.source.wiki

class WiktionarySource(sourceId: String, languageCode: String) : WikiSource(sourceId, "wiktionary", languageCode, 500) {
    override fun getSummary(page: Page) = "        ----->"

    companion object {
        fun createWordSources() = listOf(
                WiktionarySource("WKNO", "no"),
                WiktionarySource("WKSV", "sv"),
                WiktionarySource("WKDA", "da"),
                WiktionarySource("WKDE", "de"),
                WiktionarySource("WKFR", "fr"),
                WiktionarySource("WKEN", "en"),
                WiktionarySource("WKES", "es")
        )
    }
}