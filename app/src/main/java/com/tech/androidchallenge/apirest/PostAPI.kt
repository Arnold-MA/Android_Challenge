package com.tech.androidchallenge.apirest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostAPI {

    /*@POST("post")
    fun createPost(@Body movie: Post): Call<Post>*/

    @GET("posts")
    suspend fun getAllPosts(): List<Post>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>

    /*@PUT("post/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Post): Call<Post>*/

    /*@DELETE("post/{id}")
    fun deletePost(@Path("id") id: Int): Call<Post>*/
}