package soen.kgutwice.sana;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KGU on 2017-12-07.
 */

public class KutisLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kutis_login);

        final String userID = getIntent().getStringExtra("userID");

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userId = idText.getText().toString();
                final String userPw = pwText.getText().toString();
                Log.i("tt", userId);
                Log.i("tt", userPw);
                if (userId.equals("") || userId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(KutisLoginActivity.this);
                    builder.setMessage("아이디 또는 비밀번호를 확인하세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //try {
                            Log.i("test", response.toString());
                          //  JSONObject jsonResponse = new JSONObject(response);
                            //JSONArray data = (JSONArray) jsonResponse.get("data");
                            /*
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject d = data.getJSONObject(i);

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    }
                                };
                                Log.i("dd", d.getString("subjectName"));
                                Log.i("dd", d.getString("subjectProfessor"));
                                Log.i("dd", d.getString("lectureDayOfTheWeek"));
                                Log.i("dd", d.getString("startTime"));
                                Log.i("dd", d.getString("endTime"));


                                AddSubjectRequest addSubjectRequest = new AddSubjectRequest(userID, d.getString("subjectName"),
                                        d.getString("subjectProfessor"),
                                        d.getString("lectureDayOfTheWeek"),
                                        d.getString("startTime"),
                                        d.getString("endTime"),
                                        "2017",
                                        "2", responseListener);
                                RequestQueue queue = Volley.newRequestQueue(KutisLoginActivity.this);
                                queue.add(addSubjectRequest);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(KutisLoginActivity.this);
                            builder.setMessage("데이터를 가져오는데 실패했습니다.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                            return;
                        }
                        */
                    }
                };

                KutisLoginRequest kutisLoginRequest = new KutisLoginRequest(userId, userPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(KutisLoginActivity.this);
                queue.add(kutisLoginRequest);
            }
        });

    }
}