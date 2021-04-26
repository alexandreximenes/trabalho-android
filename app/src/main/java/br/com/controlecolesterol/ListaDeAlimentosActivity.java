package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlecolesterol.adapter.AlimentosAdapter;
import br.com.controlecolesterol.model.Alimento;

import static java.util.Comparator.comparing;

public class ListaDeAlimentosActivity extends AppCompatActivity {

    private LinearLayout layoutListaDeAlimentos;
    private boolean MODO_NOTURNO = false;
    public static final String LAST_ID = "0";
    private static final int OK_1 = 1;
    private static int posicaoSelecionada = -1;
    public static final String ACAO = "ACAO";
    public static final String NOVO = "NOVO";
    public static final String EDITAR = "EDITAR";
    private ListView listaDeAlimentos;
    private List<Alimento> alimentos = new ArrayList<>();
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


        layoutListaDeAlimentos = findViewById(R.id.layoutListaDeAlimentos);
//
        MODO_NOTURNO = isModoNoturno();

        setTitle(getString(R.string.lista_de_alimentos));
        listaDeAlimentos = (ListView) findViewById(R.id.listaDeAlimentos);

        popularListaDeAlimentos();

        registerForContextMenu(listaDeAlimentos);

//        listaDeAlimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                posicaoSelecionada = position;
//                telaEditarAlimento();
//            }
//        });

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
        mostrarMensagem(getApplicationContext(), item + getString(R.string.foi_clicado));

        Intent intent = new Intent(this, AlimentosActivity.class);

        intent.putExtra(AlimentosActivity.ID, item.getId());
        intent.putExtra(AlimentosActivity.NOME, (item.getNome() == null || item.getNome() == "" ? "" : item.getNome()));
        intent.putExtra(AlimentosActivity.DESCRICAO, (item.getDescricao() == null || item.getDescricao() == ""  ? "" : item.getDescricao()));
        intent.putExtra(AlimentosActivity.CONSUMO_RECOMENDADO, (item.getConsumoRecomendado() == null || item.getConsumoRecomendado() == ""  ? "" : item.getConsumoRecomendado()));
        intent.putExtra(AlimentosActivity.ALIMENTO_BOM, (item.getAlimentoBom()));

        intent.putExtra(EDITAR, true);
        intent.putExtra(LAST_ID, item.getId());

        startActivityForResult(intent, OK_1);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.acoes, menu);
    }

    private void excluir() {
        if(alimentos.size() > 0){
            alimentos.remove(posicaoSelecionada);
            montarListaAdapter();
        }
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

        int[] id = getResources().getIntArray(R.array.id_alim);
        String[] nome = getResources().getStringArray(R.array.nome_alim);
        String[] descricao = getResources().getStringArray(R.array.descricao);
        String[] consumo = getResources().getStringArray(R.array.consumo_diario);
        String[] qualidade = getResources().getStringArray(R.array.qualidade);

        for (int i = 0; i < 3 ; i++){
            Alimento alimento = new Alimento(id[i], nome[i], descricao[i], consumo[i], true, qualidade[i]);
            alimentos.add(alimento);
        }

        montarListaAdapter();
    }

    private void ordenarLista() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alimentos = alimentos.stream().sorted(comparing(Alimento::getId)).collect(Collectors.toList());
        }
    }

    private void montarListaAdapter() {
        ordenarLista();
        adapter = new AlimentosAdapter(getApplicationContext(), alimentos, MODO_NOTURNO);
//        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alimentos);
        listaDeAlimentos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void mostrarMensagem(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    public void telaNovoAlimentos() {
        Intent intent = new Intent(this, AlimentosActivity.class);
        intent.putExtra(NOVO, true);
        intent.putExtra(LAST_ID, alimentos.size()+1);
        startActivityForResult(intent, OK_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OK_1 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(bundle != null){

                Alimento alimento = new Alimento();

                alimento.setId(bundle.getInt("id"));
                alimento.setNome(bundle.getString("nome"));
                alimento.setConsumoRecomendado(bundle.getString("quantidade"));
                alimento.setDescricao(bundle.getString("descricao"));
                alimento.setAlimentoBom(bundle.getBoolean("qualidade"));


                if(bundle.getString(ACAO) != null){

                    if(bundle.getString(ACAO).equals(NOVO)){

                        alimentos.add(alimento);

                    }else if(bundle.getString(ACAO).equals(EDITAR)){

                        alimentos.remove(posicaoSelecionada);
                        alimentos.add(alimento);

                        posicaoSelecionada = -1;
                    }
                }


                boolean isModoNoturno = bundle.getBoolean(UserPreferences.MODO_NOTURNO, false);
                if(isModoNoturno){
                    mostrarMensagem(getBaseContext(), "modo noturno ativo");
                }

                montarListaAdapter();
            }
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