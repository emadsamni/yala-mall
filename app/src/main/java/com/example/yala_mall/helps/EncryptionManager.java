package com.example.yala_mall.helps;





import com.example.yala_mall.utils.Constants;

import se.simbio.encryption.Encryption;

public class EncryptionManager {
    private static EncryptionManager instance;

    private Encryption encryption;
    private EncryptionManager() {
        String key = Constants.PREF_APP_KEY;
        String salt = Constants.PREF_APP_SALT;
        byte[] iv = new byte[16];
        encryption = Encryption.getDefault(key, salt, iv);
    }

    public static EncryptionManager getInstance() {
        if (instance == null) {
            instance = new EncryptionManager();
        }
        return instance;
    }

    public String encrypt(String value) {
        String encrypted = encryption.encryptOrNull(value);
        return encrypted;
    }


    public String decrypt(String encryptedText) {
        String decrypted = encryption.decryptOrNull(encryptedText);
        return decrypted;
    }
}
