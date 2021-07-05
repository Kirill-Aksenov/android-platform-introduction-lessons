package ru.geekbrains.social_network.data;

import java.util.Random;

import ru.geekbrains.social_network.R;

public class PictureIndexConverter {

    private static Random rnd = new Random();
    private static Object syncObj = new Object();

    private static int[] picIndex = {R.drawable.nature1,
            R.drawable.nature1,
            R.drawable.nature1,
            R.drawable.nature1,
            R.drawable.nature1,
            R.drawable.nature1,
            R.drawable.nature1,
    };

    public static int randomPictureIndex(){
        synchronized (syncObj){
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture){
        for(int i = 0; i < picIndex.length; i++){
            if (picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }
}
