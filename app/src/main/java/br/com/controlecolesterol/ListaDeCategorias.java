package br.com.controlecolesterol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlecolesterol.adapter.AlimentosAdapter;
import br.com.controlecolesterol.adapter.CategoriaAdapter;
import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Alimento;
import br.com.controlecolesterol.model.Categoria;
import br.com.controlecolesterol.model.Medicamento;

import static java.util.Comparator.comparing;

public class ListaDeCategorias extends AppCompatActivity {

    private LinearLayout linearlayoutCategorias;
    private CategoriaAdapter adapter;
    private ListView listViewCategorias;
    private View viewSelcionada;
    private ActionMode actionMode;
    private static int posicaoSelecionada = -1;
    private boolean MODO_NOTURNO = false;
    public static final String ACAO = "ACAO";
    public static final String NOVO = "NOVO";
    public static final String EDITAR = "EDITAR";
    public static final int OK_1 = 1;
    public static final String ID = "ID";
    public static final String DESCRICAO = "DESCRICAO";
    private Database database;

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
                    editar();
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

            listViewCategorias.setEnabled(true);
        }
    };

    private void excluir() {
        Categoria item = (Categoria) listViewCategorias.getItemAtPosition(posicaoSelecionada);

        int countAllByCategoriaId = database.alimentoDao().countAllByCategoriaId(item.getId());
        if(countAllByCategoriaId == 0){
            database.categoriaDao().delete(item);
        }else{
            Mensagem.toast(getBaseContext(), getString(R.string.categoria_nao_pode_ser_excluida));
        }
        popularLista();
    }

    private void editar() {
        Categoria item = (Categoria) listViewCategorias.getItemAtPosition(posicaoSelecionada);
        Mensagem.toast(getApplicationContext(), item + getString(R.string.foi_clicado));

        Intent intent = new Intent(getBaseContext(), CategoriaActivity.class);

        intent.putExtra(ID, item.getId());
        intent.putExtra(DESCRICAO, item.getDescricao());

        intent.putExtra(EDITAR, true);

        startActivityForResult(intent, OK_1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_categorias);

        database = Database.getDatabase(getBaseContext());

        listViewCategorias = (ListView) findViewById(R.id.listViewCategorias);
        linearlayoutCategorias = findViewById(R.id.linearlayoutCategorias);

        MODO_NOTURNO = isModoNoturno();

        setTitle(getString(R.string.lista_de_categorias));
        listViewCategorias = (ListView) findViewById(R.id.listViewCategorias);

        setModeNoturno();

        popularLista();

        registerForContextMenu(listViewCategorias);

        listViewCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(actionMode != null)
                    return false;

                posicaoSelecionada = position;

                viewSelcionada = view;

                view.setBackgroundColor(Color.LTGRAY);

                listViewCategorias.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);

                return true;
            }
        });
    }

    private void setModeNoturno() {
        if(MODO_NOTURNO){
            linearlayoutCategorias.setBackgroundColor(UserPreferences.COLOR_DARK);
        }else {
            linearlayoutCategorias.setBackgroundColor(UserPreferences.COLOR_WHITE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OK_1 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(bundle != null){

                MODO_NOTURNO = bundle.getBoolean("MODO_NOTURNO");

                setModeNoturno();
                popularLista();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acao_categoria, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuItemNovaCategoria:
                telaNovaCategoria();
                return true;
            case R.id.menuItemSairCategoria:
                finish();
                return true;
            case R.id.menuItemMainActivity:
                finish();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void telaNovaCategoria() {
        Intent intent = new Intent(this, CategoriaActivity.class);
        intent.putExtra(NOVO, true);
        startActivityForResult(intent, OK_1);
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
    }

    private void popularLista() {

        List<Categoria> categorias = database.categoriaDao().findAll();

        categorias = ordenarLista(categorias);
        adapter = new CategoriaAdapter(getApplicationContext(), categorias, MODO_NOTURNO, linearlayoutCategorias);
        listViewCategorias.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<Categoria> ordenarLista(List<Categoria> categorias) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return categorias.stream().sorted(comparing(Categoria::getDescricao)).collect(Collectors.toList());
        }
        return categorias;
    }
}