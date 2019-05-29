package com.project.dyuapp.myutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class BitmapUtil {

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitamp(Drawable drawable) {
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(50, 50,config);

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}

		if (drawable.getIntrinsicWidth() != 0 & drawable.getIntrinsicHeight() != 0) {
			int w = drawable.getIntrinsicWidth();
			int h = drawable.getIntrinsicHeight();

			// 取 drawable 的颜色格式

			try {
				bitmap = Bitmap.createBitmap(w, h, config);
			} catch (Exception e) {
				Log.e("OutOfMemoryError",e.toString());
			}

			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, w, h);
			drawable.draw(canvas);
			return bitmap;
		}

		return bitmap;
	}
}
