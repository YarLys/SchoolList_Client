package com.example.schoollistclient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.schoollistclient.models.Mark;
import com.example.schoollistclient.models.Student;
import com.example.schoollistclient.models.Subject;
import com.example.schoollistclient.models.Workload;
import com.example.schoollistclient.retrofit.MarkAPI;
import com.example.schoollistclient.retrofit.StudentAPI;
import com.example.schoollistclient.retrofit.SubjectAPI;
import com.example.schoollistclient.retrofit.WorkloadAPI;
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
    public void getStudentsByClass(Handler handler, String classId) {
        retrofitService retrofitService = new retrofitService();
        StudentAPI studentAPI = retrofitService.getRetrofit().create(StudentAPI.class);
        studentAPI.getStudentsByClass(classId)
                .enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_STUDENTS_CLASS", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_STUDENTS_CL_ERR", response_code);
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
    public void getStudentMarks(Handler handler, Integer studentId) {
        retrofitService retrofitService = new retrofitService();
        MarkAPI markAPI = retrofitService.getRetrofit().create(MarkAPI.class);
        markAPI.getStudentMarks(studentId)
                .enqueue(new Callback<List<Mark>>() {
                    @Override
                    public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_STUDENT_MARKS", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_STUDENT_MARKS_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<List<Mark>> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
    public void getSubjectById(Handler handler, Integer subjectId) {
        retrofitService retrofitService = new retrofitService();
        SubjectAPI subjectAPI = retrofitService.getRetrofit().create(SubjectAPI.class);
        subjectAPI.getSubjectById(subjectId)
                .enqueue(new Callback<Subject>() {
                    @Override
                    public void onResponse(Call<Subject> call, Response<Subject> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_SUBJECT", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_SUBJECT_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<Subject> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
    public void getWorkloadById(Handler handler, Integer workloadId) {
        retrofitService retrofitService = new retrofitService();
        WorkloadAPI workloadAPI = retrofitService.getRetrofit().create(WorkloadAPI.class);
        workloadAPI.getWorkloadById(workloadId)
                .enqueue(new Callback<Workload>() {
                    @Override
                    public void onResponse(Call<Workload> call, Response<Workload> response) {
                        String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                        Message msg = new Message();
                        if (response_code.equals("200")) {
                            msg.obj = response.body();
                            msg.what = 200;
                            Log.d("GET_WORKLOAD", "SUCCESS");
                        }
                        else {
                            msg.what = Integer.parseInt(response_code);
                            Log.d("GET_WORKLOAD_ERR", response_code);
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(Call<Workload> call, Throwable t) {
                        Log.d("SERVER_ERROR", t.toString());
                        Message msg = new Message();
                        msg.what = 500;
                        handler.sendMessage(msg);
                    }
                });
    }
}
