package com.example.hesham.moves.Utilities;

/**
 * Created by root on 2/17/18.
 */

public class CommentUpdateModel {

    private static CommentUpdateModel mInstance;
    OnCommentAddedListener onCommentAddedListener;

    private int position;

    public interface OnCommentAddedListener {
        void commentDelete();
    }

    public CommentUpdateModel() {
    }

    public static CommentUpdateModel getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new CommentUpdateModel();
        }
        return mInstance;
    }

    public void setListener(OnCommentAddedListener listener) {
        onCommentAddedListener = listener;
    }


    public void DeleteComment() {
        if (onCommentAddedListener != null) {
            notifyCommentDelete();
        }
    }

    private void notifyCommentDelete() {
        if (onCommentAddedListener != null) {
            onCommentAddedListener.commentDelete();
        }
    }



    public int getPosition() {
        return position;
    }
}