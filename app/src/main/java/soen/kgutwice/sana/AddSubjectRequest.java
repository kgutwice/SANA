package soen.kgutwice.sana;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minsung on 2017-11-16.
 */

public class AddSubjectRequest extends StringRequest {

    final static private String URL = "http://203.249.17.196:2013/ms/android/SANA_connector/addLecture.php";

    private Map<String, String> parameters;

    public AddSubjectRequest(String userID, String subjectName, String subjectProfessor, String lectureDayOfTheWeek, String startTime, String endTime, String takeClassYear, String takeClassSemester, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("subjectName", subjectName);
        parameters.put("subjectProfessor", subjectProfessor);
        parameters.put("lectureDayOfTheWeek", lectureDayOfTheWeek);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
        parameters.put("takeClassYear", takeClassYear);
        parameters.put("takeClassSemester", takeClassSemester);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
