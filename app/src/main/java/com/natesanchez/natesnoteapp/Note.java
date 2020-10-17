package com.natesanchez.natesnoteapp;

import java.util.Date;

public class Note implements Comparable<Note> {

  private String title;
  private String text;
  private Date lastUpdated;

  public Note(String title, String text) {
    this.title = title;
    this.text = text;
    lastUpdated = new Date();
  }

  public String getTitle() { return title; }

  public String getText() { return text; }

  public Date getDate() { return lastUpdated; }

  public void updateTitle(String newTitle) { title = newTitle; }

  public void updateText(String newText) { text = newText; }

  @Override
  public int compareTo(Note otherNote) {
    if (lastUpdated.before(otherNote.getDate())) {
      return -1;
    } else if (lastUpdated.after(otherNote.getDate())) {
      return 1;
    } else {
      return 0;
    }
  }
}
