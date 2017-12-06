package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SemesterTodoList extends AppCompatActivity {

    String userID;
    Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getStringExtra("userID");

        // 여기는 한 학기의 모든 일정을 보는 곳입니다.

        final ListView listView;

        userID = getIntent().getStringExtra("userID");

        listView = (ListView)findViewById(R.id.semesterTodoListView);

        final Spinner askYear = (Spinner)findViewById(R.id.askYear);
        Integer[] YearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> YearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, YearItems);
        askYear.setAdapter(YearAdapter);

        final Spinner askSemester = (Spinner)findViewById(R.id.askSemester);
        Integer[] SemesterItems = new Integer[]{1, 2};
        ArrayAdapter<Integer> SemesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SemesterItems);
        askSemester.setAdapter(SemesterAdapter);

        //서버로부터 데이터 받아오기
        responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray data = (JSONArray)jsonResponse.get("data");
                    final TodoAdapter todoAdapter;
                    todoAdapter = new TodoAdapter();
                    listView.setAdapter(todoAdapter);

                    if(success) {
                        for(int i=0; i<data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);
                            //todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
                            Log.i("tt", d.toString());
                            todoAdapter.addItem(d.getString("todoName"), d.getString("subjectName"), d.getString("deadLine"), d.getString("actualDeadLine"),d.getBoolean("completed"), d.getInt("importance"));
                        }
                        todoAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                        // 아니면 아무것도 없어도 뜸
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SemesterTodoListRequest semesterTodoListRequest = new SemesterTodoListRequest(userID,"2017","1",responseListener);

        RequestQueue queue = Volley.newRequestQueue(SemesterTodoList.this);
        queue.add(semesterTodoListRequest);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent intent = new Intent(getApplicationContext(), ModifyTodo.class);
                        intent.putExtra("userID", userID);
                        TodoItem todoItem = (TodoItem)parent.getAdapter().getItem(i);
                        String subjectName = todoItem.getSubject();
                        intent.putExtra("subjectName", subjectName);
                        startActivity(intent);
                    }
                }
        );

        askYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = askYear.getSelectedItem().toString();
                String semester = askSemester.getSelectedItem().toString();

                SemesterTodoListRequest semesterTodoListRequest = new SemesterTodoListRequest(userID, year, semester, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SemesterTodoList.this);
                queue.add(semesterTodoListRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        askSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = askYear.getSelectedItem().toString();
                String semester = askSemester.getSelectedItem().toString();

                SemesterTodoListRequest semesterTodoListRequest = new SemesterTodoListRequest(userID, year, semester, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SemesterTodoList.this);
                queue.add(semesterTodoListRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_menu, menu);
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


}
