package com.vhh.fragment.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.vhh.fragment.R;
import com.vhh.fragment.databinding.ViewDetailInfoBinding;
import com.vhh.fragment.model.Animal;

public class DetailInfoDialog extends Dialog implements View.OnClickListener {
    private final ViewDetailInfoBinding binding;
    private final Animal animal;
    private final Context context;

    public DetailInfoDialog(@NonNull Context context, Animal animal) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        this.animal = animal;
        binding = ViewDetailInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        binding.ivBack1.setOnClickListener(this);
        binding.tvTitle1.setText(animal.getName());
        binding.webInfo.getSettings().setJavaScriptEnabled(true);
        binding.webInfo.getSettings().setAllowContentAccess(true);
        binding.webInfo.getSettings().setBuiltInZoomControls(true);
        binding.webInfo.getSettings().setAllowContentAccess(true);
        binding.webInfo.getSettings().setSupportZoom(true);
        binding.webInfo.setWebChromeClient(new WebChromeClient());
        binding.webInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        binding.webInfo.loadUrl("http://en.m.wikipedia.org/wiki/" + animal.getName());
    }

    @Override
    public void dismiss() {
        if(!binding.webInfo.canGoBack()){
            super.dismiss();
            return;
        }
        binding.webInfo.goBack();
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        if (view.getId() == R.id.iv_back1) {
            dismiss();
        }
    }
}
