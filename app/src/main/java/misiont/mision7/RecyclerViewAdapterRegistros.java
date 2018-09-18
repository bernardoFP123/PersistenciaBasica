package misiont.mision7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bernardo_NoAdmin on 15/02/2018.
 */

public class RecyclerViewAdapterRegistros extends RecyclerView.Adapter<RecyclerViewAdapterRegistros.RegistrosViewHolder> {

    Context context;
    List<Registro> registros;

    public RecyclerViewAdapterRegistros(Context context, List<Registro> registros){
        this.context = context;
        this.registros = registros;
    }

    @Override
    public RegistrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.registro_card_view,parent,false);
        return new RegistrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegistrosViewHolder holder, int position) {
        holder.bind(registros.get(position));
    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public class RegistrosViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMatricula;
        TextView textViewNoCliente;

        public RegistrosViewHolder(View itemView) {
            super(itemView);
            textViewNoCliente = itemView.findViewById(R.id.textViewMatricula);
            textViewMatricula = itemView.findViewById(R.id.textViewNoCliente);
        }


        public void bind(Registro registro){
            textViewMatricula.setText(registro.getMatricula());
            textViewNoCliente.setText(registro.getNumeroCliente());

        }
    }
}
