package soen.kgutwice.sana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 설정 클릭하면 체크 읽어서 서버로 전송 후 아이디에 값 변경
    }

}
