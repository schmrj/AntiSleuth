/**
 * @author Bob Schmidinger, Robert.Schmidinger@gmail.com
 * License: Apache 2.0
 * Copywrite © 2015
 */
package com.antisleuthsecurity.asc_api.cryptography.ciphers.symmetric;

import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.antisleuthsecurity.asc_api.cryptography.ciphers.Ciphers;
import com.antisleuthsecurity.asc_api.exceptions.AscException;

public class AesCipher extends Ciphers {

    private Strength       strength = Strength.S128;
    private CipherInstance instance = CipherInstance.AESCBCPKCS5Padding;
    private byte[]         key      = null;
    private byte[]         iv       = null;

    public enum Strength {
        NONE(128), S128(128), S192(192), S256(256);

        Integer value = null;

        private Strength(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    public enum CipherInstance {
        AESCBCPKCS5Padding("AES/CBC/PKCS5Padding"),
        AESCBCPKSC7Padding("AES/CBC/PKCS7Padding");

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

    public AesCipher() {
        super();
        this.strength = Strength.S128;
        this.instance = CipherInstance.AESCBCPKCS5Padding;
    }

    public AesCipher(Strength strength) {
        super();
        this.strength = strength;
        this.instance = CipherInstance.AESCBCPKCS5Padding;
    }

    @Override
    public void setStrength(int strength) {
        switch (strength) {
            case 128:
                this.strength = Strength.S128;
                break;
            case 192:
                this.strength = Strength.S192;
                break;
            case 256:
                this.strength = Strength.S256;
                break;
            default:
                this.strength = Strength.S128;
                break;
        }
    }

    public byte[] generateKey() throws AscException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(this.getStrength());
            SecretKey sKey = keyGen.generateKey();
            return sKey.getEncoded();
        }
        catch (Exception e) {
            throw new AscException("Could not generate key", e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int getStrength(Enum strength) {
        Strength stren = (Strength) strength;
        if (stren == null)
            stren = Strength.NONE;

        return stren.getValue();
    }

    @Override
    public int getStrength() {
        return getStrength(strength);
    }

    public void setStrength(Strength strength) {
        if (strength != null)
            this.strength = (Strength) strength;
        else
            this.strength = Strength.S128;
    }
    
    public void setStrength(String strength){
        Integer str = Integer.parseInt(strength);
        
        Strength[] strengths = Strength.values();
        
        for(Strength s : strengths){
            if(s.getValue() == str){
                this.strength = s;
            }
        }
    }

    public String getInstance() {
        return getInstance(this.instance);
    }

    public String getInstance(CipherInstance instance) {
        if (instance == null)
            instance = this.instance;
        return instance.getValue();
    }

    public void setInstance(CipherInstance cipherInstance) {
        if (cipherInstance != null)
            this.instance = (CipherInstance) cipherInstance;
        else
            this.instance = CipherInstance.AESCBCPKCS5Padding;
    }

    public Cipher getCipher() throws AscException {
        return this.getCipher(this.mode);
    }

    @Override
    public Cipher getCipher(int mode) throws AscException {
        Cipher cipher = null;
        this.prepare();

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            IvParameterSpec iv = new IvParameterSpec(this.iv);

            SecretKeySpec sKey = new SecretKeySpec(this.key, "AES");

            cipher = Cipher.getInstance(this.getInstance(), "BC");
            cipher.init(mode, sKey, iv);
        }
        catch (Exception e) {
            throw new AscException("Could not build new AES Cipher", e);
        }

        return cipher;
    }

    private void prepare() throws AscException {
        if (this.iv == null)
            this.iv = this.generateIV();
        if (this.key == null)
            this.key = this.generateKey();
        if (this.instance == null)
            this.setInstance(null);
    }

    public void setKey(byte[] key) throws AscException {
        try {
            if (key == null || key.length < 16) {
                this.key = this.generateKey();
            }
            else {
                this.key = key;
            }
        }
        catch (Exception e) {
            throw new AscException("Key Not Valid", e);
        }
    }

    public byte[] getKey() {
        return this.key;
    }

    public byte[] generateIV() {
        return new IvParameterSpec(new SecureRandom().generateSeed(16)).getIV();
    }

    public byte[] getIv() {
        return this.iv;
    }

    public void setIv(byte[] iv) throws AscException {
        try {
            if (iv == null || iv.length != 16) {
                this.iv = generateIV();
            }
            else {
                this.iv = iv;
            }
        }
        catch (Exception e) {
            throw new AscException("Key Not Valid", e);
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
