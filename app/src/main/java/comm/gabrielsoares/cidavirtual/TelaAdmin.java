package comm.gabrielsoares.cidavirtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import comm.gabrielsoares.cidavirtual.R;
import comm.gabrielsoares.cidavirtual.modelo.Evento;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class TelaAdmin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edtNome;
    Button btAdd;
    Button btCalendario;
    Button btTarefas;
    TextView textView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference notificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_admin);
        edtNome = (EditText) findViewById(R.id.edittextevento);
        btAdd = (Button) findViewById(R.id.btaddevento);
        btTarefas = (Button) findViewById(R.id.bttarefas);
        btCalendario = (Button) findViewById(R.id.btescolhadata);
        textView = (TextView) findViewById(R.id.mostrardata);
        notificacao = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cidavirtual-9cf11.firebaseio.com/").child("notificacao");



        inicializarFirebase();


        btCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });





        btAdd.setOnClickListener(new View.OnClickListener() {
     @Override
         public void onClick(View view) {

         if (edtNome.getText().length() == 0){
             edtNome.setError("Campo Vazio");
         }else if (textView.getText().length() == 0) {
             textView.setError("Campo Vazio");
         }else{

             Evento e = new Evento();
             e.setUid(UUID.randomUUID().toString());
             e.setNome(edtNome.getText().toString());
             e.setData(textView.getText().toString());

             notificacao.setValue(edtNome.getText().toString());

             databaseReference.child("Eventos").child(e.getData()).setValue(e);
             limparcampos();





             AlertDialog.Builder alerta = new AlertDialog.Builder(TelaAdmin.this);
             alerta.setTitle("OBRIGADO");
             alerta  .setIcon(R.drawable.ic_aviso)
                     .setMessage("Evento adicionado com sucesso!")
                     .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.cancel();
                         }


                     });

             AlertDialog alertDialog = alerta.create();
             alertDialog.show();

             limparcampos();

         }

         btTarefas.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(TelaAdmin.this, TelaTarefas.class);
                 startActivity(intent);
             }
         });


    }
});



    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(TelaAdmin.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void limparcampos (){

        edtNome.setText("");
        textView.setText("");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
       String date = + dayofMonth + "/" + month + "/" + year;
        textView.setText(date);

    }



}
