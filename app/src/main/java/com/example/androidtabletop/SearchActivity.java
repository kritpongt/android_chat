package com.example.androidtabletop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.androidtabletop.Database.SQLite;
import com.example.androidtabletop.Interface.ItemClickListener;
import com.example.androidtabletop.ItemViewHolder.GameViewHolder;
import com.example.androidtabletop.Model.Game;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    //SEARCH DATABASE
    FirebaseRecyclerAdapter<Game, GameViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    //RECYCLEVIEW
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //FIREBASE
    FirebaseDatabase firebaseDatabase;
    DatabaseReference gameList;
    //SEARCH BAR
    MaterialSearchBar materialSearchBar;
    SQLite localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //FIREBASE
        firebaseDatabase = FirebaseDatabase.getInstance();
        gameList = firebaseDatabase.getReference("Game");
        localDB = new SQLite(this);
        //RECYCLEVIEW SEARCH
        recyclerView =(RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //SEARCH BAR
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter...");
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(8);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for(String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    recyclerView.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        loadSuggest();
    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Game, GameViewHolder>(
                Game.class,
                R.layout.game_item,
                GameViewHolder.class,
                gameList.orderByChild("Name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(GameViewHolder viewHolder, Game model, int position) {
                viewHolder.game_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.game_image);
                final Game local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(SearchActivity.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        //Start Game Detail Activity
                        Intent detailIntent = new Intent(SearchActivity.this,GameDetail.class);
                        detailIntent.putExtra("GameID",searchAdapter.getRef(position).getKey());
                        startActivity(detailIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        gameList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Game game = postSnapshot.getValue(Game.class);
                    suggestList.add(game.getName());
                }
                //setSuggest
                materialSearchBar.setLastSuggestions(suggestList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        if (searchAdapter != null) {
        }
        super.onStop();
    }
}
