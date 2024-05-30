package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Mark;
import com.example.schoollistclient.models.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MarkAPI {
    @POST("/api/marks/save")
    public Call<Mark> saveMark(@Body Mark mark);
    @GET("/api/marks/get_student_marks/{id}")
    public Call<List<Mark>> getStudentMarks(@Path("id") Integer studentId);
    @GET("/api/marks/get_student_subject_marks/{student_id}/{subject_id}")
    public Call<List<Mark>> getStudentSubjectMarks(@Path("student_id") Integer studentId, @Path("subject_id") Integer subjectId);
    @DELETE("/api/marks/delete/{id}")
    public Call<Mark> deleteMarkById(@Path("id") Integer markId);
    @PUT("/api/marks/update")
    public Call<Mark> updateMark(@Body Mark mark);
}
