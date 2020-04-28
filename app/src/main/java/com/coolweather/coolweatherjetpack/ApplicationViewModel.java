package com.coolweather.coolweatherjetpack;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * @ProjectName: mvvmdemo
 * @Package: com.coolweather.coolweatherjetpack
 * @ClassName: ApplicationViewModel
 * @Description: java类作用描述
 * @Author: wangc
 * @CreateDate: 2019/12/3 14:19
 * @Version: 1.0
 */
public class ApplicationViewModel extends AndroidViewModel {
    private String test;
    private MutableLiveData<String> userName ;
    public MutableLiveData<String>  passWord ;
    public ApplicationViewModel(@NonNull Application application) {
        super(application);
        passWord = new MutableLiveData<>();
    }

}
