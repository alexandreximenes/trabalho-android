package br.com.controlecolesterol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private static final String NOVO = "NOVO";
    public static final int OK_1 = 1;
    private ConstraintLayout layoutMain;
    Button btAlim, btMed, btCadastrarUsuario, btAutoria;
    private boolean MODO_NOTURNO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAlim = (Button) findViewById(R.id.btAlim);
        btMed = (Button) findViewById(R.id.btMed);
        btCadastrarUsuario = (Button) findViewById(R.id.btCadastrarUsuario);
        btAutoria = (Button) findViewById(R.id.btAutoria);
        layoutMain = findViewById(R.id.layoutMain);

        MODO_NOTURNO = isModoNoturno();
        setModoNoturno();
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
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

    public void autoria(View view) {
        Intent intent = new Intent(this, AutoriaActivity.class);
        startActivity(intent);
    }

    public void telaDeCategoriasAlimento(View view) {
        Intent intent = new Intent(this, ListaDeCategorias.class);
        startActivity(intent);
    }

    public void telaDePreferencias(View view) {
        Intent intent = new Intent(this, UserPreferences.class);
        startActivityForResult(intent, OK_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OK_1 && resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            if(bundle != null){

                MODO_NOTURNO = bundle.getBoolean("MODO_NOTURNO");

                setModoNoturno();

            }
        }
    }

    private void setModoNoturno() {
        if(MODO_NOTURNO){
            layoutMain.setBackgroundColor(UserPreferences.COLOR_DARK);
            btAutoria.setTextColor(UserPreferences.COLOR_GRAY);
        }else{
            layoutMain.setBackgroundColor(UserPreferences.COLOR_WHITE);
            btAutoria.setTextColor(UserPreferences.COLOR_BLUE);
        }
    }
}