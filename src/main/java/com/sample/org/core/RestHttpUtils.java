package com.sample.org.core;

import com.sample.org.model.ServiceType;
import com.sample.org.test.ExecutionProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sample.org.constant.TestConstants.*;
import static io.restassured.RestAssured.given;

@Data
@Component
public class RestHttpUtils {

    private String baseUrl;

    @Autowired
    public RestHttpUtils(ExecutionProperties properties) {
        RestAssured.config();
        baseUrl = properties.getBaseUrl();
    }

    private RequestSpecification requestSpecification() {
        return given().baseUri(baseUrl);
    }

    private String getBaseUrl(ServiceType serviceType) {
        if (serviceType.equals(ServiceType.NANE)) {
            return NAME_ENDPOINT;
        } else if (serviceType.equals(ServiceType.All)) {
            return ALL_ENDPOINT;
        } else {
            return CODE_ENDPOINT;
        }
    }

    public Response getRequest(String name, ServiceType serviceType) {
        return requestSpecification()
                .contentType(ContentType.JSON)
                .pathParam(serviceType.getValue(), name)
                .when()
                .get(getBaseUrl(serviceType))
                .prettyPeek();
    }


    public Response getRequest(ServiceType serviceType) {
        return requestSpecification()
                .contentType(ContentType.JSON)
                .when()
                .get(getBaseUrl(serviceType))
                .prettyPeek();
    }
}