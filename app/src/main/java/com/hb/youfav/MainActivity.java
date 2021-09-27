package com.hb.youfav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import dao.YoutubeVideoDAO;
import model.YoutubeVideo;

public class MainActivity extends AppCompatActivity {

    private YoutubeVideoDAO youtubeVideoDAO;
    private List<YoutubeVideo> youtubeVideoList;
    private Context context;
    private RecyclerView rvYoutubeVideos;
    private YoutubeVideoAdapter youtubeVideoAdapter;
    private LinearLayout listItem;

    public class TodoAsyncTasks extends AsyncTask<String, String, List<YoutubeVideo>> {

        @Override
        protected List<YoutubeVideo> doInBackground(String... strings) {

            List<YoutubeVideo> responseYoutubeVideo = new ArrayList<>();

            try {
                responseYoutubeVideo = youtubeVideoDAO.findAll();
            }catch (Exception e){
                e.printStackTrace();
            }

            return responseYoutubeVideo;
        }

        @Override
        protected void onPostExecute(List<YoutubeVideo> youtubeVideoList) {
            youtubeVideoAdapter = new YoutubeVideoAdapter(youtubeVideoList);
            rvYoutubeVideos.setAdapter(youtubeVideoAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_video_add:
                Intent intent = new Intent(getApplicationContext(),NewVideoAddScreen.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_videos_list);

        rvYoutubeVideos = findViewById(R.id.rvVideoList);
        context = getApplicationContext();
        youtubeVideoDAO = new YoutubeVideoDAO(getApplicationContext());

        //If it's the first time we launch the app there is no data so we need to create an example of a saved video
        youtubeVideoList = youtubeVideoDAO.findAll();
        if (youtubeVideoList.size() == 0){
            YoutubeVideo youtubeVideo = new YoutubeVideo("As I Lay Dying [2007] An Ocean Between Us", "Full Album", "https://www.youtube.com/watch?v=T9TtmYCPCLU","Music");
            youtubeVideoDAO.save(youtubeVideo);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        rvYoutubeVideos.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TodoAsyncTasks todoAsyncTasks = new TodoAsyncTasks();
        todoAsyncTasks.execute();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Upload");
        menu.add(0, v.getId(), 0, "Search");
    }

}