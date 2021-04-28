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
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlecolesterol.adapter.AlimentosAdapter;
import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Alimento;

import static java.util.Comparator.comparing;

public class ListaDeAlimentosActivity extends AppCompatActivity {

    private LinearLayout layoutListaDeAlimentos;
    private boolean MODO_NOTURNO = false;
    private static final int OK_1 = 1;
    private static int posicaoSelecionada = -1;
    public static final String ACAO = "ACAO";
    public static final String NOVO = "NOVO";
    public static final String EDITAR = "EDITAR";
    private ListView listaDeAlimentos;
    private Database database;
    private AlimentosAdapter adapter;
    private View viewSelcionada;
    private ActionMode actionMode;

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
                    telaEditarAlimento();
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

            listaDeAlimentos.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_alimentos);

        database = Database.getDatabase(getBaseContext());

        layoutListaDeAlimentos = findViewById(R.id.layoutListaDeAlimentos);

        MODO_NOTURNO = isModoNoturno();

        if(MODO_NOTURNO){
            layoutListaDeAlimentos.setBackgroundColor(UserPreferences.COLOR_DARK);
        }else{
            layoutListaDeAlimentos.setBackgroundColor(UserPreferences.COLOR_WHITE);
        }

        setTitle(getString(R.string.lista_de_alimentos));
        listaDeAlimentos = (ListView) findViewById(R.id.listaDeAlimentos);

        popularListaDeAlimentos();

        registerForContextMenu(listaDeAlimentos);

        listaDeAlimentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(actionMode != null)
                    return false;

                posicaoSelecionada = position;

                viewSelcionada = view;

                view.setBackgroundColor(Color.LTGRAY);

                listaDeAlimentos.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alimentos, menu);
        return true;
    }

    private void telaEditarAlimento() {

        Alimento item = (Alimento) listaDeAlimentos.getItemAtPosition(posicaoSelecionada);
        Mensagem.toast(getApplicationContext(), item + getString(R.string.foi_clicado));

        Intent intent = new Intent(this, AlimentosActivity.class);

        intent.putExtra(AlimentosActivity.ID, item.getId());
        intent.putExtra(EDITAR, true);

        startActivityForResult(intent, OK_1);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.acoes, menu);
    }

    private void excluir() {

        Alimento item = (Alimento) listaDeAlimentos.getItemAtPosition(posicaoSelecionada);

        String mensagem = getString(R.string.deletando)+item.getNome() + getString(R.string.deseja_continuar);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                if(item != null){
                                    database.alimentoDao().delete(item);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuAddAlimento:
                telaNovoAlimentos();
                return true;
            case R.id.menuSobreAutoria:
                intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuIrTelaPrincipal:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuItemPreferencias:
                intent = new Intent(this, UserPreferences.class);
                startActivityForResult(intent, OK_1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void popularListaDeAlimentos() {

        Database database = Database.getDatabase(this);

        database.carregaCategoriaPadrao(this);
        database.carregaAlimentos(this);

        montarListaAdapter();
    }

    private List<Alimento> getAlimentosList() {

        List<Alimento> alimentos = new ArrayList<>();

        int[] id = getResources().getIntArray(R.array.id_alim);
        String[] nome = getResources().getStringArray(R.array.nome_alim);
        String[] descricao = getResources().getStringArray(R.array.descricao);
        String[] consumo = getResources().getStringArray(R.array.consumo_diario);
        int[] categoriasId = getResources().getIntArray(R.array.categoriasId);

        for (int i = 0; i < id.length ; i++){
            Alimento alimento = new Alimento(id[i], nome[i], descricao[i], consumo[i], true, categoriasId[i]);
            alimentos.add(alimento);
        }
        return alimentos;
    }

    private List<Alimento> ordenarLista(List<Alimento> alimentos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return alimentos.stream().sorted(comparing(Alimento::getId)).collect(Collectors.toList());
        }
        return alimentos;
    }

    private void montarListaAdapter() {

        List<Alimento> alimentos = database.alimentoDao().findAll();
        alimentos = ordenarLista(alimentos);
        adapter = new AlimentosAdapter(getApplicationContext(), alimentos, MODO_NOTURNO, layoutListaDeAlimentos);
//        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alimentos);
        listaDeAlimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void telaNovoAlimentos() {
        Intent intent = new Intent(this, AlimentosActivity.class);
        intent.putExtra(NOVO, true);
        startActivityForResult(intent, OK_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OK_1 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(bundle != null){

                MODO_NOTURNO = bundle.getBoolean("MODO_NOTURNO");

            }

            montarListaAdapter();
        }
    }

    public void autoria(View view) {
        Intent intent = new Intent(this, AutoriaActivity.class);
        startActivity(intent);
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
    }
}