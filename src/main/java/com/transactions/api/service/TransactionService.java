package com.transactions.api.service;

import com.transactions.api.database.DatabaseManager;
import com.transactions.api.database.DatabaseManagerImpl;
import com.transactions.api.database.TransactionStatus;
import com.transactions.api.model.error.ErrorMessage;
import com.transactions.api.model.dto.TransactionDTO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.*;

import java.util.List;

@Path("/transaction")
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
    private static final DatabaseManager DBMANAGER = DatabaseManagerImpl.getInstance();
    @Context private ServletContext context;

    @PostConstruct
    public void postConst() {
        DBMANAGER.initDatabase();
        ConfigureLogger(context);
    }

    @PreDestroy
    public void preDestroy() {
        DBMANAGER.destroyDatabase();
    }

    /**
     * creates a transaction against a specific account
     * @param accountId the account that the transaction is created for
     * @param transactionAmount the amount to be deducted
     * @param transactionUUID The assigned UUID for the transaction
     * @return a response whether the transaction created or not
     */
    @POST
    @Path("/createTransaction/{accountId}/{transactionAmount}/{transactionUUID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(@PathParam("accountId") int accountId, @PathParam("transactionAmount") double transactionAmount, @PathParam("transactionUUID") String transactionUUID) {
        LOGGER.info("createTransaction is triggered");
        String errorMessageStr = "Internal database error in creating a new transaction for accountId" + accountId;
        return createOrDeleteTransaction(accountId, DBMANAGER.createTransaction(accountId, transactionAmount, transactionUUID), errorMessageStr);
    }

    /**
     * retrieve transactions for the account
     * @param accountId get all transactions for the account by its id
     * @return list of json serialized transactions
     */
    @GET
    @Path("/getTransactions/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransactionDTO> getTransactions(@PathParam("accountId") int accountId) {
        LOGGER.info("getTransactions is triggered");
        return DBMANAGER.getTransactions(accountId);
    }

    /**
     * delete a transaction by its uuid, and account id
     * @param accountId the account used for this transaction
     * @param transactionUUID the transaction uuid
     * @return ok if deleted successfully, otherwise the specified error message
     */
    @DELETE
    @Path("/deleteTransaction/{accountId}/{transactionUUID}")
    public Response deleteTransaction(@PathParam("accountId") int accountId, @PathParam("transactionUUID") String transactionUUID) {
        LOGGER.info("deleteTransaction is triggered");
        String errorMessageStr = "Internal database error in deleting the transaction of transactionUUID" + transactionUUID;
        return createOrDeleteTransaction(accountId, DBMANAGER.deleteTransaction(accountId, transactionUUID), errorMessageStr);
    }

    private Response createOrDeleteTransaction(int accountId, TransactionStatus transaction, String errorMessageStr) {
        JSONObject json = null;
        TransactionStatus ts = transaction;
        if (ts.getResult() == TransactionStatus.TransactionResult.SUCCESS) {
            json = new JSONObject();
            json.put("result", "success");
            json.put("message", ts.getMessage());
            return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
        }
        ErrorMessage errorMessage = new ErrorMessage(errorMessageStr, 500, ts.getMessage());
        return Response.serverError().entity(errorMessage).build();
    }

    private static void ConfigureLogger(ServletContext context) {
        if(context != null) {
            String log4jConfigPath = context.getRealPath("WEB-INF/log4j.properties");
            PropertyConfigurator.configure(log4jConfigPath);
        }
    }
}
