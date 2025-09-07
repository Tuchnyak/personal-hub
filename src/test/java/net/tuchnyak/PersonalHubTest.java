package net.tuchnyak;

import org.junit.jupiter.api.Test;
import rife.test.MockConversation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalHubTest {

    @Test
    void verifyRoot() {
        var m = new MockConversation(new PersonalHubSite(Optional.empty()));
        assertEquals(200, m.doRequest("/").getStatus());
    }

}
