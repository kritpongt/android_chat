package com.example.androidtabletop.ItemViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtabletop.GameDetail;
import com.example.androidtabletop.GameList;
import com.example.androidtabletop.Interface.ItemClickListener;
import com.example.androidtabletop.Model.Bookmarks;
import com.example.androidtabletop.Model.Game;
import com.example.androidtabletop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class BookmarksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView bookmarks_name;
    public ImageView bookmarks_image;
    private ItemClickListener itemClickListener;

    public BookmarksViewHolder(@NonNull View itemView) {
        super(itemView);
        bookmarks_name = (TextView)itemView.findViewById(R.id.bookmarks_name);
        bookmarks_image = (ImageView)itemView.findViewById(R.id.bookmarks_image);

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

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksViewHolder>{
    private List<Bookmarks> bookmarksList = new ArrayList<>();
    private Context context;

    public BookmarksAdapter(List<Bookmarks> bookmarksList, Context context) {
        this.bookmarksList = bookmarksList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bookmarks_item,parent,false);
        return new BookmarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksViewHolder viewHolder, int position) {
        viewHolder.bookmarks_name.setText(bookmarksList.get(position).getGameName());
        Picasso.with(context).load(bookmarksList.get(position).getGameImage()).into(viewHolder.bookmarks_image);
        final Bookmarks local = bookmarksList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, ""+local.getGameName(), Toast.LENGTH_SHORT).show();
                //Start Game Detail Activity
                Intent detailIntent = new Intent(context, GameDetail.class);
                detailIntent.putExtra("GameID",bookmarksList.get(position).getGameId());
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarksList.size();
    }
}