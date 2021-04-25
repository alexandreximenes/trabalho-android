package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.controlecolesterol.adapter.AlimentosAdapter;
import br.com.controlecolesterol.model.Alimento;

import static java.util.Comparator.comparing;

public class ListaDeAlimentosActivity extends AppCompatActivity {

    public static final String LAST_ID = "0";
    private static final int OK_1 = 1;
    public static final String RETORNAR_DADOS_SALVOS = "retornar_dados_salvos";
    private ListView listaDeAlimentos;
    List<Alimento> alimentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_alimentos);

        listaDeAlimentos = (ListView) findViewById(R.id.listaDeAlimentos);

        popularListaDeAlimentos();

        listaDeAlimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alimento item = (Alimento) listaDeAlimentos.getItemAtPosition(position);
                mostrarMensagem(getApplicationContext(), item + getString(R.string.foi_clicado));

                chamarActivityAlimento(item);
            }
        });
    }

    private void chamarActivityAlimento(Alimento item) {

        Intent i = new Intent(this, AlimentosActivity.class);

        i.putExtra(AlimentosActivity.ID, item.getId());
        i.putExtra(AlimentosActivity.NOME, (item.getNome() == null || item.getNome() == "" ? "" : item.getNome()));
        i.putExtra(AlimentosActivity.DESCRICAO, (item.getDescricao() == null || item.getDescricao() == ""  ? "" : item.getDescricao()));
        i.putExtra(AlimentosActivity.CONSUMO_RECOMENDADO, (item.getConsumoRecomendado() == null || item.getConsumoRecomendado() == ""  ? "" : item.getConsumoRecomendado()));
        i.putExtra(AlimentosActivity.ALIMENTO_BOM, (item.getAlimentoBom()));

        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alimentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuAddAlimento:
                intent = new Intent(this, AlimentosActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuSobreAutoria:
                intent = new Intent(this, AutoriaActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuIrTelaPrincipal:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            alimentos = alimentos.stream().sorted(comparing(Alimento::getId)).collect(Collectors.toList());
        }

        montarListaAdapter();
    }

    private void montarListaAdapter() {
        AlimentosAdapter adapter = new AlimentosAdapter(getApplicationContext(), alimentos);
//        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alimentos);
        listaDeAlimentos.setAdapter(adapter);
    }

    private void mostrarMensagem(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    public void telaCadastroAlimentos(View view) {
        Intent intent = new Intent(this, AlimentosActivity.class);
        intent.putExtra(RETORNAR_DADOS_SALVOS, true);
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

                alimentos.add(alimento);
                montarListaAdapter();
            }
        }
    }

    public void autoria(View view) {
        Intent intent = new Intent(this, AutoriaActivity.class);
        startActivity(intent);
    }

    public void menuPrincipal(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}