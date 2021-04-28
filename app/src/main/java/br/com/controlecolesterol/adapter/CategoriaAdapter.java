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
import br.com.controlecolesterol.model.Categoria;

public class CategoriaAdapter extends BaseAdapter {

    private Context context;
    private List<Categoria> categorias;
    private boolean MODO_NOTURNO;
    private LinearLayout layoutCategoriaAdapter;
    private TextView tvCategoria, tvCategoriaId;

    public CategoriaAdapter(Context context, List<Categoria> categorias, boolean modoNoturno, LinearLayout layoutCategoriaAdapter) {
        this.context = context;
        this.categorias = categorias;
        this.MODO_NOTURNO = modoNoturno;
        this.layoutCategoriaAdapter = layoutCategoriaAdapter;
    }

    public static class CategoriaHolder{
        public TextView id;
        public TextView descricao;
    }
    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CategoriaHolder holder;
        if (view == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.layout_categorias_adapter, viewGroup, false);

            holder = new CategoriaHolder();

            tvCategoria = view.findViewById(R.id.tvCategoria);
            tvCategoriaId = view.findViewById(R.id.tvCategoriaId);

            holder.id = view.findViewById(R.id.tvCategoriaId);
            holder.descricao = view.findViewById(R.id.tvCategoria);

            view.setTag(holder);

            setModoNoturno(view);

        } else {
            holder = (CategoriaHolder) view.getTag();
        }

        holder.id.setText(categorias.get(i).getId().toString());
        holder.descricao.setText(categorias.get(i).getDescricao());

        notifyDataSetChanged();

        return view;
    }

    private void setModoNoturno(View view) {
        if(MODO_NOTURNO){
            view.setBackgroundColor(UserPreferences.COLOR_DARK);
            tvCategoria.setTextColor(UserPreferences.COLOR_GRAY);
            tvCategoriaId.setTextColor(UserPreferences.COLOR_GRAY);
        }else{
            view.setBackgroundColor(UserPreferences.COLOR_WHITE);
            tvCategoria.setTextColor(UserPreferences.COLOR_GRAY);
            tvCategoriaId.setTextColor(UserPreferences.COLOR_GRAY);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
