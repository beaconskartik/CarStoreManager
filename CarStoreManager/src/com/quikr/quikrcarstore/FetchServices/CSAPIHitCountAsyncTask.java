package com.quikr.quikrcarstore.FetchServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.quikr.quikrcarstore.FetchServices.CSCarEntry.Demographic;
import com.quikr.quikrcarstore.Utils.CSConstans;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.TextView;


public class CSAPIHitCountAsyncTask extends AsyncTask<String, Void, Void>
{

	public CSAPIHitCountAsyncTask(TextView apihit) 
	{
		// TODO Auto-generated constructor stub
		mAPIHit = apihit;
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... url) 
	{
		
		try
		{
			String readJSON = getJSON(url[0]);
			Log.d(CSConstans.CAR_STORE_LOG_TAG, "kartik + -->" + readJSON);
			JSONObject jsonObj = new JSONObject(readJSON);
			 getCarListContentFromJsonObject(jsonObj);
			
		} 
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		if (sHits != null)
			mAPIHit.setText("No. of API hits : " +sHits);
		super.onPostExecute(result);
	}

	private void getCarListContentFromJsonObject(JSONObject jsObject) throws MalformedURLException, JSONException
	{
		sHits = jsObject.has(API_HITS) ? jsObject.getString(API_HITS) : null;	
			
	}

	private String getJSON(String address) throws ClientProtocolException, IOException
	{
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(address);


		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200)
		{
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
		} 
		else 
		{
			Log.e(CSConstans.CAR_STORE_LOG_TAG,"Failed to get JSON object");
		}

		return builder.toString();
	}

	//member variable
	private TextView mAPIHit;
	private String sHits;
	public static final String API_HITS = "api_hits";

}
