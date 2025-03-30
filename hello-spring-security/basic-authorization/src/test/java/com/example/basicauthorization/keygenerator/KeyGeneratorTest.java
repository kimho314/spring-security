package com.example.basicauthorization.keygenerator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

@Slf4j
@SpringBootTest
public class KeyGeneratorTest {
    @Test
    void StringKeyGeneratorTest(){
        StringKeyGenerator stringKeyGenerator = KeyGenerators.string();
        String key = stringKeyGenerator.generateKey();
        log.info(key);
    }

    @Test
    void BytesKeyGeneratorTest(){
        BytesKeyGenerator bytesKeyGenerator = KeyGenerators.secureRandom(); // by default generate 8bytes key
        byte[] key = bytesKeyGenerator.generateKey();
        int keyLength = bytesKeyGenerator.getKeyLength();
        log.info("key={}, length={}", key, keyLength);
    }

    @Test
    void BytesKeyGeneratorTest_With16BytesLength(){
        BytesKeyGenerator bytesKeyGenerator = KeyGenerators.secureRandom(16); // by default generate 8bytes key
        byte[] key = bytesKeyGenerator.generateKey();
        int keyLength = bytesKeyGenerator.getKeyLength();
        log.info("key={}, length={}", key, keyLength);
    }
}
