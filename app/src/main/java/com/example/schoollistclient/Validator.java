package com.example.schoollistclient;

import android.widget.EditText;

public class Validator {
    public static boolean checkFirstName(EditText field) {
        String firstName = field.getText().toString();
        if (firstName.isEmpty()) {
            field.setError("Имя пользователя не может быть пустым!");
            return false;
        }
        else {
            field.setError(null);
            return true;
        }
    }

    public static boolean checkSurname(EditText field) {
        String surname = field.getText().toString();
        if (surname.isEmpty()) {
            field.setError("Фамилия пользователя не может быть пустой!");
            return false;
        }
        else {
            field.setError(null);
            return true;
        }
    }

    public static boolean checkPhone(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Телефон пользователя не может быть пустым!");
            return false;
        }

        String phone = field.getText().toString().substring(1);
        if (!phone.matches("\\d+")) {
            field.setError("Введите корректный номер!");
            return false;
        }
        else {
            field.setError(null);
            return true;
        }
    }

    public static boolean checkEmail(EditText field) {
        String email = field.getText().toString();
        if (email.isEmpty()) {
            field.setError("Email пользователя не может быть пустым!");
            return false;
        }
        if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) { // ^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$
            field.setError("Введите корректный email-адрес!");
            return false;
        }
        else {
            field.setError(null);
            return true;
        }
    }

    public static boolean checkPassword(EditText field) {
        String password = field.getText().toString();
        if (password.isEmpty()) {
            field.setError("Пароль не может быть пустым!");
            return false;
        }
        else {
            field.setError(null);
            return true;
        }
    }
}
