package com.example.yaowu.steamhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PriceActivity extends AppCompatActivity {

    private EditText searchText;
    private ImageView search_game_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        searchText = (EditText) findViewById(R.id.search_games);
        search_game_icon = (ImageView) findViewById(R.id.search_game_icon);

        searchText.setHint("Please enter game name(don't include any symbol)");
        search_game_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(PriceActivity.this, ProfileWebView.class);
                search_intent.putExtra("search game", "https://steamdb.info/search/?a=app&q=" + searchContent(searchText.getText().toString()));
                startActivity(search_intent);
            }
        });
    }

    private String searchContent(String text){
        StringBuilder sb = new StringBuilder();
        for(char c: text.toCharArray()){
            if(c == ' ') sb.append('+');
            else sb.append(c);
        }
        return sb.toString();
    }
}
