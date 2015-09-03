package dag.ord.searchresult;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<WordDescriptor> wordDescriptors = new ArrayList<WordDescriptor>();
    private long lookupTimeInMillis;

    public long getLookupTimeInMillis() {
        return lookupTimeInMillis;
    }

    public void setLookupTimeInMillis(long lookupTimeInMillis) {
        this.lookupTimeInMillis = lookupTimeInMillis;
    }

    public boolean isNotEmpty() {
        return wordDescriptors.size() > 0;
    }

    public void add(List<WordDescriptor> wordDescriptors) {
        if (wordDescriptors != null) {
            for (WordDescriptor wordDescriptor : wordDescriptors) {
                if (!StringUtil.isBlank(wordDescriptor.getSummary())) {
                    this.wordDescriptors.add(wordDescriptor);
                }
            }
        }
    }

    public int getResultCount() {
        return wordDescriptors.size();
    }

    public WordDescriptor getWordDescriptor(int i) {
        return wordDescriptors.get(i);
    }

    public String toString() {

        StringBuilder sb = new StringBuilder("(" + lookupTimeInMillis + ")\n");

        for (WordDescriptor wordDescriptor : wordDescriptors) {
            sb.append(wordDescriptor.toString()).append("\n");
        }

        return sb.toString();
    }
}
