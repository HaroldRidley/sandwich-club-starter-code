package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownTv;
    private TextView mAlsoKnownLabelTv;
    private TextView mOriginTv;
    private TextView mOriginLabelTv;
    private TextView mDescriptionTv;
    private TextView mIngredientTv;
    private ImageView mSandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSandwichIv = findViewById(R.id.image_iv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mAlsoKnownLabelTv = findViewById(R.id.alsoKnownAs_label);
        mOriginTv = findViewById(R.id.origin_tv);
        mOriginLabelTv = findViewById(R.id.placeOfOrigin_label);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientTv = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mSandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        loadImage(mSandwichIv, sandwich.getImage());
        loadPlaceOfOrigin(sandwich);
        loadAlsoKnownAs(sandwich);
        loadDescription(sandwich);
        loadIngredients(sandwich);

    }

    private void loadIngredients(Sandwich sandwich) {
        if (sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            setText(mIngredientTv, stringBuilder.toString());
        }
    }

    private void loadDescription(Sandwich sandwich) {
        setText(mDescriptionTv, sandwich.getDescription());
    }

    private void loadPlaceOfOrigin(Sandwich sandwich) {
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            setText(mOriginTv, sandwich.getPlaceOfOrigin());
        } else {
            setVisibilityNone(mOriginTv);
            setVisibilityNone(mOriginLabelTv);
        }
    }

    private void loadAlsoKnownAs(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            setText(mAlsoKnownTv, stringBuilder.toString());
        } else {
            setVisibilityNone(mAlsoKnownTv);
            setVisibilityNone(mAlsoKnownLabelTv);
        }
    }

    private void loadImage(ImageView imageView, String image) {
        Picasso.with(this).load(image).into(imageView);
    }

    private void setText(TextView textView, String text) {
        textView.setText(text);
    }

    private void setVisibilityNone(TextView textView) {
        textView.setVisibility(View.GONE);
    }
}
