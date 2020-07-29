package com.dev.foodreservation.database.interfaces;

import com.dev.foodreservation.objects.Wallet;
import com.dev.foodreservation.objects.WalletTransaction;

import java.sql.SQLException;
import java.util.List;

public interface IntWallet {

    List<Wallet> get(long id) throws SQLException;

    boolean chargeUp(long id, double amount) throws SQLException;

    boolean makeTransaction(long id, double amount) throws SQLException;

    List<WalletTransaction> getTransactions(long id) throws SQLException;
}
