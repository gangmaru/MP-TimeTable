package androidtown.org;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id=findViewById(R.id.edit_id);
        pw=findViewById(R.id.edit_pw);
        Button login=(Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userId=id.getText().toString();
                String userPw=pw.getText().toString();

                //test
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


//                if (authenticate(userId, userPw)){
//                    //인증 성공 시 MainActivity로 전환
//                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("userId",userId);
//                    intent.putExtra("userPw",userPw);
//                    //startActivityForResult(intent,1);
//                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(),userId+"님 환영합니다",Toast.LENGTH_LONG).show();
//                    finish();
//                }else {
//                    //인증 실패 시 에러 메세지 표시
//                    Toast.makeText(getApplicationContext(),"아이디와 비밀번호가 올바르지않습니다",Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
    private boolean authenticate(String userId, String userPw) {
        //인증 로직 구현...
        return "id".equals(userId) && "password".equals(userPw);
    }
}