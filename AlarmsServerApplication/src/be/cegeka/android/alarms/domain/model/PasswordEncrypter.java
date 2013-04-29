package be.cegeka.android.alarms.domain.model;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordEncrypter {

    /**
     * chars gebruikt om salt te maken.
     */
    private static char chars[] = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
        'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'M',
        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '&', 'é', '\"', '\'', '(',
        '!', 'ç', ')', '-', '|', '@', '#', '^', '{', '}', '_', '*', '$', '[',
        ']', '%', '?', ',', '.', ';', '/', ':', '+', '=', '~', '<', '>', '\\'};
    /**
     * lengte van een hash.
     */
    private int hashLength;
    /**
     * lengte van een salt
     */
    private int saltLength;
    /**
     * Random gebruikt voor salt te maken.
     */
    private static SecureRandom random = new SecureRandom();

    /**
     * encrypteer een paswoord met een hashlengte van 32 en een nieuwe salt van
     * 20 karakters lang
     *
     * @param password het te encrypteren passwoord
     * @return EncryptionResult
     */
    public static EncryptionResult encryptNewPassword(String password) {
        PasswordEncrypter e = new PasswordEncrypter(32, 20);
        return e.encrypt(password);
    }

    /**
     * controleert of een paswoord en een salt overeen komen met een hash
     *
     * @param hash de hash van het correct paswoord en de salt
     * @param password het na te kijken paswoord
     * @param salt de salt die gebruikt wordt voor deze gebruiker.
     * @return true indien correct
     */
    public static boolean isCorrect(String hash, String password, String salt) {
        PasswordEncrypter e = new PasswordEncrypter(32, 20);
        EncryptionResult res = e.encrypt(password, salt);
        return res.hash.equals(hash);
    }

    /**
     * initializeert de passwordEncrypter.
     *
     * @param hashLength lengte van een hash
     * @param saltLength lengte van een salt
     */
    public PasswordEncrypter(int hashLength, int saltLength) {
        if (hashLength < 1) {
            throw new IllegalArgumentException("hashLength moet groter of gelijk aan 0 zijn");
        }
        if (saltLength < 0) {
            throw new IllegalArgumentException("saltLength moet positief zijn");
        }
        this.hashLength = hashLength;
        this.saltLength = saltLength;
    }

    /**
     * encrypteer een paswoord en maak een nieuwe salt
     *
     * @param password het te encrypteren paswoord != null, != empty
     * @return EncryptionResult
     */
    public EncryptionResult encrypt(String password) {
        StringBuilder salt = new StringBuilder(saltLength);
        for (int i = 0; i < saltLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            salt.append(c);
        }
        return encrypt(password, salt.toString());
    }

    /**
     * Bereken de hash van een paswoord en een salt.
     *
     * @param password != null, != empty
     * @param salt != null
     * @return EncryptionResult
     */
    public EncryptionResult encrypt(String password, String salt) {
        if (password == null) {
            throw new NullPointerException("password mag niet null zijn");
        }
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("password mag niet leeg zijn");
        }
        if (salt == null) {
            throw new NullPointerException("salt mag niet null zijn");
        }

        String plaintext = password + salt;
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (Exception ex) {
            throw new RuntimeException("MD5 not supported...", ex);
        }
        m.reset();
        m.update(plaintext.getBytes());

        byte[] digest = m.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        if (hashtext.length() > hashLength) {
            hashtext = hashtext.substring(0, hashLength);
        }

        while (hashtext.length() < hashLength) {
            hashtext = "0" + hashtext;
        }
        return new EncryptionResult(password, salt, hashtext);


    }

    public class EncryptionResult {

        private String password;
        private String salt;
        private String hash;

        private EncryptionResult(String password, String salt, String hash) {
            this.password = password;
            this.salt = salt;
            this.hash = hash;
        }

        /**
         *
         * @return het paswoord
         */
        public String getPassword() {
            return password;
        }

        /**
         * @return de salt, mogelijk nieuw gegenereerd
         */
        public String getSalt() {
            return salt;
        }

        /**
         *
         * @return de berekende hash op basis van het paswoord en de hash
         */
        public String getHash() {
            return hash;
        }
    }
}
