package com.transactions.api.serialization;

import com.transactions.api.model.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class JsonObjectDeserializerTest {
    private static final Logger LOGGER = Logger.getLogger(JsonObjectDeserializerTest.class.getName());
    public static List<TransactionTest> jsonToTransactions(Reader reader) throws IOException, ParseException {
        List<TransactionTest> transactions = new ArrayList<>();
        JSONParser parser=new JSONParser();
        Object object = parser.parse(reader);

        JSONArray array = (JSONArray) object;
        array.forEach(tx -> {
            JSONObject jTransaction = (JSONObject) tx;
            TransactionTest transaction = new TransactionTest((long) jTransaction.get("transactionId"), (double) jTransaction.get("transactionAmount"), (String)jTransaction.get("transactionUUID"), (long) jTransaction.get("accountId"));
            transactions.add(transaction);
        });

        return transactions.size() > 0 ? transactions : null;
    }

}
