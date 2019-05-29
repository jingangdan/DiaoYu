package com.project.dyuapp.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


/**
 * @describe： 公用adapter
 * @author：刘晓丽
 * @createdate：2017/4/2 9:05
 */

public abstract class MyCommonAdapter<T> extends BaseAdapter {
	protected List<T> list;
	protected Context mContext;
	protected int resid;

	public MyCommonAdapter(List<T> list, Context mContext, int resid) {
		this.list = list;
		this.mContext = mContext;
		this.resid = resid;
	}
	
	@Override
	public int getCount() {
		if (list != null && list.size()>0) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if(list!=null && list.size()>0){
			return list.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		MyCommentViewHolder commentViewHolder = MyCommentViewHolder.getViewHolder(
				arg1, mContext, resid);
		setDate(commentViewHolder, arg0);
		return commentViewHolder.getConverView();
	}

	public abstract void setDate(MyCommentViewHolder commentViewHolder, int position);



}
