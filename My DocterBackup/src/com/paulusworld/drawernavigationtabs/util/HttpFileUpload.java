package com.paulusworld.drawernavigationtabs.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.paulusworld.drawernavigationtabs.constants.Constants;

import android.util.Log;
	
public class HttpFileUpload implements Runnable{
        URL connectURL;
        String responseString;
        String Title;
        String Description;
        String patientEmail;
        String doctorEmail;
        byte[] dataToServer;
        FileInputStream fileInputStream = null;
        String messageType;

        public HttpFileUpload(String urlString, String vTitle, String vDesc,String patientEmail,String doctorEmail,String messageType){
                try{
                        connectURL = new URL(urlString);
                        this.Title= vTitle;
                       this. Description = vDesc;
                       this.patientEmail=patientEmail;
                       this.doctorEmail=doctorEmail;
                       this.messageType=messageType;
                }catch(Exception ex){
                	ex.printStackTrace();
                }
        }
	
        public void Send_Now(FileInputStream fStream){
                fileInputStream = fStream;
                Sending();
        }
	
        void Sending(){
//                String iFileName = ;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String Tag="fSnd";
                try
                {
	
                        // Open a HTTP connection to the URL
                        HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
	
                        // Allow Inputs
                        conn.setDoInput(true);
	
                        // Allow Outputs
                        conn.setDoOutput(true);
	
                        // Don't use a cached copy.
                        conn.setUseCaches(false);
	
                        // Use a post method.
                        conn.setRequestMethod("POST");
	
                        conn.setRequestProperty("Connection", "Keep-Alive");
	
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
	
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"patientEmail\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(patientEmail);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
	                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"doctorEmail\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(doctorEmail);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(Title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"voiceMsgType\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(messageType);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
	                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + "Hell9.mp3" +"\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes("Content-Type: "+URLConnection.guessContentTypeFromStream(fileInputStream));
                        dos.writeBytes(lineEnd);
	
                        // create a buffer of maximum size
                        int bytesAvailable = fileInputStream.available();
	                        
                        int maxBufferSize = 1024;
                        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        byte[ ] buffer = new byte[bufferSize];
	
                        // read file and write it into form...
                        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	
                        while (bytesRead > 0)
                        {
                                dos.write(buffer, 0, bufferSize);
                                bytesAvailable = fileInputStream.available();
                                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                                bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                        }
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	
                        // close streams
                        fileInputStream.close();
	                        Log.e("dos HttpUpload", dos+"");
                        dos.flush();
	                        
	                         
                        InputStream is = conn.getInputStream();
	                        
                        // retrieve the response from server
                        int ch;
	
                        StringBuffer b =new StringBuffer();
                        while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                        String s=b.toString();
                        dos.close();
                }
                catch (MalformedURLException ex)
                {
                	 Log.e("dos HttpUpload MalformedURLException", ex+"");
                	ex.printStackTrace();
                }
	
                catch (IOException ioe)
                {
                	 Log.e("dos HttpUpload IOException", ioe+"");
                	ioe.printStackTrace();
                }
        }
	
        @Override
        public void run() {
                // TODO Auto-generated method stub
        }
}