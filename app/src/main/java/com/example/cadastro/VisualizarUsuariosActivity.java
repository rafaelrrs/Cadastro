package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;


public class VisualizarUsuariosActivity extends AppCompatActivity {

    ListView listviewUsuarios;
    ConstraintLayout root_layout_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_usuarios);
        root_layout_users = findViewById(R.id.root_layout_users);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<Cadastro> cadastro = new ArrayList<>();

        listviewUsuarios = findViewById(R.id.listviewUsuarios);

        db.collection("cadastros")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Toast.makeText(this,"Teste IF",Toast.LENGTH_SHORT).show();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Toast.makeText(this,"Teste FOR inicio",Toast.LENGTH_LONG).show();
                            cadastro.add(new Cadastro(
                                    document.get("nome").toString(),
                                    document.get("cpf").toString(),
                                    document.get("email").toString(),
                                    document.get("senha").toString(),
                                    document.get("telefone").toString(),
                                    document.get("celular").toString(),
                                    document.get("cidade").toString()
                            ));
                            document.getReference().addSnapshotListener((documentSnapshot, e) -> {
                                showSnackbar("Ocorreu uma atualização.");
                            });
                        }
                        ArrayAdapter<Cadastro> arrayAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, cadastro);
                        listviewUsuarios.setAdapter(arrayAdapter);
                    } else {
                        showSnackbar(task.getException().getMessage());
                    }
                });
    }

    private void showSnackbar(String msg){
        Snackbar.make(
                findViewById(R.id.root_layout_users),
                msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }
}


