package com.dev.foodreservation.database;

import com.dev.foodreservation.objects.Wallet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO {

    private final Connection connection;
    private final Statement Statement;

    public WalletDAO() throws SQLException {
        connection = new DatabaseConnection().connect();
        Statement = connection.createStatement();
    }

    public List<Wallet> getBalance(long id) throws SQLException {
        List<Wallet> wallets = new ArrayList<>();
        ResultSet resultSet;
        resultSet = Statement.executeQuery(
                "EXEC [dbo].[GetBalance]" +
                        "@ri =" + id
        );
        while (resultSet.next()) wallets.add(walletRSV(resultSet));
        return wallets;
    }

    public boolean updateBalance(long id, double amount) throws SQLException {
        return Statement.executeUpdate(
                "EXEC [dbo].[UpdateBalance]" +
                        "@ri ="+id+", " +
                        "@b ="+amount
        ) > 0;
    }

    private Wallet walletRSV(ResultSet set) throws SQLException {
        return new Wallet(set.getLong(1),
                set.getDouble("balance"));
    }
}
