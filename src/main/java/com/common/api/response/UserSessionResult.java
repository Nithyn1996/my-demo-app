package com.common.api.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;

import com.common.api.model.Division;
import com.common.api.model.Group;
import com.common.api.model.Language;
import com.common.api.model.ModulePreference;
import com.common.api.model.User;
import com.common.api.model.UserPreference;
import com.common.api.model.UserSession;
import com.common.api.model.field.AppPayloadSettingField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserSessionResult")
@TypeAlias(value = "UserSessionResult")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionResult {

	@ApiModelProperty(value = "user", required = true)
	@JsonProperty(value = "user")
	private User user = new User();

	@ApiModelProperty(value = "userSession", required = true)
	@JsonProperty(value = "userSession")
	private UserSession userSession = new UserSession();

	@ApiModelProperty(value = "group", required = true)
	@JsonProperty(value = "group")
	private Group group = new Group();

	@ApiModelProperty(value = "division", required = true)
	@JsonProperty(value = "division")
	private Division division = new Division();

	@ApiModelProperty(value = "language", required = true)
	@JsonProperty(value = "language")
	private Language language = new Language();

	@ApiModelProperty(value = "modulePreferences", required = true)
	@JsonProperty(value = "modulePreferences")
	private List<ModulePreference> modulePreferences = new ArrayList<>();

	@ApiModelProperty(value = "userPreferences", required = true)
	@JsonProperty(value = "userPreferences")
	private List<UserPreference> userPreferences = new ArrayList<>();

	@ApiModelProperty(value = "appPayloadSettingField", required = true)
	@JsonProperty(value = "appPayloadSettingField")
	private AppPayloadSettingField appPayloadSettingField = new AppPayloadSettingField();

	public UserSessionResult() {
	}

	public UserSessionResult(User user, UserSession userSession) {
		this.user = user;
		this.userSession = userSession;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<ModulePreference> getModulePreferences() {
		return modulePreferences;
	}

	public void setModulePreferences(List<ModulePreference> modulePreferences) {
		this.modulePreferences = modulePreferences;
	}

	public List<UserPreference> getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(List<UserPreference> userPreferences) {
		this.userPreferences = userPreferences;
	}

	public AppPayloadSettingField getAppPayloadSettingField() {
		return appPayloadSettingField;
	}

	public void setAppPayloadSettingField(AppPayloadSettingField appPayloadSettingField) {
		this.appPayloadSettingField = appPayloadSettingField;
	}

	@Override
	public String toString() {
		return "UserSessionResult [user=" + user + ", userSession=" + userSession + ", group=" + group + ", division="
				+ division + ", language=" + language + ", modulePreferences=" + modulePreferences
				+ ", userPreferences=" + userPreferences + "]";
	}

}
