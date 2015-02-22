package dag.ord.wordsource.nob;

import org.jsoup.nodes.Element;

/**
 * Created by Dag on 14.08.2014.
 */
public class NoBmWordSource extends NoWordSource {
    private static final String BASEURL = "http://www.nob-ordbok.uio.no/perl/ordbok.cgi?startpos=1&antall_vise=20&OPP=+[Q]&ordbok=bokmaal&bokmaal=%2B&spraak=bokmaal";


    public NoBmWordSource() {
        super("NOBM", "byttutBM");
    }

    @Override
    protected void removeUnwantedTags(Element artikkel) {
        artikkel.select("span[class=oppsgramordklassevindu]").remove();
        artikkel.select("span[class=doeme kompakt]").remove();
        artikkel.select("span[class=tydingC kompakt]").remove();
        artikkel.select("span[class=doemeliste kompakt]").remove();

    }

    @Override
    protected boolean tagToBeProcessed(String tag) {
        return !("style".equals(tag) || "img".equals(tag));
    }

    @Override
    protected String findMarkup(Element element) {
        String tag = element.tagName();

        if ("span".equals(tag)) {
            String attr = element.attr("class");

            if (attr != null && !"".equals(attr)) {
                return findSpanClassMarkup(attr);
            }

            attr = element.attr("style");
            if (attr != null && !"".equals(attr)) {
                return findSpanStyleMarkup(attr);
            }

            if (element.attributes().size() == 0) {
                return findSpanNoAttribs(element);
            }

            return "[span a=" + element.toString() + "]";

        }

        if ("div".equals(tag)) {
            String attr = element.attr("class");

            if (attr != null && !"".equals(attr)) {
                return findDivClassMarkup(attr);
            }
            return "[div a=" + element.toString() + "]";
        }

        return "[T=" + tag + "]";
    }

    private String findDivClassMarkup(String attr) {
        if ("doemeliste utvidet".equals(attr)) {
            return null;
        }

        if ("tyding utvidet".equals(attr)) {
            return null;
        }
        return "[divclass=" + attr + "]";
    }

    private String findSpanNoAttribs(Element element) {
        if (element.children().size() > 0) {
            Element c0 = element.child(0);
            if ("style".equals(c0.tagName())) {
                String text = c0.data();

                if ("font-weight: bold".equals(text)) {
                    return "b";
                }

                return "!sna txt=" + text;
            } else {
                return "!sna tag=" + c0.tagName();
            }
        } else {
            return null;
        }
    }

    private String findSpanStyleMarkup(String attr) {
        if ("font-style: italic".equals(attr)) {
            return "i";
        }

        if ("font-style: normal".equals(attr)) {
            return null;
        }
        if (attr.startsWith("font-family: Times New Roman")) {
            return "b";
        }
        return "[spanstyle=" + attr + "]";
    }

    private String findSpanClassMarkup(String attr) {
        if ("oppslagsord b".equals(attr)) {
            return "b";
        }

        if ("oppsgramordklasse".equals(attr)) {
            return "i";
        }

        if ("etymtilvising".equals(attr)) {
            return "i";
        }

        if ("utvidet".equals(attr)) {
            return null;
        }

        if ("doeme utvidet".equals(attr)) {
            return null;
        }

        if ("kompakt".equals(attr)) {
            return null;
        }
        return "[spanclass=" + attr + "]";
    }

    protected String getLookupUrl(String urlEncodedQueryWord) {
        return BASEURL.replace("[Q]", urlEncodedQueryWord);
    }

}
