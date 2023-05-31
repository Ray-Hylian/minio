package com.infotel.mytransfer.service;

import java.security.Signature;

public interface SignatureService {

	/**
	 * Reads the private key information into memory.
	 */
	public void readPrivateKey();
	
	/**
	 * Creates a signature for the given bytes an return it.
	 * 
	 * @param object The object to sign.
	 * @return The byte of the signature.
	 */
	public byte[] signObject(byte[] object);
	
	/**
	 * Creates a signature instance, ready to be passed to other functions.
	 * @return A {@link java.security.Signature} instance.
	 */
	public Signature beginSignature();
	
	/**
	 * Updates the signature with the given data.
	 * 
	 * @param signature the signature to update.
	 * @param data The data to pass into the signature.
	 * @param off The offset for the data array.
	 * @param len The length to read from the array.
	 */
	public void updateSigature(Signature signature, byte[] data, int off, int len);
	
	/**
	 * Ends the signature and returns its bytes.
	 * @param signature The signature to end.
	 * @return The bytes of the signature.
	 */
	public byte[] endSignature(Signature signature);
}
