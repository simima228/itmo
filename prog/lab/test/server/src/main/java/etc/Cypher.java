package etc;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Cypher {
    private static final String PEPPER = "POSXALKOPRIMITELABYPZH";
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] digest = md.digest((PEPPER + password + salt).getBytes());
            return bytesToHex(digest);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD2 не поддерживается(это врядли)", e);
        }
    }
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
        String inputHash = hashPassword(inputPassword, storedSalt);
        return inputHash.equals(storedHash);
    }

}
