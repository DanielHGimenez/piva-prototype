package org.pivaprototype.socket.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class KeyGenerator {

    private static java.security.KeyPairGenerator keyGen;
    private static KeyFactory keyFactory;

    static {
        try {
            keyGen = java.security.KeyPairGenerator.getInstance("RSA");
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static synchronized KeyPair generatePair(int keySize) {
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }

    public static PrivateKey recoverPrivateKey(byte[] key) throws InvalidKeySpecException {
        return keyFactory.generatePrivate(new X509EncodedKeySpec(key));
    }

    public static PublicKey recoverPublicKey(byte[] key) throws InvalidKeySpecException {
        return keyFactory.generatePublic(new X509EncodedKeySpec(key));
    }

}