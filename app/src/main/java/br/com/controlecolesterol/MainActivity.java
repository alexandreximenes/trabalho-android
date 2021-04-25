package br.com.controlecolesterol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btAlim, btMed, btCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAlim = (Button) findViewById(R.id.btAlim);
        btMed = (Button) findViewById(R.id.btMed);
        btCadastrarUsuario = (Button) findViewById(R.id.btCadastrarUsuario);

    }

    public void telaMedicamentos(View view) {
        Intent intent = new Intent(this, ListaDeMedicamentosActivity.class);
        startActivity(intent);
    }

    public void telaListaAlimentos(View view) {
        Intent intent = new Intent(this, ListaDeAlimentosActivity.class);
        startActivity(intent);
    }

    public void telaUsuario(View view) {
        Intent intent = new Intent(this, UsuarioActivity.class);
        startActivity(intent);
    }

    public void listaAlimentos(View view) {
        Intent intent = new Intent(this, ListaDeAlimentosActivity.class);
        startActivity(intent);
    }

    public void autoria(View view) {
        Intent intent = new Intent(this, AutoriaActivity.class);
        startActivity(intent);
    }
}