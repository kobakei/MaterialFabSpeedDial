package io.github.kobakei.materialfabspeeddialexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.kobakei.materialfabspeeddial.FabSpeedDial
import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu

class Simple2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple2)

        val fab = findViewById<FabSpeedDial>(R.id.fab)

        // Create menu in code
        val menu = FabSpeedDialMenu(this)
        menu.add("One").setIcon(R.drawable.ic_action_cut)
        menu.add("Two").setIcon(R.drawable.ic_action_copy)
        menu.add("Three").setIcon(R.drawable.ic_action_paste)
        fab.setMenu(menu)

        fab.addOnMenuItemClickListener { _, _, itemId -> Toast.makeText(this@Simple2Activity, "Click: $itemId", Toast.LENGTH_SHORT).show() }
    }
}
