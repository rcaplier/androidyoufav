package com.hb.youfav;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dao.YoutubeVideoDAO;
import model.YoutubeVideo;

public class VideoDetailsScreen extends AppCompatActivity {

    private Intent intent;
    private YoutubeVideo youtubeVideo = null;
    private YoutubeVideoDAO youtubeVideoDAO;
    private TextView tvTitre;
    private TextView tvDescription;
    private TextView tvUrl;
    private TextView tvCategory;
    private Button btnSeeVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_details);

        intent = getIntent();
        tvTitre = findViewById(R.id.tvVideoTitle);
        tvDescription = findViewById(R.id.tvVideoDescription);
        tvUrl = findViewById(R.id.tvVideoUrl);
        tvCategory = findViewById(R.id.tvVideoCategory);
        btnSeeVideo = (Button) findViewById(R.id.btnSeeVideo);

        youtubeVideoDAO = new YoutubeVideoDAO(getApplicationContext());

        Long videoId = intent.getLongExtra("youtubeVideoId", -1L);

        if (videoId != -1L) {
            youtubeVideo = youtubeVideoDAO.findById(videoId);
            tvTitre.setText(youtubeVideo.getTitle());
            tvDescription.setText(youtubeVideo.getDescription());
            tvUrl.setText(youtubeVideo.getUrl());
            tvCategory.setText(youtubeVideo.getCategory());
        }

        btnSeeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(youtubeVideo.getUrl()));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }

        });
    }
}
