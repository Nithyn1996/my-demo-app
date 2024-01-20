package com.common.api.test;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.common.api.model.DivisionPreference;
import com.common.api.model.field.DivisionPreferenceField;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = APICommonApp.class)
@AutoConfigureMockMvc
public class DivisionPreferencePostTest {

	@Autowired
    private MockMvc mockMvc;

    DivisionPreferenceField divisionPreferenceFieldTemp = new DivisionPreferenceField();

    @Test
    public void validValues() throws Exception {

        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift num2");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void invalidReferredBy() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 011");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEBs")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidSessionId() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 011");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874basas")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidCompanyId() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001ss");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 011");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidGroupId() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001reras");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 011");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidName() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 011 ./");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidStatus() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 0032");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED11");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void invalidType() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 0032");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFTSS");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(412));
    }

    @Test
    public void nameAlreadyExist() throws Exception {
        DivisionPreference divisionPreference = new DivisionPreference();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse("00:00:00");
        Date endTime = sdf.parse("00:00:00");

        divisionPreference.setCompanyId("comp00000000000000000001");
        divisionPreference.setGroupId("grup00000000000000000001");
        divisionPreference.setDivisionId("divi00000000000000000001");
        divisionPreference.setName("Shift 1");
        divisionPreference.setDivisionPreferenceField(divisionPreferenceFieldTemp);
        divisionPreference.setStatus("REGISTERED");
        divisionPreference.setStartTime(new Time(startTime.getTime()));
        divisionPreference.setEndTime(new Time(endTime.getTime()));
        divisionPreference.setCategory("");
        divisionPreference.setType("SHIFT");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/divisionPreference")
                .header("sessionId", "649d9133c1446c00a354874b")
                .header("referredBy", "WEB")
                .content(new ObjectMapper().writeValueAsString(divisionPreference))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409));
    }
}
