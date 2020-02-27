package dag.ord.source

import dag.ord.source.naob.NaobSource
import dag.ord.source.nob.NobSource
import dag.ord.source.snl.SnlSource
import dag.ord.source.wiki.WikipediaSource
import dag.ord.source.wiki.WiktionarySource

object Sources {
    private val _sources = mutableListOf<Source>()

    val sources
        get() = _sources.toList()

    val size
        get() = _sources.size

    init {
//        _sources += NobSource.createWordSources()
//        _sources += SnlSource.createWordSources()
//        _sources += WikipediaSource.createWordSources()
//        _sources += WiktionarySource.createWordSources()
        _sources += NaobSource.createWordSources()
    }
}