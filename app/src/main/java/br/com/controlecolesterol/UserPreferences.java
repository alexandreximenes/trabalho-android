package br.com.controlecolesterol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity {

    public static final int COLOR_DARK = Color.rgb(20, 20, 31);
    public static final int COLOR_GRAY = Color.GRAY;
    public static final int COLOR_BLUE = Color.BLUE;
    public static final int COLOR_WHITE = Color.WHITE;
    public static final int COLOR_BLACK = Color.BLACK;
    public static final int COLOR_YELLOW = Color.YELLOW;

    private ConstraintLayout constraintLayout;
    private TextView textViewTitulo, textViewLabelModoNoturno;
    private int textColor;
    private int backgroundColorLayout;


    public static final String MODO_NOTURNO = "MODO_NOTURNO";
    private Switch switchModoNoturno;
    private boolean modoNoturnoChecked;


    public static final String PREFERENCES_PATH = "br.com.controlecolesterol.preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        switchModoNoturno = findViewById(R.id.switchModoNoturno);
        textViewTitulo = findViewById(R.id.textViewTitulo);
        textViewLabelModoNoturno = findViewById(R.id.textViewLabelModoNoturno);
        constraintLayout = findViewById(R.id.layoutUserPrefId);

        backgroundColorLayout = constraintLayout.getDrawingCacheBackgroundColor();
        textColor = textViewTitulo.getTextColors().getDefaultColor();

        SharedPreferences prefs = getPrefs();

        boolean isSwitchChecked = prefs.getBoolean(MODO_NOTURNO, false);

        switchModoNoturno.setChecked(isSwitchChecked);

        modoNoturnoChecked = isSwitchChecked;

        setModoNoturno();

        switchModoNoturno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchModoNoturno.setChecked(isChecked);
                modoNoturnoChecked = isChecked;
                setModoNoturno();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acao_salvar_sair, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
                switch (item.getItemId()){
                        case R.id.menuItemSalvarPrefs:
                                salvarPrefs();
                                return true;
                        case R.id.menuItemSairPrefs:
                                voltarTela();
                                return true;
                        default:
                                return super.onOptionsItemSelected(item);
        }

    }

    private void voltarTela() {
        finish();
    }


    private SharedPreferences getPrefs() {

        return getSharedPreferences(PREFERENCES_PATH, Context.MODE_PRIVATE);

    }

    public boolean isModoNoturno() {

        SharedPreferences prefs = getPrefs();
        return prefs.getBoolean(MODO_NOTURNO, false);

    }

    private void salvarPrefs() {

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_PATH, Context.MODE_PRIVATE);

        modoNoturnoChecked = switchModoNoturno.isChecked();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(MODO_NOTURNO, modoNoturnoChecked);

        editor.commit();

        setModoNoturno();

        mostrarMensagem(getBaseContext(), getString(R.string.preferencias_salvas));

        Intent i = new Intent();
        i.putExtra(MODO_NOTURNO, modoNoturnoChecked);

        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void setModoNoturno(){

        if(modoNoturnoChecked){

            constraintLayout.setBackgroundColor(COLOR_DARK);
            textViewTitulo.setTextColor(COLOR_GRAY);
            textViewLabelModoNoturno.setTextColor(COLOR_GRAY);

        }else{
            constraintLayout.setBackgroundColor(backgroundColorLayout);
            textViewTitulo.setTextColor(textColor);
            textViewLabelModoNoturno.setTextColor(textColor);
        }
    }

    private void mostrarMensagem(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}