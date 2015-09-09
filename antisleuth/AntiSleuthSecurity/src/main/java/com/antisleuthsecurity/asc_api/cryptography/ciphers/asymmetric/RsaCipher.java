package com.antisleuthsecurity.asc_api.cryptography.ciphers.asymmetric;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.util.encoders.UrlBase64;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.Ciphers;
import com.antisleuthsecurity.asc_api.exceptions.AscException;
import com.antisleuthsecurity.asc_api.utilities.ASLog;

public class RsaCipher extends Ciphers {

    private int            strength   = 1024;
    private PrivateKey     privateKey = null;
    private PublicKey      publicKey  = null;

    private CipherInstance instance   = CipherInstance.RSANONEOAEPWithSHA256AndMGF1Padding;
    
    public enum CipherInstance {
        RSANONENoPadding ("RSA/NONE/NoPadding"),
        RSANONEPKCS1Padding ("RSA/NONE/PKCS1Padding"), 
        RSANONEOAEPWithMD5AndMGF1Padding ("RSA/NONE/OAEPWithMD5AndMGF1Padding"), 
        RSANONEOAEPWithSHA1AndMGF1Padding ("RSA/NONE/OAEPWithSHA1AndMGF1Padding"), 
        RSANONEOAEPWithSHA224AndMGF1Padding ("RSA/NONE/OAEPWithSHA224AndMGF1Padding"), 
        RSANONEOAEPWithSHA256AndMGF1Padding ("RSA/NONE/OAEPWithSHA256AndMGF1Padding"), 
        RSANONEOAEPWithSHA384AndMGF1Padding ("RSA/NONE/OAEPWithSHA384AndMGF1Padding"), 
        RSANONEOAEPWithSHA512AndMGF1Padding ("RSA/NONE/OAEPWithSHA512AndMGF1Padding");

        String value = null;

        private CipherInstance(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public void setStrength(int strength) {
        if ((strength % 128) == 0) {
            this.strength = strength;
        }
    }
    
    public void setStrength(String strength){
        Integer value = Integer.parseInt(strength);
        this.setStrength(value);
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    @Override
    @Deprecated
    public int getStrength(Enum strength) {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getCipherInstance() {
        return this.getCipherInstance(this.instance);
    }

    public KeyPair generateKeyPair() throws AscException {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
            SecureRandom random = new SecureRandom();
            gen.initialize(this.strength, random);

            KeyPair pair = gen.generateKeyPair();

            this.publicKey = pair.getPublic();
            this.privateKey = pair.getPrivate();

            return pair;
        }
        catch (Exception e) {
            throw new AscException(e.getMessage(), e);
        }
    }

    public void setPrivateKey(PrivateKey key) {
        this.privateKey = key;
    }

    public void setPublicKey(PublicKey key) {
        this.publicKey = key;
    }

    public void setPublicKeyUrlB64(String key) {
        byte[] encodedKey = UrlBase64.decode(key.getBytes());
        try {
            PublicKey pKey = null;
            pKey = KeyFactory.getInstance("RSA").generatePublic(
                    new X509EncodedKeySpec(encodedKey));
            this.publicKey = pKey;
        }
        catch (Exception e) {
            ASLog.error("Could not set public key", e);
        }
    }

    public Cipher getCipher() throws AscException {
        return this.getCipher(this.mode);
    }

    @Override
    public Cipher getCipher(int mode) throws AscException {
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance(this.getCipherInstance(), "BC");

            if (mode == Cipher.ENCRYPT_MODE) {
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            }
            else {
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
            }

            return cipher;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AscException("Could not build RSA Cipher", e);
        }
    }

    public String getCipherInstance(CipherInstance instance) {
        return instance.getValue();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setKeyPair(KeyPair pair) {
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
    }

    public CipherInstance getInstance() {
        return instance;
    }

    public void setInstance(CipherInstance instance) {
        this.instance = instance;
    }
    
    public void setInstance(String value){
        CipherInstance[] values = CipherInstance.values();
        
        for(CipherInstance instance : values){
            if(instance.getValue().equals(value))
                this.instance = instance;
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
