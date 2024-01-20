package com.common.api.model.field;

import java.util.ArrayList;
import java.util.List;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyPreferenceField extends APIFixedConstant {

 	@ApiModelProperty(value = "questions", required = true)
    @JsonProperty(value = "questions")
    private List<String> questions = new ArrayList<>();

 	@ApiModelProperty(value = "answers", required = false)
    @JsonProperty(value = "answers")
    private List<String> answers = new ArrayList<>();

 	@ApiModelProperty(value = "lines", required = false)
    @JsonProperty(value = "lines")
    private List<String> lines = new ArrayList<>();

 	@ApiModelProperty(value = "metaTitle", required = true)
    @JsonProperty(value = "metaTitle")
    private String metaTitle = "";

 	@ApiModelProperty(value = "metaValue", required = true)
    @JsonProperty(value = "metaValue")
    private String metaValue = "";

	public CompanyPreferenceField() {
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaValue() {
		return metaValue;
	}

	public void setMetaValue(String metaValue) {
		this.metaValue = metaValue;
	}

	@Override
	public String toString() {
		return "CompanyPreferenceField [questions=" + questions + ", answers=" + answers + ", lines=" + lines
				+ ", metaTitle=" + metaTitle + ", metaValue=" + metaValue + "]";
	}

}
