package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class AddSubjectRequest extends StringRequest {

    final static private String URL = "http://203.249.17.196:2013/ms/android/addLecture.php";

    private Map<String, String> parameters;

    public AddSubjectRequest(String datas, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("parameters", datas);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
