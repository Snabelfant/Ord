package dag.ord.source.snl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dag.ord.search.Result;
import dag.ord.source.JsonSource;
import dag.ord.source.Source;

public class SnlSource extends JsonSource {
    private static final String BASEURL = "https://[subdomene]snl.no/api/v1/search?query=[query]";
    private SubDomain subDomain;

    private SnlSource(String shortName, SubDomain subDomain) {
        super(shortName);
        this.subDomain = subDomain;
    }

    public static List<Source> createWordSources() {
        List<Source> sources = new ArrayList<Source>();
        sources.add(new SnlSource("SNL", SubDomain.SNL));
        sources.add(new SnlSource("NKL", SubDomain.NKL));
        sources.add(new SnlSource("NBL", SubDomain.NBL));
        sources.add(new SnlSource("SML", SubDomain.SML));
        return sources;
    }

    protected String getLookupUrl(String urlEncodedQueryWord) {
        return BASEURL.replace("[subdomene]", subDomain.getSubDomain()).replace("[query]",urlEncodedQueryWord);
    }

    protected List<Result> toResults(String queryWord, String json, int maxWordDescriptorLength) {
        List<Result> Results = new ArrayList<Result>();

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

                Result result = new Result(displayUrl, summary, maxWordDescriptorLength);
                Results.add(result);
            }
        } catch (JSONException e) {
            Result result = new Result(null, e.toString(), maxWordDescriptorLength);
            Results.add(result);
        }

        return Results;
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
