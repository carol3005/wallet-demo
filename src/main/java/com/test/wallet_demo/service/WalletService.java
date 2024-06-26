package com.test.wallet_demo.service;

import com.test.wallet_demo.model.Transaction;
import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.repository.TransactionRepository;
import com.test.wallet_demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet addAmount(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType("ADD");
        transaction.setDate(new Date());
        transaction.setDescription("Adding amount");
        transactionRepository.save(transaction);

        return wallet;
    }

    // Implement other methods for withdrawal, purchases, cancellation, refund, etc.

    public List<Transaction> getTransactions(Long walletId) {
        return transactionRepository.findAll();
    }
}