package com.base.new_base.Cryptography;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashEncoder {
    public String SHA256(String data){
        return Hashing.sha256()
                .hashString(data, StandardCharsets.UTF_8)
                .toString();
    }
}