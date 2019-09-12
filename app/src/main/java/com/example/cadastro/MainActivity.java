package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText editTextNome;
    EditText editTextCpf;
    EditText editTextEmail;
    EditText editTextSenha;
    EditText editTextTelefone;
    EditText editTextCelular;
    EditText editTextCidade;
    Button buttonSalvar;
    Button buttonLimpar;
    Button buttonVisualizar;
    ConstraintLayout root_layout_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextCidade = findViewById(R.id.editTextCidade);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
        buttonVisualizar = findViewById(R.id.buttonVisualizar);
        root_layout_main = findViewById(R.id.root_layout_main);
    }

    public void chamarFormVisualizarUsuarios(View view){
        Intent intent = new Intent(this, VisualizarUsuariosActivity.class);
        startActivity(intent);
    }

    public void salvar(View view) {
        String nome = editTextNome.getText().toString();
        String cpf = editTextCpf.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();
        String telefone = editTextTelefone.getText().toString();
        String celular = editTextCelular.getText().toString();
        String cidade = editTextCidade.getText().toString();

        Cadastro cadastro = new Cadastro(nome, cpf, email, senha, telefone, celular, cidade);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cadastros")
                .add(cadastro)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Snackbar.make(root_layout_main,"Cadastro Realizado!",
                                Snackbar.LENGTH_LONG).show();
                        limpar(view);
                    } else {
                        showSnackbar(task.getException().getMessage());
                    }
                }
                )
        ;
    }

    public void limpar(View view) {
        editTextNome.setText(null);
        editTextCpf.setText(null);
        editTextEmail.setText(null);
        editTextSenha.setText(null);
        editTextTelefone.setText(null);
        editTextCelular.setText(null);
        editTextCidade.setText(null);
    }

    private void showSnackbar(String msg){
        Snackbar.make(
                findViewById(R.id.root_layout_main),
                msg,
                Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
}
