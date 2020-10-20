package com.natesanchez.natesnoteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

  private EditText editTitle;
  private EditText editBody;
  private Note passedNote;
  private int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);

    editTitle = findViewById(R.id.editTextNoteTitle);
    editBody = findViewById(R.id.editTextNoteBody);

    Intent intent = getIntent();
    if (intent.hasExtra("PASSED_NOTE")) {
      passedNote = (Note) intent.getSerializableExtra("PASSED_NOTE");
      editTitle.setText(passedNote.getTitle());
      editBody.setText(passedNote.getText());
    }
    if (intent.hasExtra("POS")) {
      pos = intent.getIntExtra("POS", 0);
    } else {
      pos = -1;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.edit_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.save:
        saveNote();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void saveNote() {

    EditText etTitle = findViewById(R.id.editTextNoteTitle);
    EditText etBody = findViewById(R.id.editTextNoteBody);
    String title = etTitle.getText().toString();
    String body = etBody.getText().toString();
    if (title.trim().equals("")) {
      finish();
      Toast.makeText(this, "Note was not saved (missing title)", Toast.LENGTH_SHORT).show();
    }
    passedNote = new Note(title, body);
    Intent newNote = new Intent();
    newNote.putExtra("NEW_NOTE", passedNote);
    if (pos != -1) {
      newNote.putExtra("POS", pos);
    }
    Toast.makeText(this, "Saving note", Toast.LENGTH_SHORT).show();
    setResult(RESULT_OK, newNote);
    finish();
  }

  @Override
  public void onBackPressed() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Save note");
    builder.setMessage("Do you want to save this note before exiting?");
    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
        saveNote();
      }
    });
    builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(getApplicationContext(),"Exited",Toast.LENGTH_SHORT).show();
        finish();
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }
}
