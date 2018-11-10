package io.github.kobakei.materialfabspeeddialexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import io.github.kobakei.materialfabspeeddial.FabSpeedDial

class VectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector)

        val fab = findViewById<FabSpeedDial>(R.id.fab)
        fab.addOnMenuItemClickListener { _, _, itemId -> Toast.makeText(this@VectorActivity, "Click: $itemId", Toast.LENGTH_SHORT).show() }
    }

    companion object {

        // You need this snippet for selector of vector drawables on Android 4.x
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
