package com.alklid.ldap.model.mapper;

import com.alklid.ldap.model.ADPerson;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class ADPersonContextMapper implements ContextMapper<ADPerson> {

    @Override
    public ADPerson mapFromContext(Object ctx) throws NamingException {
        DirContextAdapter contextAdapter = (DirContextAdapter) ctx;
        Attributes attributes = contextAdapter.getAttributes();

        ADPerson adPerson = ADPerson.from(attributes);
        return adPerson;
    }

}
