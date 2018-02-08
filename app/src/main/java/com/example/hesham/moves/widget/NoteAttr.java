package com.example.hesham.moves.widget;

/**
 * Created by root on 2/8/18.
 */

public class NoteAttr {

    private String tit;
    private String steps;

    public NoteAttr() {
    }

    public NoteAttr(String title, String step) {
        this.tit = title;
        this.steps = step;

    }

    public String getTitle() {
        return tit;
    }

    public void setTitle(String title) {
        this.tit = title;
    }

    public String getStep() {
        return steps;
    }

    public void setStep(String step) {
        this.steps = step;
    }


}


