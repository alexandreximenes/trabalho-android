package br.com.controlecolesterol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {

    EditText edNomeUser, edSobrenomeUser, edDataNascimentoUser, edTelefoneUser, edEmailUser;
    RadioGroup rgSexo;
    RadioButton rbSexoFemininoUser, rbSexoMasculinoUser, rbSexoNaoInformadoUser;
    String nome, sobrenome, dataNascimento, telefone, email;
    int sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        edNomeUser = (EditText) findViewById(R.id.edNomeUser);
        edSobrenomeUser = (EditText) findViewById(R.id.edSobrenomeUser);
        edDataNascimentoUser = (EditText) findViewById(R.id.edDataNascimentoUser);
        edTelefoneUser = (EditText) findViewById(R.id.edTelefoneUser);
        edEmailUser = (EditText) findViewById(R.id.edEmailUser);

        rgSexo = (RadioGroup) findViewById(R.id.rgSexo);

        rbSexoMasculinoUser = (RadioButton) findViewById(R.id.rbSexoMasculinoUser);
        rbSexoFemininoUser = (RadioButton) findViewById(R.id.rbSexoFemininoUser);
        rbSexoNaoInformadoUser = (RadioButton) findViewById(R.id.rbSexoNaoInformadoUser);
    }

    public void salvarUsuario(View view) {

        nome = edNomeUser.getText().toString();
        sobrenome = edSobrenomeUser.getText().toString();
        dataNascimento = edDataNascimentoUser.getText().toString();
        telefone = edTelefoneUser.getText().toString();
        email = edEmailUser.getText().toString();

        if(nome == "" || nome.trim().isEmpty()) {
            mostrarMensagem("Informe seu nome");
            edNomeUser.requestFocus();
            return;
        }
        if(sobrenome == "" || sobrenome.trim().isEmpty()) {
            mostrarMensagem("Informe seu sobrenome");
            edSobrenomeUser.requestFocus();
            return;
        }
        if(dataNascimento == "" || dataNascimento.trim().isEmpty()) {
            mostrarMensagem("Informe sua data de nascimento");
            edDataNascimentoUser.requestFocus();
            return;
        }
        if(telefone == "" || telefone.trim().isEmpty()) {
            mostrarMensagem("Informe seu telefone celular");
            edTelefoneUser.requestFocus();
            return;
        }
        if(email == "" || email.trim().isEmpty()) {
            mostrarMensagem("Informe seu e-mail");
            edEmailUser.requestFocus();
            return;
        }

        switch (rgSexo.getCheckedRadioButtonId()){
            case R.id.rbSexoMasculinoUser:
                mostrarMensagem("Sexo Masculino");
                sexo = R.id.rbSexoMasculinoUser;
                break;
            case R.id.rbSexoFemininoUser:
                mostrarMensagem("Sexo Masculino");
                sexo = R.id.rbSexoFemininoUser;
                break;
            case R.id.rbSexoNaoInformadoUser:
                sexo = R.id.rbSexoNaoInformadoUser;
                break;
        }
        Log.d("Sexo escolhido: ", String.valueOf(sexo));

        mostrarMensagem(getString(R.string.mensagem_salvar));

    }

    public void limparUsuario(View view) {

        edNomeUser.setText("");
        edSobrenomeUser.setText("");
        edDataNascimentoUser.setText("");
        edTelefoneUser.setText("");
        edEmailUser.setText("");
        rgSexo.clearCheck();
        edNomeUser.requestFocus();
        mostrarMensagem(getString(R.string.mensagem_limpar));

    }

    private void mostrarMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

}