package dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import model.YoutubeVideo;

public class YoutubeVideoDAO extends DAO{

    public YoutubeVideoDAO(Context context) {
        super(new YoutubeVideoDBHelper(context));
    }

    public YoutubeVideo findById(Long id){

        open();

        YoutubeVideo ytv = null;

        Cursor cursor = this.db.rawQuery(
                "SELECT * FROM " + YoutubeVideoDBHelper.YTVIDEO_TABLE_NAME +
                        " WHERE " + YoutubeVideoDBHelper.YTVIDEO_KEY + " = ? ",
                new String[] {String.valueOf(id)}
        );

        if (cursor != null && cursor.moveToFirst()){
            ytv = new YoutubeVideo(
                    cursor.getLong(YoutubeVideoDBHelper.YTVIDEO_KEY_COLUMN_INDEX),
                    cursor.getString(YoutubeVideoDBHelper.YTVIDEO_TITLE_COLUMN_INDEX),
                    cursor.getString(YoutubeVideoDBHelper.YTVIDEO_DESCRITION_COLUMN_INDEX),
                    cursor.getString(YoutubeVideoDBHelper.YTVIDEO_URL_COLUMN_INDEX),
                    cursor.getString(YoutubeVideoDBHelper.YTVIDEO_CATEGORY_COLUMN_INDEX)
            );
            cursor.close();
        }
        return ytv;
    }


    public List<YoutubeVideo> findAll(){
        List<YoutubeVideo> youtubeVideoList = new ArrayList<>();

        open();

        Cursor cursor = this.db.rawQuery("SELECT * FROM " + YoutubeVideoDBHelper.YTVIDEO_TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                YoutubeVideo youtubeVideo = new YoutubeVideo(
                        cursor.getLong(YoutubeVideoDBHelper.YTVIDEO_KEY_COLUMN_INDEX),
                        cursor.getString(YoutubeVideoDBHelper.YTVIDEO_TITLE_COLUMN_INDEX),
                        cursor.getString(YoutubeVideoDBHelper.YTVIDEO_DESCRITION_COLUMN_INDEX),
                        cursor.getString(YoutubeVideoDBHelper.YTVIDEO_URL_COLUMN_INDEX),
                        cursor.getString(YoutubeVideoDBHelper.YTVIDEO_CATEGORY_COLUMN_INDEX)
                );
                youtubeVideoList.add(youtubeVideo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return youtubeVideoList;
    }

    public YoutubeVideo save(YoutubeVideo youtubeVideo){

        open();

        ContentValues values = new ContentValues();

        values.put(YoutubeVideoDBHelper.YTVIDEO_TITLE, youtubeVideo.getTitle());
        values.put(YoutubeVideoDBHelper.YTVIDEO_DESCRIPTION, youtubeVideo.getDescription());
        values.put(YoutubeVideoDBHelper.YTVIDEO_URL, youtubeVideo.getUrl());
        values.put(YoutubeVideoDBHelper.YTVIDEO_CATEGORY, youtubeVideo.getCategory());


        long id = this.db.insert(YoutubeVideoDBHelper.YTVIDEO_TABLE_NAME, null, values);

        youtubeVideo.setId(id);;

        close();

        return youtubeVideo;
    }

    public void update(YoutubeVideo youtubeVideo){
        open();

        ContentValues values = new ContentValues();

        values.put(YoutubeVideoDBHelper.YTVIDEO_TITLE, youtubeVideo.getTitle());
        values.put(YoutubeVideoDBHelper.YTVIDEO_DESCRIPTION, youtubeVideo.getDescription());
        values.put(YoutubeVideoDBHelper.YTVIDEO_URL, youtubeVideo.getUrl());
        values.put(YoutubeVideoDBHelper.YTVIDEO_CATEGORY, youtubeVideo.getCategory());


        String whereClause = YoutubeVideoDBHelper.YTVIDEO_KEY + " = ? ";

        this.db.update(YoutubeVideoDBHelper.YTVIDEO_TABLE_NAME, values, whereClause,new String[] {String.valueOf(youtubeVideo.getId())});

        close();
    }

    public void delete(YoutubeVideo youtubeVideo){
        open();

        this.db.delete(YoutubeVideoDBHelper.YTVIDEO_TABLE_NAME, YoutubeVideoDBHelper.YTVIDEO_KEY + " = ?", new String[] {String.valueOf(youtubeVideo.getId())});

    }
}
