package comm.gabrielsoares.cidavirtual.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import comm.gabrielsoares.cidavirtual.R;
import comm.gabrielsoares.cidavirtual.TelaTarefas;
import comm.gabrielsoares.cidavirtual.modelo.Evento;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolderEvento>{


    private List<Evento> dados;

    public EventoAdapter(TelaTarefas telaTarefas, List<Evento> dados){
        this.dados = dados;
    }

    @NonNull
    @Override
    public EventoAdapter.ViewHolderEvento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tarefas_linha, parent, false);

        ViewHolderEvento holderEvento = new ViewHolderEvento(view);

        return holderEvento;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.ViewHolderEvento holder, int position) {

        if ( (dados != null) && (dados.size() > 0 ) ) {

            Evento evento = dados.get(position);

            holder.txtdata.setText(evento.getData());
            holder.txtnome.setText(evento.getNome());

        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderEvento extends RecyclerView.ViewHolder{

        public TextView txtdata;
        public TextView txtnome;



        public ViewHolderEvento(View itemView){
            super(itemView);

            txtdata = itemView.findViewById(R.id.tarefasData);
            txtnome = itemView.findViewById(R.id.tarefasNome);
        }
    }
}