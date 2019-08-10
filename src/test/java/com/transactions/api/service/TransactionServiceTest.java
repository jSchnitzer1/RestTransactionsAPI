package com.transactions.api.service;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.transactions.api.model.TransactionTest;
import com.transactions.api.serialization.JsonObjectDeserializerTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class TransactionServiceTest extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TransactionService.class);
    }

    @Test
    public void createTransaction() {
        createTransaction("createTransaction", 1, "1e754a06-f894-441f-b509-f2f7850bf2e5");
    }

    @Test
    public void getTransactions() {
        createTransaction("getTransactions", 777, "2e754a06-f894-441f-b509-f2f7850bf2e5");
        Response response = target("/transaction/getTransactions/777").request().get();
        InputStream data = testResponse(response, "getTransactions");

        List<TransactionTest> transactions = null;
        try {
            transactions = JsonObjectDeserializerTest.jsonToTransactions(new InputStreamReader(data, "UTF-8"));
        } catch (IOException e) {
            fail("IOException in getTransactions: " + e.getMessage());
        } catch (ParseException e) {
            fail("ParseException in getTransactions: " + e.getMessage());
        }

        assertNotNull("getTransactions - list of customers are not null or empty", transactions);
        Optional<TransactionTest> oTransaction = transactions.stream().filter(tx -> tx.getAccountId() == 777).findFirst();
        assertTrue("getTransactions - Account (with id 777) should be has a new transaction in the database", oTransaction.isPresent());
    }

    @Test
    public void deleteTransaction() {
        createTransaction("deleteTransaction", 999, "9e754a06-f894-441f-b509-f2f7850bf2e5");
        Response response = target("/transaction/deleteTransaction/999/9e754a06-f894-441f-b509-f2f7850bf2e5").request().delete();
        testResponseStatus(response, "deleteTransaction");
    }

    private void testResponseStatus(Response response, String methodName) {
        int status = response.getStatus();
        assertEquals(methodName + " - response status is: 200", status, 200);
    }

    private InputStream testResponse(Response response, String methodName) {
        InputStream data = (InputStream) response.getEntity();
        assertNotNull(methodName + " response data should not be null", data);
        return data;
    }

    private void createTransaction(String methodName, int accountId, String UUID) {
        Response response = target("/transaction/createTransaction/" + accountId + "/75.7/" + UUID).request().post(Entity.json(null));
        testResponseStatus(response, methodName);
    }
}
