package com.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class BitmapDataObject implements Serializable {

    public byte[] imageByteArray;
    public int tamanho;

    public void bitMattoArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByteArray = stream.toByteArray();
        tamanho = (imageByteArray.length);
        imageByteArray = imageByteArray;
    }
    
    public Bitmap arraytoBitmap(){
        Bitmap image = BitmapFactory.decodeByteArray(imageByteArray, 0, tamanho);        
        return image;

    }
}
