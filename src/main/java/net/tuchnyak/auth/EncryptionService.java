package net.tuchnyak.auth;

import java.security.NoSuchAlgorithmException;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface EncryptionService {

    String hashPassword(String plainPassword) throws NoSuchAlgorithmException;
    boolean checkPassword(String plainPassword, String hashed) throws NoSuchAlgorithmException;

}
