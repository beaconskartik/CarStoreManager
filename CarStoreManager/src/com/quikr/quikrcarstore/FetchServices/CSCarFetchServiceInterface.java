package com.quikr.quikrcarstore.FetchServices;

import java.util.ArrayList;

public interface CSCarFetchServiceInterface
{
	void onLoadCarListStarted();
	void onLoadCarListFailed();
	void onLoadCarListSuccess(ArrayList<CSCarEntry> carList, int nuberOfCars);
}