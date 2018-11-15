package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject object = new JSONObject(json);

            JSONObject name = object.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            String description = object.getString(DESCRIPTION);
            String image = object.getString(IMAGE);
            String placeOfOrigin = object.optString(PLACE_OF_ORIGIN);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = JsonArrayToList(JSONArrayAlsoKnownAs);

            JSONArray JSONArrayIngredients = object.getJSONArray(INGREDIENTS);
            List<String> ingredients = JsonArrayToList(JSONArrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> JsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}