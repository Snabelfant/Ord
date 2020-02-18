package dag.ord.source.wiktionary

import dag.ord.source.Source
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Tag
import java.util.*

class WikipediaHtmlSource(shortName: String, languageCode: String?) : WikiHtmlSource(shortName, "wikipedia", languageCode) {
    override fun transform(top: Element): String? {
        val tagsToRemove = "table,sup,h1, h2,h3,noscript,script,img,div[class~=thumb.*], " +
                "div[class~=references.*],div[id=page-secondary-actions]," +
                "div[class=noprint],div[class=floatleft]," +
                "div[class=vedlikehold], div[class~=stubb.*]," +
                "div[style~=clear: both.*],div[id=catlinks]"
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
        var html = top.toString()
        html = html.replace("<div.*?>".toRegex(), "").replace("</div>", "")
        //        Log.i( getShortName() + " '" + html + "'");
        return html
    }

    companion object {
        fun createWordSources(): List<Source> {
            val sources: MutableList<Source> = ArrayList()
            sources.add(WikipediaHtmlSource("WPNO", "no"))
            sources.add(WikipediaHtmlSource("WPSV", "sv"))
            sources.add(WikipediaHtmlSource("WPDA", "da"))
            sources.add(WikipediaHtmlSource("WPDE", "de"))
            sources.add(WikipediaHtmlSource("WPFR", "fr"))
            sources.add(WikipediaHtmlSource("WPEN", "en"))
            return sources
        }
    }
}