package com.alklid.ldap.model.mapper;

import org.springframework.ldap.core.AuthenticatedLdapEntryContextMapper;
import org.springframework.ldap.core.LdapEntryIdentification;

import javax.naming.directory.DirContext;

public class ADAuthenticateMapper implements AuthenticatedLdapEntryContextMapper<String> {

    @Override
    public String mapWithContext(DirContext ctx, LdapEntryIdentification ldapEntryIdentification) {
        return ldapEntryIdentification.getAbsoluteName().toString();
    }

}
