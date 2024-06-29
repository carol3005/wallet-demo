package com.test.wallet_demo.repository;

import com.test.wallet_demo.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
