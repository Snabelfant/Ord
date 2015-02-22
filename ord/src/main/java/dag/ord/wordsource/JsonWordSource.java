package dag.ord.wordsource;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonWordSource extends WordSource {
    protected JsonWordSource(String shortName) {
        super(shortName);
    }


    private String toString(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.isNull(key)) {
            return null;
        }

        Object o = jsonObject.get(key);
        if (o == null ) {
            return null;
        }
         return o.toString();
    }

    protected String toTrimmedString(JSONObject jsonObject, String key) throws JSONException {
        String s = toString(jsonObject, key);

        if (s != null) {
            String st = s.trim();
            if (st.isEmpty()) {
                return null;
            }

            return st;
        }

        return null;
    }
}
