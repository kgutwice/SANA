package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SemesterTodoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // 여기는 한 학기의 모든 일정을 보는 곳입니다.

        ListView listView;
        TodoAdapter todoAdapter;

        todoAdapter = new TodoAdapter();

        listView = (ListView)findViewById(R.id.semesterTodoListView);
        listView.setAdapter(todoAdapter);

        // 아래 부분을 한학기의 요청으로 바꿔야함.
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

    }
}
