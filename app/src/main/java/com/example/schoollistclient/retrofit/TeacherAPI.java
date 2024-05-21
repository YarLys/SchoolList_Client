package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TeacherAPI {
    @GET("/api/user/get")
    public Call<List<Teacher>> getAllTeachers();
    @GET("/api/user/get/{id}")
    public Call<Teacher> getTeacherById(@Path("id") Integer teacherId);
    @POST("/api/user/register")
    public Call<Teacher> saveTeacher(@Body Teacher teacher);
    @DELETE("/api/user/delete/{id}")
    public Call<Teacher> deleteTeacherById(@Path("id") Integer teacherId);
}
