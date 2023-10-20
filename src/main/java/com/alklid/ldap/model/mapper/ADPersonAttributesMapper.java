package com.alklid.ldap.model.mapper;

import com.alklid.ldap.model.ADPerson;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class ADPersonAttributesMapper implements AttributesMapper<ADPerson> {

    @Override
    public ADPerson mapFromAttributes(Attributes attributes) throws NamingException {
        ADPerson adPerson = ADPerson.from(attributes);
        return adPerson;
    }

}
