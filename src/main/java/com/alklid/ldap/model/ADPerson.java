package com.alklid.ldap.model;

import com.alklid.ldap.util.LdapUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import static com.alklid.ldap.common.Constant.Ldap.*;

@Getter
@Setter
@ToString
public class ADPerson {

    private String guid;
    private String sid;

    private String distinguishedName;
    private String name;
    private String email;


    public static ADPerson from(Attributes attributes) throws NamingException {
        ADPerson adPerson = new ADPerson();

        if (attributes.get(ATTR_KEY_OBJECT_GUID) != null) {
            String guid = LdapUtils.decodeObjectGuid(attributes.get(ATTR_KEY_OBJECT_GUID).get().toString().getBytes());
            adPerson.setGuid(guid);
        }

        if (attributes.get(ATTR_KEY_OBJECT_SID) != null) {
            String objectSid = LdapUtils.decodeObjectSid(attributes.get(ATTR_KEY_OBJECT_SID).get().toString().getBytes());
            adPerson.setSid(objectSid);
        }

        if (attributes.get(ATTR_KEY_DISTINGUISHED_NAME) != null) {
            adPerson.setDistinguishedName(StringUtils.defaultString((String) attributes.get(ATTR_KEY_DISTINGUISHED_NAME).get(), ""));
        }

        if (attributes.get(ATTR_KEY_NAME) != null) {
            adPerson.setName(StringUtils.defaultString((String) attributes.get(ATTR_KEY_NAME).get(), ""));
        }

        if (attributes.get(ATTR_KEY_USER_PRINCIPAL_NAME) != null) {
            adPerson.setEmail(StringUtils.defaultString((String) attributes.get(ATTR_KEY_USER_PRINCIPAL_NAME).get(), ""));
        }

        return adPerson;
    }

}
