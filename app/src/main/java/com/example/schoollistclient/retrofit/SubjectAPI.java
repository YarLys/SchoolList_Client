package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Group;
import com.example.schoollistclient.models.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SubjectAPI {
    @POST("/api/subjects/save/{name}")
    public Call<Subject> saveSubject(@Path("name") String subjectName);
    @GET("/api/subjects/get")
    public Call<List<Subject>> getAllSubjects();
    @GET("/api/subjects/get/{id}")
    public Call<Subject> getSubjectById(@Path("id") Integer subjectId);
    @DELETE("/api/subjects/delete/{id}")
    public Call<Subject> deleteSubjectById(@Path("id") Integer subjectId);
}
