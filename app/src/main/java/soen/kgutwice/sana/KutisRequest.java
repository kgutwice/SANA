package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class KutisRequest extends StringRequest {

    final static private String URL = "http://kutis.kyonggi.ac.kr/webkutis/view/hs/wslogin/loginCheck.jsp";

    private Map<String, String> parameters;

    public KutisRequest(String userId, String userPw, Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userPw", userPw);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
