package com.paulusworld.drawernavigationtabs.util;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharingPreferences<T> {

	private String PREFS_NAME = "MYDOCTER";
	SharedPreferences settings;
	Editor editor;

	public SharingPreferences(String prefsName) 
	{
		this.PREFS_NAME = prefsName; 
	}
	public SharingPreferences() {
	}

	public void save(Context context,String Key, T object) {

		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();

		Gson gson = new Gson();
		String objectJson = gson.toJson(object);

		editor.putString(Key, objectJson);

		editor.commit();
	}

	public void remove(Context context,String Key) {
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();

		editor.remove(Key);
		editor.commit();
	}

	public T get(Context context,String Key,Class<T> cls) {
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		T object = null;
		if (settings.contains(Key)) {
			String jsonFavorites = settings.getString(Key, null);
			Gson gson = new Gson();
			object = gson.fromJson(jsonFavorites, cls);
		}
		return object;

	}
}
