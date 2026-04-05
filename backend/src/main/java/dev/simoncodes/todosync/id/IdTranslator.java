package dev.simoncodes.todosync.id;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

public class IdTranslator {

    private static final String charString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char[] charArray = charString.toCharArray();

    private static final BigInteger SIXTY_TWO = BigInteger.valueOf(62L);

    public static String encodeUuidToBase62(UUID id) {
        if (id == null) return null;
        BigInteger bigInt = uuidToBigInteger(id);
        char[] result = new char[22];
        int index = 21;
        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            BigInteger modulus = bigInt.mod(SIXTY_TWO);
            result[index--] = charArray[modulus.intValue()];
            bigInt = bigInt.divide(SIXTY_TWO);
        }
        while (index >= 0) {
            result[index--] = charArray[0];
        }
        return new String(result);
    }

    public static UUID decodeBase62ToUuid(String id) {
        if (id == null) return null;
        char[] incoming = id.toCharArray();
        if (incoming.length != 22) {
            throw new InvalidIdException("Incoming id does not appear to be base62 encoded. Length: " + incoming.length + " Value: " + id);
        }
        BigInteger bigInt = BigInteger.ZERO;
        for (int i = 0; i < 22; i++) {
            BigInteger position = BigInteger.valueOf(charString.indexOf(incoming[i]));
            bigInt = bigInt.multiply(SIXTY_TWO).add(position);
        }

        return bigIntegerToUuid(bigInt);
    }

    private static BigInteger uuidToBigInteger(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        byte[] bytes = buffer.array();
        return new BigInteger(1, bytes);
    }

    private static UUID bigIntegerToUuid(BigInteger bigInteger) {
        byte[] bytes = bigInteger.toByteArray();
        byte[] result = new byte[16];
        int byteIndex = bytes.length - 1;
        for (int i = 15; i >= 0; i--) {
            if (byteIndex >= 0) {
                result[i] = bytes[byteIndex];
            }
            byteIndex--;
        }
        ByteBuffer buffer = ByteBuffer.wrap(result);
        long valueOne = buffer.getLong();
        long valueTwo = buffer.getLong();
        return new UUID(valueOne, valueTwo);
    }
}
