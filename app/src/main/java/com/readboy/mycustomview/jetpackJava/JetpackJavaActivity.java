package com.readboy.mycustomview.jetpackJava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.readboy.mycustomview.R;
import com.readboy.mycustomview.databinding.ActivityJavaJetpackBinding;
import com.readboy.mycustomview.jetpackJava.viewModel.MyJavaViewModel;

public class JetpackJavaActivity extends AppCompatActivity {
    private ActivityJavaJetpackBinding binding;
    private MyJavaViewModel viewModel;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_java_jetpack);
        viewModel = new ViewModelProvider(this).get(MyJavaViewModel.class);

        binding.setMyJavaViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }
}
