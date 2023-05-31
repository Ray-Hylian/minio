package com.infotel.mytransfer.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import javax.annotation.PostConstruct;

import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infotel.mytransfer.service.SignatureService;

@Service
public class SignatureServiceImpl implements SignatureService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SignatureServiceImpl.class);

	private static final String PRIVATE_KEY_FILE = "privatekey.pem";
	private static final String HASH_ALGORITHM = "SHA256";
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = HASH_ALGORITHM + "with" + KEY_ALGORITHM;
	
	private PrivateKey privateKey;

	@PostConstruct
	@Override
	public void readPrivateKey() {
		try {
			PemReader reader = new PemReader(new FileReader(new File(PRIVATE_KEY_FILE)));
			PemObject pemObject = reader.readPemObject();
			
			byte[] keyContent = pemObject.getContent();
			
			RSAPrivateKey rsaPrivateKey = RSAPrivateKey.getInstance(keyContent);
			
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());

			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			
			privateKey = keyFactory.generatePrivate(spec);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
			LOGGER.error("Unable to read private key", e);
		}
	}
	
	public Signature beginSignature() {
		Signature signature = null;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			LOGGER.error("Unable to instanciate signature", e);
		}
		return signature;
	}
	
	@Override
	public void updateSigature(Signature signature, byte[] data, int off, int len) {
		try {
			signature.update(data, off, len);
		} catch (SignatureException e) {
			LOGGER.error("Unable to update signature", e);
		}
	}
	
	public byte[] endSignature(Signature signature) {
		try {
			return signature.sign();
		} catch (SignatureException e) {
			LOGGER.error("Unable to sign object", e);
		}
		return null;
	}
	
	@Override
	public byte[] signObject(byte[] object) {
		try {
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
			
			signature.update(object);
			
			return signature.sign();
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			LOGGER.error("Unable to sign object", e);
		}
		return null;
	}

}
