package com.company.coursemanager.utils;

import java.util.Map;

public interface IAuthenticatedUserService {
    Long getUserId();

    Map<String, Object> getAttributes();
}