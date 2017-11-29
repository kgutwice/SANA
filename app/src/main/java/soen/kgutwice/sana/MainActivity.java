package soen.kgutwice.sana;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getStringExtra("userID");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        ListView listView;
        TodoAdapter todoAdapter;

        todoAdapter = new TodoAdapter();

        listView = (ListView)findViewById(R.id.todayTodoListView);
        listView.setAdapter(todoAdapter);

        // 아래 부분을 전부 투데이 요청으로 바꿔야함.
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
        todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent intent = new Intent(getApplicationContext(), ModifyTodo.class);
                        startActivity(intent);
                    }
                }
        );


        ImageButton listButton = (ImageButton)findViewById(R.id.listButton);

        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SemesterTodoList.class);
                startActivity(intent);
            }
        });

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), year + " " + month + " " + dayOfMonth, Toast.LENGTH_LONG).show(); // Example : 2017 10 30

                // 요청을 날짜로 받고 해당 날짜의 투두만 리턴해야함
                // getTodayTodoFromDB(userID, year, month, dayOfMonth);

            }
        });

        // 먼저 만들어지자 마자 오늘 데이터를 가져온다.
        // getTodayTodoFromDB(userID, 오늘 년도, 오늘 달, 오늘 날짜);


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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getTodayTodoFromDB(final String userID, final String year, final String month, final String day){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="하루의 Todo를 요청하는 URL입니다.";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 제이슨으로 받는다면

                        try{
                            JSONObject reader = new JSONObject(response);
                            JSONObject todoData = reader.getJSONObject("");

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
                params.put("year", year);
                params.put("month", month);
                params.put("day", day);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
