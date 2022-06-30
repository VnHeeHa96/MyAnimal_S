package com.vhh.fragment.view.frg;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vhh.fragment.App;
import com.vhh.fragment.databinding.FragmentM000SplashBinding;
import com.vhh.fragment.viewmodel.CommonVM;

public class M000SplashFrg extends BaseFragment<FragmentM000SplashBinding, CommonVM> {
    public static final String TAG = M000SplashFrg.class.getName();

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    protected void initViews() {
        binding.imIcon.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_slide_in_bottom));
        binding.tvTitle.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_slide_in_top));
        callBack.disableDrawer();

        new Handler().postDelayed(() -> gotoMainFrg(), 2000);
    }

    private void gotoMainFrg() {
        callBack.showFragment(M001MainFrg.TAG, null, false);
    }

    @Override
    protected FragmentM000SplashBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentM000SplashBinding.inflate(inflater, container, false);
    }
}
