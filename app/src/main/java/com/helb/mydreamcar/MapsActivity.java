package com.helb.mydreamcar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapInitOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.Plugin;


public class MapsActivity extends AppCompatActivity{



    private MapView mapView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView);

        /*mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @androidx.annotation.NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull @androidx.annotation.NonNull Style style) {
                        style.addImage("red-pin-icon-id", BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_baseline_place_24)));
                        style.addLayer(new SymbolLayer("icon-layer-id", "icon-source-id").withProperties(
                                iconImage("red-pin-icon-id"),
                                iconIgnorePlacement(true),
                                iconAllowOverlap(true),
                                iconOffset(new Float[]{0f,-9f})
                        ));
                        GeoJsonSource iconGeoJsonSource = new GeoJsonSource("icon-source-id", Feature.fromGeometry(Point.fromLngLat(107.6345122,-6.9249233)));
                        style.addSource(iconGeoJsonSource);
                        mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.9249233,107.6345122), 15.7));
                    }
                });
            }
        });*/

        //mapView.onCreate(savedInstanceState);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}