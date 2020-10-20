package com.natesanchez.natesnoteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  private final ArrayList<Note> notes = new ArrayList<>();
  private RecyclerView recView;
  NoteAdapter noteAdapter;
  private static final int editRequestCode = 1;
  private Note passedNote;
  private int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    readJSON();

    recView = findViewById(R.id.noteRecycler);
    noteAdapter = new NoteAdapter(notes, this);
    recView.setAdapter(noteAdapter);
    recView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_about:
        about();
        return true;
      case R.id.menu_add:
        addNote();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onPause() {
    writeJSON();
    super.onPause();
  }

  private void readJSON() {
    // get the saved notes from the json file
    try {
      FileInputStream fis = getApplicationContext().openFileInput(getString(R.string.note_save_file));
      byte[] data = new byte[(int) fis.available()];
      int loaded = fis.read(data);
      fis.close();
      String json = new String(data);

      JSONArray notesArray = new JSONArray(json);
      for (int i = 0; i < notesArray.length(); i++) {
        JSONObject jObj = notesArray.getJSONObject(i);
        Note n = new Note(
                jObj.getString("title"),
                jObj.getString("text")
        );
        n.setDate(jObj.getLong("lastUpdated"));
        notes.add(n);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void writeJSON() {
    try {
      FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.note_save_file), Context.MODE_PRIVATE);
      JsonWriter jw = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
      jw.setIndent("  ");
      jw.beginArray();
      for (Note n : notes) {
        jw.beginObject();
        jw.name("title").value(n.getTitle());
        jw.name("text").value(n.getText());
        jw.name("lastUpdated").value(n.getDate().getTime());
        jw.endObject();
      }
      jw.endArray();
      jw.close();
      Toast.makeText(getApplicationContext(),"Saved notes",Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void about() {
    // opens the about activity
    Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
    startActivity(intent);
  }

  private void addNote() {
    // opens the edit activity to make a new note
    Intent intent = new Intent(this, EditActivity.class);
    startActivityForResult(intent, editRequestCode);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent in) {
    super.onActivityResult(requestCode, resultCode, in);
    if (requestCode == editRequestCode) {
      if (resultCode == RESULT_OK) {
        passedNote = (Note) in.getSerializableExtra("NEW_NOTE");
        passedNote.setDate((long) new Date().getTime());
        if (in.hasExtra("POS")) {
          pos = in.getIntExtra("POS", 0);
          if (!(passedNote.getTitle().equals(notes.get(pos).getTitle())
                || passedNote.getText().equals(notes.get(pos).getText()))) {
            deleteNote(pos);
            notes.add(0, passedNote);
          }
        } else {
          notes.add(0, passedNote);
        }
        noteAdapter.notifyDataSetChanged();
      }
    }
  }

  private void deleteNote(int pos) {
    notes.remove(pos);
    noteAdapter.notifyDataSetChanged();
  }

  public void onClick(View v) {
    pos = recView.getChildLayoutPosition(v);
    passedNote = notes.get(pos);
    Intent intent = new Intent(this, EditActivity.class);
    intent.putExtra("PASSED_NOTE", passedNote);
    intent.putExtra("POS", pos);
    startActivityForResult(intent, editRequestCode);
  }

  public void onLongClick(View v) {
    pos = recView.getChildLayoutPosition(v);
    Note n = notes.get(pos);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Delete note");
    builder.setMessage("Are you sure you want to delete this note?");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialogInterface, int i) {
        deleteNote(pos);
        Toast.makeText(getApplicationContext(),"Note deleted",Toast.LENGTH_SHORT).show();
      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(getApplicationContext(),"Cancelled deletion",Toast.LENGTH_SHORT).show();
      }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
  }
}