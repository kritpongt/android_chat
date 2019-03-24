package com.example.androidtabletop.ItemViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidtabletop.Interface.ItemClickListener;
import com.example.androidtabletop.R;

public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView game_name;
    public ImageView game_image;
    private ItemClickListener itemClickListener;
    //public Button btnBookmarks;

    public GameViewHolder(@NonNull View itemView) {
        super(itemView);
        game_name = (TextView)itemView.findViewById(R.id.game_name);
        game_image = (ImageView)itemView.findViewById(R.id.game_image);
        //btnBookmarks = (Button)itemView.findViewById(R.id.btnBookmarks);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
