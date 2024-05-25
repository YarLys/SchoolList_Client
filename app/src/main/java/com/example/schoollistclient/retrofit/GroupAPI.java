package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Group;
import com.example.schoollistclient.models.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupAPI {
    @POST("/api/groups/save")
    public Call<Group> saveGroup(@Body Group group);
    @GET("/api/groups/get")
    public Call<List<Group>> getAllGroups();
    @GET("/api/groups/get/{id}")
    public Call<Group> getGroupById(@Path("id") String groupId);
    @DELETE("/api/groups/delete/{id}")
    public Call<Group> deleteGroupById(@Path("id") String groupId);
}
