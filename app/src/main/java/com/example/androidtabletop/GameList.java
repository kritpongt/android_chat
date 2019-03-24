package com.example.androidtabletop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.androidtabletop.Interface.ItemClickListener;
import com.example.androidtabletop.Model.Game;
import com.example.androidtabletop.ItemViewHolder.GameViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class GameList extends AppCompatActivity {
    RecyclerView recyclerView_game;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference gameList;

    String categoryId="";
    FirebaseRecyclerAdapter<Game, GameViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        //INIT FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        gameList = firebaseDatabase.getReference("Game");
        //RecyclerView Game
        recyclerView_game = (RecyclerView) findViewById(R.id.recycler_game);
        recyclerView_game.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_game.setLayoutManager(layoutManager);
        //Get Intent
        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryID");
        }
        if(!categoryId.isEmpty() && categoryId != null){
            loadListGame(categoryId);
        }
    }
    //LOAD GAME LIST
    private void loadListGame(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Game, GameViewHolder>(Game.class, R.layout.game_item, GameViewHolder.class,
                gameList.orderByChild("CategoryID").equalTo(categoryId)) { //SELECT * FROM Game WHERE CategoryID=
            @Override
            protected void populateViewHolder(final GameViewHolder viewHolder, Game model, final int position) {
                viewHolder.game_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.game_image);
                final Game local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(GameList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        //Start Game Detail Activity
                        Intent detailIntent = new Intent(GameList.this,GameDetail.class);
                        detailIntent.putExtra("GameID",adapter.getRef(position).getKey());
                        startActivity(detailIntent);
                    }
                });
            }
        };
        recyclerView_game.setAdapter(adapter);
    }
}
