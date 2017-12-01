package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SemesterTodoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // 여기는 한 학기의 모든 일정을 보는 곳입니다.

        ListView listView;
        final TodoAdapter todoAdapter;

        todoAdapter = new TodoAdapter();

        listView = (ListView)findViewById(R.id.semesterTodoListView);
        listView.setAdapter(todoAdapter);

        //서버로부터 데이터 받아오기
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
                            //todoAdapter.addItem("testtodo","testSubject", "testDeadline", "testActualDeadline", false, 2);
                            Log.i("tt", d.toString());
                            todoAdapter.addItem(d.getString("todoName"), d.getString("subjectName"), d.getString("deadLine"), d.getString("actualDeadLine"),d.getBoolean("completed"), d.getInt("importance"));
                        }
                        todoAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SemesterTodoListRequest semesterTodoListRequest = new SemesterTodoListRequest("sms2831","2017","1",responseListener);

        RequestQueue queue = Volley.newRequestQueue(SemesterTodoList.this);
        queue.add(semesterTodoListRequest);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent intent = new Intent(getApplicationContext(), ModifyTodo.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
