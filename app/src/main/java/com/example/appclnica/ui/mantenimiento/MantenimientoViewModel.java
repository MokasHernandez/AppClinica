package com.example.appclnica.ui.mantenimiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MantenimientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MantenimientoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}