package dag.ord.source.nob

import dag.ord.search.Result
import dag.ord.source.HtmlSource
import dag.ord.source.Source
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.util.*

abstract class NobSource(shortName: String, private val htmlTableId: String) : HtmlSource(shortName) {

    override fun toWordDescriptors(word: String, document: Document, maxWordDescriptorLength: Int): List<Result> {
        val Results: MutableList<Result> = ArrayList()
        val displayUrl = getLookupUrl(word)
        val tables = document.select("table[id=$htmlTableId]")
        if (tables.size > 0) {
            val artikler = document.select("div[class=artikkelinnhold]")
            for (artikkel in artikler) {
                removeUnwantedTags(artikkel)
                val artikkelHtmlSb = StringBuilder()
                traverse(artikkel, artikkelHtmlSb)
                val artikkelHtml = doReplacements(artikkelHtmlSb)
                //                Log.i("'" + artikkelHtml + "'");
                val wordDescriptor = Result(displayUrl, artikkelHtml, maxWordDescriptorLength)
                Results.add(wordDescriptor)
            }
        }
        return Results.toList()
    }

    protected abstract fun removeUnwantedTags(artikkel: Element?)
    private fun doReplacements(artikkelHtml: StringBuilder): String {
        return artikkelHtml.toString().replace("([^ ])<i>".toRegex(), "$1 <i>")
    }

    private fun traverse(node: Node, sb: StringBuilder) {
        if (node is Element) {
            for (child in node.childNodes()) {
                if (child is TextNode) {
                    sb.append(child.text())
                } else {
                    if (child is Element) {
                        val e = child
                        if (tagToBeProcessed(e.tagName())) {
                            val tag = findMarkup(e)
                            if (tag != null) {
                                sb.append("<").append(tag).append(">")
                            }
                            traverse(child, sb)
                            if (tag != null) {
                                sb.append("</").append(tag).append(">")
                            }
                        }
                    }
                }
            }
        } else {
            sb.append("<b>XXX</b>")
        }
    }

    protected abstract fun findMarkup(element: Element?): String?
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