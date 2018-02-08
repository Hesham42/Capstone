//package com.example.hesham.moves.adapter.AdapterOfTriall;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.hesham.moves.R;
//import com.example.hesham.moves.Utilities.Config;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubeStandalonePlayer;
//import com.google.android.youtube.player.YouTubeThumbnailLoader;
//import com.google.android.youtube.player.YouTubeThumbnailView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Hesham on 9/25/2017.
// */
//
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder> {
//
//    //these ids are the unique id for each video
//    List<String> VideoID =new ArrayList<>();
//    List<String> Ttitles = new ArrayList<>();
//    Context ctx;
//
//    public RecyclerAdapter(Context context,List<String> VideoID,List<String> Ttitles) {
//        this.ctx = context;
//        this.VideoID=VideoID;
//        this.Ttitles=Ttitles;
//    }
//
//    @Override
//    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtrailer, parent, false);
//        return new VideoInfoHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
//
//
//        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//            @Override
//            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//            }
//
//            @Override
//            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//                youTubeThumbnailView.setVisibility(View.VISIBLE);
//                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
//            }
//        };
//
//        holder.youTubeThumbnailView.initialize(Config.API_KEY_YOUTUBE, new YouTubeThumbnailView.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//
//                youTubeThumbnailLoader.setVideo(VideoID.get(position));
//
//                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
//                holder.videosTitleTextView.setText(Ttitles.get(position));
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//                //write something for failure
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return VideoID.size();
//    }
//
//    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
//        YouTubeThumbnailView youTubeThumbnailView;
//        protected ImageView playButton;
//        protected TextView videosTitleTextView;
//
//        public VideoInfoHolder(View itemView) {
//            super(itemView);
//            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
//            videosTitleTextView = (TextView) itemView.findViewById(R.id.videosTitle_tv);
//            playButton.setOnClickListener(this);
//            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
//            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
//        }
//
//        @Override
//        public void onClick(View v) {
//
//            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, Config.API_KEY_YOUTUBE, VideoID.get(getLayoutPosition()));
//            ctx.startActivity(intent);
//        }
//    }
//}
