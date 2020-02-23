package dag.ord.source.nob

import org.jsoup.nodes.Element

private const val BASEURL = "https://ordbok.uib.no/perl/ordbok.cgi?startpos=1&antall_vise=20&OPP=+[Q]&nynorsk=+&ordbok=nynorsk"

class NobNnSource : NobSource("NOBNN", "byttutNN") {

    override fun removeUnwantedTags(artikkel: Element) {
        artikkel.select("span[class=oppsgramordklassevindu]").remove()
        artikkel.select("span[class=doeme kompakt]").remove()
        artikkel.select("span[class=tydingC kompakt]").remove()
        artikkel.select("span[class=doemeliste kompakt]").remove()
        artikkel.select("div[class=utvidet]").remove()
    }

    override fun tagToBeProcessed(tagName: String?) = "style" != tagName && "img" != tagName

    override fun findMarkup(element: Element): String? {
        val tag = element.tagName()
        if ("span" == tag) {
            var attr = element.attr("class")
            if (attr != null && "" != attr) {
                return findSpanClassMarkup(attr)
            }
            attr = element.attr("style")
            if (attr != null && "" != attr) {
                return findSpanStyleMarkup(attr)
            }
            return if (element.attributes().size() == 0) {
                findSpanNoAttribs(element)
            } else "[span a=$element]"
        }
        if ("div" == tag) {
            val attr = element.attr("class")
            return if (attr != null && "" != attr) {
                findDivClassMarkup(attr)
            } else "[div a=$element]"
        }
        return "[T=$tag]"
    }

    private fun findDivClassMarkup(attr: String) =
            when (attr) {
                "doemeliste utvidet",
                "artikkelinnhold",
                "tyding utvidet" -> null
                else -> "[divclass=$attr]"
            }

    private fun findSpanNoAttribs(element: Element?): String? {
        return if (element!!.children().size > 0) {
            val c0 = element.child(0)
            if ("style" == c0.tagName()) {
                val text = c0.data()
                if ("font-weight: bold" == text) {
                    "b"
                } else "!sna txt=$text"
            } else {
                "!sna tag=" + c0.tagName()
            }
        } else {
            null
        }
    }

    private fun findSpanStyleMarkup(attr: String) =
            when {
                attr == "font-style: italic" -> "i"
                attr == "font-style: normal" -> null
                attr == "font-weight: normal" -> null
                attr.startsWith("font-family: Times New Roman") -> "b"
                else -> "[spanstyle=$attr]"
            }

    private fun findSpanClassMarkup(attr: String) =
            when (attr) {
                "oppslagsord b" -> "b"
                "oppsgramordklasse" -> "i"
                "etymtilvising" -> "i"
                "utvidet" -> null
                "doeme utvidet" -> null
                "kompakt" -> null
                "henvisning" -> "u"
                "tilvising" -> "i"
                "artikkeloppslagsord" -> "b"
                else -> "[spanclass=$attr]"
            }

    override fun getLookupUrl(urlEncodedQueryWord: String) = BASEURL.replace("[Q]", urlEncodedQueryWord, false)
}