package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView also_known_as_tv,ingredients_as_tv,origin_as_tv,description_as_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        also_known_as_tv = findViewById(R.id.also_known_tv);
        ingredients_as_tv = findViewById(R.id.ingredients_tv);
        origin_as_tv = findViewById(R.id.origin_tv);
        description_as_tv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich)
    {
        String s = " ";
        for (int i=0;i<sandwich.getAlsoKnownAs().size();i++)
            s = s + sandwich.getAlsoKnownAs().get(i) + "\n";
        also_known_as_tv.setText(s);
        s =" ";
        for (int i=0;i<sandwich.getIngredients().size();i++)
            s = s + sandwich.getIngredients().get(i)+ "\n";
        ingredients_as_tv.setText(s);
        origin_as_tv.setText(sandwich.getPlaceOfOrigin());
        description_as_tv.setText(sandwich.getDescription());

    }
}
