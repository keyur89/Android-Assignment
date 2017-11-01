package com.test.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.test.assignment.Scenario2.view.Scenario2Activity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btnScenario1)
    Button btnScenario1;
    @Bind(R.id.btnScenario2)
    Button btnScenario2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnScenario2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Scenario2Activity.class);
                startActivity(intent);
            }
        });
    }
}
