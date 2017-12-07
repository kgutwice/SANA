package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class SettingRequest extends StringRequest {

    final static private String URL = "http://203.249.17.196:2013/ms/android/SANA_connector/modifySetting.php";

    private Map<String, String> parameters;

    public SettingRequest(String userID, String lastPlan, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("lastPlan", lastPlan);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
