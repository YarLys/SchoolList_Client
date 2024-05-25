package com.example.schoollistclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.schoollistclient.models.Teacher;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TeacherInfo";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;
    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(Serializable param1) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Teacher teacherInfo = (Teacher) mParam1;
        TextView first_name = view.findViewById(R.id.Profile_fname);
        first_name.setText(teacherInfo.getFirst_name());
        TextView surname = view.findViewById(R.id.Profile_surname);
        surname.setText(teacherInfo.getSurname());
        TextView last_name = view.findViewById(R.id.Profile_lname);
        last_name.setText(teacherInfo.getLast_name());
        TextView phone = view.findViewById(R.id.Profile_phone);
        phone.setText(teacherInfo.getPhone());
        TextView email = view.findViewById(R.id.Profile_email);
        email.setText(teacherInfo.getEmail());

        Button buttonFunc = view.findViewById(R.id.Go_to_functions); // переход на экран с добавлением классов, дисциплин, видов нагрузки
        Bundle bundle = new Bundle();
        bundle.putSerializable("TeacherInfo", teacherInfo); // Передадим информацию о текущем пользователе
        buttonFunc.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_profile_to_functions));

        Button buttonOut = view.findViewById(R.id.Sign_out); // кнопка выхода из профиля, возвращает на главный экран
        buttonOut.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_profile_to_root));

        return view;
    }
}