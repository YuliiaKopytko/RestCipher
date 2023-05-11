package com.kopytko.service;

import com.kopytko.utils.AESUtils;
import com.kopytko.dao.UserRepository;
import com.kopytko.dto.EncryptUserDto;
import com.kopytko.dto.IdDto;
import com.kopytko.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final Logger log = LogManager.getLogger(this);

    public String getEncryptedFio(IdDto idDto) {
        String fio = this.userRepository.findById(idDto.id())
                .map(User::getFio)
                .orElse(null);
        String encryptPasswordBased = "";
        if (Objects.nonNull(fio)) {
            try {
                encryptPasswordBased = AESUtils.encrypt(fio);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException |
                     InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException |
                     BadPaddingException e) {
                log.error(e);
                return null;
            }
        }
        return encryptPasswordBased;
    }

    public String getDecryptedFio(EncryptUserDto encryptUserDto) {
        String decryptString;
        try {
            decryptString = AESUtils.decrypt(encryptUserDto.fio_encrypt());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeySpecException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException e) {
            log.error(e);
            return null;
        }
        return this.userRepository.findByFio(decryptString)
                .map(User::getFio)
                .orElse("");
    }
}
