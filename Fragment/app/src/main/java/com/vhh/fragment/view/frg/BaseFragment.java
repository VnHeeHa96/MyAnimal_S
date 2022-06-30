package com.vhh.fragment.view.frg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.vhh.fragment.OnMainCallBack;
import com.vhh.fragment.viewmodel.BaseVM;

public abstract class BaseFragment<B extends ViewBinding, M extends BaseVM>
        extends Fragment implements View.OnClickListener {
    protected M viewModel;
    protected Context context;
    protected B binding;
    protected OnMainCallBack callBack;
    protected Object mData;

    @Override
    public final void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater, container);
        viewModel = new ViewModelProvider(this).get(initViewModel());
        initViews();
        return binding.getRoot();
    }

    protected abstract Class<M> initViewModel();


    @Override
    public final void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {
        //do nothing
    }

    public final void setCallBack(OnMainCallBack callBack) {
        this.callBack = callBack;
    }

    protected abstract void initViews();


    protected abstract B initViewBinding(@NonNull LayoutInflater inflater,
                                         @Nullable ViewGroup container);

    public void setData(Object data) {
        mData = data;
    }
}
