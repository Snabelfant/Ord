package dag.ord.search

class SourceResult(val sourceId: String, private val _results : List<Result> ) {
    val results
        get() = _results.toList()

    val isEmpty
        get() = _results.isEmpty()

    val size
        get() = _results.size

    val displayName
        get() = when (sourceId.length) {
            5 -> "${sourceId.substring(0, 3)}\n${sourceId.substring(3)}"
            4 -> "${sourceId.substring(0, 2)}\n${sourceId.substring(2)}"
            else -> sourceId
        }

    override fun toString(): String {
        return "_results= ${_results.map { it.summary.substring(0..10) }}"
    }
}