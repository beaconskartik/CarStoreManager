package com.quikr.quikrcarstore;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.quikr.quikrcarstore.FetchServices.CSAPIHitCountAsyncTask;
import com.quikr.quikrcarstore.FetchServices.CSCarEntry;
import com.quikr.quikrcarstore.FetchServices.CSCarFetchServiceInterface;
import com.quikr.quikrcarstore.FetchServices.CSLoadCarListAsynTask;
import com.quikr.quikrcarstore.ListLoader.CSCarListAdapter;
import com.quikr.quikrcarstore.ListLoader.CSCarListAdapter.SORT_CARS;
import com.quikr.quikrcarstore.Utils.CSConstans;


public class CarStoreManagerFragment extends Fragment implements CSCarFetchServiceInterface, OnItemClickListener
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_car_list,
				container, false);
		return rootView;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		mLoadingScreen = (CarStoreManagerTouchDisabledLinearLayout) view.findViewById(R.id.loading_view);
		//mCarListRefresh = (SwipeRefreshLayout) view.findViewById(R.id.car_list_view_swipe_container);
		mCarListView = (ListView) view.findViewById(R.id.car_list_view);
		mNoCarFound = (TextView) view.findViewById(R.id.no_matching_car_found);
		mNoCarTodisplay = (TextView) view.findViewById(R.id.noCarListToDisplay);
		mCustomViewStub = (ViewStub)view.findViewById(R.id.custom_view_stub);
		
        View footerView =  placeCustomViewBelowTabStrip(R.layout.list_view_footer);
        mNoAPIHits = (TextView) footerView.findViewById(R.id.apihit);
        mNoCars = (TextView) footerView.findViewById(R.id.carscount);
		
        if (getAvailableNetworkType(getActivity()) != NETWORK_AVAILABILITY_STATUS.NO_NETWORK)
        {
        	new CSLoadCarListAsynTask(this).execute(CSConstans.CAR_STORE_LIST_FETCH_API);
        	new CSAPIHitCountAsyncTask(mNoAPIHits).execute(CSConstans.CAR_STORE_HIT_COUNT_API);
        }
        
        
