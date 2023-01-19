package hr.fer.oprpp1.hwo5.crypto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	/**
	 * private variable showing the number of bytes read during the reading of files
	 */
	private static final int READSIZE = 32;
	/**
	 * Private variable representing the encrypt/decrypt key 
	 */
	private static String keyText;
	/**
	 * Private variable representing the initialization vector value
	 */
	private static String ivText;

	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Invalid number of arguments!");
			return;
		}

		switch(args[0]) {
		case "checksha":
			getUserInput(1,args[1]);
			System.out.println(checksha(args[1],keyText));
			break;
		case "encrypt":
			getUserInput(2,args[1]);
			System.out.println(encrypt(args[1],args[2]));
			break;
		case "decrypt":
			getUserInput(3,args[1]);
			System.out.println(decrypt(args[1],args[2]));
			break;
		default:
			getUserInput(4,null);
		}

	}


	/**
	 * Auxiliary function that checks if the command exists in the program and asks the user for additional data depending on the commandID.
	 * List of commandIDs: 1 -> checksha, 2 -> encrypt , 3 -> decrypt			
	 * @param commandID the ID of the command the user initiated the program with
	 * @param fileName the name of the file/s on which the command needs to be executed 
	 * @throws CryptoException an exception that is given if the ID o
	 */
	private static void getUserInput(int commandID,String fileName) {
		Scanner sc = new Scanner(System.in);
		if(commandID == 1) {
			System.out.print("Please provide expected sha-256 digest for " + fileName + ":\n>");
			keyText = sc.nextLine();
		}else if(commandID == 2 || commandID == 3) {
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): \n>");
			keyText = sc.nextLine();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n>");
			ivText = sc.nextLine();
		}else
			throw new IllegalArgumentException("Invalid command!");
		sc.close();
	}


	/**
	 * Private function that checks if the sha-256 that the user put in is the same as the actual sha-256 of the selected file
	 * @param testedFile name of the tested file
	 * @param expectedDigest the expected sha of the file that the user put input the program
	 * @return a string representing the result of the comparison between the actual sha-256 and the expected sha-256 of the user defined file 
	 */
	private static String checksha(String testedFile, String expectedDigest) {
		Path path = Path.of(testedFile);
		byte[] currentRead = new byte[READSIZE];
		try(InputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {
			MessageDigest md = MessageDigest.getInstance("sha256");
			for(int i = inputStream.read(currentRead) ; i > -1 ; i = inputStream.read(currentRead)) {
				md.update(currentRead);
			}
			String actualDigest = Util.bytetohex(md.digest());
			if(actualDigest.equalsIgnoreCase(expectedDigest)) {
				return "Digesting completed. Digest of " + testedFile + " matches expected digest.";
			}else {
				return "Digesting completed. Digest of " + testedFile + " does not match the expected digest. Digest was: " + actualDigest;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Private function that encrypts the given file into the file with the name given by the user as the last argument
	 * @param currentFile the file that needs to be encrypted
	 * @param generatedFile the encrypted file generated from the user defined file
	 * @return a string that represents the final status of the encryption operation
	 */
	private static String encrypt(String currentFile, String generatedFile) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher;
		String returnText;
		try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Path.of(currentFile))); OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Path.of(generatedFile)))){
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
			byte[] currentReadWrite = new byte[READSIZE];
			for(int i = inputStream.read(currentReadWrite) ; i > -1 ; i = inputStream.read(currentReadWrite)) {
				outputStream.write(cipher.update(currentReadWrite));
			}
			outputStream.write(cipher.doFinal());
			returnText = "Encryption completed. Generated file " + generatedFile + " based on file " + currentFile;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			returnText = e.getMessage();
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			returnText = e.getMessage();
		} catch (IOException e) {
			returnText = e.getMessage();
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			returnText = e.getMessage();
		}
		return returnText;
	}
	
	/**
	 * Private function that decrypts the user given file using the AES algorithm into a new file the name and path of it given by the user as the last argument
	 * @param currentFile the file that needs to be decrypted
	 * @param generatedFile file generated from the encrypted file
	 * @return a string that respresents the final status of the decryption operation
	 */
	private static String decrypt(String currentFile, String generatedFile) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher;
		String returnText;
		try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(Path.of(currentFile))); OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Path.of(generatedFile)))){
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
			byte[] currentReadWrite = new byte[READSIZE];
			for(int i = inputStream.read(currentReadWrite) ; i > -1 ; i = inputStream.read(currentReadWrite)) {
				outputStream.write(cipher.update(currentReadWrite));
			}
			outputStream.write(cipher.doFinal());
			returnText = "Decryption completed. Generated file " + generatedFile + " based on file " + currentFile;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			returnText = e.getMessage();
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			returnText = e.getMessage();
		} catch (IOException e) {
			returnText = e.getMessage();
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			returnText = e.getMessage();
		}
		return returnText;
	}

}
