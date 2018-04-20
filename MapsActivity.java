package com.example.heloise.finaltrial;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMyLocationClickListener
{
    private Button subscribe;
    private EditText subscribeTopic;
    private MqttAndroidClient client;
    private PahoMqttClient pahoMqttClient;
    private GoogleMap mMap;
    String l1="",l2="";



    @Override
    protected void onStart() {
        super.onStart();



    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



            pahoMqttClient = new PahoMqttClient();






        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
            client.setCallback(new MqttCallbackExtended() {

                                   @Override
                                   public void connectComplete(boolean b, String s) {

                                   }

                                   @Override
                                   public void connectionLost(Throwable throwable) {

                                   }

                                   @Override
                                   public void messageArrived(String s, MqttMessage mqttMessage) throws Exception
                                   {
                                       String topic = subscribeTopic.getText().toString().trim();
                                       Log.d("receiving message from", topic);
                                       JsonParser parser = new JsonParser();
                                       String payload = new String(mqttMessage.getPayload());
                                       //Log.d(" message is", String.valueOf((payload)));
                                       JsonObject jsonObject = parser.parse(payload).getAsJsonObject();
                                       Log.d("message", String.valueOf((jsonObject)));
                                       /*Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                       intent.putExtra("message",String.valueOf((jsonObject)) );
                                       startActivity(intent);*/
                                       //Bundle bundle = getIntent().getExtras();
                                       String value1=String.valueOf(jsonObject);
                                       Log.d("print:",value1);
                                       String s1[]=value1.split(",");
                                       Log.d("print1",s1[0]);
                                       Log.d("print1",s1[1]);
                                       l1=s1[0].substring(7);
                                       l2=s1[1].substring(7,17);
                                       Log.d("print2",l1);
                                       Log.d("print2",l2);

                                   }


                                   @Override
                                   public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
                                   {

                                   }
                               }
            );






        }








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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {}

        else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);

        }
        subscribe = findViewById(R.id.subscribe);
        subscribeTopic = (EditText) findViewById(R.id.subscribeTopic);
        String topic = subscribeTopic.getText().toString().trim();
        try {
            pahoMqttClient.subscribe(client, topic, 1);//originaly qos=1

        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Add a marker in Sydney and move the camera
        //double d1=Double.parseDouble(l1);
        //double d2=Double.parseDouble(l2);
        LatLng sydney = new LatLng(Double.valueOf(l1),Double.valueOf(l2));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Bus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

       double lat = 15.327748 ;
       double longi = 73.9313777;
       Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(15.326663, 73.933233)).title("Marker in Verna"));
        double lat1=15.326820;
        double longi1=73.933559;
        Marker marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(15.326664, 73.933218)).title("Marker in Verna"));

    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(Location location){
        Toast.makeText(this, "Curren location:\n" + location, Toast.LENGTH_LONG).show();
    }


}

