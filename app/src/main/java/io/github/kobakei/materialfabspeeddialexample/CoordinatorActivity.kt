package io.github.kobakei.materialfabspeeddialexample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class CoordinatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener { v -> Snackbar.make(v, "Hello", Snackbar.LENGTH_SHORT).show() }
    }
}
