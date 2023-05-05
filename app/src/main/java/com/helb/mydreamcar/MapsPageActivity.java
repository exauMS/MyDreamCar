package com.helb.mydreamcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MapsPageActivity extends AppCompatActivity {

    private MapsFragment mapsFragment;
    private Bundle bundle;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_page);

        bundle = this.getIntent().getExtras();
        city = (String)bundle.getSerializable("cityName");
        System.out.println(city);
        mapsFragment = new MapsFragment(getLocationFromAddress(getApplicationContext(),city));
        getSupportFragmentManager().beginTransaction().replace(R.id.map_container, mapsFragment).commit();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng point = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            point = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return point;
    }
}