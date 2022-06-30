package com.vhh.fragment.viewmodel;

import com.vhh.fragment.App;
import com.vhh.fragment.model.Animal;

import java.io.IOException;

public class M001MainVM extends BaseVM {
    public void initData(String typeAnimal)  {
        App.getInstance().getStorage().listAnimal.clear();
        try {
            String[] paths = App.getInstance().getAssets().list(typeAnimal);
            for (String item : paths) {
                String name = item.replace(".png", "");
                Animal animal = new Animal("sound/" + typeAnimal + "/" + name + ".mp3",
                        typeAnimal + "/" + item, name);
                App.getInstance().getStorage().listAnimal.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
