package dag.ord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dag.ord.ui.WebPageUi
import dag.ord.util.Logger

class WebPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webpage)
        WebPageUi.build(this)
        intent.extras?.getString("url")?.also { WebPageUi.show(it) }
    }
}
