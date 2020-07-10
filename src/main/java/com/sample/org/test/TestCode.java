package com.sample.org.test;

import com.sample.org.model.ServiceType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TestCode extends Base {


    @BeforeClass
    public void checkServiceUpAndRunning() {
        String code = "AD";
        Response response = restHttpUtils.getRequest(code, ServiceType.CODE);
        response.then().statusCode(HttpStatus.SC_OK);
    }


    @AfterClass
    public void tearDown(){

    }

    @Test
    public void validateAllCountryCodeWithAlpha() {
        List<String> countryCodes = Arrays.asList(Locale.getISOCountries());
        countryCodes.stream().forEach(code -> {
            try {
                Response response = restHttpUtils.getRequest(code, ServiceType.CODE);
                response.then().statusCode(HttpStatus.SC_OK);
                JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();
                System.out.println(String.format("Running validation for : %s", code));
                Assert.assertEquals(jsonPath.getString("alpha2Code"), code);
            } catch (Exception e) {
                e.getMessage();
            }
        });
    }

    @Test
    public void testEstoniaCapital() {
        String code = "AD";
        List<String> mandatoryFiled = Arrays
                .asList("name", "capital", "region", "demonym", "nativeName", "languages");
        mandatoryFiled.stream()
                .forEach(field -> {
                    Response response = restHttpUtils.getRequest(code, ServiceType.CODE);
                    JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();

                    Assert.assertNotEquals(jsonPath.getString(field), "");
                });
    }


    @Test
    public void checkNameFieldShouldNotBeEmpty() {
        String name = "Albania";
        Response response = restHttpUtils.getRequest(name, ServiceType.NANE);
        JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();

        String flag = jsonPath.getString("flag");

        boolean isLink = flag.startsWith("[https://");
        Assert.assertTrue(isLink);
    }

}
