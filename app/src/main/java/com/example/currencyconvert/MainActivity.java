package com.example.currencyconvert;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView usdText;
    TextView tryText;
    TextView jpyText;
    TextView cadText;
    TextView chfText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try{

            String url = "http://data.fixer.io/api/latest?access_key=6acee5d26e1a9715552031789328be2c&format=1";

            downloadData.execute(url);

        }catch (Exception e) {

            e.printStackTrace();

        }


    }


    private class DownloadData extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data > 0 ){

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }

                return result;

            }catch (Exception e){
                return null;
            }


        }

        @SuppressLint("WrongViewCast")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("Alınan Data : " + s);

           try{

               JSONObject jsonObject = new JSONObject(s);
               String base = jsonObject.getString("base");

               String rates = jsonObject.getString("rates");

               JSONObject jsonObject1 = new JSONObject(rates);
               String trLira = jsonObject1.getString("TRY");
               tryText = findViewById(R.id.tryText);
               tryText.setText("TRY : " + trLira);

               String cad = jsonObject1.getString("CAD");
               cadText = findViewById(R.id.cadText);
               cadText.setText("CAD : " + cad);

               String usd = jsonObject1.getString("USD");
               usdText = findViewById(R.id.usdText);
               usdText.setText("USD : " + usd);

               String jpy = jsonObject1.getString("JPY");
               jpyText = findViewById(R.id.jpyText);
               jpyText.setText("JPY : " + jpy);

               String chf = jsonObject1.getString("CHF");
               chfText = findViewById(R.id.chfText);
               chfText.setText("CHF : " + chf);



           }catch (Exception e){

               e.printStackTrace();


           }


        }
    }



}
