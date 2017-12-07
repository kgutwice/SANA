package soen.kgutwice.sana;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifySubject extends AppCompatActivity {

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_subject);

        //userID = getIntent().getStringExtra("userID");
        String asdf = getIntent().getStringExtra("subjectName");
        Toast.makeText(getApplicationContext(), asdf, Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editTextSubjectName = (EditText)findViewById(R.id.modifySubjectName);
        final EditText editTextSubjectProfessor = (EditText)findViewById(R.id.modifySubjectProfessor);
        final Spinner spinnerLectureDayOfTheWeek = (Spinner)findViewById(R.id.modifySubjectLectureDayOfTheWeek);
        final Spinner spinnerStartTime = (Spinner)findViewById(R.id.modifySubjectStartTime);
        final Spinner spinnerEndTime = (Spinner)findViewById(R.id.modifySubjectEndTime);
        final Spinner spinnerTakeClassYear = (Spinner)findViewById(R.id.modifySubjectTakeClassYear);
        final Spinner spinnerTakeClassSemester = (Spinner)findViewById(R.id.modifySubjectTakeClassSemester);

        Spinner modifySubjectLectureDayOfTheWeek =(Spinner)findViewById(R.id.modifySubjectLectureDayOfTheWeek);
        String[] lectureDayOfTheWeekItems = new String[]{"월", "화", "수", "목", "금", "토", "일"};
        ArrayAdapter<String> lectureDayOfTheWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lectureDayOfTheWeekItems);
        modifySubjectLectureDayOfTheWeek.setAdapter(lectureDayOfTheWeekAdapter);

        Spinner modifySubjectStartTime =(Spinner)findViewById(R.id.modifySubjectStartTime);
        String[] startTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        ArrayAdapter<String> startTimeitemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, startTimeItems);
        modifySubjectStartTime.setAdapter(startTimeitemsAdapter);
        // 되돌아가게? 원형으로?

        Spinner modifySubjectEndTime =(Spinner)findViewById(R.id.modifySubjectEndTime);
        String[] endTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, endTimeItems);
        modifySubjectEndTime.setAdapter(endTimeAdapter);

        Spinner modifySubjectTakeClassYear =(Spinner)findViewById(R.id.modifySubjectTakeClassYear);
        Integer[] takeClassYearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> takeClassYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassYearItems);
        modifySubjectTakeClassYear.setAdapter(takeClassYearAdapter);
        // 끝없이?

        Spinner modifySubjectTakeClassSemester =(Spinner)findViewById(R.id.modifySubjectTakeClassSemester);
        Integer[] takeClassSemesterItems = new Integer[]{1, 2};
        ArrayAdapter<Integer> takeClassSemesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassSemesterItems);
        modifySubjectTakeClassSemester.setAdapter(takeClassSemesterAdapter);

        final String subjectName = editTextSubjectName.getText().toString();
        final String subjectProfessor = editTextSubjectProfessor.getText().toString();
        final String lectureDayOfTheWeek = spinnerLectureDayOfTheWeek.getSelectedItem().toString();
        final String startTime = spinnerStartTime.getSelectedItem().toString();
        final String endTime = spinnerEndTime.getSelectedItem().toString();
        final String takeClassYear = spinnerTakeClassYear.getSelectedItem().toString();
        final String takeClassSemester = spinnerTakeClassSemester.getSelectedItem().toString();

        Button modifySubjectButton = (Button)findViewById(R.id.modifySubjectButton);
        modifySubjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 데이터 송신
                modifySubjectToDB(userID, subjectName, subjectProfessor, lectureDayOfTheWeek, startTime, endTime, takeClassYear, takeClassSemester);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.subjectList) {
            Intent intent = new Intent(getApplicationContext(), SubjectList.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void modifySubjectToDB(final String userID, final String subjectName, final String subjectProfessor, final String lectureDayOfTheWeek, final String startTime, final String endTime, final String takeClassYear, final String takeClassSemester){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/modifySubject.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject reader = new JSONObject(response);
                            boolean success = reader.getBoolean("success");
                            if(success) {
                                Intent intent = new Intent(getApplicationContext(), SubjectList.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID", userID);
                params.put("subjectName", subjectName);
                params.put("subjectProfessor", subjectProfessor);
                params.put("lectureDayOfTheWeek", lectureDayOfTheWeek);
                params.put("startTime", startTime);
                params.put("endTime", endTime);
                params.put("takeClassYear", takeClassYear);
                params.put("takeClassSemester", takeClassSemester);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void deleteSubjectToDB(final String userID, final String subjectName){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/modifySubject.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject reader = new JSONObject(response);
                            boolean success = reader.getBoolean("success");
                            if(success) {
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID", userID);
                params.put("subjectName", subjectName);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
