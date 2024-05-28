package com.example.schoollistclient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.retrofit.StudentAPI;
import com.example.schoollistclient.retrofit.retrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {
    public void getStudents(Handler handler) {
        retrofitService retrofitService = new retrofitService();
        StudentAPI studentAPI = retrofitService.getRetrofit().create(StudentAPI.class);
        studentAPI.getAllStudents()
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_STUDENTS", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_STUDENTS_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
    public void getStudentById(Handler handler, Integer studentId) {
        retrofitService retrofitService = new retrofitService();
        StudentAPI studentAPI = retrofitService.getRetrofit().create(StudentAPI.class);
        studentAPI.getStudentById(studentId)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_STUDENT_BY_ID", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_STUDENT_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
    public void deleteStudentById(Handler handler, Integer studentId) {
        retrofitService retrofitService = new retrofitService();
        StudentAPI studentAPI = retrofitService.getRetrofit().create(StudentAPI.class);
        studentAPI.deleteStudentById(studentId)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("DEL_STUDENT_BY_ID", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("DEL_STUDENT_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
    public void saveStudent(Handler handler, Student student) {
        retrofitService retrofitService = new retrofitService();
        StudentAPI studentAPI = retrofitService.getRetrofit().create(StudentAPI.class);
        studentAPI.saveStudent(student)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("SAVE_STUDENT", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("SAVE_STUDENT_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
}
