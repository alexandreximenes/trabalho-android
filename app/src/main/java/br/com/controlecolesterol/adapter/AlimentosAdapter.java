package br.com.controlecolesterol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.controlecolesterol.R;
import br.com.controlecolesterol.model.Alimento;

public class AlimentosAdapter extends BaseAdapter {

    private Context context;
    private List<Alimento> alimentos;

    public AlimentosAdapter(Context context, List<Alimento> alimentos) {
        this.context = context;
        this.alimentos = alimentos;
    }

    public static class AlimentoHolder{
        public TextView id;
        public TextView nome;
        public TextView consumo;
        public TextView qualidade;
        public TextView descricao;
    }
    @Override
    public int getCount() {
        return alimentos.size();
    }

    @Override
    public Object getItem(int position) {
        return alimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        AlimentoHolder holder;

        if (view == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_alimentos_adapter, viewGroup, false);

            holder = new AlimentoHolder();

            holder.id = view.findViewById(R.id.tvId);
            holder.nome = view.findViewById(R.id.tvNome);
            holder.consumo = view.findViewById(R.id.tvConsumo);
            holder.qualidade = view.findViewById(R.id.tvQualidade);
            holder.descricao = view.findViewById(R.id.tvDescricao);

            view.setTag(holder);

        } else {
            holder = (AlimentoHolder) view.getTag();
        }

        holder.id.setText(alimentos.get(i).getId().toString());
        holder.nome.setText(alimentos.get(i).getNome());
        holder.consumo.setText(alimentos.get(i).getConsumoRecomendado());
        holder.qualidade.setText(alimentos.get(i).getAlimentoBom() ? R.string.alimento_bom : R.string.alimento_ruim);
        holder.descricao.setText(alimentos.get(i).getDescricao());

        notifyDataSetChanged();

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
