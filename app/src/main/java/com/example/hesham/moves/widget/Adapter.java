package com.example.hesham.moves.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hesham.moves.R;

import java.util.List;

/**
 * Created by root on 2/24/18.
 */

public class Adapter extends ArrayAdapter<ModelNote> {

    public Adapter(@NonNull Context context, int resource, List<ModelNote> list_of_noteModelNotes) {
        super(context, resource, list_of_noteModelNotes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.itemwidget,
                    parent,
                    false);

        }
        TextView Note = (TextView) convertView.findViewById(R.id.NoteTitle);

        ModelNote modelNote = getItem(position);
        Note.setText(modelNote.getnote());

        return convertView;
    }
}
