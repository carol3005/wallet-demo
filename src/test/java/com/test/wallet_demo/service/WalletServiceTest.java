package com.test.wallet_demo.service;

import com.test.wallet_demo.model.Transaction;
import com.test.wallet_demo.model.TransactionType;
import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.repository.TransactionRepository;
import com.test.wallet_demo.repository.WalletRepository;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testDepositAmount() {
        MockitoAnnotations.openMocks(this);

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.ZERO);

        when(walletRepository.findById(1L)).thenReturn(java.util.Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.depositAmount(1L, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), updatedWallet.getBalance());
    }

    @Test
    void testWithdrawAmount() throws Exception {
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(100), null);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.
                withdrawAmount(1L, BigDecimal.valueOf(50));

        assertEquals(BigDecimal.valueOf(50), updatedWallet.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testWithdrawAmount_InsufficientBalance() {
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(30), null);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.withdrawAmount(1L, BigDecimal.valueOf(50));
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testMakePurchase() throws Exception {
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(100), null);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.makePurchase(1L, BigDecimal.valueOf(40), "Purchase item");

        assertEquals(BigDecimal.valueOf(60), updatedWallet.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testCancelTransaction() {
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(100), null);
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(50), TransactionType.DEPOSIT, null, "Test deposit", wallet);
        wallet.setTransactions(List.of(transaction));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.cancelTransaction(1L, 1L);

        assertEquals(BigDecimal.valueOf(50), updatedWallet.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testRefundTransaction() {
        Wallet wallet = new Wallet(1L, BigDecimal.valueOf(50), null);
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(20), TransactionType.PURCHASE, null, "Test purchase", wallet);
        wallet.setTransactions(List.of(transaction));

        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.refundTransaction(1L, 1L);

        assertEquals(BigDecimal.valueOf(70), updatedWallet.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}
