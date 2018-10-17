package com.travis.stellar.stellarbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlJsonUtil {

    public String getStellarInfo(URL url, Boolean price){

        String returnString = null;

        StringBuilder sb = new StringBuilder();
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while((line = br.readLine()) != null){

                sb.append(line);

            }
            conn.disconnect();

            JSONObject jsonObject = new JSONObject(sb.toString());

            if(price){
                returnString =  getUSDPrice(jsonObject);
            }
            else{
                returnString = getAccountInfo(jsonObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;

    }

    public String getUSDPrice(JSONObject jsonObject){

        JSONObject data = (JSONObject)jsonObject.opt("data");

        JSONObject quotes = (JSONObject)data.opt("quotes");

        JSONObject USD = (JSONObject)quotes.opt("USD");

        return USD.get("price").toString();

    }

    public String getAccountInfo(JSONObject jsonObject){

        String balance = "0";

        JSONArray jsonArray = (JSONArray)jsonObject.opt("balances");

        for (int i = 0; i<jsonArray.length(); i++) {

            JSONObject account = jsonArray.getJSONObject(i);

            if(account.opt("asset_type").equals("native")){

                balance = (String)account.get("balance");

            }

        }

        return balance;
    }

}
