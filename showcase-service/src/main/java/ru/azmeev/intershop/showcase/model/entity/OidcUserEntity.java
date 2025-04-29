package ru.azmeev.intershop.showcase.model.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;

public abstract class OidcUserEntity extends BaseEntity implements OidcUser, UserDetails {

    @Transient
    protected OidcUser delegate;

    public OidcUser getDelegate() {
        return delegate;
    }

    public void setDelegate(OidcUser delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transient
    public Map<String, Object> getClaims() {
        return delegate.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return delegate.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return delegate.getIdToken();
    }

    @Override
    @Transient
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
