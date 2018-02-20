package com.example.ramon.proyectofinalramonsolerhernan.Config;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Brisanocturna on 19/02/2018.
 */

public class PhotoManager {

    public boolean uploadPhoto(String imagen){
        URL url = null;
        try {
            url = new URL(Config.photomanager);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            byte[] postData = imagen.getBytes();

            Log.d("XXXXXXX", ""+postData.length);

            connection.setConnectTimeout(2000);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setUseCaches(false);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("encodedimage", imagen);
            jsonObject.put("name", System.currentTimeMillis()+".jpg");

            System.out.println(jsonObject.toString());


            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(jsonObject.toString());
            out.close();

            int responsecode = connection.getResponseCode();

            if(responsecode == 200){
                connection.getContent();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(in));
                StringBuilder responseString = new StringBuilder();
                String temp;
                while((temp=streamReader.readLine())!=null){
                    responseString.append(temp);
                }
                String json = responseString.toString();
                System.out.println(json);

                String str=json.replaceAll("\\\\", " ");
                System.out.println(str);
                return true;
            }else{
                return false;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return false;
    }

    
}
