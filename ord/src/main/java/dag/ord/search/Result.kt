package dag.ord.search

class Result(val displayUrl: String?, val unabbreviatedSummary: String, maxResultLength: Int) {
    val summary: String =
            if (maxResultLength > 0 && unabbreviatedSummary.length > maxResultLength)
                unabbreviatedSummary.substring(0, maxResultLength) + "..."
            else
                unabbreviatedSummary

    override fun toString(): String {
        return "$summary, $displayUrl"
    }

}