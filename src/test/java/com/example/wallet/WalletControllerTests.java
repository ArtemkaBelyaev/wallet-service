package com.example.wallet;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletRepository walletRepository;

    private UUID walletId;

    @BeforeEach
    public void setUp() {
        walletId = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        wallet.setBalance(1000L);
        walletRepository.save(wallet);
    }

    @AfterEach
    public void cleanUp() {
        if (walletId != null) {
            walletRepository.deleteById(walletId);
        }
    }

    @Test
    public void testDepositSuccess() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(WalletOperationRequest.OperationType.DEPOSIT);
        request.setAmount(1000);

        mockMvc.perform(post("/api/v1/wallet")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDepositUnsuccessful() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(WalletOperationRequest.OperationType.DEPOSIT);
        request.setAmount(-1000);

        mockMvc.perform(post("/api/v1/wallet")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWithdrawSuccess() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(WalletOperationRequest.OperationType.WITHDRAW);
        request.setAmount(1000);

        mockMvc.perform(post("/api/v1/wallet")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithdrawUnsuccessful() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(WalletOperationRequest.OperationType.WITHDRAW);
        request.setAmount(1001);

        mockMvc.perform(post("/api/v1/wallet")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBalanceSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetBalanceUnsuccessful() throws Exception {
        UUID wrongId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/wallet/" + wrongId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}