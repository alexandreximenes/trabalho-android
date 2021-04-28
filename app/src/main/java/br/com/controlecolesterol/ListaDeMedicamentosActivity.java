package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlecolesterol.adapter.AlimentosAdapter;
import br.com.controlecolesterol.adapter.MedicamentosAdapter;
import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Alimento;
import br.com.controlecolesterol.model.Medicamento;

import static java.util.Comparator.comparing;

public class ListaDeMedicamentosActivity extends AppCompatActivity {

    public static final String ACAO = "ACAO";
    public static final String NOVO = "NOVO";
    public static final String EDITAR = "EDITAR";
    private LinearLayout layoutListaDeMedicamentos;
    private ListView listaDeMedicamentos;
    private List<Medicamento> medicamentos = new ArrayList<>();
    private static final int OK_1 = 1;
    private View viewSelcionada;
    private static int posicaoSelecionada = -1;
    private MedicamentosAdapter adapter;
    private ActionMode actionMode;
    private Database database;
    private boolean MODO_NOTURNO = false;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.acoes, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){

                case R.id.menuItemEditar:
                    mode.finish();
                    telaEditarMedicamento();
                    return true;
                case R.id.menuItemExcluir:
                    mode.finish();
                    excluir();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if(viewSelcionada != null){
                viewSelcionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelcionada = null;

            listaDeMedicamentos.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_medicamentos);

        layoutListaDeMedicamentos = findViewById(R.id.layoutListaDeMedicamentos);
        database = Database.getDatabase(getBaseContext());

        MODO_NOTURNO = isModoNoturno();

        setModoNoturno();

        setTitle(getString(R.string.lista_de_alimentos));
        listaDeMedicamentos = (ListView) findViewById(R.id.listaDeMedicamentos);

        popularListaDeMedicamentos();

        registerForContextMenu(listaDeMedicamentos);

        listaDeMedicamentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(actionMode != null)
                    return false;

                posicaoSelecionada = position;

                viewSelcionada = view;

                view.setBackgroundColor(Color.LTGRAY);

                listaDeMedicamentos.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_medicamentos, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.acoes, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuAddMedicamentos:
                telaNovoMedicamento();
                return true;
            case R.id.menuSobreAutoria:
                intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItemPreferencias:
                intent = new Intent(this, UserPreferences.class);
                startActivityForResult(intent, OK_1);
                return true;
            case R.id.menuIrTelaPrincipal:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void telaNovoMedicamento() {
        Intent intent = new Intent(this, MedicamentosActivity.class);
        intent.putExtra(NOVO, true);
        startActivityForResult(intent, OK_1);
    }

    private void excluir() {

        Medicamento item = (Medicamento) listaDeMedicamentos.getItemAtPosition(posicaoSelecionada);

        String mensagem = getString(R.string.deletando)+item.getNome() + getString(R.string.deseja_continuar);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:

                        if(item != null){
                            database.medicamentoDao().delete(item);
                            montarListaAdapter();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        Mensagem.alertDialog(this, mensagem, listener);
    }

    private void telaEditarMedicamento() {
        Medicamento item = (Medicamento) listaDeMedicamentos.getItemAtPosition(posicaoSelecionada);
        Mensagem.toast(getApplicationContext(), item + getString(R.string.foi_clicado));

        Intent intent = new Intent(this, MedicamentosActivity.class);

        intent.putExtra(MedicamentosActivity.ID, item.getId());
        intent.putExtra(EDITAR, true);

        startActivityForResult(intent, OK_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OK_1 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(bundle != null){

                MODO_NOTURNO = bundle.getBoolean("MODO_NOTURNO");
                setModoNoturno();

            }

            montarListaAdapter();
        }
    }

    private void setModoNoturno() {
        if(MODO_NOTURNO){
            layoutListaDeMedicamentos.setBackgroundColor(UserPreferences.COLOR_DARK);
        }else{
            layoutListaDeMedicamentos.setBackgroundColor(UserPreferences.COLOR_WHITE);
        }
    }

    private List<Medicamento> ordenarLista(List<Medicamento> medicamentos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return medicamentos.stream().sorted(comparing(Medicamento::getNome)).collect(Collectors.toList());
        }
        return medicamentos;
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
    }

    private void popularListaDeMedicamentos() {
        montarListaAdapter();
    }

    private void montarListaAdapter() {

        List<Medicamento> medicamentos = database.medicamentoDao().findAll();
        medicamentos = ordenarLista(medicamentos);
        adapter = new MedicamentosAdapter(getApplicationContext(), medicamentos, MODO_NOTURNO, layoutListaDeMedicamentos);
        listaDeMedicamentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}