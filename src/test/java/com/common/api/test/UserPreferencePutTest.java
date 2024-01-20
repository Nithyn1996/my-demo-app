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
import com.common.api.model.UserPreference;
import com.common.api.model.field.UserPrefAppBootSetting;
import com.common.api.model.field.UserPreferenceField;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = APICommonApp.class)
@AutoConfigureMockMvc
public class UserPreferencePutTest {

	@Autowired
	private MockMvc mockMvc;

	UserPreferenceField userPreferenceFieldTemp = new UserPreferenceField();
	UserPrefAppBootSetting userPrefAppBootSettingTemp = new UserPrefAppBootSetting();

	@Test
	public void validValues() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874b")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(202));
	}

	@Test
	public void invalidReferredBy() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874b")
				.header("referredBy", "WEBss")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidSessionId() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874bssas")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidMetadatavalues() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001aa");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROIDEW");
		userPreference.setStatus("REGISTEREDsasW");
		userPreference.setType("LICENCESS");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874b")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidId() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73ADSA");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidDivisionId() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001ADs");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidName() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2./");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void invalidStatus() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCEWq");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "649d9133c1446c00a354874")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(412));
	}

	@Test
	public void accessFail() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "6501b212b1d5ae00a4790901")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	public void sessionFail() throws Exception {

		UserPreference userPreference = new UserPreference();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date startTime = sdf.parse("00:00:00");
		Date EndTime = sdf.parse("00:00:00");

		userPreference.setId("657c1590b0757f4094b60e73");
		userPreference.setCompanyId("comp00000000000000000001");
		userPreference.setDivisionId("divi00000000000000000001");
		userPreference.setModuleId("modu00000000000000000001");
		userPreference.setUserId("64a3b62df6ce2100a34ff7f0");
		userPreference.setName("License 2");
		userPreference.setCode("d12c877ab9cb02a1");
		userPreference.setUserPreferenceField(userPreferenceFieldTemp);
		userPreference.setUserPrefAppBootSetting(userPrefAppBootSettingTemp);
		userPreference.setStartTime(new Time(startTime.getTime()));
		userPreference.setEndTime(new Time(EndTime.getTime()));
		userPreference.setSubCategory("LICENCE_RECREATED");
		userPreference.setCategory("ANDROID");
		userPreference.setStatus("REGISTERED");
		userPreference.setType("LICENCE");

		mockMvc.perform(MockMvcRequestBuilders
				.put("/userPreference")
				.header("sessionId", "202120212021202120212021")
				.header("referredBy", "WEB")
				.content(new ObjectMapper().writeValueAsString(userPreference))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(440));
	}
}
