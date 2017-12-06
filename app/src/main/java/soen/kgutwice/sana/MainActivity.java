package soen.kgutwice.sana;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String userID = null;
    private String deadLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getStringExtra("userID");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        final ListView listView;
        TodoAdapter todoAdapter;

        todoAdapter = new TodoAdapter();

        listView = (ListView)findViewById(R.id.todayTodoListView);
        listView.setAdapter(todoAdapter);

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

        ImageButton listButton = (ImageButton)findViewById(R.id.listButton);

        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SemesterTodoList.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), year + " " + month + " " + dayOfMonth, Toast.LENGTH_LONG).show(); // Example : 2017 10 30

                listView.setAdapter(null);

                TodoAdapter todoAdapter;
                todoAdapter = new TodoAdapter();

                listView.setAdapter(todoAdapter);

                // 투두 요청

                 getTodayTodoFromDB(userID, year, month, dayOfMonth, todoAdapter);
            }
        });

        getLectureFromDB(userID, "2017", "1");
        getIDandNameandSet(userID);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.setting) {
            // 설정 액티비티로
            Intent intent = new Intent(getApplicationContext(), Setting.class);
            startActivity(intent);

        } else if (false) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getTodayTodoFromDB(final String userID, final int year, final int month, final int day, final TodoAdapter todoAdapter){
        final HashMap<String, Object> todo = new HashMap<String, Object>();
        final ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();

        deadLine = Integer.toString(year) + Integer.toString(month+1) + Integer.toString(day);

        Log.i("tt", deadLine);

        //서버로부터 데이터 받아오기
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray data = (JSONArray)jsonResponse.get("data");

                    if(success) {
                        for(int i = 0; i<data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);
                            //todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);

                            todoAdapter.addItem(d.getString("todoName"), d.getString("subjectName"), d.getString("deadLine"), d.getString("actualDeadLine"),d.getBoolean("completed"), d.getInt("importance"));

                        }
                        todoAdapter.notifyDataSetChanged();

                    } else {

                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MainActivityTodoListRequest mainActivityTodoListRequest = new MainActivityTodoListRequest("sms2831",deadLine,responseListener);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(mainActivityTodoListRequest);


    }

    void getIDandNameandSet(final String userID){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="아이디와 이름을 요청하는 URL입니다.";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject reader = new JSONObject(response);
                            String receiveUserID = reader.getString("userID");
                            String receiveUserName = reader.getString("userName");
                            TextView idTextView = (TextView)findViewById(R.id.userID);
                            TextView nameTextView = (TextView)findViewById(R.id.userName);
                            idTextView.setText(receiveUserID);
                            nameTextView.setText(receiveUserName);

                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID", userID);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void getLectureFromDB(final String userID, final String takeClassYear, final String takeClassSemeter){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/getLecture.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject reader = new JSONObject(response);
                            boolean success = reader.getBoolean("success");
                            JSONArray data = (JSONArray)reader.get("data");
                            Log.i("tt", data.toString());
                            if(success) {
                                for(int i=0; i<data.length(); i++) {
                                    JSONObject row = data.getJSONObject(i);
                                    NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
                                    Menu m = navView.getMenu();
                                    m.add(row.getString("subjectName"));
                                }
                            } else {

                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID", userID);
                params.put("takeClassYear", takeClassYear);
                params.put("takeClassSemester", takeClassSemeter);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
