package com.project.dyuapp.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;


/**
 * 作者 : 刘晓丽
 * 创建时间：2017.4.2
 * 类说明 ：公用viewholder
 */
public class MyCommentViewHolder {
	private View mConvertview;
	private Context mContext;
	private int resid;

	public MyCommentViewHolder(Context mContext, int resid) {
		this.mContext = mContext;
		this.resid = resid;
		mConvertview = LayoutInflater.from(mContext).inflate(resid, null);
		AutoUtils.autoSize(mConvertview);
		mConvertview.setTag(this);
	}

	public View getConverView() {
		return mConvertview;
	}

	public static MyCommentViewHolder getViewHolder(View convertview, Context mContext, int resid) {
		MyCommentViewHolder commentViewHolder;
		if (convertview == null) {
			commentViewHolder = new MyCommentViewHolder(mContext, resid);
		} else {
			commentViewHolder = (MyCommentViewHolder) convertview.getTag();
		}
		return commentViewHolder;

	}

	public View FindViewById(int id) {
		return mConvertview.findViewById(id);
	}
}

