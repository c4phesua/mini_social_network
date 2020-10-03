package tests;

public class sha256Testing {
    public static void main(String[] args) {
        String sha256Psw = org.apache.commons.codec.digest.DigestUtils.sha256Hex("một hai ba bốn");
        String sha256Psw2= org.apache.commons.codec.digest.DigestUtils.sha256Hex("một hai ba bốn");
        System.out.println(sha256Psw);
        System.out.println(sha256Psw2);
        System.out.println(sha256Psw.equals(sha256Psw2));
    }
}
