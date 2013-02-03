package com.example.dcontrol;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class DCAutom extends Activity{
	
	Button mButtonMode; 
	TextView textX;
	TextView textY;
	TextView textZ;
	TextView textFast;
	TextView textJinji;
	TextView textCool;
	TextView textSpeed;
	TextView textKnife;
	TextView textMainArbor;
	TextView textKapan;
	TextView textDelay;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
	        setContentView(R.layout.dcautom);
	        
	        mButtonMode=((Button)findViewById(R.id.btnMode));
	        textX=((TextView)findViewById(R.id.textViewX));
	        textY=((TextView)findViewById(R.id.textViewY));
	        textZ=((TextView)findViewById(R.id.textViewZ));
	        textFast=((TextView)findViewById(R.id.textViewFast));
	        textJinji=((TextView)findViewById(R.id.textViewJinji));
	        textCool=((TextView)findViewById(R.id.textViewCool));
	    	textSpeed=((TextView)findViewById(R.id.textViewSpeed));
	    	textKnife=((TextView)findViewById(R.id.textViewKnife));
	    	textMainArbor=((TextView)findViewById(R.id.textViewMainArbor));
	    	textKapan=((TextView)findViewById(R.id.textViewKapan));
	    	textDelay=((TextView)findViewById(R.id.textViewDelay));
	    	
	    	
	        textX.setTextColor(Color.BLUE);
	        textY.setTextColor(Color.BLUE);
	        textZ.setTextColor(Color.BLUE);
	        textFast.setTextColor(Color.GREEN);
	        textJinji.setTextColor(Color.GREEN);
	        textKnife.setTextColor(Color.GREEN);
	        textCool.setTextColor(Color.GREEN);
	        textSpeed.setTextColor(Color.GREEN);
	        textKapan.setTextColor(Color.GREEN);
	        textMainArbor.setTextColor(Color.GREEN);
	        textDelay.setTextColor(Color.GREEN);
	        
	        mButtonMode.setOnTouchListener(new View.OnTouchListener()
	        {

				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction())
					{
					case MotionEvent.ACTION_UP:
					{
						
						//Intent enabler = new Intent(MainActivity.this, DCAutom.class);
						Intent enabler = new Intent(DCAutom.this, MainActivity.class);
						startActivity(enabler);
						break;
					}
					
					
					}
				
					// TODO Auto-generated method stub
					return false;
				
	        	
	        
	              }
	        });
	 }

}
