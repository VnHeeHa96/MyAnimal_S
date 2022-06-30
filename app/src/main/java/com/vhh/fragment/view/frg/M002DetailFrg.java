package com.vhh.fragment.view.frg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.vhh.fragment.App;
import com.vhh.fragment.MTask;
import com.vhh.fragment.R;
import com.vhh.fragment.databinding.FragmentM002DetailBinding;
import com.vhh.fragment.model.Animal;
import com.vhh.fragment.view.adapter.DetailAdapter;
import com.vhh.fragment.view.dialog.DetailInfoDialog;
import com.vhh.fragment.viewmodel.M002DetailVM;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class M002DetailFrg extends BaseFragment<FragmentM002DetailBinding, M002DetailVM> {
    public static final String TAG = M002DetailFrg.class.getName();

    @Override
    protected void initViews() {
        Animal animal = (Animal) mData;
        binding.tvTitle.setText(animal.getName());
        callBack.makeBackArrow(App.getInstance().getStorage().typeAnimal);
        binding.vpAnimal.setAdapter(new DetailAdapter(context, App.getInstance().getStorage().listAnimal, this::clickView));
        binding.vpAnimal.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Animal animal = App.getInstance().getStorage().listAnimal.get(position);
                binding.tvTitle.setText(animal.getName());
            }
        });
        binding.vpAnimal.setCurrentItem(App.getInstance().getStorage().listAnimal.indexOf(animal));

    }

    @Override
    protected Class<M002DetailVM> initViewModel() {
        return M002DetailVM.class;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_download:
                saveToStorage((Animal) view.getTag());
                break;
            case R.id.iv_start:
                playSound((Animal) view.getTag());
                break;
            case R.id.iv_search:
                //  searchImage(getListAnimal().get(index).getName());
                showInfoDiaLog((Animal) view.getTag());
                break;
        }
    }

    @SuppressLint("NewApi")
    private void saveToStorage(Animal animal) {
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }
        new MTask("KEY_SAVE_PHOTO", new MTask.OnMTaskCallBack() {
            @Override
            public Object execTask(String key, MTask task, Object data) {
                return viewModel.copyPhotoStorage(animal);
            }
            @Override
            public void completeTask(String key, Object value) {
                if (value == null) {
                    Toast.makeText(context, "Could not save this photo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Photo is save", Toast.LENGTH_SHORT).show();
                }
            }
        }).startAsync(null);
    }


    private void playSound(Animal animal) {
        try {
            AssetFileDescriptor afd = App.getInstance().getAssets().openFd(animal.getIdSound());
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showInfoDiaLog(Animal animal) {
        DetailInfoDialog dialog = new DetailInfoDialog(context, animal);
        dialog.show();
    }

    private void searchImage(String name) {
        try {
            String word = URLEncoder.encode(name, "UTF-8");
            Uri uri = Uri.parse("http://www.google.com/search?hl=en&q=" + word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected FragmentM002DetailBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentM002DetailBinding.inflate(inflater, container, false);
    }


}
