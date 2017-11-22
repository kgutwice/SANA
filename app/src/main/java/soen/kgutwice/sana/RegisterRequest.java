package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://203.249.17.196:2013/ms/android/register.php";
    final static private String URL2 = "http://203.249.17.196:2013/ms/android/idcheck.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userId, String userPw, String userName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userPw", userPw);
        parameters.put("userName", userName);
    }

    public RegisterRequest(String userId, Response.Listener<String> listener){
        super(Method.POST, URL2, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
