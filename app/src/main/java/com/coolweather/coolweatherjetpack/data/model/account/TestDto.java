package com.coolweather.coolweatherjetpack.data.model.account;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @Author: wangc
 * @CreateDate: 2020/4/13 17:21
 */
public class TestDto implements Parcelable {
    public String name;
    public int age;

    public TestDto(String name,int age){
        this.name = name;
        this.age = age;
    }

    protected TestDto(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<TestDto> CREATOR = new Creator<TestDto>() {
        @Override
        public TestDto createFromParcel(Parcel in) {
            return new TestDto(in);
        }

        @Override
        public TestDto[] newArray(int size) {
            return new TestDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
