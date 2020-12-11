package com.example.appmapa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Locale;

//Para quitar coordenadas al hacer drga al marker, se pone extends FragmentActivity, en lugar de AppCompatActivity
public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerDragListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker markerDrag;
    private Button btn_hibrido, btn_Marcador;
    ArrayList<Marker> listadoMar = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btn_hibrido = (Button) findViewById(R.id.btnhibrido);
        btn_Marcador = (Button) findViewById(R.id.btnMarcador);

        btn_Marcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                //insertarMarcador();
            }
        });

        btn_hibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        final LatLng tibas = new LatLng(9.957626, -84.075182);
        markerDrag = googleMap.addMarker(new MarkerOptions()
                        .position(tibas)
                        //Importante activar para rastrear
                        .draggable(true));
        //añadimos el marcador a la lista
        listadoMar.add(markerDrag);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tibas));
        //Opciones de zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //Importante activar, para poder arrastrar y posicionar
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);

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
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //@Override
    //public void onClickDelete(GoogleMap googleMap) {
//Cuando vayas a eliminar si tenemos el id del marker a borrar recorremos la lista y eliminamos este marcador
        //for(int i = 0; i<listadoMar.size; i++)
        //{
          //  if(listadoMar.get(i).getId()==id){ //Comparamos los id de los marcadores de la lista con el del marker que queremos eliminar
            //    listadoMar.remove(listadoMar.get(i));
            //}
        //}
        //Ahora que hemos eliminado el marcador, recargamos el mapa
        //mMap.clear();  //Primero eliminamos lo que había en el mapa

        for(int i = 0; i<listadoMar.size; i++)  //Añadimos todos los marcadores otra vez
        {
            mMap.addMarker(listadoMar.getID(i));
        }
    //}

}