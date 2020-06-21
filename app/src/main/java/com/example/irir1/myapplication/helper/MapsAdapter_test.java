package com.example.irir1.myapplication.helper;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.irir1.myapplication.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsAdapter_test extends RecyclerView.ViewHolder implements OnMapReadyCallback {

    private TextView userName, eventName;
    protected CardView mCardView;
    GoogleMap mMap;
    MapView map_view;
    public LatLng latLngEvent, latLanPickUp, latLangStartLocation;

    public MapsAdapter_test(View v) {
        super(v);

      /*  userName = (TextView) v.findViewById(R.id.tvPssengersName);
        eventName  = (TextView) v.findViewById(R.id.tvPassengersEventName);
        map_view = (MapView) v.findViewById(R.id.passengersMapView);
        mCardView = (CardView) v.findViewById(R.id.passenger_card_view);*/
    }

    public void initializeMapView() {
        if (map_view != null) {
            map_view.onCreate(null);
            map_view.onResume();
            map_view.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  MapsInitializer.initialize(context);
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
    }

    public void setMarkers(GoogleMap map, LatLng name, String title) {
        map.addMarker(new MarkerOptions().position(name).title(title));
    }

    public void setPolyLines(GoogleMap map, LatLng start, LatLng end, int color) {
        PolylineOptions line = new PolylineOptions().add(start,end);
        line.color(color);
        map.addPolyline(line);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return p1;
    }

    private LatLng computeCentroid(List<LatLng> points) {
        double latitude = 0;
        double longitude = 0;
        int n = points.size();

        for (LatLng point : points) {
            latitude += point.latitude;
            longitude += point.longitude;
        }

        return new LatLng(latitude/n, longitude/n);
    }

    private double calculateDistance(List<LatLng> points) {
        Location loc1 = new Location("");
        loc1.setLatitude(points.get(0).latitude);
        loc1.setLongitude(points.get(0).longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(points.get(1).latitude);
        loc2.setLongitude(points.get(1).longitude);

        return distance_between(loc1.getLatitude(),loc1.getLongitude(),loc2.getLatitude(),loc2.getLongitude());
    }

    double distance_between(double lat1, double lon1, double lat2, double lon2) {
        //float results[] = new float[1];
            /* Doesn't work. returns inconsistent results
            Location.distanceBetween(
            l1.getLatitude(),
            l1.getLongitude(),
            l2.getLatitude(),
            l2.getLongitude(),
            results);
            */

        double R = 6371; // km
        double dLat = (lat2-lat1)*Math.PI/180;
        double dLon = (lon2-lon1)*Math.PI/180;
        lat1 = lat1*Math.PI/180;
        lat2 = lat2*Math.PI/180;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c * 1000;

        return d;
    }
}

