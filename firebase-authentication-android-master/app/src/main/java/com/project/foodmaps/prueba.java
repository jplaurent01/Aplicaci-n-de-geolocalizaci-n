package com.project.foodmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class prueba extends AppCompatActivity {

    private EditText texto;
    private Button botonMostar;
    private TextView output;
    FirebaseAuth fAuth;
    String userId;
    private final String KEY = "edittextValue";
    public static final String EXTRA_MESSAGE = "com.project.foodmaps.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Button btn_move;
        Button btn_add;
        btn_move = (Button) findViewById(R.id.btnmove);
        btn_add = (Button) findViewById(R.id.btnwrite);
        texto = (EditText) findViewById(R.id.txt);
        output = (TextView) findViewById(R.id.view);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }
        });

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        if (SharedPreferences.contains(micaja)) {
//            micaja.setText(SharedPreferences.getString(Micaja, ""));
//        }
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MoveMarker.class));
            }
        });

    }

    private String getValue() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String savedValue = sharedPref.getString(KEY, ""); //the 2 argument return default value

        return savedValue;
    }

    private void saveFromEditText(String text) {
        userId = fAuth.getCurrentUser().getUid();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(userId, text);
        editor.apply();
    }
}