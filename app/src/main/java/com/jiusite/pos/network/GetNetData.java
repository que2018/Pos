package com.jiusite.pos.network;

import java.net.URL;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;

public class GetNetData {
	
	public static void send(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			//readStream(in);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			//urlConnection.disconnect();
		}
	}
}
