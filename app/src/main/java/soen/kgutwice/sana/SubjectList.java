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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();

        ListView listView;
        final SubjectAdapter subjectAdapter;
        subjectAdapter = new SubjectAdapter();

        listView = (ListView)findViewById(R.id.subjectListView);
        listView.setAdapter(subjectAdapter);

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonResponse = new JSONObject(s);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray data = (JSONArray)jsonResponse.get("data");
                    if(success) {
                        for(int i=0; i<data.length(); i++) {
                            JSONObject d = data.getJSONObject(i);
                            Log.i("tt", d.toString());
                            String courseTime = "" + d.getString("lectureDayOfTheWeek") + "요일, " + d.getString("startTime") + " ~ " + d.getString("endTime");
                            subjectAdapter.addItem(d.getString("subjectName"), d.getString("subjectProfessor"), courseTime);
                        }
                        subjectAdapter.notifyDataSetChanged();
                        Log.i("test", subjectAdapter.toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SubjectListRequest subjectoListRequest = new SubjectListRequest(userID,"2017","1",responseListener);

        RequestQueue queue = Volley.newRequestQueue(SubjectList.this);
        queue.add(subjectoListRequest);

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
                        intent.putExtra("subjectName", subjectName);
                        startActivity(intent);
                    }
                }
        );

//        Button modifyButton = (Button)findViewById(R.id.modifySubject);
//        modifyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View parentRow = (View)view.getParent();
//                ListView listView = (ListView)parentRow.getParent();
//                final int position = listView.getPositionForView(parentRow);
//
//                Intent intent = new Intent(getApplicationContext(), ModifySubject.class);
//                intent.putExtra("userID", userID);
//                SubjectItem subjectItem = (SubjectItem)listView.getAdapter().getItem(position);
//                String subjectName = subjectItem.getSubjectName();
//                intent.putExtra("subjectName", subjectName);
//            }
//        });
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
