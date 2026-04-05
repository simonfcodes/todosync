package dev.simoncodes.todosync.id;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class IdTranslatorTest {

    @Test
    void testIdTranslator_happyPath() {
        UUID uuid = UUID.randomUUID();
        String encodedUuid = IdTranslator.encodeUuidToBase62(uuid);
        UUID decodedUuid = IdTranslator.decodeBase62ToUuid(encodedUuid);

        Assertions.assertEquals(uuid, decodedUuid, "The original and decoded UUID should be the same.");
        Assertions.assertEquals(22, encodedUuid.length(), "The encoded UUID should be 22 characters in length.");
    }

    @Test
    void testIdTranslator_multipleUUIDs() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        String encodedUuid1 = IdTranslator.encodeUuidToBase62(uuid1);
        String encodedUuid2 = IdTranslator.encodeUuidToBase62(uuid2);
        UUID decodedUuid1 = IdTranslator.decodeBase62ToUuid(encodedUuid1);
        UUID decodedUuid2 = IdTranslator.decodeBase62ToUuid(encodedUuid2);
        Assertions.assertEquals(uuid1, decodedUuid1, "The original and decoded UUID (one) should match.");
        Assertions.assertEquals(uuid2, decodedUuid2, "The original and decoded UUID (two) should match.");
        Assertions.assertEquals(22,  encodedUuid1.length(), "The encoded UUID should be 22 characters in length.");
        Assertions.assertEquals(22,  encodedUuid2.length(), "The encoded UUID should be 22 characters in length.");
    }

    @Test
    void testIdTranslator_maxValueLongs() {
        UUID uuid = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
        String encodedUuid = IdTranslator.encodeUuidToBase62(uuid);
        UUID decodedUuid = IdTranslator.decodeBase62ToUuid(encodedUuid);
        Assertions.assertEquals(uuid, decodedUuid, "The original and decoded UUID should match.");
        Assertions.assertEquals(22,  encodedUuid.length(), "The encoded UUID should be 22 characters in length.");
    }

    @Test
    void testIdTranslator_minValueLongs() {
        UUID uuid = new UUID(Long.MIN_VALUE, Long.MIN_VALUE);
        String encodedUuid = IdTranslator.encodeUuidToBase62(uuid);
        UUID decodedUuid = IdTranslator.decodeBase62ToUuid(encodedUuid);
        Assertions.assertEquals(uuid, decodedUuid, "The original and decoded UUID should match.");
        Assertions.assertEquals(22,  encodedUuid.length(), "The encoded UUID should be 22 characters in length.");
    }

    @Test
    void testIdTranslator_allZeroUUID() {
        UUID uuid = new UUID(0, 0);
        String encodedUuid = IdTranslator.encodeUuidToBase62(uuid);
        UUID decodedUuid = IdTranslator.decodeBase62ToUuid(encodedUuid);
        Assertions.assertEquals(uuid, decodedUuid, "The original and decoded UUID should match.");
        Assertions.assertEquals(22,  encodedUuid.length(), "The encoded UUID should be 22 characters in length.");
    }

    @Test
    void testIdTranslator_decodeNonBase62String() {
        String testString = "test";
        Exception exception = Assertions.assertThrows(InvalidIdException.class, () -> IdTranslator.decodeBase62ToUuid(testString));
        Assertions.assertTrue(exception.getMessage().contains("Incoming id does not appear to be base62 encoded."));
    }
}
