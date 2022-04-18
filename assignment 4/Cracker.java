import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	public static int numOfCharacters=40;
	public static MessageDigest md;
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	
	
	
	
	
	
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
	
	
	public class CrackSolver implements Runnable{
		private int from;
		private int to;
		private int passwordlength;
		private String hashed;
		private CountDownLatch cl;
		
		
		/* main constructor for the CrackSolver Object */
		public CrackSolver(int length, int from, int to, CountDownLatch cl, String hashed){
			passwordlength=length;
			this.hashed=hashed;
			this.cl=cl;
			this.from=from;
			this.to=to;
		}
		
		
		
		/* recursive method to generate all the words, based on length */
		private void rec(int length, int curLength, String curResult){
			
			if(curLength==length){
				String res=StringToHash(curResult);
				if(res.equals(hashed)){
					System.out.println(curResult);
				}
			}
			else{
				if(curLength==0){
					for(int i=from; i<to; i++){
						rec(length, curLength+1, curResult+CHARS[i]);
					}
				}
				else{
					for(int i=0; i<numOfCharacters; i++){
						rec(length, curLength+1, curResult+CHARS[i]);
					}
				}
			}
		}
		
		
		/* run method for the thread */
		public void run() {
			String s="";
			for(int i=1; i<=passwordlength; i++){
				rec(i, 0, s);
			}
			cl.countDown();
		}
		
	}
	
	
	
	
	
	
	
	/* this method is called when in cracking mode, generating String for the given hash value, maximum word length and number of threads */
	private static void HashToString(String word, int wordLength, int threadNum){
		Cracker c=new Cracker();
		String s=word;
		int length=wordLength;
		int numOfThreads=threadNum;
		CountDownLatch cl=new CountDownLatch(numOfThreads);
		Thread[] t=new Thread[numOfThreads];
		int intervalSize=numOfCharacters/numOfThreads;
		for(int i=0; i<numOfThreads; i++){
			if(i==numOfThreads-1){
				CrackSolver cs=c.new CrackSolver(length, i*intervalSize, numOfCharacters, cl, s);
				t[i]=new Thread(cs);
			}
			else{
				CrackSolver cs=c.new CrackSolver(length, i*intervalSize, i*intervalSize+intervalSize, cl, s);
				t[i]=new Thread(cs);
			}
			t[i].start();
		}
		try {
			cl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/* this is called when in generation mode, getting hash value for the string */
	private static String StringToHash(String hash){
		try {
			md=MessageDigest.getInstance("SHA-1");
			byte[] bytes=md.digest(hash.getBytes());
			return hexToString(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/* main method */
	public static void main(String[] args){
		
		if(args.length==1){
			System.out.println(StringToHash(args[0]));
		}
		if(args.length==3){
			HashToString(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}
	}

}
