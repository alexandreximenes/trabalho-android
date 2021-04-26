package br.com.controlecolesterol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.controlecolesterol.model.Alimento;

public class AlimentosActivity extends AppCompatActivity {

    public static final String INDEX_ALIMENTO = "";
    private TextView textViewLabelCodigo, textViewCodigo;
    private EditText edNomeAlim, edDescricaoAlim, edQuantidadeAlim;
    private CheckBox cbAlimentoBom;
    private String nome, descricao, consumoRecomendado;
    private boolean acaoNovo;
    private boolean acaoEditar;
    private int lastID;

    public static final String ID = "ID";
    public static final String NOME = "NOME";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String CONSUMO_RECOMENDADO = "CONSUMO_RECOMENDADO";
    public static final String ALIMENTO_BOM = "ALIMENTO_BOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        textViewLabelCodigo = (TextView) findViewById(R.id.textViewLabelCodigo);
        textViewCodigo = (TextView) findViewById(R.id.textViewCodigo);
        textViewCodigo.setVisibility(View.INVISIBLE);
        textViewLabelCodigo.setVisibility(View.INVISIBLE);

        edNomeAlim = (EditText) findViewById(R.id.edNomeAlim);
        edNomeAlim = (EditText) findViewById(R.id.edNomeAlim);
        edDescricaoAlim = (EditText) findViewById(R.id.edDescricaoAlim);
        edQuantidadeAlim = (EditText) findViewById(R.id.edQuantidadeAlim);
        cbAlimentoBom = (CheckBox) findViewById(R.id.cbBomAlim);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            int id = bundle.getInt(ID, -1);
            if (id > 0) {
                acaoEditar = bundle.getBoolean(ListaDeAlimentosActivity.EDITAR, false);
                lastID = bundle.getInt(ListaDeAlimentosActivity.LAST_ID);
                exibirDadosDeEdicaoNaTela(bundle);
            } else {
                String title = getString(R.string.novo) + getString(R.string.cadastro);
                mostrarMensagem(title);
                setTitle(title);

                acaoNovo = bundle.getBoolean(ListaDeAlimentosActivity.NOVO, false);
                lastID = bundle.getInt(ListaDeAlimentosActivity.LAST_ID);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar_limpar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuItemSalvar:
                salvarAlim();
                return true;
            case R.id.menuItemLimpar:
                limparAlim();
                return true;
            case R.id.menuIrTelaPrincipal:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void exibirDadosDeEdicaoNaTela(Bundle bundle) {

        textViewCodigo.setVisibility(View.VISIBLE);
        textViewLabelCodigo.setVisibility(View.VISIBLE);

        String title = getString(R.string.editando) + getString(R.string.cadastro);
        mostrarMensagem(title);
        setTitle(title);

        lastID = bundle.getInt(ID);
        textViewCodigo.setText(String.valueOf(lastID));

        edNomeAlim.setText(bundle.getString(NOME));
        edDescricaoAlim.setText(bundle.getString(DESCRICAO));
        edQuantidadeAlim.setText(bundle.getString(CONSUMO_RECOMENDADO));
        cbAlimentoBom.setChecked(bundle.getBoolean(ALIMENTO_BOM));
    }

    public void limparAlim() {

        edNomeAlim.setText("");
        edDescricaoAlim.setText("");
        edQuantidadeAlim.setText("");
        cbAlimentoBom.setChecked(false);
        edNomeAlim.requestFocus();
        mostrarMensagem(getString(R.string.limpo_com_sucesso));

    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    public void salvarAlim() {

        Alimento alimento = new Alimento();

        nome = edNomeAlim.getText().toString();
        descricao = edDescricaoAlim.getText().toString();
        consumoRecomendado = edQuantidadeAlim.getText().toString();

        //Só o nome é obrigatório
        if(nome == "" || nome.trim().isEmpty()) {
            mostrarMensagem(getString(R.string.informe_o_nome_do_alimento));
            edNomeAlim.requestFocus();
            return;
        }

        alimento.setId(lastID);
        alimento.setNome(nome);
        alimento.setDescricao(descricao);
        alimento.setConsumoRecomendado(consumoRecomendado);
        alimento.setAlimentoBom(cbAlimentoBom.isChecked());

        montarIntentResult(alimento);
        mostrarMensagem(getString(R.string.salvo_com_sucesso));

    }

    private void montarIntentResult(Alimento alimento) {

        Intent intent = new Intent();

        if(acaoNovo){
            intent.putExtra(ListaDeAlimentosActivity.ACAO, ListaDeAlimentosActivity.NOVO);
            mostrarMensagem(getString(R.string.salvo_com_sucesso));
        }else if(acaoEditar){
            intent.putExtra(ListaDeAlimentosActivity.ACAO, ListaDeAlimentosActivity.EDITAR);
            mostrarMensagem(getString(R.string.editado_com_sucesso));
        }

        intent.putExtra("id", alimento.getId());
        intent.putExtra("nome", alimento.getNome());
        intent.putExtra("descricao", alimento.getDescricao());
        intent.putExtra("quantidade", alimento.getConsumoRecomendado());
        intent.putExtra("qualidade", alimento.getAlimentoBom());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}