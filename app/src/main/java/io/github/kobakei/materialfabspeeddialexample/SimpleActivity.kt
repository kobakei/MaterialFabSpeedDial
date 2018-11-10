package io.github.kobakei.materialfabspeeddialexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.kobakei.materialfabspeeddial.FabSpeedDial

class SimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        val fab = findViewById<FabSpeedDial>(R.id.fab)
        fab.addOnMenuItemClickListener { _, _, itemId -> Toast.makeText(this@SimpleActivity, "Click: $itemId", Toast.LENGTH_SHORT).show() }
        fab.addOnStateChangeListener { open -> Toast.makeText(this@SimpleActivity, "Open: $open", Toast.LENGTH_SHORT).show() }
    }
}
