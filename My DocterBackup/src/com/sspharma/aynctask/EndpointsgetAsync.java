package com.sspharma.aynctask;

import java.lang.reflect.Type;

import com.sspharma.util.JsonUtil;

import android.os.AsyncTask;
import android.util.Log;

public class EndpointsgetAsync<T> extends AsyncTask<String, String, T> {

	EndpointAsyncInterface<T> source;
	Type type;

	public EndpointsgetAsync(EndpointAsyncInterface<T> source, Type type) {
		this.source = source;
		this.type = type;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected T doInBackground(String... params) {
		return JsonUtil.getContentFromUrl(params[0], type);
		 
	}

	@Override
	protected void onPostExecute(T result) {
		Log.e("Async Return", result+"");
		source.updateData(result);
	}
}