package dag.ord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dag.ord.ui.OrdUi

class OrdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ord)
        val ordViewModel = ViewModelProvider(this).get(OrdViewModel::class.java)
        OrdUi.build(this, ordViewModel)
    }
}