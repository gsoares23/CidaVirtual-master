package comm.gabrielsoares.cidavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import comm.gabrielsoares.cidavirtual.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLoginTutores extends AppCompatActivity {

    private EditText usernameTutor, senhaTutor;
    private Button btloginTutor;
    private Button btSouAdmin;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_tutores);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        btSouAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaLoginTutores.this, TelaLoginAdmin.class);
                startActivity(intent);
            }
        });

        btloginTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });



    }

    private void loginUserAccount() {

        final String username = usernameTutor.getText().toString();
        final String senha = senhaTutor.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Por favor, digite seu username.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(getApplicationContext(), "Por favor, digite a sua senha.", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(username, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (username.equals("tutor")&&senha.equals("12345")) {
                            Toast.makeText(getApplicationContext(), "Login realizado com sucesso.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TelaLoginTutores.this, TelaTarefas.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Falha no login! Por favor, tente novamente.", Toast.LENGTH_LONG).show();
                        }
                    }
                });




    }

    private void initializeUI() {
        usernameTutor = findViewById(R.id.usernametutor);
        senhaTutor = findViewById(R.id.senhatutor);
        btloginTutor = findViewById(R.id.btlogintutor);
        btSouAdmin = findViewById(R.id.btsouadmin);
    }
}
