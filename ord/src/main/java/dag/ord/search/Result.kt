package dag.ord.search

class Result(val displayUrl: String?, val unabbreviatedSummary: String, maxWordDescriptorLength: Int) {
    val summary: String =
            if (maxWordDescriptorLength > 0 && unabbreviatedSummary.length > maxWordDescriptorLength)
                unabbreviatedSummary.substring(0, maxWordDescriptorLength) + "..."
            else
                unabbreviatedSummary

    override fun toString(): String {
        return "$summary, $displayUrl"
    }

}