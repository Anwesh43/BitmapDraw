package com.example.bitmapdraw;

import android.os.Bundle;
import android.graphics.*;
import android.widget.*;
import android.view.*;
import android.graphics.Bitmap;
import android.app.Activity;
import android.os.*;
import android.view.Menu;
import android.content.pm.*;
import java.util.*;
public class DrawArr extends Activity {
	ImageView im1;
	Canvas canvas;
	Rundo r;
	Thread t1;
	boolean isr=true;
	Handler handle=new Handler();
	int j=0;
	float x[]=new float[1000],y[]=new float[1000],t[]=new float[1000],k[]=new float[1000];
	Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
	Bitmap b;
	Button b1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_arr);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		im1=(ImageView)findViewById(R.id.imageView1);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v)
			{
				Random random=new Random();
				x[j]=random.nextInt(240);
				y[j]=random.nextInt(300);
				t[j]=0;
				k[j]=10;
				j++;
			}
		});
		b=Bitmap.createBitmap(240,300,Bitmap.Config.ARGB_8888);
		canvas=new Canvas(b);
		
		r=new Rundo();
		t1=new Thread(r);
		t1.start();
	}
	public void onPause()
	{
		super.onPause();
		isr=false;
		while(true)
		{
			try
			{
				t1.join();
				break;
			}
			catch(Exception ex)
			{
				
			}
		}
	}
	public void onResume()
	{
		super.onResume();
		isr=true;
		t1=new Thread(r);
		t1.start();
	}
	class Rundo implements Runnable
	{
		public void run()
		{
			while(isr)
			{
				canvas.drawColor(Color.WHITE);
				p.setColor(Color.GREEN);
				for(int i=0;i<j;i++)
				{
				canvas.save();
				canvas.translate(x[i],y[i]);
				canvas.rotate(t[i]);
				canvas.drawRect(new RectF(-50,-50,50,50),p);
				canvas.restore();
				t[i]+=k[i];
				}
				handle.post(new Runnable(){
					public void run()
					{
						im1.setImageBitmap(b);
					}
				});
				
				try
				{
					Thread.sleep(100);
				}
				catch(Exception ex)
				{
					
				}
			}
		}
	
	}
}
