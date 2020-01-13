package com.app.bambuappabz.apiNews;


import com.app.bambuappabz.Post;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApiService {
     String key= "d1a9cad08fb645b39fbedbf18b78053e";


    @GET("top-headlines?country=mx&apiKey="+key)
    Call<List<Post>> getPost();



}
