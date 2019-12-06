package comm.gabrielsoares.cidavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;


import comm.gabrielsoares.cidavirtual.R;
import comm.gabrielsoares.cidavirtual.adapter.EventoAdapter;
import comm.gabrielsoares.cidavirtual.modelo.Evento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaTarefas extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref, notificacao;
    RecyclerView recycler;
    ArrayList<Evento> list;
    EventoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_tarefas);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Evento>();
        ref = FirebaseDatabase.getInstance().getReference().child("Eventos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Evento e = dataSnapshot1.getValue(Evento.class);
                    list.add(e);
                }

                adapter = new EventoAdapter(TelaTarefas.this,list);
                recycler.setAdapter(adapter);

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
