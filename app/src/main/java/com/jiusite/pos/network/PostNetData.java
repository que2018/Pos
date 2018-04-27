package com.jiusite.pos.network;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

 //https://stackoverflow.com/questions/2938502/sending-post-data-in-android

public class PostNetData {
	
	public static void send(String urlString, String data) {
		OutputStream out = null;
		
		try {
			URL url = new URL(urlString);

			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

			out = new BufferedOutputStream(urlConnection.getOutputStream());

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

			writer.write(data);

			writer.flush();

			writer.close();

			out.close();

			urlConnection.connect();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}


