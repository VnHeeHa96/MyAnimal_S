package com.vhh.fragment.view.act;

import android.app.AlertDialog;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.vhh.fragment.R;
import com.vhh.fragment.databinding.ActivityMain1Binding;
import com.vhh.fragment.view.frg.M000SplashFrg;
import com.vhh.fragment.view.frg.M001MainFrg;
import com.vhh.fragment.viewmodel.CommonVM;

public class MainActivity extends BaseAct<ActivityMain1Binding, CommonVM> {
    public static final String TYPE_MAMMAL = "mammal";
    public static final String TYPE_SEA = "sea";
    public static final String TYPE_BIRD = "bird";
    private static final String TAG = M000SplashFrg.class.getName();

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    protected void initViews() {
        binding.menu.frMammal.setOnClickListener(this);
        binding.menu.frBird.setOnClickListener(this);
        binding.menu.frSea.setOnClickListener(this);
        showFragment(M000SplashFrg.TAG, null, false);
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.fr_mammal) {
            showListAnimal(TYPE_MAMMAL);
        } else if (v.getId() == R.id.fr_bird) {
            showListAnimal(TYPE_BIRD);
        } else if (v.getId() == R.id.fr_sea) {
            showListAnimal(TYPE_SEA);
        }
    }

    private void showListAnimal(String typeAnimal) {
        Fragment frg = getSupportFragmentManager().findFragmentByTag(M001MainFrg.TAG);
        if (frg == null) return;
        ((M001MainFrg) frg).showListAnimal(typeAnimal);
        binding.lnDrawer.closeDrawer(GravityCompat.START);
    }


    @Override
    protected ActivityMain1Binding initViewBinding() {
        return ActivityMain1Binding.inflate(getLayoutInflater());
    }

    @Override
    public void backToPrevious() {
    }

    @Override
    public void disableDrawer() {
        binding.lnDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void enableDrawer() {
        binding.actionbar.ivMenu.setOnClickListener(v -> binding.lnDrawer.openDrawer(GravityCompat.START));
        binding.lnDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        binding.actionbar.getRoot().setVisibility(View.VISIBLE);
        binding.actionbar.tvTitleActionbar.setText(R.string.app_name);
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_menu);
    }

    @Override
    public void makeBackArrow(String typeAnimal) {
        disableDrawer();
        binding.actionbar.getRoot().setVisibility(View.VISIBLE);
        binding.actionbar.ivMenu.setImageResource(R.drawable.ic_back);
        binding.actionbar.tvTitleActionbar.setText(typeAnimal);
        binding.actionbar.ivMenu.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        if (binding.lnDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.lnDrawer.closeDrawer(GravityCompat.START);
            return;
        }

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            askForExitApp();
            return;
        }
        super.onBackPressed();
    }

    private void askForExitApp() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Alert");
        alert.setMessage("Close this App?");
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Close", (dialogInterface, i) -> super.onBackPressed());
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Don't", (dialogInterface, i) -> {
        });
        alert.show();
    }

}