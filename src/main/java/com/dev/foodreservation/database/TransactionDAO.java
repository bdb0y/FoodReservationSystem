package com.dev.foodreservation.database;

import com.dev.foodreservation.objects.WalletTransaction;
import com.github.mfathi91.time.PersianDate;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private final Connection connection;
    private final Statement Statement;
    private final Time time;
    private final Date date;

    public TransactionDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        Statement = connection.createStatement();
        time = Time.valueOf(LocalTime.now());
        date = Date.valueOf(PersianDate.now().toString());
    }

    public boolean makeTransaction(long id, double amount) throws SQLException {
        return Statement.executeUpdate(
                "EXEC [dbo].[MakeTransation]" +
                        "@rii =" + id + "," +
                        "@t =" + time + ", " +
                        "@d =" + date + "," +
                        "@a =" + amount
        ) > 0;
    }

    public List<WalletTransaction> getTransactions(long id) throws SQLException {
        List<WalletTransaction> transactions = new ArrayList<>();
        ResultSet resultSet;
        if (id == -1)
            resultSet = Statement.executeQuery(
                    "EXEC [dbo].[GetAllTransactions]"
            );
        else
            resultSet = Statement.executeQuery(
                    "EXEC [dbo].[GetTransactions]" +
                            "@ri =" + id
            );

        while (resultSet.next())
            transactions.add(walletTransactionsRSV(resultSet));
        return transactions;
    }

    private WalletTransaction
    walletTransactionsRSV(ResultSet resultSet) throws SQLException {
        return new WalletTransaction(
                resultSet.getLong(1),
                resultSet.getLong(2),
                resultSet.getDate(3),
                resultSet.getTime(4),
                resultSet.getDouble(5)
        );
    }
}
