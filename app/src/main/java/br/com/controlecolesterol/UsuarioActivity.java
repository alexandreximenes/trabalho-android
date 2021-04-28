package br.com.controlecolesterol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {

    EditText edNomeUser, edSobrenomeUser, edDataNascimentoUser, edTelefoneUser, edEmailUser;
    RadioGroup rgSexo;
    RadioButton rbSexoFemininoUser, rbSexoMasculinoUser, rbSexoNaoInformadoUser;
    String nome, sobrenome, dataNascimento, telefone, email;
    int sexo;
    private boolean MODO_NOTURNO = false;
//    private TextView

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

        MODO_NOTURNO = isModoNoturno();
        setModoNoturno();

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

    public void salvarUsuario(View view) {

        nome = edNomeUser.getText().toString();
        sobrenome = edSobrenomeUser.getText().toString();
        dataNascimento = edDataNascimentoUser.getText().toString();
        telefone = edTelefoneUser.getText().toString();
        email = edEmailUser.getText().toString();

        if(nome == "" || nome.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_seu_nome));
            edNomeUser.requestFocus();
            return;
        }
        if(sobrenome == "" || sobrenome.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_seu_sobrenome));
            edSobrenomeUser.requestFocus();
            return;
        }
        if(dataNascimento == "" || dataNascimento.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_sua_data_de_nascimento));
            edDataNascimentoUser.requestFocus();
            return;
        }
        if(telefone == "" || telefone.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_seu_telefone_celular));
            edTelefoneUser.requestFocus();
            return;
        }
        if(email == "" || email.trim().isEmpty()) {
            Mensagem.toast(getBaseContext(), getString(R.string.informe_seu_email));
            edEmailUser.requestFocus();
            return;
        }

        switch (rgSexo.getCheckedRadioButtonId()){
            case R.id.rbSexoMasculinoUser:
                Mensagem.toast(getBaseContext(), getString(R.string.sexo_masculino));
                sexo = R.id.rbSexoMasculinoUser;
                break;
            case R.id.rbSexoFemininoUser:
                Mensagem.toast(getBaseContext(), getString(R.string.sexo_feminino));
                sexo = R.id.rbSexoFemininoUser;
                break;
            case R.id.rbSexoNaoInformadoUser:
                sexo = R.id.rbSexoNaoInformadoUser;
                break;
        }

        Mensagem.toast(getBaseContext(), getString(R.string.salvo_com_sucesso));

    }

    public void limparUsuario(View view) {

        edNomeUser.setText("");
        edSobrenomeUser.setText("");
        edDataNascimentoUser.setText("");
        edTelefoneUser.setText("");
        edEmailUser.setText("");
        rgSexo.clearCheck();
        edNomeUser.requestFocus();
        Mensagem.toast(getBaseContext(), getString(R.string.limpo_com_sucesso));

    }
}