package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        final String userId = getIntent().getStringExtra("userId");

        final EditText editTextSubjectName = (EditText)findViewById(R.id.subjectName);
        final EditText editTextSubjectProfessor = (EditText)findViewById(R.id.subjectProfessor);
        final Spinner spinnerLectureDayOfTheWeek = (Spinner)findViewById(R.id.lectureDayOfTheWeek);
        final Spinner spinnerStartTime = (Spinner)findViewById(R.id.startTime);
        final Spinner spinnerEndTime = (Spinner)findViewById(R.id.endTime);
        final Spinner spinnerTakeClassYear = (Spinner)findViewById(R.id.takeClassYear);
        final Spinner spinnerTakeClassSemester = (Spinner)findViewById(R.id.takeClassSemester);

        Spinner lectureDayOfTheWeek =(Spinner)findViewById(R.id.lectureDayOfTheWeek);
        String[] lectureDayOfTheWeekItems = new String[]{"월", "화", "수", "목", "금", "토", "일"};
        ArrayAdapter<String> lectureDayOfTheWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lectureDayOfTheWeekItems);
        lectureDayOfTheWeek.setAdapter(lectureDayOfTheWeekAdapter);

        Spinner startTime =(Spinner)findViewById(R.id.startTime);
        String[] startTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        ArrayAdapter<String> startTimeitemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, startTimeItems);
        startTime.setAdapter(startTimeitemsAdapter);
        // 되돌아가게? 원형으로?

        Spinner endTime =(Spinner)findViewById(R.id.endTime);
        String[] endTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, endTimeItems);
        endTime.setAdapter(endTimeAdapter);

        Spinner takeClassYear =(Spinner)findViewById(R.id.takeClassYear);
        Integer[] takeClassYearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> takeClassYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassYearItems);
        takeClassYear.setAdapter(takeClassYearAdapter);
        // 끝없이?

        Spinner takeClassSemester =(Spinner)findViewById(R.id.takeClassSemester);
        Integer[] takeClassSemesterItems = new Integer[]{1, 2};
        ArrayAdapter<Integer> takeClassSemesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassSemesterItems);
        takeClassSemester.setAdapter(takeClassSemesterAdapter);

        Button addSubjectButton = (Button)findViewById(R.id.submitButton);
        addSubjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 데이터 송신

                String subjectName = editTextSubjectName.getText().toString();
                String subjectProfessor = editTextSubjectProfessor.getText().toString();
                String lectureDayOfTheWeek = spinnerLectureDayOfTheWeek.getSelectedItem().toString();
                String startTime = spinnerStartTime.getSelectedItem().toString();
                String endTime = spinnerEndTime.getSelectedItem().toString();
                String takeClassYear = spinnerTakeClassYear.getSelectedItem().toString();
                String takeClassSemester = spinnerTakeClassSemester.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), "sms2831" + subjectName + subjectProfessor + lectureDayOfTheWeek + startTime + endTime + takeClassYear + takeClassSemester, Toast.LENGTH_LONG).show();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonResponse = new JSONObject(s);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(getApplicationContext(), SubjectList.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddSubjectRequest addSubjectRequest = new AddSubjectRequest("testid", subjectName, subjectProfessor, lectureDayOfTheWeek, startTime, endTime, takeClassYear, takeClassSemester, responseListener);

                RequestQueue queue = Volley.newRequestQueue(AddSubject.this);
                queue.add(addSubjectRequest);
            }
        });
    }

}
