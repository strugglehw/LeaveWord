package com.atao.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener {
	private static final int SELECT_IMAGE = 0x123;
	private EditText content;
	private Button insertImg;
	private Button confirm;
	ImageGetter imageGetter;
	Map<Integer, String> imgPos;
	String con;
	StringBuilder cont = new StringBuilder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		content = (EditText) findViewById(R.id.content);
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
		insertImg = (Button) findViewById(R.id.insertImage);
		insertImg.setOnClickListener(this);

		// answers.
		imgPos = new HashMap<Integer, String>();
		imageGetter = new ImageGetter() {

			@Override
			public Drawable getDrawable(String source) {
				Drawable d = Drawable.createFromPath(source);
				d.setBounds(0, 0, 30, 30);
				return d;
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.insertImage:
			Intent getImage = new Intent(Intent.ACTION_PICK,
					MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(getImage, SELECT_IMAGE);
			break;
		case R.id.confirm:
			Intent intent = new Intent(this, ShowContent.class);
			String[] paths = new String[imgPos.size()];
			int[] positions = new int[imgPos.size()];
			int i = 0;
			for (Entry<Integer, String> ent : imgPos.entrySet()) {
				if (i == imgPos.size())
					break;
				positions[i] = ent.getKey();
				paths[i] = ent.getValue();
				i++;
			}

			intent.putExtra("positions", positions);
			intent.putExtra("paths", paths);
			Editable con = content.getText();
			intent.putExtra("content", con.toString());
			startActivity(intent);
			break;

		}
	}

	int id = 0;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_IMAGE:
				String path = getpath(data.getData());
				
				Drawable drawable = Drawable.createFromPath(path);
				drawable.setBounds(0, 0, 20, 20);
				
				SpannableString spannable = new SpannableString("[file"+id+"]");
				id++;
				ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
				spannable.setSpan(span, 0,"[file]".length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				int select=content.getSelectionStart();
				System.out.println("select:"+select);
				
				Editable edit = content.getEditableText();
				edit.insert(select, spannable);
				
				content.requestFocus();
				con = content.getText().toString();
				System.out.println("con:"+con);
				imgPos.put(con.length(), path);
				break;
			}

		}
	}

	String getpath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// ¼ÇÂ¼Í¼Æ¬µÄÎ»ÖÃ
		cursor.moveToNext();
		return cursor.getString(column_index);
	}

	int poi = 0;

	public void setImage(String source) {
		Drawable drawable = Drawable.createFromPath(source);
		drawable.setBounds(0, 0, 20, 20);

		SpannableString spannable = new SpannableString(content.getText()
				+ "[file" + poi + "]");

		ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

		spannable.setSpan(span, content.getText().length(), content.getText()
				.length()
				+ ("[file" + poi + "]").length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		poi++;
		content.setText(spannable);
	}

//	edit
//	Html.fromHtml(source, imageGetter, tagHandler)
//	Html.
//	content.onCreateInputConnection(outAttrs)
/*				Spanned span=Html.fromHtml(path, imageGetter, null);
	SpannableStringBuilder spanBuilder=new SpannableStringBuilder("[file]"); 
	spanBuilder.setSpan(span, 0, "[file]".length(), 0);*/
//	content.append(spannable);
	// setImage(path);
}
