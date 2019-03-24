package com.example.androidtabletop.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.androidtabletop.Model.Bookmarks;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteAssetHelper {
    private  static final String DB_NAME="Database.db";
    private  static final int DB_VER=1;
    public SQLite(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    //Bookmarks
    public List<Bookmarks> getBookmarks(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"GameID","GameName","GameDescription","GameImage","GameDate"};
        String sqlTable = "Bookmarks";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Bookmarks> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do{
                result.add(new Bookmarks(
                        cursor.getString(cursor.getColumnIndex("GameID")),
                        cursor.getString(cursor.getColumnIndex("GameName")),
                        cursor.getString(cursor.getColumnIndex("GameDescription")),
                        cursor.getString(cursor.getColumnIndex("GameImage")),
                        cursor.getString(cursor.getColumnIndex("GameDate"))));
            }while (cursor.moveToNext());
        }
        return result;
    }
    //Add to Bookmarks
    public  void addToBookmarks(Bookmarks game){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO Bookmarks" +
                "(GameID, GameName, GameDescription, GameImage, GameDate) VALUES" +
                "('%s', '%s', '%s', '%s', '%s');",
                game.getGameId(),
                game.getGameName(),
                game.getGameDescription(),
                game.getGameImage(),
                game.getGameDate());
        sqLiteDatabase.execSQL(query);
    }
    public void removeFromBookmarks(String gameId){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM Bookmarks WHERE GameID='%s';",gameId);
        sqLiteDatabase.execSQL(query);
    }
    public  boolean isBookmarks(String gameId){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("SELECT * FROM Bookmarks WHERE GameID='%s';",gameId);
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return  true;
    }
}
