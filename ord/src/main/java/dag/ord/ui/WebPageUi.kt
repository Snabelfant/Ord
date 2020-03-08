package dag.ord.ui

import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import dag.ord.R
import dag.ord.util.Logger

object WebPageUi {
    private lateinit var webPageView: WebView
    fun build(activity: AppCompatActivity) {
        webPageView = activity.findViewById(R.id.webpage)!!

        activity.supportActionBar?.apply {
            setLogo(R.drawable.ord_lite)
            setDisplayUseLogoEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    fun show(url: String) {
        webPageView.loadUrl(url)
    }
}
