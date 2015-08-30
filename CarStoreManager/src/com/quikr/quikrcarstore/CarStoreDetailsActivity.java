package com.quikr.quikrcarstore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quikr.quikrcarstore.FetchServices.CSCarEntry;
import com.quikr.quikrcarstore.FetchServices.CSDownloadImageAsyncTask;
import com.quikr.quikrcarstore.ListLoader.CSDemoGraphicAdapter;
import com.quikr.quikrcarstore.Utils.CSConstans;

public class CarStoreDetailsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_page);

		mCarEntry = CarStoreManagerFragment.sEntry;

		mCarDesc = (TextView) findViewById(R.id.carDesc);
		mCarTitle = (TextView) findViewById(R.id.carTitle);
		mCarPhoto = (ImageView) findViewById(R.id.carPhoto);
		mCarRating = (RatingBar) findViewById(R.id.carRating);
		mCarRatingText = (TextView) findViewById(R.id.carRatingText);
		mCityList = (ListView) findViewById(R.id.city_list);
		mCarCC = (TextView)findViewById(R.id.car_cc);
		mCarColor = findViewById(R.id.car_color_value);
		mCarHasAbs = (TextView) findViewById(R.id.car_abs);
		mCarMileage = (TextView) findViewById(R.id.car_mileage);
		mCarType = (TextView) findViewById(R.id.car_type);

		// loading 
		new CSDownloadImageAsyncTask(mCarPhoto).execute(mCarEntry.getCarImageURL());

		Log.d(CSConstans.CAR_STORE_LOG_TAG, mCarEntry.getCarName());

		mShareDetails = "Car Name : " + mCarEntry.getCarName() +  " Car Brand" + mCarEntry.getCarBrand() + " Car Type : " + mCarEntry.getCarType()
				+ " Car Engine CC : " + mCarEntry.getCarEngineCC() 
				+ " Car Color : " + mCarEntry.getCarColor()  + " Car Price : " + mCarEntry.getCarPrice()  + " Car Mileage : " + mCarEntry.getCarMileage()
				+ " Car Rating : " + mCarEntry.getCarRating() + " Car Des : " + mCarEntry.getCarDescription();

		mCityList.setAdapter(new CSDemoGraphicAdapter(this, mCarEntry.getDemoGraphic()));
		mCarTitle.setText(mCarEntry.getCarName());
		mCarDesc.setText(mCarEntry.getCarDescription());
		mCarRatingText.setText(""+mCarEntry.getCarRating());
		mCarRating.setRating((float) mCarEntry.getCarRating());
		mCarCC.setText("CC : " + mCarEntry.getCarEngineCC());
		mCarHasAbs.setText("ABS : " + (mCarEntry.isABSExistInCar() ? "YES" : "NO"));
		mCarMileage.setText("Mileage : " + mCarEntry.getCarMileage());
		mCarType.setText("Type : " + mCarEntry.getCarType());
		mCarColor.setBackgroundColor(Color.parseColor(mCarEntry.getCarColor()));
		
		//setting onclick listener for Link, rest click listener are defined in XML
		findViewById(R.id.QuikrLink).setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Link();
			}
		});
	}


	// sharing information via SMS
	public void Sms(View v)
	{
		getPhoneFromDialog();
	}

	//General share
	public void Share(View v)
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, mShareDetails);
		sendIntent.setType("text/plain");

		if (sendIntent.resolveActivity(getPackageManager()) != null)
		{
			startActivity(Intent.createChooser(sendIntent, "Share Details"));
		}
		else
		{
			Toast.makeText(this, "No Supported Application", Toast.LENGTH_SHORT).show();
		}
	}


	//back button
	public void Back(View v)
	{
		finish();	
	}

	// web 
	private void Link()
	{
		Intent webIntent = new Intent(this, CarStoreManagerOpenQuikrSite.class);
		webIntent.putExtra(CarStoreManagerOpenQuikrSite.URL, mCarEntry.getQuikrCarLink()); //Optional parameters
		webIntent.putExtra(CarStoreManagerOpenQuikrSite.TITLE, mCarEntry.getCarName());
		startActivity(webIntent);
	}

	private void getPhoneFromDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Please Enter Phone Number");

		// Set up the input
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() { 
			@SuppressWarnings("deprecation")
			@SuppressLint("ShowToast")
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				mPhoneNumber = input.getText().toString();
				SmsManager sms = SmsManager.getDefault();
				if (mPhoneNumber != null)
				{	
					sms.sendTextMessage(mPhoneNumber, null, mShareDetails, null, null); 
					Toast.makeText(CarStoreDetailsActivity.this, "SMS Send Sucessfully", Toast.LENGTH_LONG);
				}
				else
				{

					Toast.makeText(CarStoreDetailsActivity.this, "failed to send SMS !!", Toast.LENGTH_LONG);
				}
			}
		});
		builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();

	}

	//members variable
	private String mShareDetails;
	private String mPhoneNumber;
	private ImageView mCarPhoto;
	private TextView mCarTitle;
	private TextView mCarDesc;
	private RatingBar mCarRating;
	private TextView mCarRatingText;
	private CSCarEntry mCarEntry;
	private ListView mCityList;

	private View mCarColor;
	private TextView mCarCC;
	private TextView mCarType;
	private TextView mCarHasAbs;
	private TextView mCarMileage;



	//	public static final String NAME_KEY = "name_key";
	//	public static final String IMAGE_KEY = "image_url_key";
	//	public static final String URL_KEY = "url_key";
	//	public static final String PRICE_KEY = "price_key";
	//	public static final String RATING_KEY = "rating_key";
	//	public static final String DESCRIPTION_KEY = "description_key";
	//	public static final String DEMOGRAPHIC_KEY = "demographic_key";
	//	public static final String COUNTRY_KEY = "country_key";
	//	public static final String PERCENTAGE_KEY = "percentage_key";

}
