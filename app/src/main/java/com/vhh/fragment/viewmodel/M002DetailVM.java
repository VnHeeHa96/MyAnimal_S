package com.vhh.fragment.viewmodel;

import android.os.Environment;
import android.util.Log;

import com.vhh.fragment.App;
import com.vhh.fragment.model.Animal;

import java.io.FileOutputStream;
import java.io.InputStream;

public class M002DetailVM extends BaseVM {

    private static final String TAG = M002DetailVM.class.getName();

    public Object copyPhotoStorage(Animal animal) {
        try {
            InputStream in = App.getInstance().getAssets().open(animal.getIdPhoto());
            byte[] buff = new byte[1024];
            //String uotPath = App.getInstance().getExternalFilesDir(null).getPath();
            String uotPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
            // String uotPath = Environment.getDataDirectory().getPath() + "/data/" + App.getInstance().getPackageName();
            FileOutputStream uot = new FileOutputStream(uotPath + "/" + animal.getName() + ".png");

            int len = in.read(buff);
            while (len > 0) {
                uot.write(buff, 0, len);
                len = in.read(buff);
            }
            uot.close();
            in.close();
            Log.i(TAG, "photo is saved");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return true;
    }
}
