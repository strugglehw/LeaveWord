package com.atao.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class ImageViews extends EditText {
	public ImageViews(Context context) {
		super(context);
	}

	public ImageViews(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageViews(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void insertIntoEdit(int id) {
		Log.d("wljie", "===");
		SpannableStringBuilder builder = new SpannableStringBuilder(getText()
				.toString());
		Drawable drawable = getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
				.getIntrinsicHeight());
		ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
		builder.setSpan(imageSpan, getText().length(), getText().length()
				+ "[simle]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		setText(builder);
	}

}
