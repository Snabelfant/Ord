package dag.ord.source.wiktionary

import dag.ord.source.Source
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Tag
import java.util.*

class WiktionaryHtmlSource private constructor(shortName: String, languageCode: String) : WikiHtmlSource(shortName, "wiktionary", languageCode) {
    override fun transform(top: Element): String? {
        val tagsToRemove = "table,sup,noscript,script,img,div[id=page-secondary-actions]," +
                "a[class*=edit-page], div[class=thumbcaption],h1," +
                "h2:has(span[class=mw-headline]), div[class=NavFrame]" //                        "div[class~=references.*]," +
//                        "div[class=noprint],div[class=floatleft]," +
//                        "div[class=vedlikehold], div[class~=stubb.*]," +
//                        "div[style~=clear: both.*],div[id=catlinks]";
        val noMatchFound = top.select("p[class=mw-search-createlink]")
        if (!noMatchFound.isEmpty()) {
            return null
        }
        top.select(tagsToRemove).remove()
        val `as` = top.select("a")
        for (a in `as`) {
            a.replaceWith(TextNode(a.text()))
        }
        val ps = top.select("p")
        for (p in ps) {
            p.tagName("div")
        }
        val dls = top.select("dl")
        for (dl in dls) {
            dl.tagName("div")
        }
        val dds = top.select("dd")
        for (dd in dds) {
            val newDd = Element(Tag.valueOf("i"), "")
            newDd.append("//")
            newDd.appendText(dd.text())
            dd.replaceWith(newDd)
        }
        val uls = top.select("ul")
        for (ul in uls) {
            ul.tagName("div").before(Element(Tag.valueOf("br"), ""))
        }
        val ols = top.select("ol")
        for (ol in ols) {
            ol.tagName("div").before(Element(Tag.valueOf("br"), ""))
        }
        val lis = top.select("li")
        for (li in lis) {
            val liDiv = Element(Tag.valueOf("div"), "")
            liDiv.append("&bull;&nbsp;")
            liDiv.append(li.html())
            liDiv.appendElement("br")
            li.replaceWith(liDiv)
        }
        val h3s = top.select("h3")
        for (h3 in h3s) {
            val newH3 = Element(Tag.valueOf("u"), "")
            val newI = Element(Tag.valueOf("i"), "")
            newI.appendText(h3.text())
            newI.appendElement("br")
            newH3.appendChild(newI)
            h3.replaceWith(newH3)
        }
        val h4s = top.select("h4")
        for (h4 in h4s) {
            val newH4 = Element(Tag.valueOf("u"), "")
            newH4.appendText(h4.text())
            newH4.append("&nbsp;")
            h4.replaceWith(newH4)
        }
        var html = top.toString()
        html = html.replace("<div.*?>".toRegex(), "").replace("</div>", "")
        //        Log.i( getShortName() + " '" + html + "'");
        return html
    }

    companion object {
        fun createWordSources(): List<Source> {
            val sources: MutableList<Source> = ArrayList()
            sources.add(WiktionaryHtmlSource("WKNO", "no"))
            sources.add(WiktionaryHtmlSource("WKSV", "sv"))
            sources.add(WiktionaryHtmlSource("WKDA", "da"))
            sources.add(WiktionaryHtmlSource("WKEN", "en"))
            sources.add(WiktionaryHtmlSource("WKDE", "de"))
            sources.add(WiktionaryHtmlSource("WKFR", "fr"))
            return sources
        }
    }
}