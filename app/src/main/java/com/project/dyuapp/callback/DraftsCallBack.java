package com.project.dyuapp.callback;

/**
 * @describtion  草稿箱
 * @author gengqiufang
 * @created at 2017/11/30 0030
 */

public interface DraftsCallBack {
    void onClickDel(int position);

    void onClickRepeat(int position);

    void onClickChange(int position);
}
