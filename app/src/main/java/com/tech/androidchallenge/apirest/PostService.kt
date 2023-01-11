package com.tech.androidchallenge.apirest

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create<PostAPI>(PostAPI::class.java)

    suspend fun getAllPosts() : List<Post>? {
        /*var posts: List<Post>? = null
        service.getAllPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                posts = response.body()
                Log.i("Post_obtenido", Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return posts*/
        Log.i("Post_obtenido", Gson().toJson(service.getAllPosts()))
        return service.getAllPosts()
    }

    fun getPost(id: Int) : Post? {
        var post: Post? = null
        service.getPost(id = id).enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                post = response.body()
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return post
    }

}