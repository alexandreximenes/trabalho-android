package br.com.controlecolesterol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.controlecolesterol.dao.AlimentoDao;
import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Alimento;
import br.com.controlecolesterol.model.Categoria;

public class AlimentosActivity extends AppCompatActivity {

    private ConstraintLayout layoutAlimentActivity, layoutParentAlimentActivity;
    private ScrollView scrollViewAlimentoActivity;
    private EditText edNomeAlim, edDescricaoAlim, edQuantidadeAlim;
    private CheckBox cbAlimentoBom;
    private Spinner spinnerCategoria;
    private Database database;
    private String nome, descricao, consumoRecomendado;
    private boolean acaoNovo;
    private boolean acaoEditar;
    private Integer codigoId;
    private boolean MODO_NOTURNO = false;

    private TextView textViewLabelTitle, textViewLabelCodigo, textViewLabelNameFood, textViewLabelDescriptionFood, textViewLabelConsume, textViewLabelQuality, textViewLabelCategory;
    private TextView textViewCodigo;

    public static final String ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos);

        database = Database.getDatabase(getBaseContext());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        textViewLabelCodigo = (TextView) findViewById(R.id.textViewLabelCodigo);
        textViewCodigo = (TextView) findViewById(R.id.textViewCodigo);
        textViewCodigo.setVisibility(View.INVISIBLE);
        textViewLabelCodigo.setVisibility(View.INVISIBLE);

        textViewLabelTitle = (TextView) findViewById(R.id.textViewLabelTitle);
        textViewLabelNameFood = (TextView) findViewById(R.id.textViewLabelNameFood);
        textViewLabelDescriptionFood = (TextView) findViewById(R.id.textViewLabelDescriptionFood);
        textViewLabelConsume = (TextView) findViewById(R.id.textViewLabelConsume);
        textViewLabelQuality = (TextView) findViewById(R.id.textViewLabelQuality);
        textViewLabelCategory = (TextView) findViewById(R.id.textViewLabelCategory);
        layoutAlimentActivity = findViewById(R.id.layoutAlimentActivity);
        layoutParentAlimentActivity = findViewById(R.id.layoutParentAlimentActivity);
        scrollViewAlimentoActivity = findViewById(R.id.scrollViewAlimentoActivity);

        edNomeAlim = (EditText) findViewById(R.id.edNomeAlim);
        edNomeAlim = (EditText) findViewById(R.id.edNomeAlim);
        edDescricaoAlim = (EditText) findViewById(R.id.edDescricaoAlim);
        edQuantidadeAlim = (EditText) findViewById(R.id.edQuantidadeAlim);
        cbAlimentoBom = (CheckBox) findViewById(R.id.cbBomAlim);
        spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);

        MODO_NOTURNO = isModoNoturno();
        setModoNoturno();

        montarSpinnerCategoria(null);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            codigoId = bundle.getInt(ID, -1);
            if (codigoId > 0) {
                acaoEditar = bundle.getBoolean(ListaDeAlimentosActivity.EDITAR, false);
                exibirDadosDeEdicaoNaTela();
            } else {
                codigoId = null;
                String title = getString(R.string.novo) + getString(R.string.cadastro);
                Mensagem.toast(getBaseContext(), title);
                setTitle(title);
                acaoNovo = bundle.getBoolean(ListaDeAlimentosActivity.NOVO, false);
            }
        }
    }

    private void setModoNoturno() {
        if(MODO_NOTURNO){

            scrollViewAlimentoActivity.setBackgroundColor(UserPreferences.COLOR_DARK);
            layoutParentAlimentActivity.setBackgroundColor(UserPreferences.COLOR_DARK);
            layoutAlimentActivity.setBackgroundColor(UserPreferences.COLOR_DARK);
            textViewLabelCodigo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewCodigo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTitle.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelNameFood.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescriptionFood.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelConsume.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelQuality.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelCategory.setTextColor(UserPreferences.COLOR_GRAY);

            edNomeAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            edDescricaoAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edDescricaoAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            edQuantidadeAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edQuantidadeAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            cbAlimentoBom.setTextColor(UserPreferences.COLOR_GRAY);
            cbAlimentoBom.setHintTextColor(UserPreferences.COLOR_GRAY);

            spinnerCategoria.setBackgroundColor(UserPreferences.COLOR_GRAY);

        }else{

            scrollViewAlimentoActivity.setBackgroundColor(UserPreferences.COLOR_WHITE);
            layoutParentAlimentActivity.setBackgroundColor(UserPreferences.COLOR_WHITE);
            layoutAlimentActivity.setBackgroundColor(UserPreferences.COLOR_WHITE);
            textViewLabelCodigo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewCodigo.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTitle.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelNameFood.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescriptionFood.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelConsume.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelQuality.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelCategory.setTextColor(UserPreferences.COLOR_GRAY);

            edNomeAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            edDescricaoAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edDescricaoAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            edQuantidadeAlim.setTextColor(UserPreferences.COLOR_GRAY);
            edQuantidadeAlim.setHintTextColor(UserPreferences.COLOR_GRAY);

            cbAlimentoBom.setTextColor(UserPreferences.COLOR_GRAY);
            cbAlimentoBom.setHintTextColor(UserPreferences.COLOR_GRAY);

            spinnerCategoria.setBackgroundColor(UserPreferences.COLOR_GRAY);
        }
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
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

    private void exibirDadosDeEdicaoNaTela() {

        textViewCodigo.setVisibility(View.VISIBLE);
        textViewLabelCodigo.setVisibility(View.VISIBLE);

        String title = getString(R.string.editando) + getString(R.string.cadastro);
        Mensagem.toast(getBaseContext(), title);
        setTitle(title);

        Alimento alimento = database.alimentoDao().findById(codigoId);

        textViewCodigo.setText(String.valueOf(alimento.getId()));

        edNomeAlim.setText(alimento.getNome());
        edDescricaoAlim.setText(alimento.getDescricao());
        edQuantidadeAlim.setText(alimento.getConsumoRecomendado());
        cbAlimentoBom.setChecked(alimento.isAlimentoBom());

        montarSpinnerCategoria(alimento.getCategoriaId());
    }

    private void montarSpinnerCategoria(Integer categoriaId) {
        List<Categoria> categorias = database.categoriaDao().findAll();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categorias);
        spinnerCategoria.setAdapter(arrayAdapter);

       if(categoriaId != null){
           int categoriaPosition = categetPosicaoCategoriaId(categorias, categoriaId);
           spinnerCategoria.setSelection(categoriaPosition);
       }
    }

    private int categetPosicaoCategoriaId(List<Categoria> categorias, int categoriaId) {
        for(int i = 0; i < categorias.size() ; i++){
            if(categorias.get(i).getId() == categoriaId)
                return i;
        }
        return -1;
    }

    public void limparAlim() {

        edNomeAlim.setText("");
        edDescricaoAlim.setText("");
        edQuantidadeAlim.setText("");
        cbAlimentoBom.setChecked(false);
        edNomeAlim.requestFocus();
        spinnerCategoria.setSelection(0);
        Mensagem.toast(getBaseContext(), getString(R.string.limpo_com_sucesso));
    }

    public void salvarAlim() {

        Alimento alimento = new Alimento();

        nome = edNomeAlim.getText().toString();
        descricao = edDescricaoAlim.getText().toString();
        consumoRecomendado = edQuantidadeAlim.getText().toString();

        //Só o nome é obrigatório
        if(nome == "" || nome.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_o_nome_do_alimento));
            edNomeAlim.requestFocus();
            return;
        }

        if(codigoId != null){
            alimento.setId(codigoId);
        }
        alimento.setNome(nome);
        alimento.setDescricao(descricao);
        alimento.setConsumoRecomendado(consumoRecomendado);
        alimento.setAlimentoBom(cbAlimentoBom.isChecked());

        if(spinnerCategoria.getSelectedItemId() == -1){
            Mensagem.toast(getBaseContext(), getString(R.string.escolha_uma_categoria));
            return;
        }
        Categoria categoria = (Categoria) spinnerCategoria.getSelectedItem();
        alimento.setCategoriaId(categoria.getId());

        AlimentoDao alimentoDao = Database.getDatabase(getBaseContext()).alimentoDao();
        if(acaoNovo){
            alimentoDao.insert(alimento);
            Mensagem.toast(getBaseContext(), getString(R.string.salvo_com_sucesso));
        }else if(acaoEditar){
            alimentoDao.update(alimento);
            Mensagem.toast(getBaseContext(), getString(R.string.editado_com_sucesso));
        }

        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}