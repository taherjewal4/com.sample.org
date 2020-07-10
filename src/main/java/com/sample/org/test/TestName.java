package com.sample.org.test;


import com.sample.org.model.ServiceType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestName extends Base {


    @BeforeClass
    public void checkServiceHealth() {
        Response response = restHttpUtils.getRequest("eesti", ServiceType.NANE);
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testGetAllWithHttpOkStatus() {
        Response response = restHttpUtils.getRequest(ServiceType.All);
        response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();
    }

    @Test
    public void testEstoniaCapitalWithPartialName() {
        String name = "eesti";
        String capital = "[Tallinn]";
        Response response = restHttpUtils.getRequest(name, ServiceType.NANE);
        JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();

        Assert.assertEquals(jsonPath.getString("name"), "[Estonia]");
        Assert.assertEquals(jsonPath.getString("capital"), capital);
    }

    @Test
    public void testEstoniaCapitalWithFullName() {
        String name = "Estonia";
        String capital = "[Tallinn]";
        Response response = restHttpUtils.getRequest(name, ServiceType.NANE);
        JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();

        Assert.assertEquals(jsonPath.getString("name"), "[Estonia]");
        Assert.assertEquals(jsonPath.getString("capital"), capital);
    }


    @Test
    public void checkNameFieldShouldNotBeEmpty() {
        String name = "Albania";
        Response response = restHttpUtils.getRequest(name, ServiceType.NANE);
        JsonPath jsonPath = response.then().statusCode(HttpStatus.SC_OK).extract().jsonPath();

        Assert.assertNotEquals(jsonPath.getString("name"), "");

        Assert.assertEquals(jsonPath.getString("name"), "[Albania]");
    }


    @Test
    public void CheckInvalidUserNameShouldNotReturn200() {
        String name = "ytrytrytrturt";
        Response response = restHttpUtils.getRequest(name, ServiceType.NANE);
        response.then().statusCode(HttpStatus.SC_NOT_FOUND).extract().jsonPath();
    }


}
