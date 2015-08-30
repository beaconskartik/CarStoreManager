package com.quikr.quikrcarstore.ListLoader;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quikr.quikrcarstore.R;
import com.quikr.quikrcarstore.FetchServices.CSCarEntry;
import com.quikr.quikrcarstore.FetchServices.CSCarEntry.Demographic;

public class CSDemoGraphicAdapter extends BaseAdapter 
{ 
    public CSDemoGraphicAdapter(Context context, List<Demographic> items) 
    {
        this.mDemographicList = items;     
        this.mContext = context;
    }
 
    @Override
    public int getCount() 
    {
        return mDemographicList.size();
    }
 
    @Override
    public Object getItem(int position)
    {
        return mDemographicList.get(position);
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
            convertView = View.inflate(mContext, R.layout.adapter_demographic_row, null);
            holder = new CarListViewHolder();
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.percentageUse = (TextView) convertView.findViewById(R.id.percentageUses);
            convertView.setTag(holder);
        } 
        else
        {
            holder = (CarListViewHolder) convertView.getTag();
        }
        holder.countryName.setText(mDemographicList.get(position).getCountryName());
        
        int per = mDemographicList.get(position).getPercentage();
       
        	holder.percentageUse.setText(per+"%");

       
        return convertView;
    }
 
    private static class CarListViewHolder 
    {
        TextView countryName;
        TextView percentageUse;
    }
    
    //members variable
    private final List<Demographic> mDemographicList;
    private final Context mContext;
}