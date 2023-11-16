package com.example.moviejson2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        movieList = new ArrayList<>();

        fetchMovies();
    }

    private void fetchMovies() {
        String url = "https://api.jsonbin.io/v3/b/65560bc712a5d376599a49f2";

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        for (int i = 0 ; i < response.length() ; i ++){
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                String title = jsonObject.getString("title");
//                                String overview = jsonObject.getString("overview");
//                                String poster = jsonObject.getString("poster");
//                                Double rating = jsonObject.getDouble("rating");
//
//                                Movie movie = new Movie(title , poster , overview , rating);
//                                movieList.add(movie);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);
//
//                            recyclerView.setAdapter(adapter);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jArray = response.getJSONArray("record");

                    for (int i = 0 ; i < jArray.length() ; i ++){
                        try {
                            JSONObject jsonObject = jArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String overview = jsonObject.getString("overview");
                            String poster = jsonObject.getString("poster");
                            Double rating = jsonObject.getDouble("rating");

                            Movie movie = new Movie(title , poster , overview , rating);
                            movieList.add(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MovieAdapter adapter = new MovieAdapter(MainActivity.this , movieList);

                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}