package com.example.cidavirtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import java.text.DateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import com.example.cidavirtual.modelo.Evento;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;


public class TelaAdmin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText edtNome;
    Button btAdd;
    Button btCalendario;
    TextView textView;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_admin);
        edtNome = (EditText) findViewById(R.id.edittextevento);
        btAdd = (Button) findViewById(R.id.btaddevento);
        btCalendario = (Button) findViewById(R.id.btescolhadata);
        textView = (TextView) findViewById(R.id.mostrardata);



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
             databaseReference.child("Eventos").child(e.getUid()).setValue(e);
             limparcampos();

//NOTIFICAÇÃO
             Intent intent = new Intent(TelaAdmin.this, NotificacaoActivity.class);
             intent.putExtra("mensagem", edtNome.getText().toString());
             int id = (int) (Math.random()*1000);
             PendingIntent pi = PendingIntent.getActivity(getBaseContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
             Notification notification = new Notification.Builder(getBaseContext())
                     .setContentTitle("Novo Conteudo")
                     .setContentText(edtNome.getText())
                     .setSmallIcon(R.mipmap.ic_launcher)
                     .setContentIntent(pi).build();
             NotificationManager notificationManager =
                     (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
             notification.flags |= Notification.FLAG_AUTO_CANCEL;

             notificationManager.notify(id, notification);





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
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        textView.setText(currentDateString);

    }



}
