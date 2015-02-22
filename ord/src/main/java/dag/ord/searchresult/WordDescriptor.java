package dag.ord.searchresult;

public class WordDescriptor {
    private String summary;
    private String displayUrl;

    public WordDescriptor(String displayUrl, String summary, int maxWordDescriptorLength) {
        this.displayUrl = displayUrl;
        if (maxWordDescriptorLength > 0 && summary.length() > maxWordDescriptorLength) {
            this.summary = summary.substring(0, maxWordDescriptorLength) + "...";
        } else {
            this.summary = summary;
        }
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String toString() {
        return summary + ", " + displayUrl;
    }
}
