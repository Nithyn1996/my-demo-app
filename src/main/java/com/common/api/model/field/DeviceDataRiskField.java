package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDataRiskField extends APIFixedConstant {

 	@ApiModelProperty(value = "slotNegativeCount", required = false)
    @JsonProperty(value = "slotNegativeCount")
    private int slotNegativeCount = 0;

 	@ApiModelProperty(value = "slotZeroCount", required = false)
    @JsonProperty(value = "slotZeroCount")
    private int slotZeroCount = 0;

 	@ApiModelProperty(value = "slot1Count", required = false)
    @JsonProperty(value = "slot1Count")
    private int slot1Count = 0;

 	@ApiModelProperty(value = "slot2Count", required = false)
    @JsonProperty(value = "slot2Count")
    private int slot2Count = 0;

 	@ApiModelProperty(value = "slot3Count", required = false)
    @JsonProperty(value = "slot3Count")
    private int slot3Count = 0;

 	@ApiModelProperty(value = "slot4Count", required = false)
    @JsonProperty(value = "slot4Count")
    private int slot4Count = 0;

 	@ApiModelProperty(value = "slot5Count", required = false)
    @JsonProperty(value = "slot5Count")
    private int slot5Count = 0;

 	@ApiModelProperty(value = "slot6Count", required = false)
    @JsonProperty(value = "slot6Count")
    private int slot6Count = 0;

 	@ApiModelProperty(value = "slot7Count", required = false)
    @JsonProperty(value = "slot7Count")
    private int slot7Count = 0;

 	@ApiModelProperty(value = "slot8Count", required = false)
    @JsonProperty(value = "slot8Count")
    private int slot8Count = 0;

 	@ApiModelProperty(value = "slot9Count", required = false)
    @JsonProperty(value = "slot9Count")
    private int slot9Count = 0;

 	@ApiModelProperty(value = "slot10Count", required = false)
    @JsonProperty(value = "slot10Count")
    private int slot10Count = 0;
 	
	@ApiModelProperty(value = "slot11Count", required = false)
    @JsonProperty(value = "slot11Count")
    private int slot11Count = 0;

	public DeviceDataRiskField() {
	}

	public int getSlotNegativeCount() {
		return slotNegativeCount;
	}

	public void setSlotNegativeCount(int slotNegativeCount) {
		this.slotNegativeCount = slotNegativeCount;
	}

	public int getSlotZeroCount() {
		return slotZeroCount;
	}

	public void setSlotZeroCount(int slotZeroCount) {
		this.slotZeroCount = slotZeroCount;
	}

	public int getSlot1Count() {
		return slot1Count;
	}

	public void setSlot1Count(int slot1Count) {
		this.slot1Count = slot1Count;
	}

	public int getSlot2Count() {
		return slot2Count;
	}

	public void setSlot2Count(int slot2Count) {
		this.slot2Count = slot2Count;
	}

	public int getSlot3Count() {
		return slot3Count;
	}

	public void setSlot3Count(int slot3Count) {
		this.slot3Count = slot3Count;
	}

	public int getSlot4Count() {
		return slot4Count;
	}

	public void setSlot4Count(int slot4Count) {
		this.slot4Count = slot4Count;
	}

	public int getSlot5Count() {
		return slot5Count;
	}

	public void setSlot5Count(int slot5Count) {
		this.slot5Count = slot5Count;
	}

	public int getSlot6Count() {
		return slot6Count;
	}

	public void setSlot6Count(int slot6Count) {
		this.slot6Count = slot6Count;
	}

	public int getSlot7Count() {
		return slot7Count;
	}

	public void setSlot7Count(int slot7Count) {
		this.slot7Count = slot7Count;
	}

	public int getSlot8Count() {
		return slot8Count;
	}

	public void setSlot8Count(int slot8Count) {
		this.slot8Count = slot8Count;
	}

	public int getSlot9Count() {
		return slot9Count;
	}

	public void setSlot9Count(int slot9Count) {
		this.slot9Count = slot9Count;
	}

	public int getSlot10Count() {
		return slot10Count;
	}

	public void setSlot10Count(int slot10Count) {
		this.slot10Count = slot10Count;
	}	

	public int getSlot11Count() {
		return slot11Count;
	}

	public void setSlot11Count(int slot11Count) {
		this.slot11Count = slot11Count;
	}

	@Override
	public String toString() {
		return "DeviceDataRiskField [slotNegativeCount=" + slotNegativeCount + ", slotZeroCount=" + slotZeroCount
				+ ", slot1Count=" + slot1Count + ", slot2Count=" + slot2Count + ", slot3Count=" + slot3Count
				+ ", slot4Count=" + slot4Count + ", slot5Count=" + slot5Count + ", slot6Count=" + slot6Count
				+ ", slot7Count=" + slot7Count + ", slot8Count=" + slot8Count + ", slot9Count=" + slot9Count
				+ ", slot10Count=" + slot10Count + ", slot11Count=" + slot11Count + "]";
	}



}
