package com.project.foodmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

//Para quitar coordenadas al hacer drga al marker, se pone extends FragmentActivity, en lugar de AppCompatActivity
public class MoveMarker extends AppCompatActivity implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker markerDrag;
    private Button btn_hibrido, btn_Marcador, btn_logout;
    ArrayList<Marker> listadoMar = new ArrayList<Marker>();
    ArrayList<Double> latitudes = new ArrayList<Double>();
    ArrayList<Double> longitudes = new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        load_map(mMap);
        btn_hibrido = (Button) findViewById(R.id.btnhibrido);
        btn_Marcador = (Button) findViewById(R.id.btnMarcador);
        btn_logout = (Button) findViewById(R.id.btnlogout);

        btn_hibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }


    /** private void insertarMarcador() {
     Mmap.addMarker(new MarkerOptions()
     .position(new LatLng(40.3936945, -3.701519))
     .title("Pais: España"));
     }}*/

    /**public void Cambiarhibrido (View view){
     mMap.setMapStyle(GoogleMap.MAP_TYPE_HYBRID);
     }*/
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
    public void load_map (GoogleMap googleMap) {
        for(int i = 0; i<latitudes.size(); i++)  //Añadimos todos los marcadores otra vez
        {
//            final LatLng punto = new LatLng(latitudes.get(i), longitudes.get(i));
            final LatLng punto = new LatLng(latitudes.get(i),  longitudes.get(i));
            markerDrag = googleMap.addMarker(
                    new MarkerOptions()
                            .position(punto)
                            //Importante activar para rastrear
                            .draggable(true));
        }
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        final LatLng tibas = new LatLng(9.957626, -84.075182);
        markerDrag = googleMap.addMarker(
                new MarkerOptions()
                        .position(tibas)
                        //Importante activar para rastrear
                        .draggable(true));


        listadoMar.add(markerDrag);

//        for(int i = 0; i<listadoMar.size(); i++)  //Añadimos todos los marcadores otra vez
//        {
//                mMap.addMarker(listadoMar.get(i));
//
//        }



        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tibas));
        //Opciones de zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //Importante activar, para poder arrastrar y posicionar
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
//        latitudes.add(markerDrag.getPosition().latitude);
//        longitudes.add(markerDrag.getPosition().longitude);
//        for(int i = 0; i<latitudes.size(); i++)  //Añadimos todos los marcadores otra vez
//        {
////            final LatLng punto = new LatLng(latitudes.get(i), longitudes.get(i));
//            final LatLng punto = new LatLng(9.0, -75);
//            markerDrag = googleMap.addMarker(
//                    new MarkerOptions()
//                            .position(punto)
//                            //Importante activar para rastrear
//                            .draggable(true));
//        }


//        try {
//            // Modes: MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
//            FileOutputStream output = openFileOutput("coordenadas.txt",
//                    Context.MODE_PRIVATE);
//            DataOutputStream dout = new DataOutputStream(output);
//            dout.writeUTF(markerDrag.getPosition().latitude+ "," + markerDrag.getPosition().longitude);
//            Log.v("write",  markerDrag.getPosition().latitude + "," + markerDrag.getPosition().longitude);
//            dout.flush(); // Flush stream ...
//            dout.close(); // ... and close.
//        } catch (IOException exc) {
//            exc.printStackTrace();
//        }
//

        //Localizacion actual, primero se agregan permisos y luego se agrega opcion de localizacion
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        //Se puede quitar esta opcion para activar el boton de gps, caso contrario, se habilita solo
        //Fuente: https://www.youtube.com/watch?v=a3JXG3hWMIc
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        /**LatLng sydney = new LatLng(-33.852, 151.211);
         googleMap.addMarker(new MarkerOptions()
         .position(sydney)
         .title("Marker in Sydney"));
         googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    //DragLister Logica para mover marcadore
    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(markerDrag)) {
            Toast.makeText(this,"Start",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(markerDrag)) {
            String newTitle = String.format(Locale.getDefault(),
                    getString(R.string.marker_detail_latlng),
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);
            setTitle(newTitle);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerDrag)) {
            Toast.makeText(this,"Finish",Toast.LENGTH_LONG).show();
            latitudes.add(markerDrag.getPosition().latitude);
            longitudes.add(markerDrag.getPosition().longitude);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}