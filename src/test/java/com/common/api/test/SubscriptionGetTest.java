package com.common.api.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.common.api.APICommonApp;
import com.common.api.datasink.service.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = APICommonApp.class)
@AutoConfigureMockMvc
public class SubscriptionGetTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    // All values are valid
    @Test
    public void validValues() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    // All values are valid with specified id
    @Test
    public void validValuesWithId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("id", "sub000000000000000000001")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    // Invalid companyId
    @Test
    public void invalidCompanyId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp000000000000000000011")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    // Invalid divisionId
    @Test
    public void invalidDivisionId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi000000000000000000011")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid type
    @Test
    public void invalidtype() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi000000000000000000011")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTIONa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// All values are invalid
    @Test
    public void invalidValues() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp000000000000000000011")
                .param("divisionId", "divi000000000000000000011")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTIONa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    // Invalid referredBy
    @Test
    public void invalidReferredBy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROIDs")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Invalid sessionId
    @Test
    public void sessionId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874bs")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

	// Session Fail
    @Test
    public void sessionFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "202120212021202120212021")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(440));
    }

    // Access Fail
    @Test
    public void accessFail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "6501b212b1d5ae00a4790901")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000001")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    // Record Not Exist
    @Test
    public void recordNotExist() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/subscription")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "ANDROID")
                .param("companyId", "comp00000000000000000002")
                .param("divisionId", "divi00000000000000000001")
                .param("type", "MOBILE_DEVICE_LICENSE_SUBSCRIPTION")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(440));
    }


}
