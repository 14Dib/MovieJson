package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ListView listView;
    Adapter_List adapterList;
    String data_json;
    private static String Json_Url = "https://api.jsonbin.io/v3/b/65560bc712a5d376599a49f2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {

            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try{
                    url = new URL(Json_Url);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data!=-1 ){
                        current += (char) data;
                        data = inputStreamReader.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
//            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            data_json = s;
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray();
                jsonArray = jsonObject.getJSONArray("record");
                Toast.makeText(MainActivity.this, "" + jsonArray, Toast.LENGTH_SHORT).show();
                for (int i = 0 ; i < jsonArray.length() ; i ++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String title = jsonObject1.getString("title");
                    String overview = jsonObject1.getString("overview");
                    String poster = jsonObject1.getString("poster");
                    Double rating = jsonObject1.getDouble("rating");
                    Movie movie = new Movie(title , poster , overview , rating);
                    movieList.add(movie);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            listView = (ListView) findViewById(R.id.listview);
            adapterList = new Adapter_List(MainActivity.this, R.layout.item, movieList);
            listView.setAdapter(adapterList);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this , DetailActivity.class);
                    Bundle bundle = new Bundle();
                    Movie movie = movieList.get(i);
                    bundle.putString("title" , movie.getTitle());
                    bundle.putString("overview" , movie.getOverview());
                    bundle.putString("poster" , movie.getPoster());
                    bundle.putDouble("rating" , movie.getRating());

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

}