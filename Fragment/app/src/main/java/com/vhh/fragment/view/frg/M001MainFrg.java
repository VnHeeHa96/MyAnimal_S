package com.vhh.fragment.view.frg;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vhh.fragment.App;
import com.vhh.fragment.R;
import com.vhh.fragment.databinding.FragmentM001MainBinding;
import com.vhh.fragment.model.Animal;
import com.vhh.fragment.view.act.MainActivity;
import com.vhh.fragment.view.adapter.AnimalAdapter;
import com.vhh.fragment.view.dialog.MiniGameDialog;
import com.vhh.fragment.viewmodel.M001MainVM;

import java.util.Locale;

public class M001MainFrg extends BaseFragment<FragmentM001MainBinding, M001MainVM> {
    public static final String TAG = M001MainFrg.class.getName();
    private TextToSpeech tts;

    @Override
    protected void initViews() {
        callBack.enableDrawer();
        initData(MainActivity.TYPE_MAMMAL);
        initListAnimal();
        binding.tvGame.setOnClickListener(this);
        tts = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.UK);
            }
        });
    }

    private void initData(String typeAnimal) {
        binding.tvTitle.setText(typeAnimal.toUpperCase());
        viewModel.initData(typeAnimal);
    }


    private void initListAnimal() {
        binding.rvAnimal.setAdapter(new AnimalAdapter(context, App.getInstance().getStorage().listAnimal, this::clickView));
    }

    @Override
    protected Class<M001MainVM> initViewModel() {
        return M001MainVM.class;
    }

    @Override
    protected void clickView(View view) {
        if (view.getId() == R.id.tv_game) {
            showMiniGame();
            return;
        }
        Animal tag = (Animal) view.getTag();
        GotoDetailScreen(tag);
    }

    private void showMiniGame() {
        MiniGameDialog miniGame = new MiniGameDialog(context,
                this,
                App.getInstance().getStorage().listAnimal);
        miniGame.show();
    }

    private void GotoDetailScreen(Animal animal) {
        tts.speak(animal.getName(), TextToSpeech.QUEUE_FLUSH, null);
        App.getInstance().getStorage().typeAnimal = binding.tvTitle.getText().toString();
        callBack.showFragment(M002DetailFrg.TAG, animal, true);
    }



    @Override
    protected FragmentM001MainBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentM001MainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void showListAnimal(String typeAnimal) {
        initData(typeAnimal);
        initListAnimal();
    }
}
