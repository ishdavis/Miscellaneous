import java.math.BigInteger;
import java.util.Random;
import java.io.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

//Ish Davis

public class MyKeyGen{
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		//output stream public
		FileOutputStream fos = new FileOutputStream("pubkey.rsa");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		//output stream private
		FileOutputStream pfos = new FileOutputStream("privkey.rsa");
		ObjectOutputStream poos = new ObjectOutputStream(pfos);
		
		//input stream
		//FileInputStream fis = new FileInputStream("pubkey.rsa");
		//ObjectInputStream ois = new ObjectInputStream(fis);
		
		Random rand = new Random();
		BigInteger P = new BigInteger(1024, 50, rand);//P
		BigInteger Q = new BigInteger(1024, 50, rand);//Q
		BigInteger N = P.multiply(Q);//N
		
		long val = 1;
		BigInteger one = BigInteger.valueOf(val);
		BigInteger pTemp = P.subtract(one);
		BigInteger qTemp = Q.subtract(one);
		BigInteger phi = qTemp.multiply(pTemp);//Phi
		
		BigInteger E = new BigInteger("0");
		boolean found = false;
		while(!found){
			E = new BigInteger(1000, rand);//E
			BigInteger GCD = E.gcd(phi);
			if(GCD.doubleValue() == 1){
				found = true;
			}
		}
		
		BigInteger D = new BigInteger("0");
		D = E.modInverse(phi);//D
		
		oos.writeObject(E);
		oos.writeObject(N);
		oos.close();
		
		poos.writeObject(D);
		poos.writeObject(N);
		poos.close();
		
	}


}