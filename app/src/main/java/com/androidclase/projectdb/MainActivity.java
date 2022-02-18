package com.androidclase.projectdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText Name, Commentary;
    private Spinner getName;
    private TextView getText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.id_commentary);
        Commentary = findViewById(R.id.text_commentary);
        getName = findViewById(R.id.spinner);
        getText = findViewById(R.id.commentary_viewer);

        dbHelper = new DatabaseHelper(this);
        dbHelper.spinner(this, getName);
    }

    public void addComment(View view) {
        String newName = Name.getText().toString();
        String newCommentary = Commentary.getText().toString();

        if (newName.isEmpty() || newCommentary.isEmpty()) {
            Toast.makeText(this, "No puede haber ningún campo vacío", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertData(newName, newCommentary);
            Toast.makeText(this, "Comentario añadido", Toast.LENGTH_SHORT).show();

            Name.setText("");
            Commentary.setText("");
        }
    }

    public void showComment(View view) {
        getText.setText(dbHelper.getData());
    }

    public void deleteComment(View view) {
        dbHelper.deleteData();
        Toast.makeText(this, "Comentario eliminado", Toast.LENGTH_SHORT).show();
    }
}