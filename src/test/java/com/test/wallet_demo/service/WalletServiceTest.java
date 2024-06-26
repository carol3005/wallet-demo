package com.test.wallet_demo.service;

import com.test.wallet_demo.model.Wallet;
import com.test.wallet_demo.repository.WalletRepository;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Test
    public void testAddAmount() {
        MockitoAnnotations.openMocks(this);

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.ZERO);

        when(walletRepository.findById(1L)).thenReturn(java.util.Optional.of(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.addAmount(1L, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), updatedWallet.getBalance());
    }
}
