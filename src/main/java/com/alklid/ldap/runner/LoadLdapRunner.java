package com.alklid.ldap.runner;

import com.alklid.ldap.model.ADPerson;
import com.alklid.ldap.model.mapper.ADAuthenticateMapper;
import com.alklid.ldap.model.mapper.ADPersonContextMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.UncategorizedLdapException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import static com.alklid.ldap.common.Constant.Ldap.*;

@Component
@Slf4j
public class LoadLdapRunner implements ApplicationRunner {

    private final LdapTemplate ldapTemplate;

    public LoadLdapRunner(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }


    @Override
    public void run(ApplicationArguments args) {
        // application.yml 환경설정을 사용하지 않고 Dynamic 설정이 필요한 경우
        //  LdapContextSource 를 별도로 만들어서 LdapTemplate 에 주입
        /*
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://{your_ldap_domain}:389");
        contextSource.setBase("{your_ldap_base}");
        contextSource.setUserDn("{your_service_account}");
        contextSource.setPassword("{your_service_account_password}");
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate();
        ldapTemplate.setIgnorePartialResultException(true);
        ldapTemplate.setContextSource(contextSource);
        */

        // User info
        String email = "{user_email}";
        String password = "{user_password}";

        // authenticate
        String distinguishedName = null;
        try {
            distinguishedName = ldapTemplate.authenticate(
                    LdapQueryBuilder.query().timeLimit(1000)
                            .where(ATTR_KEY_OBJECT_CLASS).is(ATTR_VALUE_PERSON)
                            .and(ATTR_KEY_USER_PRINCIPAL_NAME).is(email), password, new ADAuthenticateMapper());
        }
        catch (EmptyResultDataAccessException | AuthenticationException | UncategorizedLdapException e) {
            log.info("Authentication Fail!!!");
        }

        // search
        ADPerson adPerson = ldapTemplate.searchForObject(
                        LdapQueryBuilder.query().searchScope(SearchScope.SUBTREE).timeLimit(3000)
                                .where(ATTR_KEY_OBJECT_CLASS).is(ATTR_VALUE_PERSON)
                                .and(ATTR_KEY_USER_PRINCIPAL_NAME).is(email)
                                .and(ATTR_KEY_DISTINGUISHED_NAME).is(distinguishedName), new ADPersonContextMapper());

        log.info(adPerson.toString());
    }
}
