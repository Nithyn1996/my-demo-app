package com.common.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.common.api.APICommonApp;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = APICommonApp.class)
@AutoConfigureMockMvc
public class UserPreferenceGetTest  {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validValues() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

	}

    @Test
    public void validValueWithId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("id","64bf5ba3b9214300a3a8e9eb")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

	}


    @Test
    public void inValidValue() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b1")
                .header("referredBy", "WE1B")
                .param("companyId", "comp000000000000000000011")
                .param("id","64bf5ba3b9214300a3a8e9eb3")
                .param("userId", "64a3b62df6ce2100a34ff7f01")
                .param("type", "LICENCE22")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));

	}

    @Test
    public void inValidCompanyId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp000000000000000000011")
                .param("id","64bf5ba3b9214300a3a8e9eb")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));

	}

    @Test
    public void inValidUserId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("id","657c4168b0757f3344b3a09f")
                .param("userId", "64a3b62df6ce2100a34ff7f0ub")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));

	}

    @Test
    public void inValidId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("id","64bf5ba3b9214300a3a8e9eb999")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));

	}

    @Test
    public void inValidType() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("id","64bf5ba3b9214300a3a8e9eb")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "invalid")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));
	}

    @Test
    public void inValidSessionId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b999")
                .header("referredBy", "WEB")
                .param("companyId", "comp00000000000000000001")
                .param("id","64bf5ba3b9214300a3a8e9eb")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));
	}

    @Test
    public void inValidReferredBy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/userPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB111")
                .param("companyId", "comp00000000000000000001")
                .param("id","64bf5ba3b9214300a3a8e9eb")
                .param("userId", "64a3b62df6ce2100a34ff7f0")
                .param("type", "LICENCE")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(412));
	}
}


