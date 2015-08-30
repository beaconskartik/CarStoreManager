package com.quikr.quikrcarstore.ListLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quikr.quikrcarstore.R;
import com.quikr.quikrcarstore.FetchServices.CSCarEntry;
import com.quikr.quikrcarstore.Utils.CSConstans;

public class CSCarListAdapter extends BaseAdapter 
{ 
	public CSCarListAdapter(Context context, List<CSCarEntry> items) 
	{
		this.mCarList = items;     
		this.mContext = context;
		mCarArrayList = new ArrayList<>();
		mCarArrayList.addAll(mCarList);
	}

	@Override
	public int getCount() 
	{
		return mCarList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mCarList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		CarListViewHolder holder;
		if (convertView == null) 
		{
			convertView = View.inflate(mContext, R.layout.adapter_row_car_display, null);
			holder = new CarListViewHolder();
			holder.CarName = (TextView) convertView.findViewById(R.id.CarTitle);
			holder.clickToMoreInfo = (ImageView) convertView.findViewById(R.id.CarclicktoExpand);
			holder.CarPrice = (TextView) convertView.findViewById(R.id.CarPrice);
			convertView.setTag(holder);
		} 
		else
		{
			holder = (CarListViewHolder) convertView.getTag();
		}
		holder.CarName.setText(mCarList.get(position).getCarName());

		String price = mCarList.get(position).getCarPrice();
		holder.CarPrice.setText(CSConstans.INDIAN_RUPEE+ " "+price + " L");

		holder.clickToMoreInfo.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{


			}
		});

		return convertView;
	}

	// Filter Class
	public void filter(String charText)
	{
		charText = charText.toLowerCase(Locale.getDefault());
		mCarList.clear();
		if (charText.length() == 0) 
		{
			mCarList.addAll(mCarArrayList);
		} 
		else
		{
			for (CSCarEntry carEntry : mCarArrayList) 
			{
				if (carEntry.getCarName().toLowerCase(Locale.getDefault())
						.contains(charText)) 
				{
					mCarList.add(carEntry);
				}
				else if (carEntry.getCarBrand().toLowerCase(Locale.getDefault())
						.contains(charText))
				{
					mCarList.add(carEntry);
				}
				else if (carEntry.getCarType().toLowerCase(Locale.getDefault())
						.contains(charText))
				{
					mCarList.add(carEntry);
				}
			}
		}
		notifyDataSetChanged();
	}
	
	// Filter Class
	public void Sort(SORT_CARS tag)
	{
		mCarList.clear();
		switch (tag) 
		{
		case SORT_BY_TYPE:
			Collections.sort(mCarArrayList, new Comparator<CSCarEntry>() 
			{
		        @Override
		        public int compare(CSCarEntry  entry1, CSCarEntry  entry2)
		        {

		            return  entry1.getCarType().compareTo(entry2.getCarType());
		        }
		    });
			break;
			
		case SORT_BY_MILEAGE:
			Collections.sort(mCarArrayList, new Comparator<CSCarEntry>() 
					{
				        @Override
				        public int compare(CSCarEntry  entry1, CSCarEntry  entry2)
				        {

				            return  entry1.getCarMileage().compareTo(entry2.getCarMileage());
				        }
				    });
			break;
		case SORT_BY_NAME:
			Collections.sort(mCarArrayList, new Comparator<CSCarEntry>() 
					{
				        @Override
				        public int compare(CSCarEntry  entry1, CSCarEntry  entry2)
				        {

				            return  entry1.getCarName().compareTo(entry2.getCarName());
				        }
				    });
			break;
		case SORT_BY_PRICE:
			Collections.sort(mCarArrayList, new Comparator<CSCarEntry>() 
					{
				        @Override
				        public int compare(CSCarEntry  entry1, CSCarEntry  entry2)
				        {
				        	
				        	float car1 = Float.parseFloat(entry1.getCarPrice());
				        	float car2 = Float.parseFloat(entry2.getCarPrice());
				            return  car1 > car2 ? 1 : car1 == car2 ? 0 : -1;
				        }
				    });
			break;
		case SORT_BY_RATING:
			
			Collections.sort(mCarArrayList, new Comparator<CSCarEntry>() 
					{
				        @Override
				        public int compare(CSCarEntry  entry1, CSCarEntry  entry2)
				        {

				            return  entry1.getCarRating() > entry2.getCarRating() ? 1 : entry1.getCarRating() == entry2.getCarRating() ? 0 : -1;
				        }
				    });
			break;

		default:
			break;
		}
		mCarList.addAll(mCarArrayList);
		notifyDataSetChanged();
	}

	private static class CarListViewHolder 
	{
		TextView CarName;
		TextView CarPrice;
		ImageView clickToMoreInfo;
	}
	
	public enum SORT_CARS {SORT_BY_PRICE, SORT_BY_RATING, SORT_BY_NAME, SORT_BY_MILEAGE, SORT_BY_TYPE}; 

	//members variable
	private final List<CSCarEntry> mCarList;
	private ArrayList<CSCarEntry> mCarArrayList;
	private final Context mContext;
}