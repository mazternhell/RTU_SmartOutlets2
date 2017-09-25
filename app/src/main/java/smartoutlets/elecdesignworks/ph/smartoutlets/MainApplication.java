package smartoutlets.elecdesignworks.ph.smartoutlets;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**
 * Created by Lenovo on 22/09/2017.
 */

public class MainApplication extends Application{
    private final String TAG = MainApplication.class.getSimpleName();
    private static MainApplication mainApplication;
    private RequestQueue requestQueue;

    public static MainApplication getMainApplication(){ return mainApplication;}

    @Override
    public void onCreate(){
        super.onCreate();
        mainApplication = this;
        requestQueue = Volley.newRequestQueue(mainApplication);
    }
    public void addToRequestQueue(Request request, String tag){
        request.setTag(tag == null ? TAG : tag);
        requestQueue.add(request);
    }
}
