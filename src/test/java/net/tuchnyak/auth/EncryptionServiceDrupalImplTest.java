package net.tuchnyak.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tuchnyak (George Shchennikov)
 */
class EncryptionServiceDrupalImplTest {

    private EncryptionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new EncryptionServiceDrupalImpl();
    }

    @Test
    void hashPassword() throws NoSuchAlgorithmException {
        var plainPassword = "password";

        var actual = underTest.hashPassword(plainPassword);

        assertNotEquals(plainPassword, actual);
        assertEquals(55, actual.length());
    }

    @Test
    void checkPassword() throws NoSuchAlgorithmException {
        var hashOne = underTest.hashPassword("password");
        var hashTwo = underTest.hashPassword("password");

        assertNotEquals(hashOne, hashTwo);
        assertTrue(underTest.checkPassword("password", hashOne));
        assertTrue(underTest.checkPassword("password", hashTwo));
    }

}