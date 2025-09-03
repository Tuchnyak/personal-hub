package net.tuchnyak.auth;

import rife.tools.StringEncryptor;

import java.security.NoSuchAlgorithmException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class EncryptionServiceDrupalImpl implements EncryptionService {

    private final StringEncryptor encryptor;

    public EncryptionServiceDrupalImpl() {
        encryptor = StringEncryptor.DRUPAL;
    }

    @Override
    public String hashPassword(String plainPassword) throws NoSuchAlgorithmException {

        return encryptor.encrypt(plainPassword);
    }

    @Override
    public boolean checkPassword(String plainPassword, String hashed) throws NoSuchAlgorithmException {

        return StringEncryptor.matches(plainPassword, hashed);
    }

}
