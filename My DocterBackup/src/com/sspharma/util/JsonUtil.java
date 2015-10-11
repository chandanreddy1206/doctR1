package com.sspharma.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.util.Log;

public class JsonUtil {
	@SuppressWarnings("unused")
	public static JSONArray requestContent(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		String result = null;
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		InputStream instream = null;
		JSONArray items = null;

		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				instream = entity.getContent();
				result = convertStreamToString(instream);
				Log.e("JSON Util result", result + "");
				JSONObject json = new JSONObject(result);
				Log.e("JSON Util json", json + "");
				if (json == null) {

					return null;
				} else {

					items = json.getJSONArray("items");
					Log.e("JSON Util json else items", items + "");
				}
			}

		} catch (Exception e) {
			// manage exceptions
			return null;
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (Exception exc) {

				}
			}
		}

		return items;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}

		return sb.toString();
	}

	public static <T> T getContentFromUrl(String url, Type type) {
		HttpClient httpclient = new DefaultHttpClient();
		String result = null;
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		InputStream instream = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				instream = entity.getContent();
				result = convertStreamToString(instream);
				Log.e("JSON Util result", result + "");

				T t;
				if (result.contains("items")) {
					JSONObject json = new JSONObject(result);
					Log.e("JSON Util json", json + "");
					Log.e("JSON Util json.getJSONArray", json.getJSONArray("items").toString() + "");
					Log.e("JSON Util json.return ",
							new Gson().fromJson(json.getJSONArray("items").toString(), type) + "");
					t = (T) new Gson().fromJson(json.getJSONArray("items").toString(), type);
				} else {
					t = new Gson().fromJson(result, type);
				}
				Log.e("JSON Util json.return ", t + "");
				return t;
			}
		} catch (Exception e) {
			// manage exceptions
			Log.e("error", e.toString(), e);
			return null;
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (Exception exc) {

				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T postdataToUrl(String url, Map<String, Object> mapForObject, Type type) {

		T t = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		String json = "";
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> map : mapForObject.entrySet()) {
			try {
				jsonObject.accumulate(map.getKey(), map.getValue());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		json = jsonObject.toString();

		try {
			org.apache.http.entity.StringEntity se = new org.apache.http.entity.StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpclient.execute(httpPost);
			InputStream inputStream = httpResponse.getEntity().getContent();

			t = (T) new Gson().fromJson(IOUtils.toString(inputStream), type);

		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 6. set httpPost Entity

		return t;

	}

	public static void postUrlForServlet(String url, RequestParams requestParams) {

	}
}

class uploadServ extends AsyncHttpResponseHandler {
	@Override
	public void onProgress(int bytesWritten, int totalSize) {
		Log.v("AsyncHttpResponseHandler",
				String.format("Progress %d from %d (%2.0f%%)",
						new Object[] { Integer.valueOf(bytesWritten),
								Integer.valueOf(
										totalSize),
				Double.valueOf(totalSize <= 0 ? -1D : (((double) bytesWritten * 1.0D) / (double) totalSize) * 100D) }));

		Double delta = Double
				.valueOf(totalSize <= 0 ? -1D : (((double) bytesWritten * 1.0D) / (double) totalSize) * 100D);

	}

	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		// TODO Auto-generated method stub
	}
	
	public static void downloadFile(String url, File outputFile) {
		try {
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			int contentLength = conn.getContentLength();

			DataInputStream stream = new DataInputStream(u.openStream());

			byte[] buffer = new byte[contentLength];
			stream.readFully(buffer);
			stream.close();

			DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
			fos.write(buffer);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			return; // swallow a 404
		} catch (IOException e) {
			return; // swallow a 404
		}
	}
}