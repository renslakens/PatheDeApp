package com.groep3.pathedeapp.dataacces;

import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static String TAG = "NetworkUtils";

    static String getInfo() {
        URL url;
        String inline = "";
        try {
            url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=bce3e84f67721f9e61473a5c397a0bf1&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.i(TAG, "Connecting to API");
            conn.connect();
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                Log.e(TAG, "Could not connect to API, " + responsecode);
                throw new RuntimeException("HttpResponsecode: " + responsecode);
            } else {
                Log.i(TAG, "Getting data from API");
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
            }
            Log.i(TAG, "Disconnecting from API");
            conn.disconnect();
        } catch (IOException e) {
            Log.e(TAG, "Could not read data from API" + e);
            e.printStackTrace();

        }
        return inline;
    }
}
