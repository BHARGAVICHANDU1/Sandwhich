package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject name = object.getJSONObject("name");
        String main_name = name.getString("mainName");
        JSONArray also_known = name.getJSONArray("alsoKnownAs");
        ArrayList<String> known_as = new ArrayList<String>();
        for(int i=0;i<also_known.length();i++)
            known_as.add(also_known.getString(i));
        String place_of_origin = object.getString("placeOfOrigin");
        String description = object.getString("description");
        String image = object.getString("image");
        JSONArray ingredients = object.getJSONArray("ingredients");
        ArrayList<String> in = new ArrayList<String>();
        for (int i=0;i<ingredients.length();i++)
            in.add(ingredients.getString(i));
        Sandwich s = new Sandwich(main_name,known_as,place_of_origin,description,image,in);
        return s ;
    }
}
