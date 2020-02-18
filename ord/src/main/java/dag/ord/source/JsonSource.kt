package dag.ord.source

import org.json.JSONException
import org.json.JSONObject

abstract class JsonSource protected constructor(shortName: String?) : Source(shortName!!) {
    @Throws(JSONException::class)
    private fun toString(jsonObject: JSONObject, key: String): String? {
        if (jsonObject.isNull(key)) {
            return null
        }
        val o = jsonObject[key] ?: return null
        return o.toString()
    }

    @Throws(JSONException::class)
    protected fun toTrimmedString(jsonObject: JSONObject, key: String): String? {
        val s = toString(jsonObject, key)
        if (s != null) {
            val st = s.trim { it <= ' ' }
            return if (st.isEmpty()) {
                null
            } else st
        }
        return null
    }
}