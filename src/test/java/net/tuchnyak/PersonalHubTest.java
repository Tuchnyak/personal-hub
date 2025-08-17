package net.tuchnyak;

import org.junit.jupiter.api.Test;
import rife.test.MockConversation;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalHubTest {

    @Test
    void verifyRoot() {
        var m = new MockConversation(new PersonalHubSite());
        assertEquals(302, m.doRequest("/").getStatus());
    }

}
