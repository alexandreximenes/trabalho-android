package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.com.controlecolesterol.dao.Database;
import br.com.controlecolesterol.model.Medicamento;

public class MedicamentosActivity extends AppCompatActivity {

    private boolean MODO_NOTURNO = false;
    private TextView textViewtTituloMedicamento, textViewLabelNomeMedicamento, textViewLabelTratamentoMed, textViewLabelDiasMed, textViewLabelIntervaloMed;
    private EditText edNomeMed, edTratamentoMed, edDiasMed, edIntervaloMed;
    private String nome, tratamento, dias, intervalo;
    private Integer codigoId;
    public static final String ID = "ID";
    private Database database;
    private ConstraintLayout layoutMedicamentos;
    private boolean acaoNovo;
    private boolean acaoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        database = Database.getDatabase(getBaseContext());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edNomeMed = (EditText) findViewById(R.id.edNomeMed);
        edTratamentoMed = (EditText) findViewById(R.id.edTratamentoMed);
        edDiasMed = (EditText) findViewById(R.id.edDiasMed);
        edIntervaloMed = (EditText) findViewById(R.id.edIntervaloMed);

        textViewtTituloMedicamento = findViewById(R.id.textViewtTituloMedicamento);
        textViewLabelNomeMedicamento = findViewById(R.id.textViewLabelNomeMedicamento);
        textViewLabelTratamentoMed = findViewById(R.id.textViewLabelTratamentoMed);
        textViewLabelDiasMed = findViewById(R.id.textViewLabelDiasMed);
        textViewLabelIntervaloMed = findViewById(R.id.textViewLabelIntervaloMed);
        layoutMedicamentos = findViewById(R.id.layoutMedicamentos);

        MODO_NOTURNO = isModoNoturno();
        setModoNoturno();

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
                salvarMed();
                return true;
            case R.id.menuItemLimpar:
                limparMed();
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

        String title = getString(R.string.editando) + getString(R.string.cadastro);
        Mensagem.toast(getBaseContext(), title);
        setTitle(title);

        Medicamento medicamento = database.medicamentoDao().findById(codigoId);

        edNomeMed.setText(medicamento.getNome());
        edTratamentoMed.setText(medicamento.getNome());
        edDiasMed.setText(String.valueOf(medicamento.getDias()));
        edIntervaloMed.setText(String.valueOf(medicamento.getIntervalo()));

    }

    private void setModoNoturno() {
        if(MODO_NOTURNO){
            textViewtTituloMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeMed.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edTratamentoMed.setTextColor(UserPreferences.COLOR_GRAY);
            edTratamentoMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edDiasMed.setTextColor(UserPreferences.COLOR_GRAY);
            edDiasMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edIntervaloMed.setTextColor(UserPreferences.COLOR_GRAY);
            edIntervaloMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            layoutMedicamentos.setBackgroundColor(UserPreferences.COLOR_DARK);

            textViewLabelNomeMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTratamentoMed.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDiasMed.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelIntervaloMed.setTextColor(UserPreferences.COLOR_GRAY);

        }else{
            textViewtTituloMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeMed.setTextColor(UserPreferences.COLOR_GRAY);
            edNomeMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edTratamentoMed.setTextColor(UserPreferences.COLOR_GRAY);
            edTratamentoMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edDiasMed.setTextColor(UserPreferences.COLOR_GRAY);
            edDiasMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            edIntervaloMed.setTextColor(UserPreferences.COLOR_GRAY);
            edIntervaloMed.setHintTextColor(UserPreferences.COLOR_GRAY);
            layoutMedicamentos.setBackgroundColor(UserPreferences.COLOR_DARK);

            textViewLabelNomeMedicamento.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTratamentoMed.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDiasMed.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelIntervaloMed.setTextColor(UserPreferences.COLOR_GRAY);
            layoutMedicamentos.setBackgroundColor(UserPreferences.COLOR_WHITE);
        }
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
    }

    public void limparMed() {

        edNomeMed.setText("");
        edTratamentoMed.setText("");
        edDiasMed.setText("");
        edIntervaloMed.setText("");
        edNomeMed.requestFocus();
        mostrarMensagem(getString(R.string.limpo_com_sucesso));
    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    public void salvarMed() {

        Medicamento medicamento = new Medicamento();

        nome = edNomeMed.getText().toString();
        tratamento = edTratamentoMed.getText().toString();
        dias = edDiasMed.getText().toString();
        intervalo = edIntervaloMed.getText().toString();

        if(nome == "" || nome.trim().isEmpty()) {
            mostrarMensagem("Informe o nome do medicamento");
            edNomeMed.requestFocus();
            return;
        }
        if(tratamento == "" || tratamento.trim().isEmpty()) {
            mostrarMensagem("Descreva o tratamento indicado pelo medico");
            edTratamentoMed.requestFocus();
            return;
        }
        if(dias == "" || dias.trim().isEmpty()) {
            mostrarMensagem("Informe a quantidade de dias que você deve tomar o remedio");
            edDiasMed.requestFocus();
            return;
        }
        if(intervalo == "" || intervalo.trim().isEmpty()) {
            mostrarMensagem("Informe o intervalo em 'horas' para tomar o medicamento");
            edIntervaloMed.requestFocus();
            return;
        }


        if(codigoId != null){
            medicamento.setId(codigoId);
        }

        medicamento.setNome(nome);
        medicamento.setTratamento(tratamento);
        try{
            int d = Integer.parseInt(dias);
            medicamento.setDias(d);
        }catch (Exception e){
            return;
        }

        try{
            int i = Integer.parseInt(intervalo);
            medicamento.setIntervalo(i);
        }catch (Exception e){
            return;
        }

        if(acaoNovo){
            database.medicamentoDao().insert(medicamento);
            Mensagem.toast(getBaseContext(), getString(R.string.salvo_com_sucesso));
        }else if(acaoEditar){
            database.medicamentoDao().update(medicamento);
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