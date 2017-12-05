package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        final String userID = getIntent().getStringExtra("userID");
        final String subjectName = getIntent().getStringExtra("subjectName");

        Toast.makeText(getApplicationContext(), subjectName, Toast.LENGTH_LONG).show();

        final TextView textViewAddTodoSubject = (TextView)findViewById(R.id.addTodoSubject);
        textViewAddTodoSubject.setText(subjectName);

        final EditText editTextaddTodoContent = (EditText)findViewById(R.id.addTodoContent);


        final Spinner spinnerLectureDayOfTheWeek = (Spinner)findViewById(R.id.addTodoCompletedDayYear);
        final Spinner spinnerStartTime = (Spinner)findViewById(R.id.addTodoCompletedDayMonth);
        final Spinner spinnerEndTime = (Spinner)findViewById(R.id.addTodoCompletedDayDay);
        final Spinner spinnerTakeClassYear = (Spinner)findViewById(R.id.addTodoActualCompletedDayYear);
        final Spinner spinnerTakeClassSemester = (Spinner)findViewById(R.id.addTodoActualCompletedDayMonth);
        final Spinner spinnerTakeClassSemester = (Spinner)findViewById(R.id.addTodoActualCompletedDayDay);


        Spinner addTodoCompletedDayYear =(Spinner)findViewById(R.id.addTodoCompletedDayYear);
        Integer[] YearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> addTodoYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, YearItems);
        addTodoCompletedDayYear.setAdapter(addTodoYearAdapter);

        Spinner addTodoCompletedDayMonth =(Spinner)findViewById(R.id.addTodoCompletedDayMonth);
        Integer[] MonthItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ArrayAdapter<Integer> addTodoMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MonthItems);
        addTodoCompletedDayMonth.setAdapter(addTodoMonthAdapter);

        Spinner addTodoCompletedDayDay =(Spinner)findViewById(R.id.addTodoCompletedDayDay);
        Integer[] DayItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        ArrayAdapter<Integer> addTodoDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DayItems);
        addTodoCompletedDayDay.setAdapter(addTodoDayAdapter);


        Spinner addTodoActualCompletedDayYear =(Spinner)findViewById(R.id.addTodoActualCompletedDayYear);
        addTodoActualCompletedDayYear.setAdapter(addTodoYearAdapter);

        Spinner addTodoActualCompletedDayMonth =(Spinner)findViewById(R.id.addTodoActualCompletedDayMonth);
        addTodoActualCompletedDayMonth.setAdapter(addTodoMonthAdapter);

        Spinner addTodoActualCompletedDayDay =(Spinner)findViewById(R.id.addTodoActualCompletedDayDay);
        addTodoActualCompletedDayDay.setAdapter(addTodoDayAdapter);



        Button addSubjectButton = (Button)findViewById(R.id.addSubjectButton);
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

                Toast.makeText(getApplicationContext(), userID + subjectName + subjectProfessor + lectureDayOfTheWeek + startTime + endTime + takeClassYear + takeClassSemester, Toast.LENGTH_LONG).show();

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

                AddSubjectRequest addSubjectRequest = new AddSubjectRequest(userID, subjectName, subjectProfessor, lectureDayOfTheWeek, startTime, endTime, takeClassYear, takeClassSemester, responseListener);

                RequestQueue queue = Volley.newRequestQueue(AddTodo.this);
                queue.add(addSubjectRequest);
            }
        });
    }

}
