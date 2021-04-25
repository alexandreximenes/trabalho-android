package br.com.controlecolesterol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;

import br.com.controlecolesterol.model.Alimento;

public class AlimentosActivity extends AppCompatActivity {

    private TextView textViewLabelCodigo, textViewCodigo;
    private EditText edNomeAlim, edDescricaoAlim, edQuantidadeAlim;
    private CheckBox cbAlimentoBom;
    private String nome, descricao, consumoRecomendado;
    private boolean retornarDadosParaActivity;
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
                editarCadastro(bundle);
            } else {
                String title = getString(R.string.novo) + getString(R.string.cadastro);
                mostrarMensagem(title);
                setTitle(title);

                retornarDadosParaActivity = bundle.getBoolean(ListaDeAlimentosActivity.RETORNAR_DADOS_SALVOS);
                lastID = bundle.getInt(ListaDeAlimentosActivity.LAST_ID);
            }

        }
    }

    private void editarCadastro(Bundle bundle) {

        textViewCodigo.setVisibility(View.VISIBLE);
        textViewLabelCodigo.setVisibility(View.VISIBLE);

        String title = getString(R.string.editando) + getString(R.string.cadastro);
        mostrarMensagem(title);
        setTitle(title);

        lastID = bundle.getInt(ID);

        edNomeAlim.setText(bundle.getString(NOME));
        edDescricaoAlim.setText(bundle.getString(DESCRICAO));
        edQuantidadeAlim.setText(bundle.getString(CONSUMO_RECOMENDADO));
        cbAlimentoBom.setChecked(bundle.getBoolean(ALIMENTO_BOM));
    }

    public void limparAlim(View view) {

        edNomeAlim.setText("");
        edDescricaoAlim.setText("");
        edQuantidadeAlim.setText("");
        cbAlimentoBom.setChecked(false);
        edNomeAlim.requestFocus();
        mostrarMensagem(getString(R.string.mensagem_limpar));

    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    public void salvarAlim(View view) {

        Alimento alimento = new Alimento();

        nome = edNomeAlim.getText().toString();
        descricao = edDescricaoAlim.getText().toString();
        consumoRecomendado = edQuantidadeAlim.getText().toString();

        //Só o nome é obrigatório
        if(nome == "" || nome.trim().isEmpty()) {
            mostrarMensagem("Informe o nome do alimento");
            edNomeAlim.requestFocus();
            return;
        }

        alimento.setId(lastID);
        alimento.setNome(nome);
        alimento.setDescricao(descricao);
        alimento.setConsumoRecomendado(consumoRecomendado);
        alimento.setAlimentoBom(cbAlimentoBom.isChecked());

        Intent intent = new Intent();
        intent.putExtra("id", alimento.getId());
        intent.putExtra("nome", alimento.getNome());
        intent.putExtra("descricao", alimento.getDescricao());
        intent.putExtra("quantidade", alimento.getConsumoRecomendado());
        intent.putExtra("qualidade", alimento.getAlimentoBom());

        montarIntentResult(alimento, intent);
        mostrarMensagem(getString(R.string.mensagem_salvar));

    }

    public void editarAlim(View view) {

        Alimento alimento = new Alimento();

        nome = edNomeAlim.getText().toString();
        descricao = edDescricaoAlim.getText().toString();
        consumoRecomendado = edQuantidadeAlim.getText().toString();

        //Só o nome é obrigatório
        if(nome == "" || nome.trim().isEmpty()) {
            mostrarMensagem("Informe o nome do alimento");
            edNomeAlim.requestFocus();
            return;
        }

        alimento.setId(lastID);
        alimento.setNome(nome);
        alimento.setDescricao(descricao);
        alimento.setConsumoRecomendado(consumoRecomendado);
        alimento.setAlimentoBom(cbAlimentoBom.isChecked());

        Intent intent = new Intent();
        intent.putExtra("id", alimento.getId());
        intent.putExtra("nome", alimento.getNome());
        intent.putExtra("descricao", alimento.getDescricao());
        intent.putExtra("quantidade", alimento.getConsumoRecomendado());
        intent.putExtra("qualidade", alimento.getAlimentoBom());

        montarIntentResult(alimento, intent);
        mostrarMensagem(getString(R.string.mensagem_salvar));

    }

    private void montarIntentResult(Alimento alimento, Intent intent) {

        if(retornarDadosParaActivity){
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}