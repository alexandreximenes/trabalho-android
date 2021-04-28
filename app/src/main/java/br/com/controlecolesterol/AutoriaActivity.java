package br.com.controlecolesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AutoriaActivity extends AppCompatActivity {

    private ConstraintLayout layoutAuthorship;
    private TextView tvNome, tvEmail, tvCurso, tvDescricao;
    private TextView textViewLabelNome, textViewLabelCourse, textViewLabelEmail, textViewLabelDescriptionAuthorship, textViewLabelTittleAuthorship;
    private boolean MODO_NOTURNO = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);

        tvNome = (TextView) findViewById(R.id.tvAutoriaNome);
        tvEmail = (TextView) findViewById(R.id.tvAuditoriaEmail);
        tvCurso = (TextView) findViewById(R.id.tvAutoriaCurso);
        tvDescricao = (TextView) findViewById(R.id.tvAuditoriaDescricao);

        textViewLabelNome = (TextView) findViewById(R.id.textViewLabelNome);
        textViewLabelCourse = (TextView) findViewById(R.id.textViewLabelCourse);
        textViewLabelEmail = (TextView) findViewById(R.id.textViewLabelEmail);
        textViewLabelDescriptionAuthorship = (TextView) findViewById(R.id.textViewLabelDescriptionAuthorship);
        textViewLabelTittleAuthorship = (TextView) findViewById(R.id.textViewLabelTittleAuthorship);
        layoutAuthorship = findViewById(R.id.layoutAuthorship);

        MODO_NOTURNO = isModoNoturno();

        setModoNoturno();

        tvNome.setText("Alexandre Tiago Ximenes");
        tvEmail.setText("xyymenes@gmail.com");
        tvCurso.setText("Especialização Tecnologia Java");
        tvDescricao.setText("O aplicativo tem a proposta de organizar melhor a vida do paciente, dando a opção do mesmo colocar os medicamentos que o médico receitou e principalmente colocar os alimentos que são beneficos e maleficos para o seu tratamento para lembrar e de alguma forma incentivar o paciente a buscar conhecer o que cada alimento tras de beneficio ao seu tratamento");
    }

    private void setModoNoturno() {
        if(MODO_NOTURNO){
            layoutAuthorship.setBackgroundColor(UserPreferences.COLOR_DARK);
            tvNome.setTextColor(UserPreferences.COLOR_WHITE);
            tvEmail.setTextColor(UserPreferences.COLOR_WHITE);
            tvCurso.setTextColor(UserPreferences.COLOR_WHITE);
            tvDescricao.setTextColor(UserPreferences.COLOR_WHITE);

            textViewLabelNome.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelCourse .setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelEmail .setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescriptionAuthorship.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTittleAuthorship.setTextColor(UserPreferences.COLOR_YELLOW);
        }else {
            tvNome.setTextColor(UserPreferences.COLOR_BLACK);
            tvEmail.setTextColor(UserPreferences.COLOR_BLACK);
            tvCurso.setTextColor(UserPreferences.COLOR_BLACK);
            tvDescricao.setTextColor(UserPreferences.COLOR_BLACK);

            layoutAuthorship.setBackgroundColor(UserPreferences.COLOR_WHITE);
            textViewLabelNome.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelCourse.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelEmail.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelTittleAuthorship.setTextColor(UserPreferences.COLOR_GRAY);
            textViewLabelDescriptionAuthorship.setTextColor(UserPreferences.COLOR_GRAY);
        }
    }

    public void autoria(View view) {
        Intent intent = new Intent(this, AutoriaActivity.class);
        startActivity(intent);
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences(UserPreferences.PREFERENCES_PATH, Context.MODE_PRIVATE);
    }
    public boolean isModoNoturno() {
        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(UserPreferences.MODO_NOTURNO, false);
    }
}