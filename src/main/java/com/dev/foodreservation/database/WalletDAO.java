package com.dev.foodreservation.database;

import com.dev.foodreservation.database.interfaces.IntWallet;
import com.dev.foodreservation.database.utilities.ProcedureExecutor;
import com.dev.foodreservation.objects.Wallet;
import com.dev.foodreservation.objects.WalletTransaction;
import com.github.mfathi91.time.PersianDate;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO implements IntWallet {

    private final Connection connection;
    private final Statement statement;
    private final Time time;
    private final Date date;

    public WalletDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        statement = connection.createStatement();
        time = Time.valueOf(LocalTime.now());
        date = Date.valueOf(PersianDate.now().toString());
    }

    @Override
    public List<Wallet> get(long id) throws SQLException {
        List<Wallet> wallets = new ArrayList<>();
        ResultSet resultSet;
        resultSet = statement.executeQuery(
                "EXEC [dbo].[GetBalance]" +
                        "@ri =" + id
        );
        while (resultSet.next()) wallets.add(walletRSV(resultSet));
        return wallets;
    }


    @Override
    public boolean chargeUp(long id, double amount) throws SQLException {
        return makeTransaction(id, amount);
    }

    @Override
    public boolean makeTransaction(long id, double amount) throws SQLException {
        ProcedureExecutor procedureExecutor =
                new ProcedureExecutor("MakeTransaction");
        procedureExecutor.addVariable("rii", id);
        procedureExecutor.addVariable("t", time);
        procedureExecutor.addVariable("d", date);
        procedureExecutor.addVariable("a", amount);
        return ExecuteUpdate(procedureExecutor) > 0;
    }

    @Override
    public List<WalletTransaction> getTransactions(long id) throws SQLException {
        List<WalletTransaction> transactions = new ArrayList<>();
        ResultSet resultSet;
        if (id == -1)
            resultSet = statement.executeQuery(
                    "EXEC [dbo].[GetAllTransactions]"
            );
        else
            resultSet = statement.executeQuery(
                    "EXEC [dbo].[GetTransactions]" +
                            "@ri =" + id
            );

        while (resultSet.next())
            transactions.add(walletTransactionsRSV(resultSet));
        return transactions;
    }

    private int ExecuteUpdate(ProcedureExecutor procedure) throws SQLException {
        return statement.executeUpdate(procedure.get());
    }

    private ResultSet Execute(ProcedureExecutor procedure) throws SQLException {
        return statement.executeQuery(procedure.get());
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

    private Wallet walletRSV(ResultSet set) throws SQLException {
        return new Wallet(set.getLong(1),
                set.getDouble("balance"));
    }
}
