package com.indraep.gmapexample;

import java.util.List;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	static final LatLng RAGUNAN = new LatLng(-6.3039, 106.8267);
	static final LatLng TAMANMINI = new LatLng(-6.29436, 106.8859);
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		LocationManager locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria cr = new Criteria();
		String provider = locationmanager.getBestProvider(cr, true);
		Location location = locationmanager.getLastKnownLocation(provider);
		
		LatLng currentLocation = null;
		List<Address> addr = null;
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			currentLocation = new LatLng(lat, lng);
			addr = getAddress(lat, lng);
		}
		
		 
		for(Address a: addr) {
			Log.e("DEBUG", a.toString());
		}

		Marker currMarker = map.addMarker(new MarkerOptions().position(currentLocation)
				.title("Your Location"));

		Marker tamanmini = map.addMarker(new MarkerOptions()
		.position(TAMANMINI)
		.title("Taman Mini")
		.snippet("Taman Mini itu Indah")
		.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.ic_launcher)));

		// Move the camera instantly to hamburg with a zoom of 15.
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(RAGUNAN, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public List<Address> getAddress(double latitude, double longitude) {
        List<Address> addresses = null;
        try {

            Geocoder geocoder;

            geocoder = new Geocoder(MainActivity.this);
            if (latitude != 0 || longitude != 0) {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                //testing address below 

                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                Log.d("TAG", "address = " + address + ", city =" + city
                        + ", country = " + country);

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return addresses;
    }
}
