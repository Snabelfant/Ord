package dag.ord.search

import dag.ord.source.Source
import dag.ord.util.Logger

class AllSourceResults {
    private val allSourceResults = mutableListOf<SourceResult>()
    val sourceResults
        get() = allSourceResults
                .sortedWith(
                        Comparator { s1, s2 ->
                            when {
                                s1.isEmpty && s2.isEmpty -> compareOrder(s1,s2)
                                s1.isEmpty -> 1
                                s2.isEmpty -> -1
                                else -> compareOrder(s1,s2)
                            }
                        })

    operator fun plusAssign(sourceResult: SourceResult) {
        allSourceResults += sourceResult
    }

    override fun toString() = (0 until size).map { sourceResults[it].toString() }.toString()

    val size: Int
        get() = sourceResults.size

    private fun compareOrder(s1 : SourceResult?, s2: SourceResult?):Int  {
        checkNotNull(s1)
        checkNotNull(s1.sourceId)
        checkNotNull(order[s1.sourceId])
        checkNotNull(s2)
        checkNotNull(s2.sourceId)
        checkNotNull(order[s2.sourceId])
        return order[s1.sourceId]!!.compareTo(order[s2.sourceId]!!)
    }

    companion object {
        private val order = mapOf(
                "NAOB" to 0,
                "NOBBM" to 4,
                "NOBNN" to 5,
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
                "WKES" to 65,
                "WPNO" to 70,
                "WPSV" to 71,
                "WPDA" to 72,
                "WPDE" to 73,
                "WPFR" to 74,
                "WPEN" to 75,
                "WPES" to 76
        )
    }
}