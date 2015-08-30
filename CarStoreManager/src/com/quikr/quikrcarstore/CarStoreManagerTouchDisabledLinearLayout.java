package com.quikr.quikrcarstore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CarStoreManagerTouchDisabledLinearLayout extends LinearLayout
{
	public CarStoreManagerTouchDisabledLinearLayout(Context context)
	{
		super(context);
	}
	
	public CarStoreManagerTouchDisabledLinearLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	
	public CarStoreManagerTouchDisabledLinearLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		return true;
	}
}