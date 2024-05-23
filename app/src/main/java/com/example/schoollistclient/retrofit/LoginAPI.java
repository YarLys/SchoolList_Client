package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Teacher;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginAPI {
    @POST("/api/user/login")
    public Call<Teacher> loginUser(@Body Teacher teacher);
}
