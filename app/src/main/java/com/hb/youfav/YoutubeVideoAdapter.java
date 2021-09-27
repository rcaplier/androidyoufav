package com.hb.youfav;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dao.YoutubeVideoDAO;
import model.YoutubeVideo;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoViewHolder> {

    private List<YoutubeVideo> youtubeVideoList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private LinearLayout itemLayout;


        public YoutubeVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View

            tvTitle = (TextView) itemView.findViewById(R.id.rvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.rvDescription);

            //On récupère chaque item du recycler view afin de pouvoir gérer le onClick
            itemLayout = (LinearLayout) itemView.findViewById(R.id.rvItem);
            //itemView.getParent().registerForContextMenu(itemLayout);

        }
    }

    public YoutubeVideoAdapter(List<YoutubeVideo> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_view_video_element, viewGroup, false);

        return new YoutubeVideoViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(YoutubeVideoViewHolder holder, final int position) {

        YoutubeVideo youtubeVideo = youtubeVideoList.get(position);

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.tvTitle.setText(youtubeVideo.getTitle());
        holder.tvDescription.setText(youtubeVideo.getDescription());

        //Gestion des cliques sur chaque items de la liste pour aller vers la page de détails
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, VideoDetailsScreen.class);
                intent.putExtra("youtubeVideoId", youtubeVideo.getId());
                context.startActivity(intent);
            }
        });

        holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence[] items = {"Delete", "Edit"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                YoutubeVideoDAO youtubeVideoDAO = new YoutubeVideoDAO(view.getContext());

                builder.setTitle("Select The Action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0:
                                //delete case
                                youtubeVideoDAO.delete(youtubeVideo);
                                //TODO refresh window
                                break;
                            case 1:
                                //edit case
                                Intent intent = new Intent(view.getContext(),EditVideoScreen.class);
                                intent.putExtra("youtubeVideoId", youtubeVideo.getId());
                                view.getContext().startActivity(intent);
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }
}
