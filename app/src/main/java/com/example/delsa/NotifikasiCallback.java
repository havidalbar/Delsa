package com.example.delsa;

import com.example.delsa.POJO.User;

import java.util.ArrayList;

public interface NotifikasiCallback {
    void onSuccess(int newUser);
    void onError(boolean failure);
}
