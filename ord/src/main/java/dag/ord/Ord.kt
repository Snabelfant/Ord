package dag.ord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import dag.ord.ui.OrdUi

class Ord : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ord)
        val ordViewModel = ViewModelProviders.of(this).get(OrdViewModel::class.java)
        OrdUi.build(this, ordViewModel)
    }
}