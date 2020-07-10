package com.sample.org.test;


import com.sample.org.core.RestHttpUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@Data
@Configuration
@ContextConfiguration("classpath:context.xml")
@PropertySource({"classpath:application.properties"})
public class Base extends AbstractTestNGSpringContextTests {

    @Autowired
    RestHttpUtils restHttpUtils;

    @Autowired
    ExecutionProperties properties;

    @BeforeSuite
    public void beforeSuit() {
        System.out.println("START");
    }

    @AfterSuite
    public void afterSuit() {
        System.out.println("END");
    }

}