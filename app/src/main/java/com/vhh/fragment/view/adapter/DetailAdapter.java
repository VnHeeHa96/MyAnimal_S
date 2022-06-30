package com.vhh.fragment.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.vhh.fragment.R;
import com.vhh.fragment.model.Animal;

import java.util.List;

public class DetailAdapter extends PagerAdapter {
    private final Context context;
    private final List<Animal> animalList;
    private final View.OnClickListener event;

    public DetailAdapter(Context context, List<Animal> animalList, View.OnClickListener event) {
        this.context = context;
        this.animalList = animalList;
        this.event = event;
    }

    @Override
    public int getCount() {
        return animalList.size();
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Animal item = animalList.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_animal, container, false);
        ImageView ivAnimal = view.findViewById(R.id.iv_animal);
        ImageView ivDownload = view.findViewById(R.id.iv_download);
        ImageView ivStart = view.findViewById(R.id.iv_start);
        ImageView ivSearch = view.findViewById(R.id.iv_search);

        Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + item.getIdPhoto()))
                .into(ivAnimal);
        View.OnClickListener onClick = view1 -> {
            view1.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            event.onClick(view1);
        };

        ivStart.setTag(item);
        ivSearch.setTag(item);
        ivDownload.setTag(item);
        ivStart.setOnClickListener(onClick);
        ivDownload.setOnClickListener(onClick);
        ivSearch.setOnClickListener(onClick);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
