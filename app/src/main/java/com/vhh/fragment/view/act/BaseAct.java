package com.vhh.fragment.view.act;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.vhh.fragment.OnMainCallBack;
import com.vhh.fragment.R;
import com.vhh.fragment.view.frg.BaseFragment;
import com.vhh.fragment.viewmodel.BaseVM;

import java.lang.reflect.Constructor;

public abstract class BaseAct<T extends ViewBinding,M extends BaseVM>
        extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {
    protected T binding;
    protected M viewModel;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        viewModel  = new ViewModelProvider(this).get(initViewModel());
        initViews();
    }

    protected abstract Class<M> initViewModel();

    protected abstract void initViews();

    protected abstract T initViewBinding();


    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    private void clickView(View view) {
        //do nothing
    }

    protected final void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected final void notify(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFragment(String tag, Object data, boolean isBack) {
        try {
            Class<?> clazz = Class.forName(tag); //Trỏ vào 1 fragment class
            Constructor<?> cons = clazz.getConstructor();
            BaseFragment<?,?> frg = (BaseFragment<?,?>) cons.newInstance();//tạo ra 1 thực thể từ lớp fragment
            frg.setData(data);
            frg.setCallBack(this);

            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (isBack) {
                trans.addToBackStack(null);//g0 back to previous screen
            }
            trans.replace(R.id.ln_main, frg, tag).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
