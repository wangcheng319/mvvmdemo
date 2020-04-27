package com.dajiabao.common.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.dajiabao.common.mvvm.viewmodel.BaseViewModel;

import java.util.Map;

/**
 * Description: <BaseMvpFragment><br>
 * Author:      mxdl<br>
 * Date:        2019/06/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseMvvmFragment<V extends ViewDataBinding,VM extends BaseViewModel> extends BaseFragment {
    protected V mBinding;
    protected VM mViewModel;
    private int viewModelId;

    @Override
    public void initConentView(ViewGroup root){
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), onBindLayout(),root,true);
        initViewModel();
        initBaseViewObservable();
        initViewObservable();
    }

    private void initViewModel() {
        mViewModel = createViewModel();
        viewModelId = onBindVariableId();
        if(mBinding != null){
            mBinding.setVariable(viewModelId, mViewModel);
        }
        getLifecycle().addObserver(mViewModel);
    }
    public VM createViewModel(){
        return ViewModelProviders.of(this,onBindViewModelFactory()).get(onBindViewModel());
    }
    public abstract Class<VM> onBindViewModel();
    public abstract ViewModelProvider.Factory onBindViewModelFactory();
    public abstract void initViewObservable();
    public abstract int onBindVariableId();

    protected void initBaseViewObservable() {
        mViewModel.getUC().getShowInitLoadViewEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showInitLoadView(show);
            }
        });
        mViewModel.getUC().getShowTransLoadingViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showTransLoadingView(show);
            }
        });
        mViewModel.getUC().getShowNoDataViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showNoDataView(show);
            }
        });
        mViewModel.getUC().getShowNetWorkErrViewEvent().observe(this,  new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                showNetWorkErrView(show);
            }
        });
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        mViewModel.getUC().getFinishActivityEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                if(mActivity != null){
                    mActivity.finish();
                }
            }
        });
        mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                if(mActivity != null){
                    mActivity.onBackPressed();
                }
            }
        });
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}