package com.test.wallet_demo.service;

import com.test.wallet_demo.model.Transaction;
import com.test.wallet_demo.model.TransactionType;
import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.repository.TransactionRepository;
import com.test.wallet_demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        wallet.setTransactions(new ArrayList<>());
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet depositAmount(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        wallet.setBalance(wallet.getBalance().add(amount));
        Transaction transaction = createTransaction(wallet, amount, TransactionType.DEPOSIT, "Adding amount");
        wallet.getTransactions().add(transaction);
        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        return wallet;
    }

    @Transactional
    public Wallet withdrawAmount(Long walletId, BigDecimal amount) throws Exception {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        if (amount.compareTo(wallet.getBalance()) < 0) {
            wallet.setBalance(wallet.getBalance().subtract(amount));
            Transaction transaction = createTransaction(wallet, amount, TransactionType.WITHDRAWAL, "Withdrawing amount");
            wallet.getTransactions().add(transaction);
            transactionRepository.save(transaction);
            walletRepository.save(wallet);
        } else {
            throw new Exception("Transação não autorizada. Seu saldo é insuficiente.");
        }
        return wallet;
    }

    @Transactional
    public Wallet makePurchase(Long walletId, BigDecimal amount, String description) throws Exception {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        if (amount.compareTo(wallet.getBalance()) < 0) {
            wallet.setBalance(wallet.getBalance().subtract(amount));
            Transaction transaction = createTransaction(wallet, amount, TransactionType.PURCHASE, description);
            wallet.getTransactions().add(transaction);
            transactionRepository.save(transaction);
            walletRepository.save(wallet);
        } else {
            throw new Exception("Compra não autorizada. Seu saldo é insuficiente.");
        }
        return wallet;
    }

    @Transactional
    public Wallet cancelTransaction(Long walletId, Long transactionId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();

        wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));

        Transaction cancellation = createTransaction(wallet, transaction.getAmount(), TransactionType.CANCEL, "Cancellation of transaction " + transactionId);
        wallet.getTransactions().add(cancellation);

        transactionRepository.save(cancellation);
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet refundTransaction(Long walletId, Long transactionId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();

        wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));

        Transaction refund = createTransaction(wallet, transaction.getAmount(), TransactionType.REFUND, "Refund of transaction " + transactionId);
        wallet.getTransactions().add(refund);

        transactionRepository.save(refund);
        return walletRepository.save(wallet);
    }

    public List<Transaction> getTransactions(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        return wallet.getTransactions();
    }

    private Transaction createTransaction(Wallet wallet, BigDecimal amount, TransactionType type, String description) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDate(new Date());
        transaction.setDescription(description);
        transaction.setWallet(wallet);
        return transaction;
    }
}