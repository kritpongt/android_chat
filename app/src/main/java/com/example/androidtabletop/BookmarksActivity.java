package com.example.androidtabletop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.androidtabletop.Database.SQLite;
import com.example.androidtabletop.ItemViewHolder.BookmarksAdapter;
import com.example.androidtabletop.Model.Bookmarks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {
    RecyclerView recyclerView_bookmarks;
    RecyclerView.LayoutManager layoutManager;

    List<Bookmarks> bookmarks = new ArrayList<>();
    BookmarksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        //Recycler View
        recyclerView_bookmarks = (RecyclerView)findViewById(R.id.recycler_bookmarks);
        recyclerView_bookmarks.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_bookmarks.setLayoutManager(layoutManager);
        recyclerView_bookmarks.refreshDrawableState();
        loadBookmarks();
    }
    private void loadBookmarks() {
        bookmarks = new SQLite(this).getBookmarks();
        adapter = new BookmarksAdapter(bookmarks,this);
        recyclerView_bookmarks.setAdapter(adapter);
    }
}
