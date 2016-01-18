package com.tlab.wish.utils;

import android.os.Environment;
import android.util.Log;

import com.tlab.wish.App;
import com.tlab.wish.BuildConfig;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by andranik on 1/18/16.
 */
public class Serialiser<T extends Serializable> {

    private static final String TAG = "Serialiser";

    private String CLASS_NAME = "";
    private String mFileName;

    public Serialiser(String className, String fileName){
        CLASS_NAME = className;
        mFileName = fileName;
    }

    public void serialize(T obj){
        if(BuildConfig.DEBUG){Log.d(TAG, CLASS_NAME + " Saving to file");}
        try{
            FileOutputStream fileOut = new FileOutputStream(getFilePathe());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
            if(BuildConfig.DEBUG){ Log.d(TAG, CLASS_NAME + " saved into file successfully"); }
        } catch(IOException e){
            if(BuildConfig.DEBUG){Log.d(TAG, CLASS_NAME + " EXCEPTION when saving to file");}
            ExceptionTracker.trackException(e);
        }
    }

    public T deserialize() throws Exception{
        if(BuildConfig.DEBUG){Log.d(TAG, CLASS_NAME + " Getting from file");}

        T obj = null;

        FileInputStream fileIn = new FileInputStream(getFilePathe());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        obj = (T) in.readObject();
        in.close();
        fileIn.close();
        if(BuildConfig.DEBUG){ Log.d(TAG, CLASS_NAME + " got from file successfully"); }

        return obj;
    }

    private String getFilePathe(){
        StringBuilder builder =  new StringBuilder();

        builder .append(Environment.getDataDirectory())
                .append("/data/")
                .append(App.getInstance().getPackageName())
                .append("/")
                .append(mFileName);


        return builder.toString();
    }
}
