package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class SubjectList extends AppCompatActivity {

    public static String userID;
    private ArrayAdapter adapter;
    Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();

        final ListView listView = (ListView)findViewById(R.id.subjectListView);


        final Spinner askYear = (Spinner)findViewById(R.id.askYear);
        Integer[] YearItems = new Integer[]{2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026};
        ArrayAdapter<Integer> YearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, YearItems);
        askYear.setAdapter(YearAdapter);

        final Spinner askSemester = (Spinner)findViewById(R.id.askSemester);
        Integer[] SemesterItems = new Integer[]{1, 2};
        ArrayAdapter<Integer> SemesterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SemesterItems);
        askSemester.setAdapter(SemesterAdapter);

        responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray data = (JSONArray)jsonResponse.get("data");

                    listView.setAdapter(null);
                    final SubjectAdapter subjectAdapter;
                    subjectAdapter = new SubjectAdapter();
                    listView.setAdapter(subjectAdapter);

                    if(success) {
                        for(int i=0; i<data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);
                            Log.i("tt", d.toString());
                            String courseTime = "" + d.getString("lectureDayOfTheWeek") + "요일, " + d.getString("startTime") + " ~ " + d.getString("endTime");
                            subjectAdapter.addItem(d.getString("no"), d.getString("subjectName"), d.getString("subjectProfessor"), courseTime, d.getString("takeClassYear"), d.getString("takeClassSemester"));
                        }
                        subjectAdapter.notifyDataSetChanged();
                        Log.i("test", subjectAdapter.toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "불러올 과목이 없습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음
        //SubjectListRequest subjectoListRequest = new SubjectListRequest(userID,"2017","2",responseListener);
        //여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음//여기 스태틱으로 박아넣었음

//        RequestQueue queue = Volley.newRequestQueue(SubjectList.this);
//        queue.add(subjectoListRequest);

//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        ListView subjectListView = (ListView)findViewById(R.id.subjectListView);
//        subjectListView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent intent = new Intent(getApplicationContext(), AddTodo.class);
                        intent.putExtra("userID", userID);
                        SubjectItem subjectItem = (SubjectItem)parent.getAdapter().getItem(i);
                        String subjectName = subjectItem.getSubjectName();
                        String ClassYear = subjectItem.getClassYear();
                        String ClassSemester = subjectItem.getClassSemester();
                        intent.putExtra("subjectName", subjectName);
                        intent.putExtra("ClassYear", ClassYear);
                        intent.putExtra("ClassSemester", ClassSemester);
                        startActivity(intent);
                    }
                }
        );

        /*
        Button modifyButton = (Button)findViewById(R.id.modifySubject);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentRow = (View)view.getParent();
                ListView listView = (ListView)parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);

                Intent intent = new Intent(getApplicationContext(), ModifySubject.class);
                intent.putExtra("userID", userID);
                SubjectItem subjectItem = (SubjectItem)listView.getAdapter().getItem(position);
                String no = subjectItem.getNo();
                String subjectName = subjectItem.getSubjectName();
                String ClassYear = subjectItem.getClassYear();
                String ClassSemester = subjectItem.getClassSemester();
                intent.putExtra("no", no);
                intent.putExtra("subjectName", subjectName);
                intent.putExtra("ClassYear", ClassYear);
                intent.putExtra("ClassSemester", ClassSemester);
            }
        });
        */

        askYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = askYear.getSelectedItem().toString();
                String semester = askSemester.getSelectedItem().toString();

                SubjectListRequest subjectListRequest = new SubjectListRequest(userID, year, semester, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SubjectList.this);
                queue.add(subjectListRequest);
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

                SubjectListRequest subjectListRequest = new SubjectListRequest(userID, year, semester, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SubjectList.this);
                queue.add(subjectListRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subject_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addSubject) {
            Intent intent = new Intent(getApplicationContext(), AddSubject.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
            return true;
        } else if(id == R.id.addFromKutis){
            Intent intent = new Intent(getApplicationContext(), KutisLoginActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
