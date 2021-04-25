package br.com.controlecolesterol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.controlecolesterol.R;
import br.com.controlecolesterol.model.Alimento;

public class AlimentosArrayAdapter extends ArrayAdapter<Alimento> {

    private ArrayList<Alimento> alimentos;
    private Context context;

    public AlimentosArrayAdapter(Context context, int resource, ArrayList<Alimento> alimentos) {
        super(context, resource);
        this.alimentos = alimentos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.layout_alimentos_adapter, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvNome = (TextView) convertView.findViewById(R.id.tvNome);
        TextView tvConsumo = (TextView) convertView.findViewById(R.id.tvConsumo);
        TextView tvQualidade = (TextView) convertView.findViewById(R.id.tvQualidade);
        TextView tvDescricao = (TextView) convertView.findViewById(R.id.tvDescricao);

        tvId.setText(alimentos.get(position).getId().toString());
        tvNome.setText(alimentos.get(position).getNome());
        tvConsumo.setText(alimentos.get(position).getConsumoRecomendado());
        tvQualidade.setText(alimentos.get(position).getAlimentoBom() ? "Sim" : "Não");
        tvDescricao.setText(alimentos.get(position).getDescricao());

        return super.getView(position, convertView, parent);

    }
}
