package com.project.foodmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class prueba extends AppCompatActivity {

    private EditText micaja;
    private TextView misalida;
    private Button botonMostar;
    public static final String EXTRA_MESSAGE = "com.project.foodmaps.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Button btn_move;
        btn_move = (Button) findViewById(R.id.btnmove);
        botonMostar = (Button) findViewById(R.id.button3);
        micaja = (EditText) findViewById(R.id.txt);
        misalida = (TextView) findViewById(R.id.textView9);

        botonMostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minombre = null;
                minombre = micaja.getText().toString();
                misalida.setText(minombre);
            }
        });

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MoveMarker.class));
            }
        });

    }


}