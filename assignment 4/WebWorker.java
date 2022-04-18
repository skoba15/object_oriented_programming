import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Semaphore;

import javax.swing.*;

public class WebWorker extends Thread {
	
	private final int rowNumber;
	private WebFrame wf;
	private String urlString;
	private int bytesDownloaded;
	private long spentTime;
	private Semaphore launch;
	private long starttime;
	private final String Error="err";
	private final String Interrupted="interrupted";
	
	/* main constructor for WebWorker */
	public WebWorker(String url, int row, WebFrame wf, Semaphore launch){
		this.urlString=url;
		rowNumber=row;
		this.wf=wf;
		bytesDownloaded=0;
		spentTime=0;
		this.launch=launch;
		wf.increaseRunning();
	}
	
	
	/* if the thread is interrupted updates the status correspondingly */
	private void wasInterrupted(){
		String s=Interrupted;
		updateGUI(s, spentTime);
	}
	
	
	/* returns current time */
	private String getTime(){
		Date now=new Date();
		String s=new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(now);
		return s;
	}
	
	
	/* updates the status when the thread successfully did its job */
	private void wasSuccessful(){
		String time=getTime();
		String result=time+" "+spentTime+"ms "+bytesDownloaded+" bytes";
		updateGUI(result, spentTime);
	}
	
	
	/* updates the GUI based on the finishResult and elapsed time */
	private void updateGUI(final String finishResult,  final double elapsed){
				wf.change(rowNumber, finishResult);
				wf.decreaseRunning();
				wf.increaseCompleted();
				wf.changeProgressBar();
				wf.increaseElapsedTime(elapsed);
				wf.checkend();
				launch.release();
	}
	
	
	
	/* method for download process */
	private void download(){
		
		InputStream input = null;
		StringBuilder contents = null;
		try {
			starttime=System.currentTimeMillis();
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
		
			// Set connect() to throw an IOException
			// if connection does not succeed in this many msecs.
			connection.setConnectTimeout(5000);
			
			connection.connect();
			input = connection.getInputStream();

			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
		
			char[] array = new char[1000];
			int len;
			contents = new StringBuilder(1000);
			boolean successful=true;
			while ((len = reader.read(array, 0, array.length)) > 0) {
				if(isInterrupted()){
					wasInterrupted();
					successful=false;
					break;
				}
				contents.append(array, 0, len);
				bytesDownloaded+=len;
				Thread.sleep(100);
			}
			spentTime=System.currentTimeMillis()-starttime;
			if(successful)wasSuccessful();
		
			// Successful download if we get here
			
		}
		// Otherwise control jumps to a catch...
		catch(MalformedURLException ignored) {
			if(isInterrupted()){
				wasInterrupted();
			}
			else updateGUI(Error, spentTime);
		}
		catch(InterruptedException exception) {
			wasInterrupted();
		}
		catch(IOException ignored) {
			if(isInterrupted()){
				wasInterrupted();
			}
			else updateGUI(Error, spentTime);
		}
		// "finally" clause, to close the input stream
		// in any case
		finally {
			try{
				if (input != null) input.close();
			}
			catch(IOException ignored) {}
		}
	}
	
	@Override
	public void run(){
		download();
	}
	
}