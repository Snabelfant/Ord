package dag.ord.search

class AllSourceResults {
    private val allSourceResults = mutableListOf<SourceResult>()
    val sourceResults
        get() = allSourceResults.sortedWith(
                Comparator { s1, s2 ->
                    when {
                        s1.isEmpty && s2.isEmpty -> order[s1.sourceId]!!.compareTo(order[s2.sourceId]!!)
                        s1.isEmpty -> 1
                        s2.isEmpty -> -1
                        else -> order[s1.sourceId]!!.compareTo(order[s2.sourceId]!!)
                    }
                })

    operator fun plusAssign(sourceResult: SourceResult) {
        allSourceResults += sourceResult
    }

    override fun toString() = (0 until size).map { sourceResults[it].toString() }.toString()

    val size: Int
        get() = sourceResults.size

    companion object {
        private val order = mutableMapOf(
                "NOBBM" to 0,
                "NOBNN" to 1,
                "SNL" to 10,
                "SML" to 11,
                "NBL" to 100,
                "NKL" to 101,
                "WKNO" to 60,
                "WKSV" to 61,
                "WKDA" to 62,
                "WKDE" to 63,
                "WKFR" to 64,
                "WKEN" to 65,
                "WPNO" to 70,
                "WPSV" to 71,
                "WPDA" to 72,
                "WPDE" to 73,
                "WPFR" to 74,
                "WPEN" to 75
        )
    }
}