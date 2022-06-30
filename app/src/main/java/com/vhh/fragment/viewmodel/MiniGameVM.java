package com.vhh.fragment.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.vhh.fragment.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameVM extends BaseVM {
    private final List<Animal> animalList = new ArrayList<>();
    private int index = 0;
    private Animal animal;
    private MutableLiveData<Integer> score= new MutableLiveData<>(0);

    public void initAnimalList(List<Animal> data) {
        animalList.clear();
       animalList.addAll(data);
        Collections.shuffle(this.animalList);

    }

    public void initScore(int data) {
        score.setValue(data);
    }

    public  MutableLiveData<Integer> getScore() {
        return score ;
    }

    public String[] initCard() {
        animal = animalList.get(index);
        List<Animal> tmpList = new ArrayList<>(animalList);
        tmpList.remove(animal);
        Collections.shuffle(tmpList);
        Collections.shuffle(animalList);
        String txtA;
        String txtB;
        if (new Random().nextBoolean()) {
            txtA = "A: " + animal.getName();
            txtB = "B: " + tmpList.get(0).getName();
        } else {
            txtA = "B: " + animal.getName();
            txtB = "A: " + tmpList.get(0).getName();
        }
        return new String[]{txtA, txtB};
    }

    @SuppressLint("SetTextI18n")
    public boolean checkAnswer(String ans) {
        if (ans.replace("A: ", "")
                .replace("B: ", "")
                .equals(animal.getName())) {
            score.setValue(score.getValue()+1);
            index++;
            if (index >= animalList.size()) {
                index = 0;
            }
            return true;
        }
        return false;
    }

    public Animal geAnimal() {
        return animal;
    }
}
