package com.vhh.fragment.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.vhh.fragment.CommonUtils;
import com.vhh.fragment.R;
import com.vhh.fragment.databinding.ViewMiniGameBinding;
import com.vhh.fragment.model.Animal;
import com.vhh.fragment.viewmodel.MiniGameVM;

import java.util.List;

public class MiniGameDialog extends Dialog implements View.OnClickListener {
    private static final String KEY_SCORE = "KEY_SCORE";

    private final Context context;
    private final ViewMiniGameBinding binding;
    private final MiniGameVM viewModel;


    public MiniGameDialog(@NonNull Context context, ViewModelStoreOwner owner, List<Animal> animalList) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        viewModel = new ViewModelProvider(owner).get(MiniGameVM.class);
        viewModel.initAnimalList(animalList);
        binding = ViewMiniGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews(owner);
    }

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    private void initViews(ViewModelStoreOwner owner) {
        binding.ivCard.setOnClickListener(this);
        binding.tvCard.setOnClickListener(this);
        binding.ivBack2.setOnClickListener(this);
        binding.tvA.setOnClickListener(this);
        binding.tvB.setOnClickListener(this);
        initCard();
        //recover score value from sharepreference
        String txtScore = CommonUtils.getInstance().getPref(KEY_SCORE);
        if (txtScore != null) {
//            score = Integer.parseInt(txtScore);
            viewModel.initScore(Integer.parseInt(txtScore));
        }
        viewModel.getScore().observe((LifecycleOwner) owner, score -> {
            binding.tvScore.setText(String.format("Score :%s", score));
            CommonUtils.getInstance().savePref(KEY_SCORE, score + "");

        });
    }

    @SuppressLint("SetTextI18n")
    private void initCard() {
        viewModel.initCard();
        String[] txtArr = viewModel.initCard();
        binding.tvB.setText(txtArr[0]);
        binding.tvA.setText(txtArr[1]);

        int lenA = txtArr[0].length();
        int lenB = txtArr[1].length();
        String max = lenA > lenB ? txtArr[0] : txtArr[1];

        Rect bounds = new Rect();
        Paint textPaint = binding.tvA.getPaint();
        textPaint.getTextBounds(max, 0, max.length(), bounds);
        int width = bounds.width();
        binding.tvA.setWidth(width + 150);
        binding.tvB.setWidth(width + 150);
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        if (view.getId() == R.id.iv_back2) {
            dismiss();
        } else if (view.getId() == R.id.iv_card || view.getId() == R.id.tv_card) {
            binding.frCard.startAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
            showCardAnimal();
        } else if (view.getId() == R.id.tv_a || view.getId() == R.id.tv_b) {
            checkAnswer(((TextView) view).getText().toString());
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer(String ans) {
        boolean rs = viewModel.checkAnswer(ans);
        if (rs) {
            initCard();
        } else {
            Toast.makeText(context, "Wrong answer:(", Toast.LENGTH_SHORT).show();
        }
    }



    private void showCardAnimal() {
        Glide.with(context).load(Uri.parse("file:///android_asset/" + viewModel.geAnimal().getIdPhoto())).into(binding.ivAnimal);
        binding.ivAnimal.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.ivAnimal.setVisibility(View.GONE);
            }
        }, 1000);
    }
}
