package com.example.dcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.os.Handler;








public class MainActivity<readThread> extends Activity {
	CodecUtil Mcrc16=new CodecUtil();
	
	//byte[] test = new byte[] {0, 1, 2, 3, 4};
    //byte[] crc = Mcrc16.crc16Bytes(test);
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address = "00:12:10:11:12:62";
	private readThread mreadThread = null;//读取数据线程
	public BluetoothAdapter mBluetoothAdapter = null;
	public BluetoothSocket btSocket = null;
	public OutputStream outStream = null;
	public InputStream mmInStream= null;
	Runnable runnable;
	final Handler handler = new Handler();
	
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
	Button mButtonMode;
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
	boolean kapan;
	
	 public void DisplayToast(String paramString)
	  {
	    Toast localToast = Toast.makeText(this, paramString, 1);
	    localToast.setGravity(48, 0, 220);
	    localToast.show();
	  }
	 public int getUnsignedByte (short data){return data&0x0FFFF;}  //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。

	//帧相关
	byte Head[]={(byte) 0xff,0x41,0x4f,0x47,0x45,(byte) 0xfe};//帧头 
	byte End[]={(byte) 0xfe,0x45,0x47,0x4f,0x41,(byte) 0xff};//帧尾
	short No=0;// public short getUnsignedByte (short data){return data&0x0FFFF;}  //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
	byte Order;
	byte KeyValue[]=new byte[4];
//	short CRCNum=0x0000;
	byte[] CRCNum=new byte[2];
	byte CrcTeam1[]=new byte[7];
	byte CrcTeam2[]=new byte[19];
	byte KeyStyleOn=0x02;
    byte KeyStyleUp=0x03;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     
     
        
        
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mBluetoothAdapter == null)
        {
          Toast.makeText(this, "蓝牙设备不可用，请打开蓝牙！", Toast.LENGTH_LONG).show();
          finish();
          return;
        }
       if (!mBluetoothAdapter.isEnabled()) {
        	Toast.makeText(this,  "请打开蓝牙并重新运行程序！", Toast.LENGTH_LONG).show();
        	finish();
		//	mBluetoothAdapter.enable();
			return;
        }
        
        
        
        kapan=true;
        numFast=100;
        numJinji=80;
        numSize=1.000;
        this.mButtonFastP= ((Button)findViewById(R.id.buttonFastP));//快速倍率+
        this.mButtonFastS= ((Button)findViewById(R.id.buttonFastS));//快速倍率-
        this.mButtonJinjiP=((Button)findViewById(R.id.buttonJinjiP));//进给+
        this.mButtonJinjiS=((Button)findViewById(R.id.buttonJinjiS));//进给-
        mButtonSize=((Button)findViewById(R.id.buttonSize));
        mButtonMode=((Button)findViewById(R.id.btnMode));
       
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
       // textSpeed.setTextColor(Color.GREEN);
        textSpeed.setTextColor(Color.CYAN);
        textKapan.setTextColor(Color.GREEN);
       
       // textMainArbor.setTextColor(Color.GREEN);
        textMainArbor.setTextColor(Color.MAGENTA);
       
        
        
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
        
        //步长选择 
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
        
        mButtonKapan.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
				{	
					Order=0x01;
					if(kapan)
					{
					textKapan.setText("紧");
					kapan=!kapan;
					KeyValue[0]=0x1f;
					}else{
						kapan=!kapan;
						textKapan.setText("松");
						KeyValue[0]=0x1e;
					}
				
					KeyValue[1]=0x03;
					KeyValue[2]=0x00;
					KeyValue[3]=0x00;
					
					byte[] arrayOfByte = new byte[21];
					
					System.arraycopy(Head, 0, arrayOfByte, 0, Head.length);//前6位
					No++;
					arrayOfByte[6]=(byte)No;
					arrayOfByte[7]=(byte)(No>>8);
					arrayOfByte[8]=Order;
					System.arraycopy(KeyValue, 0, arrayOfByte, 9,KeyValue.length);//System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length) 
					System.arraycopy(arrayOfByte,6,CrcTeam1,0,7);
					CRCNum=CodecUtil.crc16Bytes(CrcTeam1);
					System.arraycopy(CRCNum, 0, arrayOfByte, 13, CRCNum.length);//CRC校验码
					System.arraycopy(End, 0, arrayOfByte, 15, End.length);//最后6位
					//System.arraycopy(b, 0, c, a.length, b.length);
					arrayOfByte=(byte[])arrayOfByte;
					try
      	            {
      	              MainActivity.this.outStream = MainActivity.this.btSocket.getOutputStream();
      	              try
      	              {
      	                 MainActivity.this.outStream.write(arrayOfByte);
      	              }
      	              catch (IOException localIOException4)
      	              {
      	              }
      	     //         continue;
      	             
      	              
      	            
      	            }
      	            catch (IOException localIOException3)
      	            {
      	    //          break label104;
      	            }

					
					
				}	
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        mButtonMode.setOnTouchListener(new View.OnTouchListener()
        {

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
				case MotionEvent.ACTION_UP:
				{	
					//Intent enabler = new Intent(MainActivity.this, DCAutom.class);
					Intent enabler = new Intent(MainActivity.this, DCAutom.class);
					startActivity(enabler);
					break;
				}		
				}
			
				// TODO Auto-generated method stub
				return false;
			
        	
        
              }
        });
        
        
        //请求主机（Arm机）实时数据
        runnable=new Runnable() { 
       
        	
        	
            public void run() { 
                // TODO Auto-generated method stub 
                //要做的事情 
           //	 MainActivity.this.progressBar.setProgress(50);
            	String SName="WirelessHoldData";
        	    byte[] bName=SName.getBytes();
            	byte[] arrayOfByte = new byte[33];
            	System.arraycopy(Head, 0, arrayOfByte, 0, Head.length);//前6位
            	No++;
				arrayOfByte[6]=(byte)No;
				arrayOfByte[7]=(byte)(No>>8);
				arrayOfByte[8]=Order;
				System.arraycopy(bName, 0, arrayOfByte, 9, bName.length);//16位
				System.arraycopy(arrayOfByte,6,CrcTeam2,0,19);
				CRCNum=CodecUtil.crc16Bytes(CrcTeam2);
				System.arraycopy(CRCNum, 0, arrayOfByte, 13, CRCNum.length);//CRC校验码
				System.arraycopy(End, 0, arrayOfByte, 27, End.length);
				
                try
                {
                  MainActivity.this.outStream = MainActivity.this.btSocket.getOutputStream();
                  try
                  {
                     MainActivity.this.outStream.write(arrayOfByte);
                  }
                  catch (IOException localIOException4)
                  {
                  }
         //         continue;
                }
                catch (IOException localIOException1)
                {
         //         break label17;
                }
              
                handler.postDelayed(this, 200); 
            } 
        };
      

        handler.postDelayed(runnable, 200);//每0.2秒执行一次runnable.
        
    }

    
    @Override
   	public void onResume() {
   	
   		super.onResume();

   		
   		
   		DisplayToast("正在尝试连接智能小车，请稍后····");
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

        try {

           btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (IOException e) {

                DisplayToast("套接字创建失败！");
           }
           DisplayToast("成功连接智能小车！正在连接套接字");
           mBluetoothAdapter.cancelDiscovery();
           try {

                   btSocket.connect();
                   DisplayToast("连接成功建立，数据连接打开！");
                //   mButtonShow.setText("连接上");
                //   mButtonShow.setBackgroundResource(R.drawable.onstop);
                 //  link=true;
                  mreadThread = new readThread();  
                 mreadThread.start();  
                   

           } catch (IOException e) {

                   try {
                   	btSocket.close();

                   } catch (IOException e2) {

                           
                           DisplayToast("连接没有建立，无法关闭套接字！");
                   }
           }
      }
    
    
    

	
	 private class readThread extends Thread
	  {
	    private readThread()
	    {
	    }

	 //   String a="WirelessHoldData";
	 //   byte[] ch=a.getBytes();
	    
	    
	   

	    public void run()
	    {
	    	
	    	
	    	   byte[] buffer = new byte[1024];  
	           int bytes;  
	     //      InputStream mmInStream = null;  
	             
	      //     if(MainActivity.this.btSocket!=null)
	           {
	        	   try {
	                	mmInStream = btSocket.getInputStream();
						} catch (IOException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
						}  
	           }
	      //  } catch (IOException e1) {  
	            // TODO Auto-generated catch block  
	      //      e1.printStackTrace();  
	      //  }     
	            while (true) {  
	               try {  
	                   // Read from the InputStream  
	                   if( (bytes = mmInStream.read(buffer)) > 0 )  //返回长度
	                   {  
	                    byte[] buf_data = new byte[bytes];  
	                    for(int i=0; i<bytes; i++)  
	                    {  
	                        buf_data[i] = buffer[i];  
	                    }  
	              
	                   }  
	               } catch (IOException e) {  
	                try {  
	                	
	                    mmInStream.close();  
	                } catch (IOException e1) {  
	                    // TODO Auto-generated catch block  
	                    e1.printStackTrace();  
	                }  
	                   break;  
	               }  
	           } 
	    
	    }
	  }

		
	

   	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public void onPause() {
    	//	sensorManager.unregisterListener(lsn); // 取消注册监听器
    		super.onPause();
    		if (outStream != null) {
    			try {
    				outStream.flush();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		try {
    			btSocket.close();
    		
    		} catch (IOException e2) {

    			DisplayToast("套接字关闭失败！");
    		}
    	}
}




