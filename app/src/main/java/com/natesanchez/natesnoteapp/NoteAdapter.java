package com.natesanchez.natesnoteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

  private List<Note> noteList;
  private MainActivity mainAct;

  NoteAdapter(List<Note> nList, MainActivity ma) {
    this.noteList = nList;
    mainAct = ma;
  }

  @NonNull
  @Override
  public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_entry, parent, false);
   // noteView.setOnClickListener(mainAct);
   // noteView.setOnLongClickListener(mainAct);

    return new NoteViewHolder(noteView);
  }

  @Override
  public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

    Note n = noteList.get(position);

    holder.title.setText(n.getTitle());
    holder.text.setText(n.getText());
    holder.noteDate.setText(n.getDate().toString());
  }

  @Override
  public int getItemCount() {
    return noteList.size();
  }
}
