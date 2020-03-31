package com.coolweather.coolweatherjetpack;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
    public ApplicationViewModel(@NonNull Application application) {
        super(application);

    }
    
}
