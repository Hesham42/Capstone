package com.example.hesham.moves.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hesham.moves.R;

import java.util.List;

/**
 * Created by root on 2/8/18.
 */

public class NotesAdapter extends ArrayAdapter<NoteModel> {
    public NotesAdapter(Context context, int resource, List<NoteModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }


        TextView Title = (TextView) convertView.findViewById(R.id.Tit);
        TextView Step = (TextView) convertView.findViewById(R.id.Steps);

        NoteModel noteModel = getItem(position);
        Title.setText(noteModel.getTitle());
        Step.setText(noteModel.getStep());

        return convertView;
    }
}
