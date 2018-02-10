package com.example.hesham.moves.widget;

/**
 * Created by root on 2/8/18.
 */

public class NoteModel {

    private String Title;
    private String Note;

    public NoteModel() {
    }

    public NoteModel(String title, String step) {
        this.Title = title;
        this.Note = step;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getStep() {
        return Note;
    }

    public void setStep(String step) {
        this.Note = step;
    }


}


