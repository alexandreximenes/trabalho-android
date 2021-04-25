package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.controlecolesterol.model.Medicamento;

import static java.util.Arrays.asList;

public class MedicamentosActivity extends AppCompatActivity {

    private EditText edNomeMed, edTratamentoMed, edDiasMed, edIntervaloMed;
    private String nome, tratamento, dias, intervalo;
    private boolean retornarDadosParaActivity;
    private Spinner spinnerDoenca;
    private int lastID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        edNomeMed = (EditText) findViewById(R.id.edNomeMed);
        edTratamentoMed = (EditText) findViewById(R.id.edTratamentoMed);
        edDiasMed = (EditText) findViewById(R.id.edDiasMed);
        edIntervaloMed = (EditText) findViewById(R.id.edIntervaloMed);
        spinnerDoenca = (Spinner) findViewById(R.id.spinnerDoenca);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            retornarDadosParaActivity = bundle.getBoolean(ListaDeMedicamentosActivity.RETORNAR_DADOS_SALVOS);
            lastID = bundle.getInt(ListaDeMedicamentosActivity.LAST_ID);
        }
//        this.mostrarDoencas();

    }

    public void limparMed(View view) {

        edNomeMed.setText("");
        edTratamentoMed.setText("");
        edDiasMed.setText("");
        edIntervaloMed.setText("");
        edNomeMed.requestFocus();
        mostrarMensagem(getString(R.string.mensagem_limpar));
    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    public void salvarMed(View view) {

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

        medicamento.setId(lastID);
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

        Intent intent = new Intent();
        intent.putExtra("id", medicamento.getId());
        intent.putExtra("nome", medicamento.getNome());
        intent.putExtra("tratamento", medicamento.getTratamento());
        intent.putExtra("dias", medicamento.getDias());
        intent.putExtra("intervalo", medicamento.getIntervalo());

//        String doencaSelecionada = (String) spinnerDoenca.getSelectedItem();

        montarIntentResult(medicamento, intent);
        mostrarMensagem(getString(R.string.mensagem_salvar));

    }

    private void montarIntentResult(Medicamento medicamento, Intent intent) {

        if(retornarDadosParaActivity){
            setResult(Activity.RESULT_OK, intent);
        }

        finish();
    }

    public void mostrarDoencas() {

        List<String> doencas = asList(getString(R.string.colesterol), getString(R.string.outras));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doencas);
        spinnerDoenca.setAdapter(arrayAdapter);

    }
}