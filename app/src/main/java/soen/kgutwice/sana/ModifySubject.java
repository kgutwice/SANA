package soen.kgutwice.sana;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ModifySubject extends AppCompatActivity {

    class SubjectDatas {
        String subjectName;
        String subjectProfessor;
        String lectureDayOfTheWeek;
        String startTime;
        String endTime;
        String takeClassYear;
        String takeClassSemester;

        public void setTakeClassYear(String classYear) {
            this.takeClassYear = classYear;
        }
        public void setTakeClassSemester(String classSemester) {
            this.takeClassSemester = classSemester;
        }
        public void setSubjectName(String subejctName) {
            this.subjectName = subejctName;
        }
        public void setSubjectProfessor(String subejctProfessor) {
            this.subjectProfessor = subejctProfessor;
        }
        public void setLectureDayOfTheWeek(String subejctName) {
            this.lectureDayOfTheWeek = subejctName;
        }
        public void setStartTime(String subejctName) {
            this.startTime = subejctName;
        }
        public void setEndTime(String subejctName) {
            this.endTime = subejctName;
        }
        public String getSubjectName(){
            return subjectName;
        }
        public String getSubjectProfessor(){
            return subjectProfessor;
        }
        public String getLectureDayOfTheWeek(){
            return lectureDayOfTheWeek;
        }
        public String getStartTime(){
            return startTime;
        }
        public String getEndTime(){
            return endTime;
        }
        public String getTakeClassYear(){
            return takeClassYear;
        }
        public String getTakeClassSemester(){
            return takeClassSemester;
        }


    }

    String userID;
    String subjectName;
    SubjectDatas subjectDatas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_subject);

        userID = getIntent().getStringExtra("userID");
        Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();
        subjectName = getIntent().getStringExtra("subjectName");
        //Toast.makeText(getApplicationContext(), asdf, Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Spinner modifySubjectLectureDayOfTheWeek =(Spinner)findViewById(R.id.modifySubjectLectureDayOfTheWeek);
        final String[] lectureDayOfTheWeekItems = new String[]{"월", "화", "수", "목", "금", "토", "일"};
        final ArrayAdapter<String> lectureDayOfTheWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lectureDayOfTheWeekItems);
        modifySubjectLectureDayOfTheWeek.setAdapter(lectureDayOfTheWeekAdapter);

        final Spinner modifySubjectStartTime =(Spinner)findViewById(R.id.modifySubjectStartTime);
        final String[] startTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        final ArrayAdapter<String> startTimeitemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, startTimeItems);
        modifySubjectStartTime.setAdapter(startTimeitemsAdapter);
        // 되돌아가게? 원형으로?

        final Spinner modifySubjectEndTime =(Spinner)findViewById(R.id.modifySubjectEndTime);
        final String[] endTimeItems = new String[]{"00:00", "01:00", "09:00", "10:00", "11:00", "12:00"};
        final ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, endTimeItems);
        modifySubjectEndTime.setAdapter(endTimeAdapter);

        final Spinner modifySubjectTakeClassYear =(Spinner)findViewById(R.id.modifySubjectTakeClassYear);
        final Integer[] takeClassYearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        final ArrayAdapter<Integer> takeClassYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassYearItems);
        modifySubjectTakeClassYear.setAdapter(takeClassYearAdapter);
        // 끝없이?

        final Spinner modifySubjectTakeClassSemester =(Spinner)findViewById(R.id.modifySubjectTakeClassSemester);
        final Integer[] takeClassSemesterItems = new Integer[]{1, 2};
        final ArrayAdapter<Integer> takeClassSemesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, takeClassSemesterItems);
        modifySubjectTakeClassSemester.setAdapter(takeClassSemesterAdapter);

        final Button modifySubjectButton = (Button)findViewById(R.id.modifySubjectButton);
        modifySubjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(subjectDatas == null)
                    return;
                EditText msubjectnName = (EditText)findViewById(R.id.modifySubjectName);
                EditText msubjectProfessor = (EditText)findViewById(R.id.modifySubjectProfessor);
                String mlectureDayOfTheWeek = modifySubjectLectureDayOfTheWeek.getSelectedItem().toString();
                String mstartTime = modifySubjectStartTime.getSelectedItem().toString();
                String mendTime = modifySubjectEndTime.getSelectedItem().toString();
                String mtakeClassYear = modifySubjectTakeClassYear.getSelectedItem().toString();
                String mtakeClassSemester = modifySubjectTakeClassSemester.getSelectedItem().toString();

                Log.i("test", msubjectnName.getText().toString());
                Log.i("test", msubjectProfessor.getText().toString());

                modifySubjectToDB(userID, subjectName, msubjectnName.getText().toString(), msubjectProfessor.getText().toString(), mlectureDayOfTheWeek, mstartTime, mendTime, mtakeClassYear, mtakeClassSemester);
            }
        });



        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/addTodo.php";

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject  res = new JSONObject(response);
                    boolean success = res.getBoolean("success");
                    JSONArray data = (JSONArray)res.get("data");
                    if(success){
                        JSONObject r = data.getJSONObject(0);
                        subjectDatas = new SubjectDatas();
                        subjectDatas.setSubjectName(r.getString("subjectName"));
                        subjectDatas.setSubjectProfessor(r.getString("subjectProfessor"));
                        subjectDatas.setLectureDayOfTheWeek(r.getString("lectureDayOfTheWeek"));
                        subjectDatas.setStartTime(r.getString("startTime"));
                        subjectDatas.setEndTime(r.getString("endTime"));
                        subjectDatas.setTakeClassYear(r.getString("takeClassYear"));
                        subjectDatas.setTakeClassSemester(r.getString("takeClassSemester"));


                        Log.i("test", subjectDatas.getLectureDayOfTheWeek());
                        Log.i("test", subjectDatas.getStartTime());
                        Log.i("test", subjectDatas.getEndTime());
                        Log.i("test", subjectDatas.getTakeClassYear());
                        Log.i("test", subjectDatas.getTakeClassSemester());


                        modifySubjectLectureDayOfTheWeek.setSelection(Arrays.binarySearch(lectureDayOfTheWeekItems, subjectDatas.getLectureDayOfTheWeek()));
                        modifySubjectStartTime.setSelection(Arrays.binarySearch(startTimeItems, subjectDatas.getStartTime()));
                        modifySubjectEndTime.setSelection(Arrays.binarySearch(endTimeItems, subjectDatas.getEndTime()));
                        modifySubjectTakeClassYear.setSelection(Arrays.binarySearch(takeClassYearItems, Integer.parseInt(subjectDatas.getTakeClassYear())));
                        modifySubjectTakeClassSemester.setSelection(Arrays.binarySearch(takeClassSemesterItems, Integer.parseInt(subjectDatas.getTakeClassSemester())));

                        EditText subjectName = (EditText) findViewById(R.id.modifySubjectName);
                        EditText professorName = (EditText) findViewById(R.id.modifySubjectProfessor);

                        subjectName.setText(subjectDatas.getSubjectName());
                        professorName.setText(subjectDatas.getSubjectProfessor());

                        lectureDayOfTheWeekAdapter.notifyDataSetChanged();
                        startTimeitemsAdapter.notifyDataSetChanged();
                        endTimeAdapter.notifyDataSetChanged();
                        takeClassYearAdapter.notifyDataSetChanged();
                        takeClassSemesterAdapter.notifyDataSetChanged();
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
        ModifySubjectGetRequest modifySubjectGetRequest = new ModifySubjectGetRequest(ad.getUserId(), subjectName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ModifySubject.this);
        queue.add(modifySubjectGetRequest);




        //데이터 가죠오기
        /*
        String url ="http://203.249.17.196:2013/ms/android/SANA_connector/getLecture.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("d", response);
                        try{
                            JSONObject  r = new JSONObject(response);
                            boolean success = r.getBoolean("success");
                            if(success) {
                                subjectDatas = new SubjectDatas();
                                subjectDatas.setSubjectName(r.getString("subjectName"));
                                subjectDatas.setSubjectProfessor(r.getString("subejctProfessor"));
                                subjectDatas.setLectureDayOfTheWeek(r.getString("lectureDayOfTheWeek"));
                                subjectDatas.setStartTime(r.getString("startTime"));
                                subjectDatas.setEndTime(r.getString("endTime"));
                                subjectDatas.setTakeClassYear(r.getString("takeClassYear"));
                                subjectDatas.setTakeClassSemester(r.getString("takeClassSemester"));

                                modifySubjectLectureDayOfTheWeek.setSelection(Arrays.binarySearch(lectureDayOfTheWeekItems, subjectDatas.getLectureDayOfTheWeek()));
                                modifySubjectStartTime.setSelection(Arrays.binarySearch(startTimeItems, subjectDatas.getStartTime()));
                                modifySubjectEndTime.setSelection(Arrays.binarySearch(endTimeItems, subjectDatas.getEndTime()));
                                modifySubjectTakeClassYear.setSelection(Arrays.binarySearch(takeClassYearItems, subjectDatas.getTakeClassYear()));
                                modifySubjectTakeClassSemester.setSelection(Arrays.binarySearch(takeClassSemesterItems, subjectDatas.getTakeClassSemester()));

                                lectureDayOfTheWeekAdapter.notifyDataSetChanged();
                                startTimeitemsAdapter.notifyDataSetChanged();
                                endTimeAdapter.notifyDataSetChanged();
                                takeClassYearAdapter.notifyDataSetChanged();
                                takeClassSemesterAdapter.notifyDataSetChanged();

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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                Log.i("ee", userID);
                Log.i("ee", subjectName);
                params.put("userID", userID);
                params.put("subjectName", subjectName);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        */
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

            deleteSubjectToDB(userID, subjectName);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void modifySubjectToDB(final String userID, final String subjectName, final String msubjectName, final String subjectProfessor, final String lectureDayOfTheWeek, final String startTime, final String endTime, final String takeClassYear, final String takeClassSemester){


        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("ee", response.toString());
                    JSONObject  res = new JSONObject(response);
                    boolean success = res.getBoolean("success");
                    if(success){
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // Add the request to the RequestQueue.
        ModifySubjectRequest modifySubjectRequest = new ModifySubjectRequest( "sms2831", subjectName, msubjectName, subjectProfessor, lectureDayOfTheWeek, startTime, endTime, takeClassYear, takeClassSemester, responseListener );
        RequestQueue queue = Volley.newRequestQueue(ModifySubject.this);
        queue.add(modifySubjectRequest);

    }

    void deleteSubjectToDB(final String userID, final String subjectName) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("ee", response.toString());
                    JSONObject res = new JSONObject(response);
                    boolean success = res.getBoolean("success");
                    if (success) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // Add the request to the RequestQueue.
        ModifySubjectDelRequest modifySubjectDelRequest = new ModifySubjectDelRequest("sms2831", subjectName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ModifySubject.this);
        queue.add(modifySubjectDelRequest);
    }
}
