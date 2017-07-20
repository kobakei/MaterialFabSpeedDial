package io.github.kobakei.materialfabspeeddialexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        final FabSpeedDial fab = (FabSpeedDial) findViewById(R.id.fab);
        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView label, int itemId) {
                Toast.makeText(SimpleActivity.this, "Click: " + itemId, Toast.LENGTH_SHORT).show();
            }
        });
        fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                Toast.makeText(SimpleActivity.this, "Open: " + open, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
