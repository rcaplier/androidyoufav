package com.hb.youfav;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EditText editTextTitle;
    private String videoTitle = "";
    private EditText editTextDescription;
    private String videoDescription = "";
    private EditText editTextUrl;
    private String videoUrl = "";
    private Spinner spinnerCategories;
    private Button btnUpdateVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_edit);

        intent = getIntent();
        editTextTitle = findViewById(R.id.updateVideoTitle);
        editTextDescription = findViewById(R.id.updateVideoDescription);
        editTextUrl = findViewById(R.id.updateVideoUrl);
        spinnerCategories = findViewById(R.id.updateVideoCategory);
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

        this.spinnerCategories.setAdapter(adapter);


        // When user select a List-Item.
        this.spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
            editTextTitle.setText(youtubeVideo.getTitle());
            videoTitle = youtubeVideo.getTitle();
            editTextDescription.setText(youtubeVideo.getDescription());
            videoDescription = youtubeVideo.getDescription();
            editTextUrl.setText(youtubeVideo.getUrl());
            videoUrl = youtubeVideo.getUrl();
            for (int i = 0; i < categoriesList.length; i++) {
                if (categoriesList[i].equals(youtubeVideo.getCategory())) {
                    System.out.println("trouvé");
                    this.spinnerCategories.setSelection(i);
                    System.out.println(spinnerCategories.getSelectedItem().toString());
                } else {
                    this.spinnerCategories.setSelection(1);
                }
            }
        }

        btnUpdateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeVideo.setTitle(videoTitle);
                youtubeVideo.setDescription(videoDescription);
                youtubeVideo.setUrl(videoUrl);
                youtubeVideo.setCategory(spinnerCategories.getSelectedItem().toString());
                youtubeVideoDAO.update(youtubeVideo);
                finish();
            }
        });

        this.editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { videoTitle = editable.toString();
            }
        });

        this.editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                videoDescription = editable.toString();
            }
        });

        this.editTextUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                videoUrl = editable.toString();
            }
        });

    }

}
