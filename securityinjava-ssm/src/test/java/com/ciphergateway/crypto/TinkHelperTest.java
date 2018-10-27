package com.ciphergateway.crypto;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.quickbundle.config.RmConfig;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.DeterministicAead;
import com.google.crypto.tink.HybridDecrypt;
import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.Mac;
import com.google.crypto.tink.PublicKeySign;
import com.google.crypto.tink.PublicKeyVerify;
import com.google.crypto.tink.aead.AeadFactory;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.daead.DeterministicAeadFactory;
import com.google.crypto.tink.daead.DeterministicAeadKeyTemplates;
import com.google.crypto.tink.hybrid.HybridDecryptFactory;
import com.google.crypto.tink.hybrid.HybridEncryptFactory;
import com.google.crypto.tink.hybrid.HybridKeyTemplates;
import com.google.crypto.tink.mac.MacFactory;
import com.google.crypto.tink.mac.MacKeyTemplates;
import com.google.crypto.tink.signature.PublicKeySignFactory;
import com.google.crypto.tink.signature.PublicKeyVerifyFactory;
import com.google.crypto.tink.signature.SignatureKeyTemplates;

public class TinkHelperTest {
    byte[] plaintext = "12345678".getBytes();
    byte[] contextInfo = "abcdefgh".getBytes();
    byte[] aad = "asdfghjk".getBytes();
    byte[] data = "data1234".getBytes();
    String keysetFilename = RmConfig.getSingleton().getDefaultTempDirWithMkdirs() + File.separator + "my_keyset.json";
    
