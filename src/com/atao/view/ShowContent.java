package com.atao.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.widget.TextView;

public class ShowContent extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv=new TextView(this);
		setContentView(tv);
		Intent intent=getIntent();
		List<Spanned> list=new ArrayList<Spanned >();
		ImageGetter imageGetter = new ImageGetter() {
			public Drawable getDrawable(String source) {
				Drawable d = Drawable.createFromPath(source);
				d.setBounds(0, 0, 50, 50);
				return d;
			}
		};
		String[] paths=intent.getStringArrayExtra("paths");
		System.out.println("paths is null?"+(paths==null));
		
		int[] positions=intent.getIntArrayExtra("positions");
		System.out.println("positions is null?"+(positions==null));
		String con=intent.getStringExtra("content");
		for(String p:paths){
			list.add(Html.fromHtml("<img src='" + p + "'/>",
					imageGetter, null));
		}
		int j=0;
		int start=0;
		for(int i:positions ){
			System.out.println("positions"+i);
			if(j==list.size())break;
			String str=con.substring(start, i);
			start=i+1;
			tv.append(str);
			tv.append(list.get(j));
			j++;
		}
	}
}
