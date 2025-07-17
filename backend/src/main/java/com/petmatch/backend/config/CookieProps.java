package com.petmatch.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.cookie")
public class CookieProps {

    private String domain;
    private long   maxAge;   // segundos
    private boolean secure;

    public String  getDomain()        { return domain; }
    public long    getMaxAge()        { return maxAge; }
    public boolean isSecure()         { return secure; }
    public void    setDomain(String d){ this.domain = d; }
    public void    setMaxAge(long m)  { this.maxAge = m; }
    public void    setSecure(boolean s){ this.secure = s; }
}

