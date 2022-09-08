<<<<<<<< HEAD:src/main/java/com/jo2seo/aomd/controller/auth/dto/KakaoLoginRequest.java
package com.jo2seo.aomd.controller.auth.dto;
========
package jo2seo.aomd.api.auth.dto;
>>>>>>>> refactor1:api/src/main/java/jo2seo/aomd/api/auth/dto/KakaoLoginRequest.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginRequest {
    @Nullable
    private String code;
    @Nullable
    private String callbackUrl;
    
}
