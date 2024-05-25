package com.example.schoollistclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoollistclient.models.Teacher;
import com.example.schoollistclient.retrofit.LoginAPI;
import com.example.schoollistclient.retrofit.TeacherAPI;
import com.example.schoollistclient.retrofit.retrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button button = view.findViewById(R.id.BLOGTOPROF);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = view.findViewById(R.id.LOGETemail);
                EditText password = view.findViewById(R.id.LOGETpassword);

                if (Validator.checkEmail(email) && Validator.checkPassword(password)) {

                    Log.d("LOGIN_INFO", email.getText().toString());
                    Log.d("LOGIN_INFO", password.getText().toString());

                    retrofitService retrofitService = new retrofitService();
                    LoginAPI loginAPI = retrofitService.getRetrofit().create(LoginAPI.class);

                    Teacher teacher = new Teacher();
                    teacher.setEmail(email.getText().toString());
                    teacher.setPassword(password.getText().toString());

                    loginAPI.loginUser(teacher)
                            .enqueue(new Callback<Teacher>() { // отправляем запрос на сервер
                                @Override
                                public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                                    String response_code = response.toString().substring(response.toString().indexOf("code=")+5, response.toString().indexOf("code=")+8);
                                    Log.d("Response_Code", response_code);
                                    if (response_code.equals("200")) { // если запрос выполнен успешно
                                        Log.d("LOGIN", response.body().toString()); // информация о пользователе
                                        Log.d("LOGIN", "Sucessful");

                                        teacher.setId(response.body().getId());
                                        teacher.setFirst_name(response.body().getFirst_name());
                                        teacher.setSurname(response.body().getSurname());
                                        teacher.setLast_name(response.body().getLast_name());
                                        teacher.setPhone(response.body().getPhone());
                                        teacher.setPassword(response.body().getPassword());
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("TeacherInfo", teacher); // Передадим информацию о текущем пользователе

                                        Navigation.findNavController(view).navigate(R.id.action_login_to_profile, bundle); // переходим на страницу профиля
                                        Toast.makeText(getContext(), "Вы успешно вошли в свой аккаунт!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        if (response_code.equals("400")) {
                                            Log.d("LOGIN_ERROR", "Логин или пароль не совпадают");
                                            Toast.makeText(getContext(), "Проверьте корректность введённых данных!", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Log.d("LOGIN_ERROR", "Ошибка сервера");
                                            Toast.makeText(getContext(), "Проверьте корректность введённых данных! Возможна ошибка сервера!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Teacher> call, Throwable t) { // если не удалось соединиться с сервером
                                    Log.d("SERVER_ERROR", t.toString());
                                    Toast.makeText(getContext(), "Не удалось войти в аккаунт. Ошибка сервера: " + t, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        return view;
    }
}