package br.com.controlecolesterol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import br.com.controlecolesterol.R;
import br.com.controlecolesterol.UserPreferences;
import br.com.controlecolesterol.model.Alimento;

public class AlimentosAdapter extends BaseAdapter {

    private Context context;
    private List<Alimento> alimentos;
    private boolean modoNoturno;
    private LinearLayout layoutListaDeAlimentos;
    private TextView tvLabelId, tvLabelProduto, tvId, textViewLabelConsumo, textViewQuantidade, textViewLabelDescription;

    public AlimentosAdapter(Context context, List<Alimento> alimentos, boolean modoNoturno, LinearLayout layoutListaDeAlimentos) {
        this.context = context;
        this.alimentos = alimentos;
        this.modoNoturno = modoNoturno;
        this.layoutListaDeAlimentos = layoutListaDeAlimentos;
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

            tvLabelId = view.findViewById(R.id.tvLabelId);
            tvLabelProduto = view.findViewById(R.id.tvLabelProduto);
            tvId = view.findViewById(R.id.tvId);
            textViewLabelConsumo = view.findViewById(R.id.textViewLabelConsumo);
            textViewQuantidade = view.findViewById(R.id.textViewQuantidade);
            textViewLabelDescription = view.findViewById(R.id.textViewLabelDescription);

            view.setTag(holder);

        } else {
            holder = (AlimentoHolder) view.getTag();
        }

        holder.id.setText(alimentos.get(i).getId().toString());
        holder.nome.setText(alimentos.get(i).getNome());
        holder.consumo.setText(alimentos.get(i).getConsumoRecomendado());
        holder.qualidade.setText(alimentos.get(i).isAlimentoBom() ? R.string.alimento_bom : R.string.alimento_ruim);
        holder.descricao.setText(alimentos.get(i).getDescricao());

        if(modoNoturno){
            view.setBackgroundColor(UserPreferences.COLOR_DARK);
            tvLabelId.setTextColor(UserPreferences.COLOR_GRAY);
            tvLabelProduto.setTextColor(UserPreferences.COLOR_GRAY);
            tvId.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelConsumo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewQuantidade.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescription.setTextColor(UserPreferences.COLOR_GRAY);
            layoutListaDeAlimentos.setBackgroundColor(UserPreferences.COLOR_DARK);
        }else{
            view.setBackgroundColor(UserPreferences.COLOR_WHITE);
            tvLabelId.setTextColor(UserPreferences.COLOR_GRAY);
            tvLabelProduto.setTextColor(UserPreferences.COLOR_GRAY);
            tvId.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelConsumo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewQuantidade.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescription.setTextColor(UserPreferences.COLOR_GRAY);
            layoutListaDeAlimentos.setBackgroundColor(UserPreferences.COLOR_WHITE);
        }

        notifyDataSetChanged();

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
