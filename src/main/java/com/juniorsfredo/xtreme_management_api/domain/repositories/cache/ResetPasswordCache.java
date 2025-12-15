package com.juniorsfredo.xtreme_management_api.domain.repositories.cache;

public interface ResetPasswordCache {

    String getCode(Long userId);

    void saveCode(Long userId, String code);

    void deleteCode(Long userId);
}
