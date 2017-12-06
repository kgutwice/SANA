package soen.kgutwice.sana;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        final String userID = getIntent().getStringExtra("userID");
        final String subjectName = getIntent().getStringExtra("subjectName");

        Toast.makeText(getApplicationContext(), subjectName + userID, Toast.LENGTH_LONG).show();

        final TextView textViewAddTodoSubject = (TextView)findViewById(R.id.addTodoSubject);
        textViewAddTodoSubject.setText(subjectName);

        final EditText editTextAddTodoContent = (EditText)findViewById(R.id.addTodoContent);

        final Spinner spinnerDeadlineYear = (Spinner)findViewById(R.id.addTodoDeadlineYear);
        final Spinner spinnerDeadlineMonth = (Spinner)findViewById(R.id.addTodoDeadlineMonth);
        final Spinner spinnerDeadlineDay = (Spinner)findViewById(R.id.addTodoDeadlineDay);
        final Spinner spinnerActualCompletedDayYear = (Spinner)findViewById(R.id.addTodoActualCompletedDayYear);
        final Spinner spinnerActualCompletedDayMonth = (Spinner)findViewById(R.id.addTodoActualCompletedDayMonth);
        final Spinner spinnerActualCompletedDayDay = (Spinner)findViewById(R.id.addTodoActualCompletedDayDay);

        final CheckBox checkBoxisCompleted = (CheckBox)findViewById(R.id.addTodoisCompleted);

        final RatingBar ratingBar = (RatingBar)findViewById(R.id.addTodoRatingBar);


        Spinner addTodoCompletedDayYear =(Spinner)findViewById(R.id.addTodoDeadlineYear);
        Integer[] YearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> addTodoYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, YearItems);
        addTodoCompletedDayYear.setAdapter(addTodoYearAdapter);

        Spinner addTodoCompletedDayMonth =(Spinner)findViewById(R.id.addTodoDeadlineMonth);
        Integer[] MonthItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ArrayAdapter<Integer> addTodoMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MonthItems);
        addTodoCompletedDayMonth.setAdapter(addTodoMonthAdapter);

        Spinner addTodoCompletedDayDay =(Spinner)findViewById(R.id.addTodoDeadlineDay);
        Integer[] DayItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        ArrayAdapter<Integer> addTodoDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DayItems);
        addTodoCompletedDayDay.setAdapter(addTodoDayAdapter);


        Spinner addTodoActualCompletedDayYear =(Spinner)findViewById(R.id.addTodoActualCompletedDayYear);
        addTodoActualCompletedDayYear.setAdapter(addTodoYearAdapter);

        Spinner addTodoActualCompletedDayMonth =(Spinner)findViewById(R.id.addTodoActualCompletedDayMonth);
        addTodoActualCompletedDayMonth.setAdapter(addTodoMonthAdapter);

        Spinner addTodoActualCompletedDayDay =(Spinner)findViewById(R.id.addTodoActualCompletedDayDay);
        addTodoActualCompletedDayDay.setAdapter(addTodoDayAdapter);


        final String todo = editTextAddTodoContent.getText().toString();
        final String deadline = spinnerDeadlineYear.getSelectedItem().toString() + spinnerDeadlineMonth.getSelectedItem().toString() + spinnerDeadlineDay.getSelectedItem().toString();
        final String actualCompletedDay = spinnerActualCompletedDayYear.getSelectedItem().toString() + spinnerActualCompletedDayMonth.getSelectedItem().toString() + spinnerActualCompletedDayDay.getSelectedItem().toString();
        final String completed = String.valueOf(checkBoxisCompleted.isChecked());
        final String importance = String.valueOf(ratingBar.getRating());

        Button addTodoButton = (Button)findViewById(R.id.addTodoButton);
        addTodoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 데이터 송신

                addTodoToDB(userID, todo, subjectName, deadline, actualCompletedDay, completed, importance);

            }
        });
    }


    void addTodoToDB(final String userID, final String todo, final String subjectName, final String deadline, final String actualCompletedDay, final String completed, final String importance){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/addTodo.php";

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
                params.put("todo", todo);
                params.put("subjectName", subjectName);
                params.put("deadline", deadline);
                params.put("actualCompletedDay", actualCompletedDay);
                params.put("completed", completed);
                params.put("importance", importance);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
