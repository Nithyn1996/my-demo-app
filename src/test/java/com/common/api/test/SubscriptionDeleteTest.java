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
public class SubscriptionDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    // All values are valid
    @Test
    public void validValues() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    // All values are valid with specified id
    @Test
    public void invalidId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub0000000000000000000011"
                		+ "")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    // Invalid companyId
    @Test
    public void invalidCompanyId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp000000000000000000011")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    // Invalid divisionId
    @Test
    public void invalidDivisionId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi000000000000000000011")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// All values are invalid
    @Test
    public void invalidValues() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub0000000000000000000011")
                .param("companyId", "comp000000000000000000011")
                .param("divisionId", "divi000000000000000000011")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    // Invalid referredBy
    @Test
    public void invalidReferredBy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROIDs")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid sessionId
    @Test
    public void invalidSessionId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874bs")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Session Fail
    @Test
    public void sessionFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "202120212021202120212021")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    // Access Fail
    @Test
    public void accessFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "6501b212b1d5ae00a4790901")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    // Record Not Exist
    @Test
    public void recordNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000002")
                .param("divisionId", "divi00000000000000000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    // Record Not Exist
    @Test
    public void failedDepedency() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000002")
                .param("companyId", "comp00000000000000000002")
                .param("divisionId", "divi00000000000000000002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(424));
    }


}
