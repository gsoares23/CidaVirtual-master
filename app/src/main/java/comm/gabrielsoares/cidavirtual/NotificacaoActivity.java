package comm.gabrielsoares.cidavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import comm.gabrielsoares.cidavirtual.R;


public class NotificacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        TextView texto = (TextView) findViewById(R.id.txtMsg);

        String mensagem = getIntent().getStringExtra("mensagem");
        texto.setText(mensagem);
    }
}
