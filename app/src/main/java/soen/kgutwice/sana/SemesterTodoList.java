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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SemesterTodoList extends AppCompatActivity {

    static boolean subjectOrderCheck = true; // true이면 오름차순 false이면 내림차순
    static boolean deadlineOrder = true; // true이면 오름차순 false이면 내림차순
    static boolean actualCompletedDayOrder = true; // true이면 오름차순 false이면 내림차순
    static boolean notCompletedOrder = true; // true이면 미완료 먼저 false이면 완료 먼저

    String userID;
    Response.Listener<String> responseListener;
    int dataLength;
    ArrayList<TodoItem> TodoList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getStringExtra("userID");

        // 여기는 한 학기의 모든 일정을 보는 곳입니다.

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

                    TodoList = new ArrayList<>();

                    if(success) {
                        dataLength = data.length();
                        for(int i=0; i<data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);

                            TodoItem todoItem = new TodoItem();
                            JSONObject todo = data.getJSONObject(i);
                            todoItem.setTodo(todo.getString("todoName"));
                            todoItem.setSubject(todo.getString("subjectName"));
                            todoItem.setDeadline(todo.getString("deadLine"));
                            todoItem.setActualCompletedDay(todo.getString("actualDeadLine"));
                            todoItem.setCompleted(todo.getBoolean("completed"));
                            todoItem.setImportance(todo.getInt("importance"));
                            todoItem.setClassYear(todo.getInt("takeClassYear"));
                            todoItem.setClassSemester(todo.getInt("takeClassSemester"));

                            TodoList.add(todoItem);

                            //todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
                            Log.i("tt", d.toString());
                            todoAdapter.addItem(d.getString("todoName"), d.getString("subjectName"), d.getString("deadLine"), d.getString("actualDeadLine"),d.getBoolean("completed"), d.getInt("importance"), d.getInt("takeClassYear"), d.getInt("takeClassSemester"));
                        }
                        todoAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                        // 에러가 아니여도 아무 리스트도 받아오지 못해도 뜸
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
        if (id == R.id.subjectOrder) {
            listView.setAdapter(null);
            final TodoAdapter todoAdapter;
            todoAdapter = new TodoAdapter();
            listView.setAdapter(todoAdapter);

            ArrayList<String> subjects = new ArrayList<>();
            Map<Integer, String> mapping = new HashMap<>();

            for(int i=0; i<dataLength; i++){
                TodoItem todo = TodoList.get(i);
                mapping.put(i, todo.getSubject());
                subjects.add(todo.getSubject());
            }

            Collections.sort(subjects, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            ArrayList<Integer> order = new ArrayList<>();
            for(int i=0; i<dataLength; i++){ // 전체 iter
                for(int j=0; j<dataLength; j++){ // 검색 iter
                    if(subjects.get(i) == mapping.get(j)){
                        order.add(j);
                    }
                }
            }

            for(int i=0; i<dataLength; i++){
                TodoItem todoItem;
                int j = order.get(i);
                todoItem = TodoList.get(j);
                todoAdapter.addItem(todoItem.getTodo(), todoItem.getSubject(), todoItem.getDeadline(), todoItem.getActualCompletedDay(), todoItem.getCompleted(), todoItem.getImportance(), todoItem.getClassYear(), todoItem.getClassSemester());
            }

            return true;

        } else if(id == R.id.deadlineOrder){

            listView.setAdapter(null);
            final TodoAdapter todoAdapter;
            todoAdapter = new TodoAdapter();
            listView.setAdapter(todoAdapter);

            ArrayList<String> deadlines = new ArrayList<>();
            Map<Integer, String> mapping = new HashMap<>();

            for(int i=0; i<dataLength; i++){
                TodoItem todo = TodoList.get(i);
                String deadline = todo.getDeadline();
                String[] yearMonthDay = deadline.split(".");
                String year = yearMonthDay[0];
                String month = yearMonthDay[1];
                String day = yearMonthDay[2];
                if (Integer.parseInt(month) < 10){
                    month = "0" + month;
                }
                if (Integer.parseInt(day) < 10){
                    day = "0" + day;
                }
                deadline = year + month + day;

                mapping.put(i, deadline);
                deadlines.add(deadline);
            }

            Collections.sort(deadlines, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            ArrayList<Integer> order = new ArrayList<>();
            for(int i=0; i<dataLength; i++){ // 전체 iter
                for(int j=0; j<dataLength; j++){ // 검색 iter
                    if(deadlines.get(i) == mapping.get(j)){
                        order.add(j);
                    }
                }
            }

            for(int i=0; i<dataLength; i++){
                TodoItem todoItem;
                int j = order.get(i);
                todoItem = TodoList.get(j);
                todoAdapter.addItem(todoItem.getTodo(), todoItem.getSubject(), todoItem.getDeadline(), todoItem.getActualCompletedDay(), todoItem.getCompleted(), todoItem.getImportance(), todoItem.getClassYear(), todoItem.getClassSemester());
            }

            return true;

        } else if(id == R.id.actualCompletedDayOrder){

            listView.setAdapter(null);
            final TodoAdapter todoAdapter;
            todoAdapter = new TodoAdapter();
            listView.setAdapter(todoAdapter);

            ArrayList<String> completedDays = new ArrayList<>();
            Map<Integer, String> mapping = new HashMap<>();

            for(int i=0; i<dataLength; i++){
                TodoItem todo = TodoList.get(i);
                String completedDay = todo.getActualCompletedDay();
                String[] yearMonthDay = completedDay.split(".");
                String year = yearMonthDay[0];
                String month = yearMonthDay[1];
                String day = yearMonthDay[2];
                if (Integer.parseInt(month) < 10){
                    month = "0" + month;
                }
                if (Integer.parseInt(day) < 10){
                    day = "0" + day;
                }
                completedDay = year + month + day;

                mapping.put(i, completedDay);
                completedDays.add(completedDay);
            }

            Collections.sort(completedDays, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            ArrayList<Integer> order = new ArrayList<>();
            for(int i=0; i<dataLength; i++){ // 전체 iter
                for(int j=0; j<dataLength; j++){ // 검색 iter
                    if(completedDays.get(i) == mapping.get(j)){
                        order.add(j);
                    }
                }
            }

            for(int i=0; i<dataLength; i++){
                TodoItem todoItem;
                int j = order.get(i);
                todoItem = TodoList.get(j);
                todoAdapter.addItem(todoItem.getTodo(), todoItem.getSubject(), todoItem.getDeadline(), todoItem.getActualCompletedDay(), todoItem.getCompleted(), todoItem.getImportance(), todoItem.getClassYear(), todoItem.getClassSemester());
            }

            return true;

        } else if(id == R.id.notCompletedOrder){

            listView.setAdapter(null);
            final TodoAdapter todoAdapter;
            todoAdapter = new TodoAdapter();
            listView.setAdapter(todoAdapter);

            Map<Integer, Boolean> mapping = new HashMap<>();

            for(int i=0; i<dataLength; i++){
                TodoItem todo = TodoList.get(i);
                boolean completed = todo.getCompleted();
                mapping.put(i, completed);
            }

            for(int i=0; i<dataLength; i++){
                if(mapping.get(i) == true){
                    TodoItem todoItem = TodoList.get(i);
                    todoAdapter.addItem(todoItem.getTodo(), todoItem.getSubject(), todoItem.getDeadline(), todoItem.getActualCompletedDay(), todoItem.getCompleted(), todoItem.getImportance(), todoItem.getClassYear(), todoItem.getClassSemester());
                }
            }

            for(int i=0; i<dataLength; i++){
                if(mapping.get(i) == false){
                    TodoItem todoItem = TodoList.get(i);
                    todoAdapter.addItem(todoItem.getTodo(), todoItem.getSubject(), todoItem.getDeadline(), todoItem.getActualCompletedDay(), todoItem.getCompleted(), todoItem.getImportance(), todoItem.getClassYear(), todoItem.getClassSemester());
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
