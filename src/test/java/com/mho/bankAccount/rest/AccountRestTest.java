package com.mho.bankAccount.rest;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.usecases.UpdateAmountUseCase;
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

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {AccountRest.class})
public class AccountRestTest {

    @MockBean
    private UpdateAmountUseCase updateAmountUseCase;

    @Autowired
    protected MockMvc mvc;

    @Test
    public void updateAmountOk() throws Exception {
        performPut("/accounts/1", "5")
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateAmountKo() throws Exception {
        Mockito.doThrow(InsufficientBalanceException.class).when(updateAmountUseCase).hande(1, 5);
        performPut("/accounts/1", "5")
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    private ResultActions performPut(String url, String content) throws Exception {
        return this.mvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
    }
}