//        mEditsearch.setHint("Search (by name, brand, type");
//        // Capture Text in EditText
//     		mEditsearch.addTextChangedListener(new TextWatcher() {
//      
//     			@Override
//     			public void afterTextChanged(Editable arg0) 
//     			{
//     				// TODO Auto-generated method stub
//     				String text = mEditsearch.getText().toString().toLowerCase(Locale.getDefault());
//     				if (mCarListAdapter != null)
//     					mCarListAdapter.filter(text);
//     			}
//      
//     			@Override
//     			public void beforeTextChanged(CharSequence arg0, int arg1,
//     					int arg2, int arg3) {
//     				// TODO Auto-generated method stub
//     			}
//      
//     			@Override
//     			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//     					int arg3) {
//     				// TODO Auto-generated method stub
//     			}
//     		});
		super.onViewCreated(view, savedInstanceState);
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		final CarStoreSearchView serachView = new CarStoreSearchView(getActivity());
		MenuItem serach = menu.findItem(R.id.search);
		serach.setActionView(serachView);
		
		serachView.setQueryHint("Search By Car Name/Brand/Type");
		serachView.setOnQueryTextListener(new OnQueryTextListener() 
		{
			
			@Override
			public boolean onQueryTextSubmit(String query) 
			{
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText)
			{
				if (mCarListAdapter != null)
				{
					mCarListAdapter.filter(newText);
				}
				return false;
			}
		});
		
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		int id = item.getItemId();
		if (mCarListAdapter != null)
		{
			mCarListAdapter.Sort(getTagfromMenuID(id));
		}
		return super.onOptionsItemSelected(item);
	}
	
	private SORT_CARS getTagfromMenuID(int id)
	{
		SORT_CARS val = SORT_CARS.SORT_BY_NAME;
		switch (id) 
		{
		case R.id.sort_by_mileage:
			val = SORT_CARS.SORT_BY_MILEAGE;
			break;
		case R.id.sort_by_name:
			val = SORT_CARS.SORT_BY_NAME;
			break;

		case R.id.sort_by_price:
			val = SORT_CARS.SORT_BY_PRICE;
			break;

		case R.id.sort_by_rating:
			val = SORT_CARS.SORT_BY_RATING;
			break;

		case R.id.sort_by_type:
			val = SORT_CARS.SORT_BY_TYPE;
			break;

		default:
			break;
		}
		return val;
	}

	@Override
	public void onDestroyView() 
	{
		mLoadingScreen = null;
		mCarListView = null;
		mCarListRefresh = null;
		mNoCarFound = null;
		mNoCarTodisplay = null;
		mEditSearch = null;
		super.onDestroyView();
	}

	
	@Override
	public void onLoadCarListStarted()
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onLoadCarListFailed() 
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.GONE);
		}	
	}

	@Override
	public void onLoadCarListSuccess(ArrayList<CSCarEntry> carList, int numberOfCars) 
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.GONE);
		}	
		
		if (carList != null && !carList.isEmpty())
		{
			if (mNoCarTodisplay != null)
			{
				mNoCarTodisplay.setVisibility(View.GONE);
			}
			mCarListAdapter = new CSCarListAdapter(getActivity(), carList);
			if (mCarListView != null)
			{
				mNoCars.setText("Cars Count : "+numberOfCars);
				mCarListView.setAdapter(mCarListAdapter);
				mCarListView.setOnItemClickListener(this);
			}
		}
		else 
		{
			mNoCarTodisplay.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) 
	{

		if (position < 0 || position > (parent.getCount() - 1))
		{
			// Invalid ID , only valid values can be
			// 0,1,...size-1
			return;
		}

		Object object = parent.getItemAtPosition(position);
		if (object instanceof CSCarEntry)
		{
			CSCarEntry clickedFileEntry = (CSCarEntry) object;
			sEntry = clickedFileEntry;
			Intent openCarsDetailsIntent = new Intent(getActivity(), CarStoreDetailsActivity.class);
			getActivity().startActivity(openCarsDetailsIntent);
		}
		
	}
	
	/**
	 * Function to add the custom view below car list
	 * @return return the view that is inflated and placed
	 */
	public View placeCustomViewBelowTabStrip(int layoutID)
	{
		View inflatedView = null;
		if (mCustomViewStub != null)
		{
			mCustomViewStub.setLayoutResource(layoutID);
			inflatedView = mCustomViewStub.inflate();
		}
		return inflatedView;
	}
	
	public static CSCarEntry getCurrentClickEntry()
	{
		return sEntry;
	}
	
	public static NETWORK_AVAILABILITY_STATUS getAvailableNetworkType(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())
		{
			int type = activeNetworkInfo.getType();
			if (type == ConnectivityManager.TYPE_MOBILE)
			{
				return NETWORK_AVAILABILITY_STATUS.DATA_PLAN;
			}
			else if (type == ConnectivityManager.TYPE_WIFI)
			{
				return NETWORK_AVAILABILITY_STATUS.WIFI;
			}
		}
		
		return NETWORK_AVAILABILITY_STATUS.NO_NETWORK;
	}
	
	//private members
	public enum NETWORK_AVAILABILITY_STATUS
	{
		NO_NETWORK,
		DATA_PLAN, 
		WIFI
	}

	
	private class CarStoreSearchView extends SearchView
	{
		public CarStoreSearchView(Context context)
		{
			super(context);
		}
		
		@Override
		public void onActionViewCollapsed()
		{
			setQuery("", false);
			super.onActionViewCollapsed();
		}
		
		@Override
		public void onActionViewExpanded()
		{
			super.onActionViewExpanded();
		}
	}
	
	
	public static CSCarEntry sEntry;
	private ViewStub mCustomViewStub;
	private CarStoreManagerTouchDisabledLinearLayout mLoadingScreen;
	private ListView mCarListView;
	private SwipeRefreshLayout mCarListRefresh;
	private TextView mNoCarTodisplay;
	private TextView mNoCarFound;
	private TextView mNoAPIHits;
	private TextView mNoCars;
	private CSCarListAdapter mCarListAdapter;
	private EditText mEditSearch;
}