/*package com.example.heloise.finaltrial;



        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.google.gson.JsonObject;
        import com.google.gson.JsonParser;

        import org.eclipse.paho.android.service.MqttAndroidClient;
        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private Button subscribe;
    private EditText subscribeTopic;
    private MqttAndroidClient client;
    private PahoMqttClient pahoMqttClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pahoMqttClient = new PahoMqttClient();



        subscribe = findViewById(R.id.subscribe);
        subscribeTopic = (EditText) findViewById(R.id.subscribeTopic);
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
                                   Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                   intent.putExtra("message",String.valueOf((jsonObject)) );
                                   startActivity(intent);

                               }


                               @Override
                               public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
                               {

                               }
                           }
        );




        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = subscribeTopic.getText().toString().trim();

                if (!topic.isEmpty()) {
                    try {
                        pahoMqttClient.subscribe(client, topic, 0);//originaly qos=1
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        });









    }


}

*/