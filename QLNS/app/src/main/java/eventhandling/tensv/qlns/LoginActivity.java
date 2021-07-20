package eventhandling.tensv.qlns;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DatabaseUser;
import eventhandling.tensv.qlns.MainActivity2;
import eventhandling.tensv.qlns.R;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button btnLogin;
    DatabaseUser myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        myDB = new DatabaseUser(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "User hoac Pass trống", Toast.LENGTH_SHORT).show();

                }else {
//                    Boolean ketnoi = myDB.kiemtraUserPass(user,pass);
//                    if (ketnoi == true){
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);

//                    }else {
//                        Toast.makeText(LoginActivity.this, "Sai User hoac pass", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            }
        });
    }
}