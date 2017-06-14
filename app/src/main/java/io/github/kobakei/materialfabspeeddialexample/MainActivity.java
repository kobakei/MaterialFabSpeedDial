package io.github.kobakei.materialfabspeeddialexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FabSpeedDial fab = (FabSpeedDial) findViewById(R.id.fab);
        /*
        fab.addMenu(new FabSpeedDialMenu.Builder(this)
                .setItemId(1)
                .setTitle("Alarm")
                .setTitleColor(R.color.fab_mini_text)
                .setTitleBackgroundDrawable(R.drawable.label)
                .setDrawable(R.drawable.ic_action_alarm)
                .setDrawableTint(R.color.fab_mini_text)
                .setFabBackgroundColor(R.color.fab_mini)
                .setRippleColor(R.color.colorAccent)
                .build());
        fab.addMenu(new FabSpeedDialMenu.Builder(this)
                .setItemId(2)
                .setTitle("Camera")
                .setTitleColor(R.color.fab_mini_text)
                .setTitleBackgroundDrawable(R.drawable.label)
                .setDrawable(R.drawable.ic_action_camera)
                .setDrawableTint(R.color.fab_mini_text)
                .setFabBackgroundColor(R.color.fab_mini)
                .setRippleColor(R.color.colorAccent)
                .build());
        fab.addMenu(new FabSpeedDialMenu.Builder(this)
                .setItemId(3)
                .setTitle("Cut")
                .setTitleColor(R.color.fab_mini_text)
                .setTitleBackgroundDrawable(R.drawable.label)
                .setDrawable(R.drawable.ic_action_cut)
                .setDrawableTint(R.color.fab_mini_text)
                .setFabBackgroundColor(R.color.fab_mini)
                .setRippleColor(R.color.colorAccent)
                .build());*/
        fab.addOnMenuClickListener(new FabSpeedDial.OnMenuClickListener() {
            @Override
            public void onMenuClick(View view, int itemId) {
                Toast.makeText(MainActivity.this, "Click: " + itemId, Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fab.isShown()) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });
    }
}
