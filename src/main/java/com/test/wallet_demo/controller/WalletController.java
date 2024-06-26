package com.test.wallet_demo.controller;

import com.test.wallet_demo.model.Transaction;
import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public Wallet createWallet() {
        return walletService.createWallet();
    }

    @PostMapping("/{walletId}/add")
    public Wallet addAmount(@PathVariable Long walletId, @RequestParam BigDecimal amount) {
        return walletService.addAmount(walletId, amount);
    }

    // Implement other endpoints for withdrawal, purchases, cancellation, refund, etc.

    @GetMapping("/{walletId}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long walletId) {
        return walletService.getTransactions(walletId);
    }
}