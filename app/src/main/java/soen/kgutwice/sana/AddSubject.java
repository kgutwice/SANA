package soen.kgutwice.sana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddSubject extends AppCompatActivity {

    final static String URI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        Button addSubjectButton = (Button)findViewById(R.id.submitButton);
        addSubjectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 데이터 송신
            }
        });
    }
}
