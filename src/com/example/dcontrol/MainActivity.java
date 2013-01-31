package com.example.dcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button mButtonFastP;
	Button mButtonFastS;
	Button mButtonJinjiP;
	Button mButtonJinjiS;
	Button mButtonXp;
	Button mButtonXs;
	Button mButtonYp;
	Button mButtonYs;
	Button mButtonZp;
	Button mButtonZs;
	Button mButtonSize;
	Button mButtonKapan;
	TextView textFast;
	TextView textJinji;
	TextView textX;
	TextView textY;
	TextView textZ;
	TextView textSize;
	TextView textCool;
	TextView textSpeed;
	TextView textKnife;
	TextView textMainArbor;
	TextView textKapan;
	double numX;
	double numY;
	double numZ;
	double numSize;
	int numFast;
	int numJinji;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        numFast=100;
        numJinji=80;
        numSize=1.000;
        this.mButtonFastP= ((Button)findViewById(R.id.buttonFastP));//快速倍率+
        this.mButtonFastS= ((Button)findViewById(R.id.buttonFastS));//快速倍率-
        this.mButtonJinjiP=((Button)findViewById(R.id.buttonJinjiP));//进给+
        this.mButtonJinjiS=((Button)findViewById(R.id.buttonJinjiS));//进给-
        mButtonSize=((Button)findViewById(R.id.buttonSize));
       
        mButtonXp=((Button)findViewById(R.id.buttonXp));
        mButtonXs=((Button)findViewById(R.id.buttonXs));
        mButtonYp=((Button)findViewById(R.id.buttonYp));
        mButtonYs=((Button)findViewById(R.id.buttonYs));
        mButtonZp=((Button)findViewById(R.id.buttonZp));
        mButtonZs=((Button)findViewById(R.id.buttonZs));
        mButtonKapan=((Button)findViewById(R.id.buttonKapan));      
        
        
        this.textFast=((TextView)findViewById(R.id.textViewFast));
        this.textJinji=((TextView)findViewById(R.id.textViewJinji));
        textX=((TextView)findViewById(R.id.textViewX));
        textY=((TextView)findViewById(R.id.textViewY));
        textZ=((TextView)findViewById(R.id.textViewZ));
        textSize=((TextView)findViewById(R.id.textViewSize));
        
        textCool=((TextView)findViewById(R.id.textViewCool));
    	textSpeed=((TextView)findViewById(R.id.textViewSpeed));
    	textKnife=((TextView)findViewById(R.id.textViewKnife));
    	textMainArbor=((TextView)findViewById(R.id.textViewMainArbor));
    	textKapan=((TextView)findViewById(R.id.textViewKapan));
        
        
        
        
        
        
        
        
        this.textFast.setText(numFast+"%");
        this.textJinji.setText(numJinji+"%");
        textSize.setText(String.format("%1.3f",numSize));
        
        textFast.setTextColor(Color.GREEN);
        textJinji.setTextColor(Color.GREEN);
        textX.setTextColor(Color.BLUE);
        textY.setTextColor(Color.BLUE);
        textZ.setTextColor(Color.BLUE);
        textSize.setTextColor(Color.GREEN);
        textKnife.setTextColor(Color.GREEN);
        textCool.setTextColor(Color.GREEN);
        textSpeed.setTextColor(Color.GREEN);
        textKapan.setTextColor(Color.GREEN);
        textMainArbor.setTextColor(Color.GREEN);
        
        
        
        mButtonFastP.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					if(numFast<100)
						numFast+=25;
					textFast.setText(numFast+"%");
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        mButtonFastS.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					if(numFast>25)
						numFast-=25;
					textFast.setText(numFast+"%");
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        
        mButtonJinjiP.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					if(numJinji<150)
						numJinji+=10;
					textJinji.setText(numJinji+"%");
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        mButtonJinjiS.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					if(numJinji>10)
						numJinji-=10;
					textJinji.setText(numJinji+"%");
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        mButtonSize.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					if(numSize==0.001)
						numSize=10;
					numSize=numSize/10;
					textSize.setText(String.format("%1.3f",numSize));
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        mButtonXp.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numX+=numSize;
					textX.setText(String.format("%4.3f",numX));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        mButtonXs.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numX-=numSize;
					textX.setText(String.format("%4.3f",numX));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        
        mButtonYp.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numY+=numSize;
					textY.setText(String.format("%4.3f",numY));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        mButtonYs.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numY-=numSize;
					textY.setText(String.format("%4.3f",numY));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        mButtonZp.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numZ+=numSize;
					textZ.setText(String.format("%4.3f",numZ));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        mButtonZs.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{
					
					numZ-=numSize;
					textZ.setText(String.format("%4.3f",numZ));
					
				}
				
				
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
