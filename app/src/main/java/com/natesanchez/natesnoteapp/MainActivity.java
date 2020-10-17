package com.natesanchez.natesnoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private final List<Note> notes = new ArrayList<>();
  private RecyclerView recView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    loadFile();
    recView = findViewById(R.id.noteRecycler);
    NoteAdapter noteAdapter = new NoteAdapter(notes, this);
    recView.setAdapter(noteAdapter);
    recView.setLayoutManager(new LinearLayoutManager(this));
    notes.add(new Note("test1", "sdfdsgdsfgfdsgdf dfsg ds dsg sdfg fdsg "));
    notes.add(new Note("test2", "sdfdsgds dsf s afd affgfdsgdf dfsg ds dsg sdfg fdsg "));
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
        Toast.makeText(getApplicationContext(),"About tapped",Toast.LENGTH_SHORT).show();
        return true;
      case R.id.menu_add:
        addNote();
        Toast.makeText(getApplicationContext(),"Adding new note!",Toast.LENGTH_SHORT).show();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void loadFile() {
    try {
      InputStream is = getApplicationContext().openFileInput("noteAppSaveFile");
      BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void makeFile() {

  }

  private void about() {

  }

  private void addNote() {

  }

  private void deleteNote() {

  }
}