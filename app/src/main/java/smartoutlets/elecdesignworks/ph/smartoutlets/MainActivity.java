package smartoutlets.elecdesignworks.ph.smartoutlets;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import java.util.concurrent.CountDownLatch;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = MainActivity.class.getSimpleName();
    private Button  btn_1;
    private Button  btn_2;
    private Button  btn_3;
    private Button  btn_4;
    private Button  btn_5;
    private Button  btn_6;
    private TextView push_status;
    private TextView out1_status;
    private TextView out2_status;
    private TextView out3_status;
    private TextView detection_status;

    final CountDownLatch countDownLatch = new CountDownLatch(1);

    String push_success;
    String field_json;
    int result,motion,load1,load2,load3,out1,out2,out3;

    int count=1;
    private MainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApplication = MainApplication.getMainApplication();

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);

        push_status = (TextView) findViewById(R.id.push_status);
        out1_status = (TextView) findViewById(R.id.out1_status);
        out2_status = (TextView) findViewById(R.id.out2_status);
        out3_status = (TextView) findViewById(R.id.out3_status);
        detection_status = (TextView) findViewById(R.id.detection_status);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);

        final android.os.Handler handler = new android.os.Handler();
        Runnable run = new Runnable() {

            @Override
            public void run() {

                //    pullData(final String results,final String field,final String read_api_key, String channel, String url)

                if (count == 1) {
                    motion = pullData("1", "KDJJ75KTBLQZKDKM", "336368", "https://api.thingspeak.com/channels/"); //LOAD 1 sensor
                  //    motion = pullData("1", "4JZQUJ74J32CG0B2", "336371", "https://api.thingspeak.com/channels/"); //LOAD 1 sensor

                    if (motion == 0) {
                        detection_status.setText("NO MOTION DETECTED");
                        detection_status.setTextColor(Color.RED);
                    }
                    if (motion == 1) {
                        detection_status.setText("MOTION DETECTED");
                        detection_status.setTextColor(Color.GREEN);
                    }
                }

                if (count == 2) {
                    load1 = pullData("1", "4LZPK4K4DNTMROS1", "336369", "https://api.thingspeak.com/channels/"); //LOAD 2 Sensor
                    //load1 = pullData("1", "KDJJ75KTBLQZKDKM", "336368", "https://api.thingspeak.com/channels/"); //LOAD 2 Sensor

                    if (load1 == 0) {
                        out1_status.setText("Device Unplugged");
                        out1_status.setTextColor(Color.RED);
                    }
                    if (load1 == 1) {
                        out1_status.setText("Device plugged in");
                        out1_status.setTextColor(Color.GREEN);

                    }
                }
                if (count == 3) {
                    load2 = pullData("1", "S2CEY98ZBPJZC5MN", "336370", "https://api.thingspeak.com/channels/"); //LOAD 3 sensor
                    //load2 = pullData("1", "4LZPK4K4DNTMROS1", "336369", "https://api.thingspeak.com/channels/"); //LOAD 3 sensor

                    if (load2 == 0) {
                        out2_status.setText("Device Unplugged");
                        out2_status.setTextColor(Color.RED);
                    }
                    if (load2 == 1) {
                        out2_status.setText("Device plugged in");
                        out2_status.setTextColor(Color.GREEN);
                    }

                }

                if (count == 4) {
                    load3 = pullData("1", "WPC1Y6PMG5Y5XQNC", "336357", "https://api.thingspeak.com/channels/"); //Motion sensor
//                    load3 = pullData("1", "S2CEY98ZBPJZC5MN", "336370", "https://api.thingspeak.com/channels/"); //Motion sensor
                    if (load3 == 0) {
                 //       pullData("1", "WPC1Y6PMG5Y5XQNC", "336357", "https://api.thingspeak.com/channels/"); //Motion sensor
                        out3_status.setText("Device Unplugged");
                        out3_status.setTextColor(Color.RED);
                    }
                    if (load3 == 1) {
                        out3_status.setText("Device plugged in");
                        out3_status.setTextColor(Color.GREEN);

                    }
//                    load3 = pullData("1", "WPC1Y6PMG5Y5XQNC", "336357", "https://api.thingspeak.com/channels/"); //Motion sensor
                }

                if (count == 5) {

                  out1 = pullData("1", "Q1NNITEM9903EUSQ", "336363", "https://api.thingspeak.com/channels/"); //Motion sensor
//                    out1 = pullData("1", "WPC1Y6PMG5Y5XQNC", "336357", "https://api.thingspeak.com/channels/"); //Motion sensor

                    if(out1==0){
                        btn_1.setBackgroundColor(Color.GRAY);
                        btn_2.setBackgroundColor(Color.RED);
                    }
                    else if(out1>0){
                        btn_1.setBackgroundColor(Color.GREEN);
                        btn_2.setBackgroundColor(Color.GRAY);
                    }

                }
                if (count == 6) {
                    out2 = pullData("1", "1TS5ALVNZXAJOA33", "336364", "https://api.thingspeak.com/channels/"); //Motion sensor
//                    out2 = pullData("1", "Q1NNITEM9903EUSQ", "336363", "https://api.thingspeak.com/channels/"); //Motion sensor

                    if(out2==0){
                        btn_3.setBackgroundColor(Color.GRAY);
                        btn_4.setBackgroundColor(Color.RED);
                    }
                    else if(out2>0){
                        btn_3.setBackgroundColor(Color.GREEN);
                        btn_4.setBackgroundColor(Color.GRAY);
                    }

                }
                if (count == 7) {
                    out3 = pullData("1", "4JZQUJ74J32CG0B2", "336371", "https://api.thingspeak.com/channels/"); //Motion sensor
//                    out3 = pullData("1", "1TS5ALVNZXAJOA33", "336364", "https://api.thingspeak.com/channels/"); //Motion sensor
                    if(out3==0){
                        btn_5.setBackgroundColor(Color.GRAY);
                        btn_6.setBackgroundColor(Color.RED);
                    }
                    else if(out3>0){
                        btn_5.setBackgroundColor(Color.GREEN);
                        btn_6.setBackgroundColor(Color.GRAY);
                    }
                    count = 0;
                }

                count++;
                handler.postDelayed(this, 2000);
            }
        };

        handler.post(run);
    }

        // FUNCTIONS FOR PUSHING DATA to THINGSPEAK FOR OUTLET CONTROL

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_1:
                    sendData("field1", "1", "L3UOJ3ZX7UIIFS0H", "https://api.thingspeak.com/update");

                    if(result==0){
                        btn_1.setBackgroundColor(Color.GRAY);
                        btn_2.setBackgroundColor(Color.RED);
                    }
                        else if(result>0){
                        btn_1.setBackgroundColor(Color.GREEN);
                        btn_2.setBackgroundColor(Color.GRAY);
                    }
                    break;

                case R.id.btn_2:
                    sendData("field1", "0", "L3UOJ3ZX7UIIFS0H", "https://api.thingspeak.com/update");
                    if (result==0){
                        btn_1.setBackgroundColor(Color.GREEN);
                        btn_2.setBackgroundColor(Color.GRAY);
                    }
                        else if (result>0){
                        btn_1.setBackgroundColor(Color.GRAY);
                        btn_2.setBackgroundColor(Color.RED);
                    }
                    break;

                case R.id.btn_3:
                    sendData("field1", "1", "8JMJL8B7C24WQTC7", "https://api.thingspeak.com/update");
                    if(result==0){
                        btn_3.setBackgroundColor(Color.GRAY);
                        btn_4.setBackgroundColor(Color.RED);
                    }
                    else if(result>0){
                        btn_3.setBackgroundColor(Color.GREEN);
                        btn_4.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case R.id.btn_4:
                    sendData("field1", "0", "8JMJL8B7C24WQTC7", "https://api.thingspeak.com/update");
                    if (result==0){
                        btn_3.setBackgroundColor(Color.GREEN);
                        btn_4.setBackgroundColor(Color.GRAY);
                    }
                    else if (result>0){
                        btn_3.setBackgroundColor(Color.GRAY);
                        btn_4.setBackgroundColor(Color.RED);
                    }
                    break;
                case R.id.btn_5:
                    sendData("field1", "1", "BCHMAJCGEDVWNQNE", "https://api.thingspeak.com/update");
                    if(result==0){
                        btn_5.setBackgroundColor(Color.GRAY);
                        btn_6.setBackgroundColor(Color.RED);
                    }
                    else if(result>0){
                        btn_5.setBackgroundColor(Color.GREEN);
                        btn_6.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case R.id.btn_6:
                    sendData("field1", "0", "BCHMAJCGEDVWNQNE", "https://api.thingspeak.com/update");
                    if (result==0){
                        btn_5.setBackgroundColor(Color.GREEN);
                        btn_6.setBackgroundColor(Color.GRAY);
                    }
                    else if (result>0){
                        btn_5.setBackgroundColor(Color.GRAY);
                        btn_6.setBackgroundColor(Color.RED);
                    }
                    break;
            }

        }

    private int sendData(final String valueid, final String value, final String write_api_key, String url) {
        String tag_json_obj = "json_obj_req";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "?api_key=" + write_api_key + "&" + valueid + "=" + value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        push_success=response;
                        result = Integer.parseInt(push_success);
                        push_status.setText(push_success);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                push_success=error.toString();
                result = Integer.parseInt(push_success);
                push_status.setText(push_success);
            }
        }){
        };

        MainApplication.getMainApplication(). addToRequestQueue(stringRequest, tag_json_obj);
        return result;
    }


    private int pullData(final String field, final String read_api_key, String channel, String url) {
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
                            result = Integer.parseInt(field_json);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        countDownLatch.countDown();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                push_success=error.toString();
                push_status.setText(push_success);
                countDownLatch.countDown();

            }
        }){
        };

        MainApplication.getMainApplication(). addToRequestQueue(stringRequest, tag_json_obj);
        return result;
    }

}
