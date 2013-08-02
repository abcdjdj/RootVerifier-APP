package com.abcdjdj.rootverifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;




/**
 * @author - Madhav Kanbur (abcdjdj)
 * @version - V1.2
 */

public class MainActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setDeviceName();
		//Calling the function to display the current device model on startup of the app.
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) 
	    {
	    case R.id.action_settings://If the exit button is pressed then call the finish() function
	    	this.finish();
	    	return true;
	    	
	    	default:
	    		return true;
	    	
	        
	    }
	}

	public void Check(View v) throws IOException, InterruptedException
	{
		/*
		 * This is the main function of the app which is 
		 * called when the user clicks on the "Check" button.
		 */
			
		TextView a = (TextView) findViewById(R.id.textView1);			
		if (RootAvailibility())//Checks if su binary is available
		{
		
			try
			{
						
				Process process = Runtime.getRuntime().exec("su");
				OutputStream stdin = process.getOutputStream();
				
				//CREATING A DUMMY FILE in /system called abc.txt
				stdin.write("mount -o remount rw /system/\n".getBytes());
				stdin.write("cd system\n".getBytes());
				stdin.write("echo \"ABC\" > abc.txt\n".getBytes());
				stdin.write("exit\n".getBytes());
				stdin.flush();
				stdin.close();
				process.waitFor();
				
				
				if(checkFile())//Checks if the file has been successfully created
				{
					a.setText("DEVICE IS ROOTED");
				}
				else
				{
				a.setText("ROOT PERMISSION NOT GRANTED OR SUPERUSER APP MISSING");

				}
				
				process=Runtime.getRuntime().exec("su");
				stdin=process.getOutputStream();
				
				//DELETES THE DUMMY FILE IF PRESENT
				stdin.write("cd system\n".getBytes());
				stdin.write("rm abc.txt\n".getBytes());
				stdin.write("exit\n".getBytes());
				stdin.flush();
				stdin.close();
				process.waitFor();
				process.destroy();
								
			}
			catch(Exception e)
			{
				
			
				a.setText("ROOT PERMISSION NOT GRANTED OR SUPERUSER APP MISSING");
			}
		}
		else 
		{
			
			a.setText("NOT ROOTED");
		}
		busybox();//To checks if busybox is installed or not
		
		
	}

	public boolean checkFile() 
	{
		//Checks if the file has been created successfully
		File x = new File("/system/abc.txt");
		boolean flag=x.exists();
		return flag;

	}
	
	
	public boolean RootAvailibility()throws IOException
	{
		
		try
		{
			
		Process p = Runtime.getRuntime().exec("su");
		p.destroy();
		return true;
		}
		catch(Exception e)
		{
			return false;
		}

	}
	
	
	
	public void setDeviceName()
	{
		String x;
		x=android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
		TextView c  = (TextView)findViewById(R.id.devicemodel);
		//devicemodel is the TextView which displays the device model
		x=x.toUpperCase();
		c.setText("DEVICE:- " + x);
		
	}
	
	
	public void busybox()throws IOException
	{
		/*
		 * If the busybox process is created successfully,
		 * then IOException won't be thrown. To get the 
		 * busybox version, we must read the output of the command
		 * 
		 */
				
		TextView z = (TextView)findViewById(R.id.busyboxid);
		String line=null;char n[]=null;
		
		try
		{
			
		Process p =Runtime.getRuntime().exec("busybox");
		InputStream a = p.getInputStream();
		InputStreamReader read = new InputStreamReader(a);
		BufferedReader in = new BufferedReader(read);
		
		/*
		 * Labeled while loop so that the while loop
		 * can be directly broken from the nested for.
		 * 
		 */
		abc :while((line=in.readLine())!=null)
		{
			n=line.toCharArray();
			
			for(char c:n)
			{
				/*
				 * This nested for loop checks if 
				 * the read output contains a digit (number),
				 * because the expected output is -
				 * "BusyBox V1.xx". Just to make sure that
				 * the busybox version is read correctly. 
				 */
				if(Character.isDigit(c))
				{
					break abc;//Once a digit is found, terminate both loops.
					
				}
			}
			
		}
		z.setText("BUSYBOX INSTALLED - " + line);
		}
		catch(Exception e)
		{
			/*
			 * IOException has been thrown which indicates that
			 * that busybox is not installed.
			 * */
			
			z.setText("BUSYBOX NOT INSTALLED OR NOT SYMLINKED");
		}
	}
	
}//Class Body
