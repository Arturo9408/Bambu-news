package com.app.bambuappabz;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;


import com.app.bambuappabz.apiNews.NewsApiService;


import java.util.List;

public class News_activity extends AppCompatActivity {
    private Retrofit retrofit;
    //NewsApiService mService;
    private String Base_URL = "https://newsapi.org/v2/";
    AlertDialog dialog;
    private static final String TAG = "newsTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_activity);

        dialog= new SpotsDialog(News_activity.this);
        dialog.show();
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ObtenerDatos2();

    }

    private void ObtenerDatos2() {
        NewsApiService newsApiService = retrofit.create(NewsApiService.class);
        Call<List<Post>> call = newsApiService.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    dialog.dismiss();
                    Log.e(TAG, "onResponse: "+response.errorBody());
                    Log.e(TAG, "onResponse: "+response.code());
                    return;
                }

                List<Post> posts = response.body();
                for(Post post : posts){
                    Log.i(TAG,"Body"+post.getContent());

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: "+t.getMessage());

            }
        });
    }


}
