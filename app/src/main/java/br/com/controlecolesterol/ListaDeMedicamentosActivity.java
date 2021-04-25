package br.com.controlecolesterol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.controlecolesterol.model.Alimento;
import br.com.controlecolesterol.model.Medicamento;

public class ListaDeMedicamentosActivity extends AppCompatActivity {

    private ListView listaDeMedicamentos;
    List<Medicamento> medicamentos = new ArrayList<>();
    public static final String LAST_ID = "0";
    private static final int OK_1 = 1;
    public static final String RETORNAR_DADOS_SALVOS = "retornar_dados_salvos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_medicamentos);

        listaDeMedicamentos = (ListView) findViewById(R.id.listaDeMedicamentos);

        popularListaDeMedicamentos();

        listaDeMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Medicamento item = (Medicamento) listaDeMedicamentos.getItemAtPosition(position);
                mostrarMensagem(getApplicationContext(), item + getString(R.string.foi_clicado));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_medicamentos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()){
            case R.id.menuAddMedicamentos:
                intent = new Intent(this, MedicamentosActivity.class);
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


    private void mostrarMensagem(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    private void popularListaDeMedicamentos() {
        montarListaAdapter();
    }

    private void montarListaAdapter() {
        ArrayAdapter<Medicamento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicamentos);
        listaDeMedicamentos.setAdapter(adapter);
    }
}