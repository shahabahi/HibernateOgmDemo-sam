package com.rizvn.ogmdemo.conf;

//import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;
import org.hibernate.ogm.jpa.HibernateOgmPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Properties;

/**
 * @author Riz
 */
 @Configuration
public class DbConf {
  @Autowired
  private Environment env;
//  @Bean
//  LocalContainerEntityManagerFactoryBean entityManager() {
//    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
//    bean.setPersistenceUnitName("ogmdemo");
//    return bean;
//  }
//
//  @Bean
//  TransactionTemplate transactionTemplate(){
//    TransactionTemplate transactionTemplate = new TransactionTemplate();
//    transactionTemplate.setTransactionManager(platformTransactionManager());
//    return transactionTemplate;
//  }
//
//  @Bean
//  PlatformTransactionManager platformTransactionManager(){
//    JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//    TransactionManagerImple transactionManagerImple  = new TransactionManagerImple();
//    jtaTransactionManager.setTransactionManager(transactionManagerImple);
//    return jtaTransactionManager;
//  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager();
  }
  @Primary
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setPackagesToScan("com.rizvn.ogmdemo");
    sessionFactory.setHibernateProperties(hibernateOgmProperties());
    return sessionFactory;
  }

  private Properties hibernateOgmProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.ogm.datastore.provider", env.getRequiredProperty("hibernate.ogm.datastore.provider"));
    properties.put("hibernate.ogm.datastore.host", env.getRequiredProperty("hibernate.ogm.datastore.host"));
    properties.put("hibernate.ogm.datastore.port", env.getRequiredProperty("hibernate.ogm.datastore.port"));
    properties.put("hibernate.ogm.datastore.database", env.getRequiredProperty("hibernate.ogm.datastore.database"));
    properties.put("hibernate.ogm.datastore.create_database", env.getRequiredProperty("hibernate.ogm.datastore.create_database"));
    properties.put("org.hibernate.ogm.datastore_dialect", env.getProperty("org.hibernate.ogm.datastore_dialect"));
    return properties;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {

    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }


  @Bean
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }
}
