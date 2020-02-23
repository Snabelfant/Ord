package dag.ord.source.nob

import org.jsoup.nodes.Element

class NobBmSource() : NobSource("NOBBM", "byttutBM") {
    override fun removeUnwantedTags(artikkel: Element) {
        artikkel.select("span[class=oppsgramordklassevindu]").remove()
        artikkel.select("span[class=doeme kompakt]").remove()
        artikkel.select("span[class=tydingC kompakt]").remove()
        artikkel.select("span[class=doemeliste kompakt]").remove()
    }

    override fun tagToBeProcessed(tagName: String?): Boolean {
        return !("style" == tagName || "img" == tagName)
    }

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

    private fun findDivClassMarkup(attr: String): String? {
        if ("doemeliste utvidet" == attr) {
            return null
        }
        return if ("tyding utvidet" == attr) {
            null
        } else "[divclass=$attr]"
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

    private fun findSpanStyleMarkup(attr: String): String? {
        if ("font-style: italic" == attr) {
            return "i"
        }
        if ("font-style: normal" == attr) {
            return null
        }
        return if (attr.startsWith("font-family: Times New Roman")) {
            "b"
        } else "[spanstyle=$attr]"
    }

    private fun findSpanClassMarkup(attr: String): String? {
        if ("oppslagsord b" == attr) {
            return "b"
        }
        if ("oppsgramordklasse" == attr) {
            return "i"
        }
        if ("etymtilvising" == attr) {
            return "i"
        }
        if ("utvidet" == attr) {
            return null
        }
        if ("doeme utvidet" == attr) {
            return null
        }
        return if ("kompakt" == attr) {
            null
        } else "[spanclass=$attr]"
    }

    override fun getLookupUrl(urlEncodedQueryWord: String): String {
        return BASEURL.replace("[Q]", urlEncodedQueryWord)
    }

    companion object {
        private const val BASEURL = "https://ordbok.uib.no/perl/ordbok.cgi?startpos=1&antall_vise=20&OPP=+[Q]&ordbok=bokmaal&bokmaal=%2B&spraak=bokmaal"
    }
}