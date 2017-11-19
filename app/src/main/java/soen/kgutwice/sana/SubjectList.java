package soen.kgutwice.sana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SubjectList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        String[] items = {"여", "기", "는", "과", "목", "리", "스", "트", "를", "출", "력", "합", "니", "다"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView subjectListView = (ListView)findViewById(R.id.subjectListView);
        subjectListView.setAdapter(adapter);

        subjectListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent intent = new Intent(getApplicationContext(), AddTodo.class);
                        startActivity(intent);
                    }
                }
        );
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
        if (id == R.id.addSubject) {
            Intent intent = new Intent(getApplicationContext(), AddSubject.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.addFromKutis){
            // 이곳은 쿠티스와 연동하는 곳입니다.
        }

        return super.onOptionsItemSelected(item);
    }
}
