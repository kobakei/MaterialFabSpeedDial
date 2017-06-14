package io.github.kobakei.materialfabspeeddialexample;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu;

public class Simple2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);

        final FabSpeedDial fab = (FabSpeedDial) findViewById(R.id.fab);

        // Create menu in code
        FabSpeedDialMenu menu = new FabSpeedDialMenu(this);
        menu.add("One").setIcon(R.drawable.ic_action_cut);
        menu.add("Two").setIcon(R.drawable.ic_action_copy);
        menu.add("Three").setIcon(R.drawable.ic_action_paste);
        fab.setMenu(menu);

        fab.addOnMenuClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView label, int itemId) {
                Toast.makeText(Simple2Activity.this, "Click: " + itemId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
