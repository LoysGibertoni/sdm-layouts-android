package br.edu.ifsp.scl.sdm.layoutssdm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private static final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";
    private static final String LISTA_TELEFONES = "LISTA_TELEFONES";
    private static final String LISTA_TIPOS_TELEFONES = "LISTA_TIPOS_TELEFONES";
    private static final String LISTA_EMAILS = "LISTA_EMAILS";

    private CheckBox notificaceosCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText emailEditText;

    private LinearLayout telefoneLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private LinearLayout emailLinearLayout;
    private ArrayList<View> emailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        // Referências para as views
        notificaceosCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);
        telefoneLinearLayout= findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout= findViewById(R.id.emailLinearLayout);

        telefoneArrayList = new ArrayList<>();
        emailArrayList = new ArrayList<>();

        // Tratando evento de check no checkbox
        notificaceosCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                notificacoesRadioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> telefones = new ArrayList<>();
        ArrayList<Integer> tiposTelefones = new ArrayList<>();
        for (View viewTelefone : telefoneArrayList) {
            EditText editText = viewTelefone.findViewById(R.id.telefoneEditText);
            Spinner spinner = viewTelefone.findViewById(R.id.tipoTelefoneSpinner);
            telefones.add(editText.getText().toString());
            tiposTelefones.add(spinner.getSelectedItemPosition());
        }
        ArrayList<String> emails = new ArrayList<>();
        for (View viewEmail : emailArrayList) {
            EditText editText = viewEmail.findViewById(R.id.emailEditText);
            emails.add(editText.getText().toString());
        }

        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificaceosCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());
        outState.putStringArrayList(LISTA_TELEFONES, telefones);
        outState.putIntegerArrayList(LISTA_TIPOS_TELEFONES, tiposTelefones);
        outState.putStringArrayList(LISTA_EMAILS, emails);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        notificaceosCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));
        notificacoesRadioGroup.check(savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, View.NO_ID));
        ArrayList<String> telefones = savedInstanceState.getStringArrayList(LISTA_TELEFONES);
        ArrayList<Integer> tiposTelefones = savedInstanceState.getIntegerArrayList(LISTA_TIPOS_TELEFONES);
        ArrayList<String> emails = savedInstanceState.getStringArrayList(LISTA_EMAILS);
        if (telefones != null) {
            for (int i = 0; i < telefones.size(); i++) {
                this.criarViewTelefone(telefones.get(i), tiposTelefones.get(i));
            }
        }
        if (emails != null) {
            for (String email : emails) {
                this.criarViewEmail(email);
            }
        }
    }

    public void limparFormulario(View botao) {
        nomeEditText.setText(null);
        emailEditText.setText(null);
        notificacoesRadioGroup.clearCheck();
        notificaceosCheckBox.setChecked(false);
        nomeEditText.requestFocus();
    }

    public void adicionarTelefone(View botao) {
        this.criarViewTelefone();
    }

    private View criarViewTelefone() {
        LayoutInflater layoutInflater = getLayoutInflater();

        View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
        telefoneArrayList.add(novoTelefoneLayout);
        telefoneLinearLayout.addView(novoTelefoneLayout);
        return novoTelefoneLayout;
    }

    private View criarViewTelefone(String telefone, Integer tipo) {
        View novoTelefoneLayout = this.criarViewTelefone();
        EditText editText = novoTelefoneLayout.findViewById(R.id.telefoneEditText);
        Spinner spinner = novoTelefoneLayout.findViewById(R.id.tipoTelefoneSpinner);
        editText.setText(telefone);
        spinner.setSelection(tipo);
        return novoTelefoneLayout;
    }

    public void adicionarEmail(View botao) {
        this.criarViewTelefone();
    }

    private View criarViewEmail() {
        LayoutInflater layoutInflater = getLayoutInflater();

        View novoEmailLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
        telefoneArrayList.add(novoEmailLayout);
        telefoneLinearLayout.addView(novoEmailLayout);
        return novoEmailLayout;
    }

    private View criarViewEmail(String email) {
        View novoEmailLayout = this.criarViewEmail();
        EditText editText = novoEmailLayout.findViewById(R.id.emailEditText);
        editText.setText(email);
        return novoEmailLayout;
    }
}
