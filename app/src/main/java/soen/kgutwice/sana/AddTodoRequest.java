package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class AddTodoRequest extends StringRequest {

    final static private String URL = "http://203.249.17.196:2013/ms/android/SANA_connector/addTodo.php";

    private Map<String, String> parameters;

    public AddTodoRequest(String userID, String todoName, String subjectName, String deadline, String actualCompletedDay, String completed, String importance, String takeClassYear, String takeClassSemester, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("todoName", todoName);
        parameters.put("subjectName", subjectName);
        parameters.put("deadline", deadline);
        parameters.put("actualCompletedDay", actualCompletedDay);
        parameters.put("completed", completed);
        parameters.put("importance", importance);
        parameters.put("takeClassYear", takeClassYear);
        parameters.put("takeClassSemester", takeClassSemester);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
