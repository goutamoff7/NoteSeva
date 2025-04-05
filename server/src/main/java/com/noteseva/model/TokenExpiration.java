package com.noteseva.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class TokenExpiration {

    private long accessTokenExpiration=86400000L; // 1 day in millisecond

    private long refreshTokenExpiration=604800000L; // 7 day in millisecond
}
