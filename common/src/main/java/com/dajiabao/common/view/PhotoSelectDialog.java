package com.dajiabao.common.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.dajiabao.common.R;
import com.dajiabao.common.util.DisplayUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


/**
 * Description: <PhotoSelectDialog><br>
 * Author: mxdl<br>
 * Date: 2019/1/3<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class PhotoSelectDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    public static final String TAG = PhotoSelectDialog.class.getSimpleName();
    private OnPhotoClickLisener mOnClickLisener;
    private String mPhotoPath;

    public void setOnClickLisener(OnPhotoClickLisener onPhotoClickLisener) {
        mOnClickLisener = onPhotoClickLisener;
    }

    public static PhotoSelectDialog newInstance() {
        return new PhotoSelectDialog();
    }

    public interface OnPhotoClickLisener {
        void onTakePhototClick(String path);

        void onSelectPhotoClick(List<String> list);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(16) * 2, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_select, container, false);
        Button btnSelectPhoto = (Button) view.findViewById(R.id.btn_select_photo);
        Button btnTakephoto = (Button) view.findViewById(R.id.btn_take_photo);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        btnSelectPhoto.setOnClickListener(this);
        btnTakephoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_take_photo) {

        } else if (i == R.id.btn_select_photo) {

        } else if (i == R.id.btn_cancel) {
            dismiss();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
