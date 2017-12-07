package soen.kgutwice.sana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final AllData ad = (AllData)getApplication();
        CheckBox option1 = (CheckBox) findViewById(R.id.dontLookPastEventChecked);

        if(ad.getDontLookPastEventChecked() == 1)
            option1.setChecked(true);
        else
            option1.setChecked(false);

        Button settingButton = (Button)findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String checked;
                CheckBox option1 = (CheckBox) findViewById(R.id.dontLookPastEventChecked);
                if(option1.isChecked())
                    checked = "1";
                else
                    checked = "0";


                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("test", response.toString());
                            JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Toast.makeText(getApplicationContext(), "저장완료.", Toast.LENGTH_LONG).show();
                                    ad.setDontLookPastEventChecked(Integer.parseInt(checked));
                            } else {
                                Toast.makeText(getApplicationContext(), "요청이 실패했습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // Add the request to the RequestQueue.
                Log.i("Test", ad.getUserId());
                Log.i("Test", checked);
                SettingRequest settingRequest = new SettingRequest(ad.getUserId(), checked, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Setting.this);
                queue.add(settingRequest);
            }
        });

    }

}
