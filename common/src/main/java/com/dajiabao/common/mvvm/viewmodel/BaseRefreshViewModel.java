package com.dajiabao.common.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import com.dajiabao.common.mvvm.SingleLiveEvent;
import com.dajiabao.common.mvvm.binding.command.BindingAction;
import com.dajiabao.common.mvvm.binding.command.BindingCommand;
import com.dajiabao.common.mvvm.model.BaseModel;

/**
 * Description: <BaseRefreshViewModel><br>
 * Author:      mxdl<br>
 * Date:        2019/06/30<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public abstract class BaseRefreshViewModel<T, M extends BaseModel> extends BaseViewModel<M> {
    protected ObservableArrayList<T> mList = new ObservableArrayList<>();
    public ObservableField<Boolean> orientation = new ObservableField();
    public ObservableField<Boolean> enableLoadMore = new ObservableField();
    public ObservableField<Boolean>  enableRefresh = new ObservableField();

    public BaseRefreshViewModel(@NonNull Application application, M model) {
        super(application, model);
        enableLoadMore.set(enableLoadMore());
        enableRefresh.set(enableRefresh());
    }
    public boolean enableLoadMore(){
        return true;
    }
    public boolean enableRefresh(){
        return true;
    }
    protected BaseRefreshViewModel.UIChangeRefreshLiveData mUIChangeRefreshLiveData;

    public BaseRefreshViewModel.UIChangeRefreshLiveData getUCRefresh() {
        if (mUIChangeRefreshLiveData == null) {
            mUIChangeRefreshLiveData = new BaseRefreshViewModel.UIChangeRefreshLiveData();
        }
        return mUIChangeRefreshLiveData;
    }

    public final class UIChangeRefreshLiveData extends SingleLiveEvent {
        private SingleLiveEvent mStopRefresLiveEvent;
        private SingleLiveEvent mAutoRefresLiveEvent;
        private SingleLiveEvent mStopLoadMoreLiveEvent;
        public SingleLiveEvent getStopRefresLiveEvent() {
            return mStopRefresLiveEvent = createLiveData(mStopRefresLiveEvent);
        }
        public SingleLiveEvent<Void> getAutoRefresLiveEvent() {
            return mAutoRefresLiveEvent = createLiveData(mAutoRefresLiveEvent);
        }
        public SingleLiveEvent<Void> getStopLoadMoreLiveEvent() {
            return mStopLoadMoreLiveEvent = createLiveData(mStopLoadMoreLiveEvent);
        }
    }
    public void postStopRefreshEvent(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getStopRefresLiveEvent().call();
        }
    }
    public void postAutoRefreshEvent(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.getAutoRefresLiveEvent().call();
        }
    }
    public void postStopLoadMoreEvent(){
        if(mUIChangeRefreshLiveData != null){
            mUIChangeRefreshLiveData.mStopLoadMoreLiveEvent.call();
        }
    }
    public ObservableArrayList<T> getList() {
        return mList;
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            refreshData();
        }
    });
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            loadMore();
        }
    });
    public BindingCommand onAutoRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            refreshData();
        }
    });

    public abstract void refreshData();

    public abstract void loadMore();

}
