package com.dbc.walletapi.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication  getAuthentication();
}
