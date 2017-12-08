package rsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class logic {

	private BigInteger p, q, n, phi, d, e;

	public void GenerateKey(int bitLength){

		// Generate primes p and q
		Random rnd1 = new Random();
		Random rnd2 = new Random();

		p = BigInteger.probablePrime(bitLength, rnd1);
		q = BigInteger.probablePrime(bitLength, rnd2);

		// Calculate n
		n = p.multiply(q);

		// Calculate phi
		phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		// Calculate e
		Random rnd3 = new Random();
		e = new BigInteger(bitLength, rnd3);
		while(phi.compareTo(e) != 1 || !e.gcd(phi).equals(BigInteger.ONE) || e.compareTo(BigInteger.ONE) != 1){
			rnd3 = new Random();
			e = new BigInteger(bitLength, rnd3);
		}

		// Calculate d
		d = e.modInverse(phi);

	}

	public String CreatePublicKey(){

		// Converts String n to base 64
		byte[] nBytes = n.toByteArray();	
		String nB64 = Base64.getEncoder().encodeToString(nBytes);

		// Converts BigInteger E to base 64
		byte[] eBytes = e.toByteArray();	
		String eB64 = Base64.getEncoder().encodeToString(eBytes);

		// Creates public_key by concatenating e and n separated by a space (not a base64 character)
		String public_key =  eB64 + " " + nB64;

		return public_key;

	}

	public String CreatePrivateKey(){

		// Converts String n to base 64
		byte[] nBytes = n.toByteArray();	
		String nString = Base64.getEncoder().encodeToString(nBytes);

		// Converts BigInteger d to base 64 String
		byte[] dBytes = d.toByteArray();	
		String dString = Base64.getEncoder().encodeToString(dBytes);

		// Creates private_key
		String private_key =  dString + " " + nString;

		return private_key;

	}

	public String Encrypt(String message, String public_key){

		// Retrieve value of E from public key
		String E_b64 = public_key.split(" ")[0];

		// Retrieve value of n from public key
		String n_b64 = public_key.split(" ")[1];

		// Convert Decodes e and n from base 64
		byte[] eBytes = Base64.getDecoder().decode(E_b64);
		byte[] nBytes = Base64.getDecoder().decode(n_b64);

		// Converts e, n, and m to BigIntegers
		BigInteger eBig = new BigInteger(eBytes);
		BigInteger nBig = new BigInteger(nBytes);
		BigInteger mBig = new BigInteger(message.getBytes());

		// Calculates cipher as a BigInteger
		BigInteger cipherBig = mBig.modPow(eBig, nBig);

		// Encodes cipher as a Base64 String 
		String cipher = Base64.getEncoder().encodeToString(cipherBig.toByteArray());

		// Test if bitLength is long enough	to properly encrypt message	
		File private_key_file = new File("private_key.txt");
		String private_key = null;
		try {
			Scanner scanner = new Scanner(private_key_file);
			private_key = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		String testmessage = Decrypt(cipher, private_key);
		if(message.equals(testmessage)) {
			return cipher;
		}
		else {
			return "Bit length too short. Please encrypt this message with longer keys.";
		}
		
	}

	public String Decrypt(String cipher, String private_key){

		// Retrieve value of E from public key
		String d = private_key.split(" ")[0];

		// Retrieve value of n from public key
		String n = private_key.split(" ")[1];

		// Convert Strings to BigIntegers
		byte[] dBytes = Base64.getDecoder().decode(d);
		byte[] nBytes = Base64.getDecoder().decode(n);
		BigInteger dBig = new BigInteger(dBytes);
		BigInteger nBig = new BigInteger(nBytes);

		// Convert cipher to BigInteger
		BigInteger cBig = new BigInteger(Base64.getDecoder().decode(cipher));

		// Compute BigInteger value of message
		BigInteger messageBig = cBig.modPow(dBig, nBig);

		// Convert message from BigInteger to String
		String message = new String(messageBig.toByteArray());

		return message;

	}

	public BigInteger getP(){
		return p;
	}

	public BigInteger getQ(){
		return q;
	}

	public BigInteger getN(){
		return n;
	}

	public BigInteger getPhi(){
		return phi;
	}

	public BigInteger getD(){
		return d;
	}

	public BigInteger getE(){
		return e;
	}

}