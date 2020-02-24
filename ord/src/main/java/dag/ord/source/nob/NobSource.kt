package dag.ord.source.nob

import dag.ord.search.Result
import dag.ord.source.HtmlSource
import dag.ord.source.Source
import dag.ord.util.Logger
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.util.*

abstract class NobSource(sourceId: String, private val htmlTableId: String) : HtmlSource(sourceId) {

    override fun toResults(word: String, document: Document, maxResultLength: Int) =
            document
                    .select("div[class=artikkelinnhold]")
                    .map { artikkel ->
                        removeUnwantedTags(artikkel)
                        val artikkelHtmlSb = StringBuilder()
                        traverse(artikkel, artikkelHtmlSb)
                        val artikkelHtml = doReplacements(artikkelHtmlSb)
                        Result(getLookupUrl(word), artikkelHtml, maxResultLength)
                    }
                    .distinctBy { it.unabbreviatedSummary }

    protected abstract fun removeUnwantedTags(artikkel: Element)
    private fun doReplacements(artikkelHtml: StringBuilder) = artikkelHtml.toString().replace("([^ ])<i>".toRegex(), "$1 <i>")

    private fun traverse(node: Node, sb: StringBuilder) {
        if (node is Element) {
            for (child in node.childNodes()) {
                if (child is TextNode) {
                    sb.append(child.text())
                } else {
                    if (child is Element) {
                        if (tagToBeProcessed(child.tagName())) {
                            val tag = findMarkup(child)
                            tag?.apply { sb.append("<").append(this).append(">") }
                            traverse(child, sb)
                            tag?.apply { sb.append("</").append(this).append(">") }
                        }
                    }
                }
            }
        } else {
            sb.append("<b>XXX</b>")
        }
    }

    protected abstract fun findMarkup(element: Element): String?
    protected abstract fun tagToBeProcessed(tagName: String?): Boolean

    companion object {
        fun createWordSources(): List<Source> {
            val sources: MutableList<Source> = ArrayList()
            sources.add(NobBmSource())
            sources.add(NobNnSource())
            return sources
        }
    }

}