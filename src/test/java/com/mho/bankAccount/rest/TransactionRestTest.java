package com.mho.bankAccount.rest;

import com.google.gson.Gson;
import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.domain.exception.InvalidAmountException;
import com.mho.bankAccount.domain.model.Transaction;
import com.mho.bankAccount.rest.dto.TransactionDto;
import com.mho.bankAccount.usecases.FindTransactionsUseCase;
import com.mho.bankAccount.usecases.TransferUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TransactionRest.class})
public class TransactionRestTest {

    @MockBean
    private TransferUseCase transferUseCase;

    @MockBean
    private FindTransactionsUseCase findTransactionsUseCase;

    @Autowired
    protected MockMvc mvc;

    @Test
    public void create() throws Exception {
        performPost(buildTransactionDtoJson())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

    @Test
    public void createWithInsuffisentBalance() throws Exception {
        Mockito.doThrow(InsufficientBalanceException.class).when(transferUseCase).handle(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        performPost(buildTransactionDtoJson())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void createWithInvalidAmount() throws Exception {
        Mockito.doThrow(InvalidAmountException.class).when(transferUseCase).handle(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        performPost(buildTransactionDtoJson())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void getTransactionsOk() throws Exception {
        Transaction transaction = new Transaction(1, 2, 3, 9);
        Mockito.doReturn(List.of(transaction)).when(findTransactionsUseCase).handle(Mockito.anyInt());

        Gson gson = new Gson();

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accountId", "1");

        performGet(params)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(gson.toJson(List.of(transaction))));
    }

    @Test
    public void getTransactionsBadRequest() throws Exception {
        performGet(new LinkedMultiValueMap<>())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    private String buildTransactionDtoJson() {
        TransactionDto transactionDto = new TransactionDto(1, 2, 10);
        Gson gson = new Gson();
        return gson.toJson(transactionDto);
    }

    private ResultActions performPost(String content) throws Exception {
        return this.mvc.perform(
                MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
    }

    private ResultActions performGet(MultiValueMap<String, String> params) throws Exception {
        return this.mvc.perform(
                MockMvcRequestBuilders.get("/transactions")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }
}

