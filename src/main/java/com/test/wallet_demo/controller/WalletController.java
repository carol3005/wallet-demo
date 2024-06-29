package com.test.wallet_demo.controller;

import com.test.wallet_demo.model.Transaction;
import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public Wallet createWallet() {
        return walletService.createWallet();
    }

    @PostMapping("/{walletId}/deposit")
    public Wallet depositAmount(@PathVariable Long walletId, @RequestParam BigDecimal amount) {
        return walletService.depositAmount(walletId, amount);
    }

    @PostMapping("/{walletId}/withdraw")
    public Wallet withdrawAmount(@PathVariable Long walletId, @RequestParam BigDecimal amount) throws Exception {
        return walletService.withdrawAmount(walletId, amount);
    }

    @PostMapping("/{walletId}/purchase")
    public Wallet makePurchase(@PathVariable Long walletId, @RequestParam BigDecimal amount, @RequestParam String description) throws Exception {
        return walletService.makePurchase(walletId, amount, description);
    }

    @PostMapping("/{walletId}/cancel/{transactionId}")
    public Wallet cancelTransaction(@PathVariable Long walletId, @PathVariable Long transactionId) {
        return walletService.cancelTransaction(walletId, transactionId);
    }

    @PostMapping("/{walletId}/refund/{transactionId}")
    public Wallet refundTransaction(@PathVariable Long walletId, @PathVariable Long transactionId) {
        return walletService.refundTransaction(walletId, transactionId);
    }

    @GetMapping("/{walletId}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long walletId) {
        return walletService.getTransactions(walletId);
    }
}