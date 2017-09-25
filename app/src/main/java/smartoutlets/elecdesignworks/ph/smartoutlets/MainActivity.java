package smartoutlets.elecdesignworks.ph.smartoutlets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MainActivity extends AppCompatActivity{

    private final String TAG = MainActivity.class.getSimpleName();
    private Switch togglebtn_1;
    private Switch togglebtn_2;
    private Switch togglebtn_3;
    private TextView push_status;
    private TextView out1_status;
    private TextView out2_status;
    private TextView out3_status;
    private TextView detection_status;

    String push_success;
    String field_json;
    String one = "1";
    String zero = "0";
    String entry_id_json;

    Integer count=0;
    private MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApplication = MainApplication.getMainApplication();

        togglebtn_1 = (Switch) findViewById(R.id.togglebtn_1);
        togglebtn_2 = (Switch) findViewById(R.id.togglebtn_2);
        togglebtn_3 = (Switch) findViewById(R.id.togglebtn_3);
        push_status = (TextView) findViewById(R.id.push_status);
        out1_status = (TextView) findViewById(R.id.out1_status);
        out2_status = (TextView) findViewById(R.id.out2_status);
        out3_status = (TextView) findViewById(R.id.out3_status);
        detection_status = (TextView) findViewById(R.id.detection_status);

 //       push_status.setVisibility(View.INVISIBLE);

        final android.os.Handler handler = new android.os.Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                //    pullData(final String results,final String field,final String read_api_key, String channel, String url)
                if (count==1) {
                    pullData("1", "4LZPK4K4DNTMROS1", "336369", "https://api.thingspeak.com/channels/"); //load 1 sensor

                    push_status.setText("Load 1 " + field_json);
/*
                    if (field_json.compareTo(zero)==0) {
                        out1_status.setText("Device Unplugged");
                    }
                    if (field_json.compareTo(one)==0) {
                        out1_status.setText("Device plugged in");
                    }
 */               }
                if (count==2){
                    pullData("1", "S2CEY98ZBPJZC5MN","336370","https://api.thingspeak.com/channels/"); //load 2 sensor

                    push_status.setText("Load 2 " + field_json);

                    if (field_json == "0") {
                        out2_status.setText("Device Unplugged");
                    }
                    if (field_json == "1") {
                        out2_status.setText("Device plugged in");
                    }

                }
                if (count==3){
                    pullData("1", "4JZQUJ74J32CG0B2","336371","https://api.thingspeak.com/channels/"); //load 3 sensor

                    push_status.setText("Load 3 " + field_json);

                    if (field_json == "0") {
                        out3_status.setText("Device Unplugged");
                    }
                    if (field_json == "1") {
                        out3_status.setText("Device plugged in");
                    }

                }
                if (count>3){
                    pullData("1", "KDJJ75KTBLQZKDKM","336368","https://api.thingspeak.com/channels/"); //microwave sensor

                    push_status.setText("Movement " + field_json);

                    if (field_json == "0") {
                        detection_status.setText("Device Unplugged");
                    }
                    if (field_json == "1") {
                        detection_status.setText("Device plugged in");
                    }
                    count=0;
                }

                count++;
                handler.postDelayed(this,5000);
            }
        };

        handler.post(run);

        // FUNCTIONS FOR PUSHING DATA to THINGSPEAK FOR OUTLET CONTROL

        togglebtn_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    push_status.setText("OUTLET 1 ON");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "1", "L3UOJ3ZX7UIIFS0H", "https://api.thingspeak.com/update");
                }
                else if(!isChecked){
                    push_status.setText("OUTLET 1 OFF");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "0", "L3UOJ3ZX7UIIFS0H", "https://api.thingspeak.com/update");
                }
            }
        });

        togglebtn_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    push_status.setText("OUTLET 2 ON");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "1", "8JMJL8B7C24WQTC7", "https://api.thingspeak.com/update");
                }
                else if(!isChecked){
                    push_status.setText("OUTLET 2 OFF");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "0", "8JMJL8B7C24WQTC7", "https://api.thingspeak.com/update");
                }
            }
        });

        togglebtn_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    push_status.setText("OUTLET 3 ON");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "1", "BCHMAJCGEDVWNQNE", "https://api.thingspeak.com/update");
                }
                else if(!isChecked){
                    push_status.setText("OUTLET 3 OFF");
                    push_status.setVisibility(View.VISIBLE);
                    sendData("field1", "0", "BCHMAJCGEDVWNQNE", "https://api.thingspeak.com/update");
                }
            }
        });
    }

    private void sendData(final String valueid,final String value,final String write_api_key, String url) {
        String tag_json_obj = "json_obj_req";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?api_key=" + write_api_key + "&" + valueid + "=" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        push_success=response;
                        push_status.setText(push_success);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                push_success=error.toString();
                push_status.setText(push_success);
            }
        }){
        };

        MainApplication.getMainApplication(). addToRequestQueue(stringRequest, tag_json_obj);
    }


    private void pullData(final String field,final String read_api_key, String channel, String url) {
        String tag_json_obj = "json_obj_req";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + channel + "/fields/" + field + ".json?api_key=" + read_api_key + "&results=1", //just the last result should do
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject json = (JSONObject) new JSONTokener(response).nextValue();   //parsing the JSON response from thingspeak
                            JSONArray jsonarray = json.getJSONArray("feeds");   //feeds is a JSON array []
                            JSONObject fieldA = jsonarray.getJSONObject(0);
                            field_json = fieldA.getString("field1");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                push_success=error.toString();
                push_status.setText(push_success);
            }
        }){
        };

        MainApplication.getMainApplication(). addToRequestQueue(stringRequest, tag_json_obj);
    }

}
