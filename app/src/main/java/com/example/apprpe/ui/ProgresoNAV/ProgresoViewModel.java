package com.example.apprpe.ui.ProgresoNAV;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgresoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProgresoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}