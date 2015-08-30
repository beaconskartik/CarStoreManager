package com.quikr.quikrcarstore.FetchServices;

import java.io.InputStream;

import com.quikr.quikrcarstore.Utils.CSConstans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;



public class CSDownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap>
{
    ImageView bmImage;

    public CSDownloadImageAsyncTask(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls)
    {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try 
        {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e(CSConstans.CAR_STORE_LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) 
    {
        bmImage.setImageBitmap(result);
    }
}