package com.natesanchez.natesnoteapp;

import android.view.View;
import android.widget.TextView;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

  TextView title;
  TextView text;
  TextView noteDate;

  public NoteViewHolder(@NonNull View itemView) {
    super(itemView);

    title = itemView.findViewById(R.id.noteTitle);
    text = itemView.findViewById(R.id.noteText);
    noteDate = itemView.findViewById(R.id.noteDate);
  }
}
