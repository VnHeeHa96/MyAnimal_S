package com.vhh.fragment.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vhh.fragment.R;
import com.vhh.fragment.model.Animal;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private final Context context;
    private final List<Animal> animallist;
    private final View.OnClickListener event;

    public AnimalAdapter(Context context, List<Animal> animallist, View.OnClickListener event) {
        this.context = context;
        this.animallist = animallist;
        this.event = event;
    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);

        return new AnimalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
        Animal item = animallist.get(position);
        Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + item.getIdPhoto()))
                .into(holder.ivAnimal);
        holder.ivAnimal.setTag(item);
    }

    @Override
    public int getItemCount() {
        return animallist.size();
    }


    public class AnimalHolder extends RecyclerView.ViewHolder {
        ImageView ivAnimal;

        public AnimalHolder(@NonNull View itemView) {
            super(itemView);
            ivAnimal = itemView.findViewById(R.id.iv_animal);
            ivAnimal.setOnClickListener(view -> {
                view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
                event.onClick(view);
            });
        }
    }
}
