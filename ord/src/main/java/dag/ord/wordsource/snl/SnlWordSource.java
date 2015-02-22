package dag.ord.wordsource.snl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dag.ord.searchresult.WordDescriptor;
import dag.ord.wordsource.JsonWordSource;
import dag.ord.wordsource.WordSource;

public class SnlWordSource extends JsonWordSource {
    private static final String BASEURL = "https://[subdomene]snl.no/api/v1/search?query=[query]";
    private SubDomain subDomain;

    private SnlWordSource(String shortName, SubDomain subDomain) {
        super(shortName);
        this.subDomain = subDomain;
    }

    public static List<WordSource> createWordSources() {
        List<WordSource> wordSources = new ArrayList<WordSource>();
        wordSources.add(new SnlWordSource("SNL", SubDomain.SNL));
        wordSources.add(new SnlWordSource("NKL", SubDomain.NKL));
        wordSources.add(new SnlWordSource("NBL", SubDomain.NBL));
        wordSources.add(new SnlWordSource("SML", SubDomain.SML));
        return wordSources;
    }

    protected String getLookupUrl(String urlEncodedQueryWord) {
        return BASEURL.replace("[subdomene]", subDomain.getSubDomain()).replace("[query]",urlEncodedQueryWord);
    }

    protected List<WordDescriptor> toWordDescriptors(String queryWord, String json, int maxWordDescriptorLength) {
        List<WordDescriptor> wordDescriptors = new ArrayList<WordDescriptor>();

        try {
            JSONArray articles = new JSONArray(json);

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);

                String displayUrl = toTrimmedString(article, "article_url");
                String clarification = toTrimmedString(article, "clarification");
                String headWord = toTrimmedString(article, "headword");
                String snippet = toTrimmedString(article, "snippet");
                String taxonomyTitle = toTrimmedString(article, "taxonomy_title");
                String summary = "<b>" +headWord + "</b> <i>(";
                if (clarification != null) {
                    summary += clarification + "/";
                }
                summary += taxonomyTitle + ")</i> " + snippet;

                WordDescriptor wordDescriptor = new WordDescriptor(displayUrl, summary, maxWordDescriptorLength);
                wordDescriptors.add(wordDescriptor);
            }
        } catch (JSONException e) {
            WordDescriptor wordDescriptor = new WordDescriptor(null, e.toString(), maxWordDescriptorLength);
            wordDescriptors.add(wordDescriptor);
        }

        return wordDescriptors;
    }


    public enum SubDomain {
        SNL(""),
        NKL("nkl."),
        NBL("nbl."),
        SML("sml.");
        private String subDomain;

        SubDomain(String subDomain) {
            this.subDomain = subDomain;
        }

        String getSubDomain() {
            return subDomain;
        }
    }

}
