package com.example.cidavirtual;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cidavirtual.adapter.Evento;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TelaTarefas extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref, notificacao;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    Evento evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_tarefas);

        evento = new Evento();
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Eventos");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.tarefas_info,R.id.tarefasInfo, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    evento = ds.getValue(Evento.class);
                    list.add(evento.getData().toString() + "\n"+evento.getNome());
                    Log.d("data", evento.getData().toString());



                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        notificacao = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cidavirtual-9cf11.firebaseio.com/");

        notificacao.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String notificacao = dataSnapshot.child("notificacao").getValue().toString();

                int id = (int) (Math.random()*10000);
                Notification notification = new Notification.Builder(getBaseContext())
                        .setContentTitle("Novo Evento!")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(notificacao)

                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(id,notification);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



















    //ALERT DIALOG SAIR DO APP//
    @Override
    public void onBackPressed() {

        AlertDialog.Builder alerta = new AlertDialog.Builder(TelaTarefas.this);
        alerta.setTitle("Aviso");
        alerta  .setIcon(R.drawable.ic_aviso)
                .setMessage("Sair da tela de tutores?")
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }

                })

                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(TelaTarefas.this, TelaLoginTutores.class);
                        startActivity(intent);;

                    }
                });

        AlertDialog alertDialog = alerta.create();
        alertDialog.show();



    }


    @Override
    protected void onStart() {
        super.onStart();



    }
}
