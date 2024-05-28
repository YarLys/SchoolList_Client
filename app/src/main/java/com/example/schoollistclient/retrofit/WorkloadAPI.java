package com.example.schoollistclient.retrofit;

import com.example.schoollistclient.models.Subject;
import com.example.schoollistclient.models.Workload;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkloadAPI {
    @POST("/api/workloads/save/{name}")
    public Call<Workload> saveWorkload(@Path("name") String workloadName);
    @GET("/api/workloads/get")
    public Call<List<Workload>> getAllWorkloads();
    @GET("/api/workloads/get/{id}")
    public Call<Workload> getWorkloadById(@Path("id") Integer workloadId);
    @DELETE("/api/workloads/delete/{id}")
    public Call<Workload> deleteWorkloadById(@Path("id") Integer workloadId);
}
