package br.com.controlecolesterol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AutoriaActivity extends AppCompatActivity {

    private TextView tvNome, tvEmail, tvCurso, tvDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoria);

        TextView nome = (TextView) findViewById(R.id.tvAutoriaNome);
        TextView tvEmail = (TextView) findViewById(R.id.tvAuditoriaEmail);
        TextView tvCurso = (TextView) findViewById(R.id.tvAutoriaCurso);
        TextView tvDescricao = (TextView) findViewById(R.id.tvAuditoriaDescricao);

        nome.setText("Alexandre Tiago Ximenes");
        tvEmail.setText("xyymenes@gmail.com");
        tvCurso.setText("Especialização Tecnologia Java");
        tvDescricao.setText("O aplicativo tem a proposta de organizar melhor a vida do paciente, dando a opção do mesmo colocar os medicamentos que o médico receitou e principalmente colocar os alimentos que são beneficos e maleficos para o seu tratamento para lembrar e de alguma forma incentivar o paciente a buscar conhecer o que cada alimento tras de beneficio ao seu tratamento");
    }
}