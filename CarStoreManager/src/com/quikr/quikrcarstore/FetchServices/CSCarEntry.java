package com.quikr.quikrcarstore.FetchServices;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class CSCarEntry
{
	private String mCarName, mCarDescription, mCarPrice, mCarImage;
	private String mCarBrand, mCartype, mCarMileage;
	private String mCarColor, mCarEngineCC;
	private double mCarRating;
	private boolean mAbsExist;
	private String mQuikrCarLink;
	private ArrayList<Demographic> mDemographics = new ArrayList<>();
	public static class Demographic
	{
		private String mCityName;
		private int mUsersPercentage;
		public Demographic(String cityName, int userPercent) 
		{
			// TODO Auto-generated constructor stub
			mCityName = cityName;
			mUsersPercentage = userPercent;
		}
		public String getCountryName()
		{
			return mCityName;
		}
		
		public int getPercentage()
		{
			return mUsersPercentage;
		}
	}
	
	
	public CSCarEntry(String name, String image, String price, String brand, String type, double rating, 
			String color, String engineCC, String mileage, boolean absExist, String des, String link, ArrayList<Demographic> demographic)
	{
		mCarName = name;
		mCarImage = image;
		mCarPrice = price;
		mCarBrand = brand;
		mCartype = type;
		mCarRating = rating;
		mCarColor = color;
		mCarEngineCC = engineCC;
		mCarMileage = mileage;
		mAbsExist = absExist;
		mCarDescription = des;
		mQuikrCarLink = link;
		mDemographics = demographic;
	}
	
	public String getQuikrCarLink()
	{
		return mQuikrCarLink;
	}
	
	public String getCarName() 
	{
		return mCarName;
	}
	
	public String getCarDescription()
	{
		return mCarDescription;
	}
	
	public String getCarImageURL()
	{
		return mCarImage;
	}
	
	public String getCarPrice()
	{
		return mCarPrice;
	}
	
	public double getCarRating()
	{
		return mCarRating;
	}
	
	public ArrayList<Demographic> getDemoGraphic()
	{
		return mDemographics;
	}
	
	public String getCarColor()
	{
		return mCarColor;
	}
	
	public String getCarBrand()
	{
		return mCarBrand;
	}
	
	public String getCarMileage()
	{
		return mCarMileage;
	}
	
	public String getCarType()
	{
		return mCartype;
	}
	
	public String getCarEngineCC()
	{
		return mCarEngineCC;
	}
	public boolean isABSExistInCar()
	{
		return mAbsExist;
	}
	
}
