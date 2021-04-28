package br.com.controlecolesterol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.controlecolesterol.R;
import br.com.controlecolesterol.UserPreferences;
import br.com.controlecolesterol.model.Medicamento;

public class MedicamentosAdapter extends BaseAdapter {

    private Context context;
    private List<Medicamento> medicamentos;
    private boolean MODO_NOTURNO;
    private LinearLayout layoutMedicamentosAdapter;
    private TextView tvMedicamento, tvMedicamentoId;

    public MedicamentosAdapter(Context context, List<Medicamento> medicamentos, boolean modoNoturno, LinearLayout layoutMedicamentosAdapter) {
        this.context = context;
        this.medicamentos = medicamentos;
        this.MODO_NOTURNO = modoNoturno;
        this.layoutMedicamentosAdapter = layoutMedicamentosAdapter;
    }

    public static class CategoriaHolder{
        public TextView id;
        public TextView nome;
    }

    @Override
    public int getCount() {
        return medicamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return medicamentos.get(position);
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
            view = layoutInflater.inflate(R.layout.layout_medicamento_adapter, viewGroup, false);

            holder = new CategoriaHolder();

            tvMedicamento = view.findViewById(R.id.tvMedicamento);
            tvMedicamentoId = view.findViewById(R.id.tvMedicamentoId);

            holder.id = view.findViewById(R.id.tvMedicamentoId);
            holder.nome = view.findViewById(R.id.tvMedicamento);

            view.setTag(holder);

            setModoNoturno(view);

        } else {
            holder = (CategoriaHolder) view.getTag();
        }

        holder.id.setText(medicamentos.get(i).getId().toString());
        holder.nome.setText(medicamentos.get(i).getNome());

        notifyDataSetChanged();

        return view;
    }

    private void setModoNoturno(View view) {
        if(MODO_NOTURNO){
            view.setBackgroundColor(UserPreferences.COLOR_DARK);
            tvMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            tvMedicamentoId.setTextColor(UserPreferences.COLOR_GRAY);
        }else{
            view.setBackgroundColor(UserPreferences.COLOR_WHITE);
            tvMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            tvMedicamentoId.setTextColor(UserPreferences.COLOR_GRAY);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
