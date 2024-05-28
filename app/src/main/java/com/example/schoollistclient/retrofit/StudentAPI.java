package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.models.Workload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StudentAPI {
    @POST("/api/students/save")
    public Call<Student> saveStudent(@Body Student student);
    @GET("/api/students/get")
    public Call<List<Student>> getAllStudents();
    @GET("/api/students/get/{id}")
    public Call<Student> getStudentById(@Path("id") Integer studentId);
    @GET("/api/students/get_class/{id}")
    public Call<List<Student>> getStudentsByClass(@Path("id") Integer classId);
    @DELETE("/api/students/delete/{id}")
    public Call<Student> deleteStudentById(@Path("id") Integer studentId);
}
