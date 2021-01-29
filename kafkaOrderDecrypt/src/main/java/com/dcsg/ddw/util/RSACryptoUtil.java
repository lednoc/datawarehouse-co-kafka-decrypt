package com.dcsg.ddw.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSACryptoUtil {


  private KeyFactory keyFactory;
  private PrivateKey privateKey;


  public RSACryptoUtil(
      String privateKeyClassPathResource
  ) throws Exception {
    setKeyFactory();
    setPrivateKey(privateKeyClassPathResource);
  }

  protected void setKeyFactory() throws Exception {
    this.keyFactory = KeyFactory.getInstance("RSA");
  }

  protected void setPrivateKey(String classpathResource)
      throws Exception {
    InputStream is = this
        .getClass()
        .getClassLoader()
        .getResourceAsStream(classpathResource);

    String stringBefore
        = new String(is.readAllBytes());
    is.close();

    byte[] decoded = Base64
        .getDecoder()
        .decode(stringBefore);

    KeySpec keySpec
        = new PKCS8EncodedKeySpec(decoded);

    privateKey = keyFactory.generatePrivate(keySpec);
  }

  public String decryptFromBase64(String base64EncodedEncryptedBytes) {
    String plainText = null;
    try {
      final Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      byte[] decoded = Base64
          .getDecoder()
          .decode(base64EncodedEncryptedBytes);
      byte[] decrypted = cipher.doFinal(decoded);
      plainText = new String(decrypted);
      //log.debug("Decrypted using private key {}" , plainText);
    } catch (Exception ex) {
      log.error("Error in decrypting data using RSA private key {}" , ex);
    }
    return plainText;
  }
}
