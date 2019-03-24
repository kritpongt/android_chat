package com.example.androidtabletop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtabletop.Database.SQLite;
import com.example.androidtabletop.Model.Bookmarks;
import com.example.androidtabletop.Model.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GameDetail extends AppCompatActivity {
    TextView game_name;
    TextView game_description;
    TextView game_date;
    ImageView game_image;
    String gameId="";
    FloatingActionButton btnBookmarks;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference gameDetail;
    //Model>Game
    Game game;
    //SQLite Bookmarks Database
    SQLite localDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        //INIT FIREBASE
        firebaseDatabase= FirebaseDatabase.getInstance();
        gameDetail = firebaseDatabase.getReference("Game");
        //Init View
        btnBookmarks = (FloatingActionButton)findViewById(R.id.btnBookmarks);
        game_name = (TextView)findViewById(R.id.detail_name);
        game_description = (TextView)findViewById(R.id.detail_description);
        game_date = (TextView)findViewById(R.id.detail_date);
        game_image = (ImageView)findViewById(R.id.detail_image);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingtoolbar);
        //Local DB
        localDB = new SQLite(this);
        //Add to Bookmarks
        if (localDB.isBookmarks(gameId)){
            btnBookmarks.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }
        btnBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!localDB.isBookmarks(gameId)) {
                    new SQLite(getBaseContext()).addToBookmarks(new Bookmarks(
                            gameId,
                            game.getName(),
                            game.getDescription(),
                            game.getImage(),
                            game.getDate()));
                    btnBookmarks.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    Toast.makeText(GameDetail.this, "Added to Bookmarks", Toast.LENGTH_SHORT).show();
                }else{
                    localDB.removeFromBookmarks(gameId);
                    btnBookmarks.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(GameDetail.this, "Removed from Bookmarks", Toast.LENGTH_SHORT).show();
                    Intent bookmarksIntent = new Intent(GameDetail.this,BookmarksActivity.class);
                }
            }
        });
        //Get Intent Game ID from GameList.class
        if(getIntent() != null){
            gameId = getIntent().getStringExtra("GameID");
        }
        if(!gameId.isEmpty()){
            loadGameDetail(gameId);
            if (localDB.isBookmarks(gameId)){
                btnBookmarks.setImageResource(R.drawable.ic_bookmark_black_24dp);
            }
        }
    }
    //LOAD GAME DETAIL
    private void loadGameDetail(String gameId) {
        gameDetail.child(gameId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                game = dataSnapshot.getValue(Game.class);

                Picasso.with(getBaseContext()).load(game.getImage()).into(game_image);
                //collapsingToolbarLayout.setTitle(game.getName());
                game_name.setText(game.getName());
                game_description.setText(game.getDescription());
                game_date.setText(game.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
