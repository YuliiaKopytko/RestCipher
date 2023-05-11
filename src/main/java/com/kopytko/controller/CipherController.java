package com.kopytko.controller;

import com.kopytko.dto.DecryptUserDto;
import com.kopytko.dto.EncryptUserDto;
import com.kopytko.dto.IdDto;
import com.kopytko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CipherController {

    private final UserService userService;

    @PostMapping(path = "encrypt")
    public ResponseEntity<EncryptUserDto> getEncryptFio(@RequestBody IdDto idDto) {
        String encryptedFio = userService.getEncryptedFio(idDto);
        if (Objects.isNull(encryptedFio)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (encryptedFio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new EncryptUserDto(encryptedFio), HttpStatus.OK);
    }

    @PostMapping(path = "decrypt")
    public ResponseEntity<DecryptUserDto> getDecryptFio(@RequestBody EncryptUserDto encryptUserDto) {
        String decryptedFio = userService.getDecryptedFio(encryptUserDto);
        if (Objects.isNull(decryptedFio)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (decryptedFio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DecryptUserDto(decryptedFio), HttpStatus.OK);
    }

}
