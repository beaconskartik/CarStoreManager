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


public class CSLoadCarListAsynTask extends AsyncTask<String, Void, ArrayList<CSCarEntry>>
{
	public CSLoadCarListAsynTask(CSCarFetchServiceInterface handler)
	{
		mCarFetchInterface = handler;
	}

	@Override
	protected void onPreExecute() 
	{
		mCarFetchInterface.onLoadCarListStarted();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<CSCarEntry> doInBackground(String... url) 
	{
		ArrayList<CSCarEntry> carList = null;
		try
		{
			String readJSON = getJSON(url[0]);
			Log.d(CSConstans.CAR_STORE_LOG_TAG, "kartik + -->" + readJSON);

			JSONArray jsonArray = new JSONArray(readJSON);
			carList = getCarListContentFromJsonObject(jsonArray);
			mIsSuccess = true;
		} 
		catch(Exception e)
		{
			mIsSuccess = false;
			e.printStackTrace();
		}
		return carList;
	}

	@Override
	protected void onPostExecute(ArrayList<CSCarEntry> result)
	{
		if (mIsSuccess)
		{
			mCarFetchInterface.onLoadCarListSuccess(result, mNumberOfcars);
		}
		else
		{
			mCarFetchInterface.onLoadCarListFailed();
		}
		super.onPostExecute(result);
	}

	private ArrayList<CSCarEntry> getCarListContentFromJsonObject(JSONArray jsObject) throws MalformedURLException, JSONException
	{
		ArrayList<CSCarEntry> carList = new ArrayList<CSCarEntry>();

		// and iterate through the array
		int listSize = jsObject.length();
		mNumberOfcars = listSize;
		for (int i = 0; i < listSize; ++i)
		{
			// get individual file entry in JSON 
			JSONObject car = jsObject.getJSONObject(i);
			String name = car.has(NAME) ? car.getString(NAME) : null;
			String image = car.has(IMAGE)? car.getString(IMAGE) : null;
			String price = car.has(PRICE) ? car.getString(PRICE) : null;		
			String brand = car.has(BRAND) ? car.getString(BRAND) : null;
			String type = car.has(TYPE) ? car.getString(TYPE) : null;
			String color = car.has(COLOR) ? car.getString(COLOR) : null;
			String engine_cc = car.has(ENGINE_CC) ? car.getString(ENGINE_CC) : null;
			String mileage = car.has(MILEAGE) ? car.getString(MILEAGE) : null;
			String abs = car.has(ABS_EXIT) ? car.getString(ABS_EXIT) : null;
			boolean abs_exist = abs.equalsIgnoreCase("YES") ? true : false;
			double rating = car.has(RATING) ? car.getDouble(RATING) : 0.0;
			String link = car.has(LINK) ? car.getString(LINK): null;
			String desc = car.has(DESCRIPTION) ? car.getString(DESCRIPTION) : null;
			JSONArray demographic = car.has(DEMOGRAPHIC) ? car.getJSONArray(DEMOGRAPHIC) : null;

			ArrayList<Demographic> demoGraphic  = new ArrayList<>();
			if (demoGraphic != null)
			{
				int size = demographic.length();
				for (int j = 0; j<size; j++)
				{
					JSONObject demoGraphicObject = demographic.getJSONObject(j);
					demoGraphic.add(new Demographic(demoGraphicObject.has(CITY) ? demoGraphicObject.getString(CITY) : null , 
							demoGraphicObject.has(USER_PERCENTAGE) ? demoGraphicObject.getInt(USER_PERCENTAGE): null));
				}
			}
			carList.add(new CSCarEntry(name, image, price, brand, type, rating, color, engine_cc, mileage, abs_exist, desc,link, demoGraphic));
		}		
		return carList;		
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

	private CSCarFetchServiceInterface mCarFetchInterface;
	private boolean mIsSuccess = false; 
	private int mNumberOfcars = 0;
	//member variable
	public static final String NAME = "name";
	public static final String IMAGE = "image";
	public static final String PRICE = "price";
	public static final String BRAND = "brand";
	public static final String TYPE = 	"type";
	public static final String COLOR = "color";
	public static final String ENGINE_CC = "engine_cc";
	public static final String MILEAGE = "mileage";
	public static final String ABS_EXIT = "abs_exist";
	public static final String LINK = "link";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String DEMOGRAPHIC = "cities";
	public static final String CITY = "city";
	public static final String USER_PERCENTAGE = "users";
}
