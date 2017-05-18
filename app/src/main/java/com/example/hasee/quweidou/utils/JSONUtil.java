package com.example.hasee.quweidou.utils;

import com.example.hasee.quweidou.entity.SectionEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ken on 2017/1/7.17:09
 */

public class JSONUtil {

    /**
     * 解析json
     * @param json
     * @return
     */
    public static List<SectionEntity> getListByJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("sectionList");

            TypeToken<List<SectionEntity>> tt = new TypeToken<List<SectionEntity>>(){};
            return new Gson().fromJson(jsonArray.toString(), tt.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
