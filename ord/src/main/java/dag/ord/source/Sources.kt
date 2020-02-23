package dag.ord.source

import dag.ord.source.nob.NobSource
import dag.ord.source.snl.SnlSource
import dag.ord.source.wiktionary.WikipediaHtmlSource
import dag.ord.source.wiktionary.WiktionaryHtmlSource

object Sources {
    private val _sources = mutableListOf<Source>()

    val sources
        get() = _sources.toList()

    val size
        get() = _sources.size

    init {
        _sources += NobSource.createWordSources()
        _sources += SnlSource.createWordSources()
        _sources += WikipediaHtmlSource.createWordSources()
        _sources += WiktionaryHtmlSource.createWordSources()
    }
}