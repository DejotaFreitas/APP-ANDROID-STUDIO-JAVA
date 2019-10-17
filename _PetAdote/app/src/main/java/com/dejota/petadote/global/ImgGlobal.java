package com.dejota.petadote.global;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

public class ImgGlobal {

    private static Map<Integer, Bitmap> imgs = new HashMap<>();

    public static boolean exist(Integer key){
        if (imgs.containsKey(key)){
            return true;
        }
        return false;
    }

    public static void add(Integer key, Bitmap bmp){
        imgs.put(key, bmp);
    }

    public static Bitmap get(Integer key){
        return imgs.get(key);
    }

}
