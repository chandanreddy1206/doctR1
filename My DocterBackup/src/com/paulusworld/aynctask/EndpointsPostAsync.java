package com.paulusworld.aynctask;

import java.lang.reflect.Type;
import java.util.Map;

import com.paulusworld.drawernavigationtabs.util.JsonUtil;

import android.os.AsyncTask;

public class EndpointsPostAsync<T> extends AsyncTask<String, String, T> {
	EndpointAsyncInterface<T> source;
	Map<String,Object> mapForObject;
	Type type;
	public EndpointsPostAsync(EndpointAsyncInterface<T> source,Map<String,Object> mapForObject,Type type){
		this.source=source;
		this.mapForObject=mapForObject;
		this.type=type;
	}
	@Override
	protected T doInBackground(String... params) {
		// TODO Auto-generated method stub
		 return JsonUtil.postdataToUrl(params[0], mapForObject, type);
	}
	@Override
	protected void onPostExecute(T result) {
		// TODO Auto-generated method stub
		source.updateData(result);
	}

}
