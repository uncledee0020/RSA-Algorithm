import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.crypto.Cipher;

public class PasswordEncryption {

    public static void main(String[] args) throws Exception {
        // Load the public key certificate
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream("C:\\Users\\Donard.Musili\\public_key_certificate.crt");
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
        fis.close(); // Close the FileInputStream after use

        // Get the public key from the certificate
        PublicKey publicKey = certificate.getPublicKey();

        // Password to be encrypted
        String password = "ncba";

        // Encrypt the password using RSA with PKCS #1.5 padding
        byte[] encryptedBytes = encrypt(password, publicKey);

        // Convert the encrypted byte array to a base64 encoded string
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

        // Present the base64 encoded string in the API request as the initiator SecurityCredential value
        System.out.println("Initiator SecurityCredential value: " + encryptedBase64);
    }

    public static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }
}
