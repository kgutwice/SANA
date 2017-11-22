package soen.kgutwice.sana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    boolean idChecker = false;
    boolean pwChecker = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.pwText);
        final EditText checkPwText = (EditText) findViewById(R.id.checkPwTest);
        final EditText nameText = (EditText) findViewById(R.id.nameText);

        Button registerButton = (Button) findViewById(R.id.registerButton);

        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

                String userId = s.toString();
                TextView checkIdMessage = (TextView) findViewById(R.id.idCheckMessage);

                if(s.toString().equals("")){
                    pwChecker = false;
                    checkIdMessage.setVisibility(View.GONE);
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            TextView checkIdMessage = (TextView) findViewById(R.id.idCheckMessage);

                            if(success) {
                                idChecker = false;
                                checkIdMessage.setVisibility(View.VISIBLE);
                                checkIdMessage.setText("아이디가 이미 존제합니다.");
                            }
                            else{
                                idChecker = true;
                                checkIdMessage.setVisibility(View.VISIBLE);
                                checkIdMessage.setText("사용 가능한 아이디 입니다.");
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                TextView checkPwMessage = (TextView) findViewById(R.id.pwCheckMessage);
                String pw = checkPwMessage.getText().toString();

                if(s.toString().equals("")){
                    pwChecker = false;
                    checkPwMessage.setVisibility(View.GONE);
                    return;
                }

                if(s.toString().equals(pw)){
                    pwChecker = true;
                    checkPwMessage.setText("비밀번호가 일치합니다.");
                }
                else{
                    pwChecker = false;
                    checkPwMessage.setText("비밀번호가 일치하지 않습니다.");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkPwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                TextView checkPwMessage = (TextView) findViewById(R.id.pwCheckMessage);
                String pw = pwText.getText().toString();

                if(s.toString().equals("")){
                    pwChecker = false;
                    checkPwMessage.setVisibility(View.GONE);
                    return;
                }

                if(s.toString().equals(pw)){
                    pwChecker = true;
                    checkPwMessage.setVisibility(View.VISIBLE);
                    checkPwMessage.setText("비밀번호가 일치합니다.");
                }
                else{
                    pwChecker = false;
                    checkPwMessage.setVisibility(View.VISIBLE);
                    checkPwMessage.setText("비밀번호가 일치하지 않습니다.");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = idText.getText().toString();
                String userPw = pwText.getText().toString();
                String checkPw = checkPwText.getText().toString();
                String userName = nameText.getText().toString();

                if(!idChecker || !pwChecker){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("아이디 또는 비밀번호를 확인하세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userId, userPw, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
