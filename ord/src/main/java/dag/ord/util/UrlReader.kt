package dag.ord.util

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.*
import java.net.URL

object UrlReader {
    private val httpClient = OkHttpClient()

    fun read(url: String) : String? {
        val request = Request.Builder()
            .url(url)
            .build()

        return httpClient.newCall(request).execute().body?.string()
    }
}
