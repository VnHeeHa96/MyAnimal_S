package com.vhh.fragment;

public interface OnMainCallBack {
    void showFragment(String tag, Object data, boolean isBack);
    void backToPrevious();

    void disableDrawer();

    void enableDrawer();

    void makeBackArrow(String typeAnimal);
}
