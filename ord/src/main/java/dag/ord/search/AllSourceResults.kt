package dag.ord.search

class AllSourceResults {
    private val allSourceResults = mutableListOf<SourceResult>()
    val sourceResults
        get() = allSourceResults.toList()

    operator fun plusAssign(sourceResult: SourceResult) {
        allSourceResults += sourceResult
    }
}