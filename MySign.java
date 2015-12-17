import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.io.*;
import java.util.Scanner;

//Ish Davis

public class MySign
{
	public static void main(String[] args)throws IOException, ClassNotFoundException
	{
		if(args[0].equals("s")){sign(args[1]);} //change to args[1]
		else{verify(args[1]);}
	}
	
	public static void sign(String fileName)throws IOException, ClassNotFoundException{
		//input stream
		FileInputStream fis;
		ObjectInputStream ois;
		try{
			fis = new FileInputStream("privkey.rsa");
			ois = new ObjectInputStream(fis);
		}
		catch(Exception e){
			System.out.println("Private key not found!");
			fis = new FileInputStream("privkey.rsa");
			ois = new ObjectInputStream(fis);
			System.exit(0);
		}
		
		//Getting hash
		BigInteger D = (BigInteger) ois.readObject();//D
		BigInteger N = (BigInteger) ois.readObject();//N

		BigInteger retrieved = HashEx.hasher(fileName);
		
		BigInteger hash = retrieved.modPow(D, N);//Hash
		
		//writing to file
		String signed = fileName + ".signed";
		FileWriter fwriter = new FileWriter(signed, false);
		PrintWriter outputFile = new PrintWriter(fwriter);
		
		File myFile = new File(fileName);
		Scanner inputFile = new Scanner(myFile);
		
		while(inputFile.hasNext()){
			String temp = inputFile.nextLine();
			outputFile.println(temp);
		}
		outputFile.print("Hash:");
		outputFile.print(hash);
		inputFile.close();
		outputFile.close();
	}
	
	public static void verify(String fileName)throws IOException, ClassNotFoundException{
		String original = fileName.replace(".signed", "");
		BigInteger hash = HashEx.hasher(original);
		File myFile = new File(fileName);
		Scanner inputFile = new Scanner(myFile);
		
		BigInteger decrypted = new BigInteger("0");
		while(inputFile.hasNext()){
			String temp = inputFile.nextLine();
			if(temp.startsWith("Hash:")){
				decrypted = new BigInteger(temp.substring(5));
			}
		}
		inputFile.close();
		//input stream
		FileInputStream fis;
		ObjectInputStream ois;
		try{
			fis = new FileInputStream("pubkey.rsa");
			ois = new ObjectInputStream(fis);
		}
		catch(Exception e){
			System.out.println("Public key not found!");
			fis = new FileInputStream("pubkey.rsa");
			ois = new ObjectInputStream(fis);
			System.exit(0);
		}
		
		BigInteger E = (BigInteger) ois.readObject();//E
		BigInteger N = (BigInteger) ois.readObject();//N
		
		//Decrypted hash
		BigInteger encrypted = decrypted.modPow(E, N);
		
		if(encrypted.compareTo(hash) == 0){
			System.out.println("Congratulations, this signature is valid!");
		}
		else{
			System.out.println("Sorry this signature isn't valid...");
		}
		
	}
}