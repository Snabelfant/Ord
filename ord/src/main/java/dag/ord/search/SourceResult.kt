package dag.ord.search

class SourceResult(val shortName: String, private val _results : List<Result> ) {
    val results
        get() = _results.toList()

    val isNotEmpty: Boolean
        get() = _results.isNotEmpty()

    val count: Int
        get() = _results.size
}