    @Rule
    public TestRule init = new TestWatcher() {
       protected void starting(Description description) {
           try {
            TinkConfig.register();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
          System.out.println("--------" + description.getDisplayName() + "--------");
       }
    };

    @Test
    public void storingKeysets() throws GeneralSecurityException, IOException {
        // Generate the key material...
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES256_GCM);

        // and write it to a file.
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(keysetFilename)));

    }

    @Test
    public void storingKeysets2() throws GeneralSecurityException, IOException {
//        // Generate the key material...
//        KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);
//
//        // and write it to a file...
//        // encrypted with the this key in GCP KMS
//        String masterKeyUri = "gcp-kms://projects/tink-examples/locations/global/keyRings/foo/cryptoKeys/bar";
//        keysetHandle.write(JsonKeysetWriter.withFile(new File(keysetFilename)),
//                new GcpKmsClient().getAead(masterKeyUri));
    }

    @Test
    public void loadingExistingKeysets() throws GeneralSecurityException, IOException {
//        // The keyset is encrypted with the this key in AWS KMS.
//        String masterKeyUri = "aws-kms://arn:aws:kms:us-east-1:007084425826:key/84a65985-f868-4bfc-83c2-366618acf147";
//        KeysetHandle keysetHandle = KeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFilename)),
//                new AwsKmsClient().getAead(masterKeyUri));
    }

    @Test
    public void loadingExistingKeysets2() throws GeneralSecurityException, IOException {
        storingKeysets();
        KeysetHandle keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFilename)));
    }

    @Test
    public void symmetricKeyEncryption() throws GeneralSecurityException {
        // 1. Generate the key material.
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);

        // 2. Get the primitive.
        Aead aead = AeadFactory.getPrimitive(keysetHandle);

        // 3. Use the primitive to encrypt a plaintext,
        byte[] ciphertext = aead.encrypt(plaintext, aad);

        // ... or to decrypt a ciphertext.
        byte[] decrypted = aead.decrypt(ciphertext, aad);
    }

    @Test
    public void deterministicSymmetricKeyEncryption() throws GeneralSecurityException {
        // 1. Generate the key material.
        KeysetHandle keysetHandle = KeysetHandle.generateNew(DeterministicAeadKeyTemplates.AES256_SIV);

        // 2. Get the primitive.
        DeterministicAead daead = DeterministicAeadFactory.getPrimitive(keysetHandle);

        // 3. Use the primitive to deterministically encrypt a plaintext,
        byte[] ciphertext = daead.encryptDeterministically(plaintext, aad);

        // ... or to deterministically decrypt a ciphertext.
        byte[] decrypted = daead.decryptDeterministically(ciphertext, aad);
    }

    @Test
    public void symmetricKeyEncryptionOfStreamingData() {
//     // 1. Generate the key material.
//        KeysetHandle keysetHandle = KeysetHandle.generateNew(
//            StreamingAeadKeyTemplates.AES128_CTR_HMAC_SHA256_4KB);
//
//        // 2. Get the primitive.
//        StreamingAead streamingAead = StreamingAeadFactory.getPrimitive(keysetHandle);
//
//        // 3. Use the primitive to encrypt some data and write the ciphertext to a file,
//        FileChannel ciphertextDestination = new FileOutputStream(ciphertextFileName).getChannel();
//        WritableByteChannel encryptingChannel =
//            streamingAead.newEncryptingChannel(ciphertextDestination, aad);
//        int chunkSize = 1024 * 1024;
//        ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
//        while ( bufferContainsDataToEncrypt ) {
//          int r = encryptingChannel.write(buffer);
//          // Try to get into buffer more data for encryption.
//        }
//        // Complete the encryption (process the remaining plaintext, if any, and close the channel).
//        encryptingChannel.close();
//
//        // ... or to decrypt an existing ciphertext stream.
//        FileChannel ciphertextSource = new FileInputStream(ciphertextFileName).getChannel();
//        ReadableByteChannel decryptingChannel = s.newDecryptingChannel(ciphertextSource, aad);
//        ByteBuffer buffer = ByteBuffer.allocate(chunkSize);
//        do {
//          buffer.clear();
//          int cnt = decryptingChannel.read(buffer);
//          if (cnt > 0) {
//            // Process cnt bytes of plaintext.
//          } else if (read == -1) {
//            // End of plaintext detected.
//            break;
//          } else if (read == 0) {
//            // No ciphertext is available at the moment.
//          }
    }

    @Test
    public void messageAuthenticationCode() throws GeneralSecurityException {
        // 1. Generate the key material.
        KeysetHandle keysetHandle = KeysetHandle.generateNew(MacKeyTemplates.HMAC_SHA256_128BITTAG);

        // 2. Get the primitive.
        Mac mac = MacFactory.getPrimitive(keysetHandle);

        // 3. Use the primitive to compute a tag,
        byte[] tag = mac.computeMac(data);

        // ... or to verify a tag.
        mac.verifyMac(tag, data);
    }

    @Test
    public void digitialSignatures() throws GeneralSecurityException {
        // SIGNING

        // 1. Generate the private key material.
        KeysetHandle privateKeysetHandle = KeysetHandle.generateNew(SignatureKeyTemplates.ECDSA_P256);

        // 2. Get the primitive.
        PublicKeySign signer = PublicKeySignFactory.getPrimitive(privateKeysetHandle);

        // 3. Use the primitive to sign.
        byte[] signature = signer.sign(data);

        // VERIFYING

        // 1. Obtain a handle for the public key material.
        KeysetHandle publicKeysetHandle = privateKeysetHandle.getPublicKeysetHandle();

        // 2. Get the primitive.
        PublicKeyVerify verifier = PublicKeyVerifyFactory.getPrimitive(publicKeysetHandle);

        // 4. Use the primitive to verify.
        verifier.verify(signature, data);
    }

    @Test
    public void hybridEncryption() throws GeneralSecurityException {

        // 1. Generate the private key material.
        KeysetHandle privateKeysetHandle = KeysetHandle
                .generateNew(HybridKeyTemplates.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM);

        // Obtain the public key material.
        KeysetHandle publicKeysetHandle = privateKeysetHandle.getPublicKeysetHandle();

        // ENCRYPTING

        // 2. Get the primitive.
        HybridEncrypt hybridEncrypt = HybridEncryptFactory.getPrimitive(publicKeysetHandle);

        // 3. Use the primitive.
        byte[] ciphertext = hybridEncrypt.encrypt(plaintext, contextInfo);

        // DECRYPTING

        // 2. Get the primitive.
        HybridDecrypt hybridDecrypt = HybridDecryptFactory.getPrimitive(privateKeysetHandle);

        // 3. Use the primitive.
        byte[] plaintextResult = hybridDecrypt.decrypt(ciphertext, contextInfo);
        System.out.println("plaintextResult=" + new String(plaintextResult));
        assertArrayEquals(plaintextResult, plaintext);
    }

    @Test
    public void envelopeEncryption() throws GeneralSecurityException {

        // // 1. Generate the key material.
        // String kmsKeyUri =
        // "gcp-kms://projects/tink-examples/locations/global/keyRings/foo/cryptoKeys/bar";
        // KeysetHandle keysetHandle = KeysetHandle
        // .generateNew(AeadKeyTemplates.createKmsEnvelopeAeadKeyTemplate(kmsKeyUri,
        // AeadKeyTemplates.AES128_GCM));
        //
        // // 2. Register the KMS client.
        // KmsClients.add(new GcpKmsClient().withCredentials("credentials.json"));
        //
        // // 3. Get the primitive.
        // Aead aead = AeadFactory.getPrimitive(keysetHandle);
        //
        // // 4. Use the primitive.
        // byte[] ciphertext = aead.encrypt(plaintext, aad);
    }

    @Test
    public void keyRotation() throws GeneralSecurityException {
//        KeysetHandle keysetHandle = ...;   // existing keyset
//        KeyTemplate keyTemplate = ...;     // template for the new key
//
//        KeysetHandle rotatedKeysetHandle = KeysetManager
//            .withKeysetHandle(keysetHandle)
//            .rotate(keyTemplate)
//            .getKeysetHandle();
    }
}
