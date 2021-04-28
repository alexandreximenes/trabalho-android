package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    private EditText editTextCategory;
    private TextView textViewLabelTituloCategoria, textViewLabelDescription;
    private ConstraintLayout layoutCategoriaActivity;
    private String descricao;
    public static final String ID = "ID";
    private boolean acaoNovo;
    private boolean acaoEditar;
    private Integer codigoId;
    private Database database;
    private boolean MODO_NOTURNO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        database = Database.getDatabase(this);

        editTextCategory = (EditText) findViewById(R.id.editTextCategory);
        textViewLabelTituloCategoria = (TextView) findViewById(R.id.textViewLabelTituloCategoria);
        textViewLabelDescription = (TextView) findViewById(R.id.textViewLabelDescription);
        layoutCategoriaActivity = findViewById(R.id.layoutCategoriaActivity);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MODO_NOTURNO = isModoNoturno();
        setModoNoturno();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            codigoId = bundle.getInt(ID, -1);
            if (codigoId > 0) {
                acaoEditar = bundle.getBoolean(ListaDeAlimentosActivity.EDITAR, false);
                exibirDadosDeEdicaoNaTela(bundle);
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
            editTextCategory.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTituloCategoria.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescription.setTextColor(UserPreferences.COLOR_GRAY);
            layoutCategoriaActivity.setBackgroundColor(UserPreferences.COLOR_DARK);

        }else{
            editTextCategory.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTituloCategoria.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescription.setTextColor(UserPreferences.COLOR_GRAY);
            layoutCategoriaActivity.setBackgroundColor(UserPreferences.COLOR_WHITE);
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
        getMenuInflater().inflate(R.menu.acao_categoria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.itemMenuSalvarCategoria:
                salvarCategoria();
                return true;
            case R.id.itemMenuSairCategoria:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void exibirDadosDeEdicaoNaTela(Bundle bundle) {

        String title = getString(R.string.editando) + getString(R.string.cadastro);
        Mensagem.toast(getBaseContext(), title);
        setTitle(title);

        codigoId = bundle.getInt(ID);

        editTextCategory.setText(bundle.getString(ListaDeCategorias.DESCRICAO));

    }

    public void salvarCategoria() {

        Categoria categoria = new Categoria();

        descricao = editTextCategory.getText().toString();

        //Só a descricao é obrigatório
        if(descricao == "" || descricao.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_qual_categoria));
            editTextCategory.requestFocus();
            return;
        }

        categoria.setDescricao(descricao);

        if(codigoId != null){
            categoria.setId(codigoId);
        }

        Intent intent = new Intent();
        intent.putExtra("MODO_NOTURNO", MODO_NOTURNO);

        if(acaoNovo){
            database.categoriaDao().insert(categoria);
            intent.putExtra(ListaDeAlimentosActivity.ACAO, ListaDeAlimentosActivity.NOVO);
            Mensagem.toast(getBaseContext(), getString(R.string.salvo_com_sucesso));
        }else if(acaoEditar){
            database.categoriaDao().update(categoria);
            intent.putExtra(ListaDeAlimentosActivity.ACAO, ListaDeAlimentosActivity.EDITAR);
            Mensagem.toast(getBaseContext(), getString(R.string.editado_com_sucesso));
        }

        Mensagem.toast(getBaseContext(), getString(R.string.salvo_com_sucesso));

        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}