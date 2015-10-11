package com.sspharma.aynctask;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



/**
 * Background Async Task to download file
 * */
public class DownloadFile extends AsyncTask<String, String, String> {
	
	ProgressDialog dialog;
	DownloadFileInterface callback;
	public DownloadFile(DownloadFileInterface callback,ProgressDialog dialog) {
		this.dialog = dialog;
		this.callback = callback;
	}
    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }
 
    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();
 
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
            // Output stream to write file
            OutputStream output = new FileOutputStream(f_url[1]);
 
            byte data[] = new byte[1024];
 
            long total = 0;
 
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                // writing data to file
                output.write(data, 0, count);
            }
 
            // flushing output
            output.flush();
 
            // closing streams
            output.close();
            input.close();
 
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
 
        return null;
    }
 
    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        dialog.setProgress(Integer.parseInt(progress[0]));
   }
 
    /**
     * After completing background task
     * Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
    	dialog.dismiss();
    	callback.downloadCompleted();
    }
 
}