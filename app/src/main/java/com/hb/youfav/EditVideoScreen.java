package com.hb.youfav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import dao.YoutubeVideoDAO;
import model.YoutubeVideo;

public class EditVideoScreen extends AppCompatActivity {

    private Intent intent;
    private YoutubeVideo youtubeVideo = null;
    private YoutubeVideoDAO youtubeVideoDAO;
    private EditText titre;
    private EditText description;
    private EditText url;
    private Spinner categories;
    private Button btnUpdateVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_edit);

        intent = getIntent();
        titre = findViewById(R.id.updateVideoTitle);
        description = findViewById(R.id.updateVideoDescription);
        url = findViewById(R.id.updateVideoUrl);
        categories = findViewById(R.id.updateVideoCategory);
        btnUpdateVideo = (Button) findViewById(R.id.btnUpdateVideo);

        String[] categoriesList = {
                "Music",
                "Video game",
                "Movie",
                "Video clip",
                "Potamochère"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categoriesList);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.categories.setAdapter(adapter);


        // When user select a List-Item.
        this.categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        youtubeVideoDAO = new YoutubeVideoDAO(getApplicationContext());

        Long videoId = intent.getLongExtra("youtubeVideoId", -1L);

        if (videoId != -1L) {
            youtubeVideo = youtubeVideoDAO.findById(videoId);
            titre.setText(youtubeVideo.getTitle());
            description.setText(youtubeVideo.getDescription());
            url.setText(youtubeVideo.getUrl());
            for (int i = 0; i < categoriesList.length; i++) {
                if (categoriesList[i].equals(youtubeVideo.getCategory())) {
                    this.categories.setSelection(i);
                } else {
                    this.categories.setSelection(0);
                }
            }
        }

        btnUpdateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeVideoDAO.update(youtubeVideo);
                finish();
            }

        });

    }

}
