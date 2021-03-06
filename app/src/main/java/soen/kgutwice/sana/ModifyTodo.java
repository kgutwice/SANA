package soen.kgutwice.sana;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModifyTodo extends AppCompatActivity {

    String userID;
    String todo;
    String subjectName;
    String todoName;
    String deadLine;
    String actualDeaLine;
    String completed;
    String importance;
    String takeClassYear;
    String takeClassSemester;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_todo);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Todo 수정");


        final String getUserID = getIntent().getStringExtra("userID");
        userID = getUserID;
        subjectName = getIntent().getStringExtra("subjectName");
        todo = getIntent().getStringExtra("todo");

        final String no = getIntent().getStringExtra("no");

        final TextView textViewModifyTodoSubject = (TextView)findViewById(R.id.modifyTodoSubject);
        textViewModifyTodoSubject.setText(subjectName);

        final EditText editTextModifyTodoContent = (EditText)findViewById(R.id.modifyTodoContent);

        final Spinner spinnerDeadlineYear = (Spinner)findViewById(R.id.modifyTodoDeadlineYear);
        final Spinner spinnerDeadlineMonth = (Spinner)findViewById(R.id.modifyTodoDeadlineMonth);
        final Spinner spinnerDeadlineDay = (Spinner)findViewById(R.id.modifyTodoDeadlineDay);
        final Spinner spinnerActualCompletedDayYear = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayYear);
        final Spinner spinnerActualCompletedDayMonth = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayMonth);
        final Spinner spinnerActualCompletedDayDay = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayDay);

        final CheckBox checkBoxisCompleted = (CheckBox)findViewById(R.id.modifyTodoisCompleted);

        final RatingBar ratingBar = (RatingBar)findViewById(R.id.modifyTodoRatingBar);


        final Spinner modifyTodoCompletedDayYear = (Spinner)findViewById(R.id.modifyTodoDeadlineYear);
        final Integer[] deadlineYearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        final ArrayAdapter<Integer> modifyTodoDeadlineYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deadlineYearItems);
        modifyTodoCompletedDayYear.setAdapter(modifyTodoDeadlineYearAdapter);

        final Spinner modifyTodoCompletedDayMonth = (Spinner)findViewById(R.id.modifyTodoDeadlineMonth);
        final Integer[] deadlineMonthItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final ArrayAdapter<Integer> modifyTodoDeadlineMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deadlineMonthItems);
        modifyTodoCompletedDayMonth.setAdapter(modifyTodoDeadlineMonthAdapter);

        final Spinner modifyTodoCompletedDayDay = (Spinner)findViewById(R.id.modifyTodoDeadlineDay);
        final Integer[] deadlineDayItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        final ArrayAdapter<Integer> modifyTodoDeadlineDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deadlineDayItems);
        modifyTodoCompletedDayDay.setAdapter(modifyTodoDeadlineDayAdapter);

        final Spinner modifyTodoActualCompletedDayYear = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayYear);
        final Integer[] completedDayYearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        final ArrayAdapter<Integer> modifyTodoCompletedDayYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, completedDayYearItems);
        modifyTodoActualCompletedDayYear.setAdapter(modifyTodoCompletedDayYearAdapter);

        final Spinner modifyTodoActualCompletedDayMonth = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayMonth);
        final Integer[] completedDayMonthItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final ArrayAdapter<Integer> modifyTodoCompletedDayMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, completedDayMonthItems);
        modifyTodoActualCompletedDayMonth.setAdapter(modifyTodoCompletedDayMonthAdapter);

        final Spinner modifyTodoActualCompletedDayDay = (Spinner)findViewById(R.id.modifyTodoActualCompletedDayDay);
        final Integer[] completedDayDayItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        final ArrayAdapter<Integer> modifyTodoCompletedDayDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, completedDayDayItems);
        modifyTodoActualCompletedDayDay.setAdapter(modifyTodoCompletedDayDayAdapter);



        Button modifyTodoButton = (Button)findViewById(R.id.modifyTodoButton);
        modifyTodoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 데이터 송신

                final String todo = editTextModifyTodoContent.getText().toString();
                final String deadline = spinnerDeadlineYear.getSelectedItem().toString() + "." + spinnerDeadlineMonth.getSelectedItem().toString() + "." + spinnerDeadlineDay.getSelectedItem().toString();
                final String actualCompletedDay = spinnerActualCompletedDayYear.getSelectedItem().toString() + "." + spinnerActualCompletedDayMonth.getSelectedItem().toString() + "." + spinnerActualCompletedDayDay.getSelectedItem().toString();
                final String completed;
                if(checkBoxisCompleted.isChecked())
                    completed = "1";
                else
                    completed = "0";
                final String importance = String.valueOf(ratingBar.getRating());

                modifyTodoToDB(no, userID, todo, subjectName, deadline, actualCompletedDay, completed, importance, "2017", "2");
            }
        });





        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("tset",response.toString());
                try {
                    JSONObject  res = new JSONObject(response);
                    boolean success = res.getBoolean("success");
                    JSONArray data = (JSONArray)res.get("data");
                    if(success){
                        JSONObject r = data.getJSONObject(0);
                        todoName = r.getString("todoName");
                        deadLine = r.getString("deadLine");
                        actualDeaLine = r.getString("actualDeadLine");
                        completed = r.getString("completed");
                        importance = r.getString("importance");
                        takeClassYear = r.getString("takeClassYear");
                        takeClassSemester = r.getString("takeClassSemester");

                        Log.i("test", todoName);
                        Log.i("test", subjectName);
                        Log.i("test", deadLine);
                        Log.i("test", actualDeaLine);
                        Log.i("test", completed);
                        Log.i("test", importance);
                        Log.i("test", takeClassSemester);
                        Log.i("test", takeClassYear);

                        Log.i("dd",deadLine.split("\\.")[0]);
                        int cdy=Integer.parseInt(deadLine.split("\\.")[0]);
                        int cdm=Integer.parseInt(deadLine.split("\\.")[1]);
                        int cdd=Integer.parseInt(deadLine.split("\\.")[2]);
                        int ady=Integer.parseInt(actualDeaLine.split("\\.")[0]);
                        int adm=Integer.parseInt(actualDeaLine.split("\\.")[1]);
                        int add=Integer.parseInt(actualDeaLine.split("\\.")[2]);

                        editTextModifyTodoContent.setText(todoName);
                        modifyTodoCompletedDayYear.setSelection(Arrays.binarySearch(deadlineYearItems, cdy));
                        modifyTodoCompletedDayMonth.setSelection(Arrays.binarySearch(deadlineMonthItems, cdm));
                        modifyTodoCompletedDayDay.setSelection(Arrays.binarySearch(deadlineDayItems, cdd));
                        modifyTodoActualCompletedDayYear.setSelection(Arrays.binarySearch(completedDayYearItems, ady));
                        modifyTodoActualCompletedDayMonth.setSelection(Arrays.binarySearch(completedDayMonthItems, adm));
                        modifyTodoActualCompletedDayDay.setSelection(Arrays.binarySearch(completedDayDayItems, add));

                        modifyTodoDeadlineYearAdapter.notifyDataSetChanged();
                        modifyTodoDeadlineMonthAdapter.notifyDataSetChanged();
                        modifyTodoDeadlineDayAdapter.notifyDataSetChanged();
                        modifyTodoCompletedDayYearAdapter.notifyDataSetChanged();
                        modifyTodoCompletedDayMonthAdapter.notifyDataSetChanged();
                        modifyTodoCompletedDayDayAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // Add the request to the RequestQueue.
        AllData ad = new AllData();
        ModifyTodoGetRequest modifySubjectGetRequest = new ModifyTodoGetRequest(userID, todo, subjectName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ModifyTodo.this);
        queue.add(modifySubjectGetRequest);



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
        if (id == R.id.delItem) {

            deleteTodoToDB(userID, todo);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void modifyTodoToDB(final String no, final String userID, final String todo, final String subjectName, final String deadline, final String actualCompletedDay, final String completed, final String importance, final String takeClassYear, final String takeClassSemester){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/modifyTodo.php";

        Toast.makeText(getApplicationContext(), no + " " + userID + " " + subjectName + " " + deadline + " " + actualCompletedDay + " " + completed + " " + importance + " " + takeClassYear + " " + takeClassSemester, Toast.LENGTH_LONG).show();

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
                                Toast.makeText(getApplicationContext(), "요청이 실패했습니다. 1", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "요청이 실패했습니다. 2", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("no", no);
                params.put("userID", userID);
                params.put("todo", todo);
                params.put("subjectName", subjectName);
                params.put("deadline", deadline);
                params.put("actualCompletedDay", actualCompletedDay);
                params.put("completed", completed);
                params.put("importance", importance);
                params.put("takeClassYear", takeClassYear);
                params.put("takeClassSemester", takeClassSemester);


                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void deleteTodoToDB(final String userID, final String todo){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/deleteTodo.php";

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
                params.put("todo", todo);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
