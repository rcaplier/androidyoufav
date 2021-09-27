package com.hb.youfav;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dao.YoutubeVideoDAO;
import model.YoutubeVideo;

public class NewVideoAddScreen extends AppCompatActivity {

    private Spinner categorySpinner;
    private Button addButton;
    private YoutubeVideoDAO youtubeVideoDAO;
    private EditText titleInput;
    private EditText descriptionInput;
    private EditText urlImput;
    private String videoTitle = "";
    private String videoDescription ="";
    private String videoUrl ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_video_form);

        categorySpinner = (Spinner) findViewById(R.id.newVideoAddCategory);
        addButton = (Button) findViewById(R.id.btnAddNewVideo);
        titleInput = findViewById(R.id.newVideoAddTitle);
        descriptionInput = findViewById(R.id.newVideoAddDescription);
        urlImput = findViewById(R.id.newVideoAddUrl);

        String[] categories = {
                "Music",
                "Video game",
                "Movie",
                "Video clip",
                "Potamochère"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categories);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.categorySpinner.setAdapter(adapter);

        // When user select a List-Item.
        this.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                videoTitle = editable.toString();
            }
        });

        this.descriptionInput.addTextChangedListener(new TextWatcher() {
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

        this.urlImput.addTextChangedListener(new TextWatcher() {
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

        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (videoTitle.length() == 0 || videoDescription.length() == 0 || videoUrl.length() == 0){
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                }else{
                    youtubeVideoDAO = new YoutubeVideoDAO(getApplicationContext());
                    YoutubeVideo youtubeVideo = new YoutubeVideo(
                            videoTitle,
                            videoDescription,
                            videoUrl,
                            categorySpinner.getSelectedItem().toString()
                    );
                    youtubeVideoDAO.save(youtubeVideo);
                    finish();
                }


            }
        });

    }
}