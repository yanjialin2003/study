package com.readboy.mycustomview.jetpackJava.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyJavaViewModel extends ViewModel {
    private final MutableLiveData<String> welcomeMessage = new MutableLiveData<>();
    private int clickCount = 0;

    public MyJavaViewModel() {
        welcomeMessage.setValue("Hello World!");
    }

    public LiveData<String> getWelcomeMessage() {
        return welcomeMessage;
    }

    public void updateMessage() {
        clickCount++;
        welcomeMessage.setValue("Click " + clickCount);
    }
}
