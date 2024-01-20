package com.common.api.model.field;

import com.common.api.constant.APIFixedConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionPrefVehicleInspection extends APIFixedConstant {

 	@ApiModelProperty(value = "helmetField", required = false)
    @JsonProperty(value = "helmetField")
    private HelmetField helmetField = new HelmetField();

 	@ApiModelProperty(value = "documentField", required = false)
    @JsonProperty(value = "documentField")
    private DocumentField documentField = new DocumentField();

 	@ApiModelProperty(value = "tyreField", required = false)
    @JsonProperty(value = "tyreField")
    private TyreField tyreField = new TyreField();

 	@ApiModelProperty(value = "brakeField", required = false)
    @JsonProperty(value = "brakeField")
    private BrakeField brakeField = new BrakeField();

 	@ApiModelProperty(value = "lightField", required = false)
    @JsonProperty(value = "lightField")
    private LightField lightField = new LightField();

 	@ApiModelProperty(value = "engineBodyField", required = false)
    @JsonProperty(value = "engineBodyField")
    private EngineBodyField engineBodyField = new EngineBodyField();

 	@ApiModelProperty(value = "commentField", required = false)
    @JsonProperty(value = "commentField")
    private CommentField commentField = new CommentField();

	public SectionPrefVehicleInspection() {
	}

	public HelmetField getHelmetField() {
		return helmetField;
	}

	public void setHelmetField(HelmetField helmetField) {
		this.helmetField = helmetField;
	}

	public DocumentField getDocumentField() {
		return documentField;
	}

	public void setDocumentField(DocumentField documentField) {
		this.documentField = documentField;
	}

	public TyreField getTyreField() {
		return tyreField;
	}

	public void setTyreField(TyreField tyreField) {
		this.tyreField = tyreField;
	}

	public BrakeField getBrakeField() {
		return brakeField;
	}

	public void setBrakeField(BrakeField brakeField) {
		this.brakeField = brakeField;
	}

	public LightField getLightField() {
		return lightField;
	}

	public void setLightField(LightField lightField) {
		this.lightField = lightField;
	}

	public EngineBodyField getEngineBodyField() {
		return engineBodyField;
	}

	public void setEngineBodyField(EngineBodyField engineBodyField) {
		this.engineBodyField = engineBodyField;
	}

	@Override
	public String toString() {
		return "VehicleInspection [helmetField=" + helmetField + ", documentField=" + documentField + ", tyreField="
				+ tyreField + ", brakeField=" + brakeField + ", lightField=" + lightField + ", engineBodyField="
				+ engineBodyField + "]";
	}

	public class HelmetField {

	 	@ApiModelProperty(value = "isCertified", required = true)
	    @JsonProperty(value = "isCertified")
	    private String isCertified = "";

	 	@ApiModelProperty(value = "scratchOrDent", required = true)
	    @JsonProperty(value = "scratchOrDent")
	    private String scratchOrDent = "";

	 	@ApiModelProperty(value = "chinStrap", required = true)
	    @JsonProperty(value = "chinStrap")
	    private String chinStrap = "";

	 	public HelmetField() {
		}

		public String getIsCertified() {
			return isCertified;
		}

		public void setIsCertified(String isCertified) {
			this.isCertified = isCertified;
		}

		public String getScratchOrDent() {
			return scratchOrDent;
		}

		public void setScratchOrDent(String scratchOrDent) {
			this.scratchOrDent = scratchOrDent;
		}

		public String getChinStrap() {
			return chinStrap;
		}

		public void setChinStrap(String chinStrap) {
			this.chinStrap = chinStrap;
		}

		@Override
		public String toString() {
			return "HelmetField [isCertified=" + isCertified + ", scratchOrDent=" + scratchOrDent + ", chinStrap="
					+ chinStrap + "]";
		}

	}

	public class DocumentField {

	 	@ApiModelProperty(value = "licenseNumber", required = true)
	    @JsonProperty(value = "licenseNumber")
	    private String licenseNumber = "";

	 	@ApiModelProperty(value = "licenseValidity", required = true)
	    @JsonProperty(value = "licenseValidity")
	    private String licenseValidity = "";

	 	@ApiModelProperty(value = "registrationNumber", required = true)
	    @JsonProperty(value = "registrationNumber")
	    private String registrationNumber = "";

	 	@ApiModelProperty(value = "rcValid", required = true)
	    @JsonProperty(value = "rcValid")
	    private String rcValid = "";

	 	@ApiModelProperty(value = "insuranceValid", required = true)
	    @JsonProperty(value = "insuranceValid")
	    private String insuranceValid = "";

	 	@ApiModelProperty(value = "visiblePlates", required = true)
	    @JsonProperty(value = "visiblePlates")
	    private String visiblePlates = "";

	 	@ApiModelProperty(value = "pucCertificate", required = true)
	    @JsonProperty(value = "pucCertificate")
	    private String pucCertificate = "";

	 	public DocumentField() {
		}

		public String getLicenseNumber() {
			return licenseNumber;
		}

		public void setLicenseNumber(String licenseNumber) {
			this.licenseNumber = licenseNumber;
		}

		public String getLicenseValidity() {
			return licenseValidity;
		}

		public void setLicenseValidity(String licenseValidity) {
			this.licenseValidity = licenseValidity;
		}

		public String getRegistrationNumber() {
			return registrationNumber;
		}

		public void setRegistrationNumber(String registrationNumber) {
			this.registrationNumber = registrationNumber;
		}

		public String getRcValid() {
			return rcValid;
		}

		public void setRcValid(String rcValid) {
			this.rcValid = rcValid;
		}

		public String getInsuranceValid() {
			return insuranceValid;
		}

		public void setInsuranceValid(String insuranceValid) {
			this.insuranceValid = insuranceValid;
		}

		public String getVisiblePlates() {
			return visiblePlates;
		}

		public void setVisiblePlates(String visiblePlates) {
			this.visiblePlates = visiblePlates;
		}

		public String getPucCertificate() {
			return pucCertificate;
		}

		public void setPucCertificate(String pucCertificate) {
			this.pucCertificate = pucCertificate;
		}

		@Override
		public String toString() {
			return "DocumentField [licenseNumber=" + licenseNumber + ", licenseValidity=" + licenseValidity
					+ ", registrationNumber=" + registrationNumber + ", rcValid=" + rcValid + ", insuranceValid="
					+ insuranceValid + ", visiblePlates=" + visiblePlates + ", pucCertificate=" + pucCertificate + "]";
		}

	}

	public class TyreField {

	 	@ApiModelProperty(value = "wellInflated", required = true)
	    @JsonProperty(value = "wellInflated")
	    private String wellInflated = "";

	 	@ApiModelProperty(value = "noBulgeCut", required = true)
	    @JsonProperty(value = "noBulgeCut")
	    private String noBulgeCut = "";

	 	@ApiModelProperty(value = "noCrack", required = true)
	    @JsonProperty(value = "noCrack")
	    private String noCrack = "";

	 	@ApiModelProperty(value = "noWarmOut", required = true)
	    @JsonProperty(value = "noWarmOut")
	    private String noWarmOut = "";

	 	public TyreField() {
		}

		public String getWellInflated() {
			return wellInflated;
		}

		public void setWellInflated(String wellInflated) {
			this.wellInflated = wellInflated;
		}

		public String getNoBulgeCut() {
			return noBulgeCut;
		}

		public void setNoBulgeCut(String noBulgeCut) {
			this.noBulgeCut = noBulgeCut;
		}

		public String getNoCrack() {
			return noCrack;
		}

		public void setNoCrack(String noCrack) {
			this.noCrack = noCrack;
		}

		public String getNoWarmOut() {
			return noWarmOut;
		}

		public void setNoWarmOut(String noWarmOut) {
			this.noWarmOut = noWarmOut;
		}

		@Override
		public String toString() {
			return "TyreField [wellInflated=" + wellInflated + ", noBulgeCut=" + noBulgeCut + ", noCrack=" + noCrack
					+ ", noWarmOut=" + noWarmOut + "]";
		}

	}

	public class BrakeField {

	 	@ApiModelProperty(value = "brakeCondition", required = true)
	    @JsonProperty(value = "brakeCondition")
	    private String brakeCondition = "";

	 	@ApiModelProperty(value = "brakePedalMovement", required = true)
	    @JsonProperty(value = "brakePedalMovement")
	    private String brakePedalMovement = "";

	 	public BrakeField() {
		}

		public String getBrakeCondition() {
			return brakeCondition;
		}

		public void setBrakeCondition(String brakeCondition) {
			this.brakeCondition = brakeCondition;
		}

		public String getBrakePedalMovement() {
			return brakePedalMovement;
		}

		public void setBrakePedalMovement(String brakePedalMovement) {
			this.brakePedalMovement = brakePedalMovement;
		}

		@Override
		public String toString() {
			return "BrakeField [brakeCondition=" + brakeCondition + ", brakePedalMovement=" + brakePedalMovement + "]";
		}

	}

	public class LightField {

	 	@ApiModelProperty(value = "headLight", required = true)
	    @JsonProperty(value = "headLight")
	    private String headLight = "";

	 	@ApiModelProperty(value = "tailLight", required = true)
	    @JsonProperty(value = "tailLight")
	    private String tailLight = "";

	 	@ApiModelProperty(value = "brakeLight", required = true)
	    @JsonProperty(value = "brakeLight")
	    private String brakeLight = "";

	 	@ApiModelProperty(value = "sideIndicator", required = true)
	    @JsonProperty(value = "sideIndicator")
	    private String sideIndicator = "";

	 	public LightField() {
		}

		public String getHeadLight() {
			return headLight;
		}

		public void setHeadLight(String headLight) {
			this.headLight = headLight;
		}

		public String getTailLight() {
			return tailLight;
		}

		public void setTailLight(String tailLight) {
			this.tailLight = tailLight;
		}

		public String getBrakeLight() {
			return brakeLight;
		}

		public void setBrakeLight(String brakeLight) {
			this.brakeLight = brakeLight;
		}

		public String getSideIndicator() {
			return sideIndicator;
		}

		public void setSideIndicator(String sideIndicator) {
			this.sideIndicator = sideIndicator;
		}

		@Override
		public String toString() {
			return "LightField [headLight=" + headLight + ", tailLight=" + tailLight + ", brakeLight=" + brakeLight
					+ ", sideIndicator=" + sideIndicator + "]";
		}

	}

	public class EngineBodyField {

	 	@ApiModelProperty(value = "easyStart", required = true)
	    @JsonProperty(value = "easyStart")
	    private String easyStart = "";

	 	@ApiModelProperty(value = "engineOilLevel", required = true)
	    @JsonProperty(value = "engineOilLevel")
	    private String engineOilLevel = "";

	 	@ApiModelProperty(value = "noLeaks", required = true)
	    @JsonProperty(value = "noLeaks")
	    private String noLeaks = "";

	 	@ApiModelProperty(value = "rearViewMirror", required = true)
	    @JsonProperty(value = "rearViewMirror")
	    private String rearViewMirror = "";

	 	public EngineBodyField() {
		}

		public String getEasyStart() {
			return easyStart;
		}

		public void setEasyStart(String easyStart) {
			this.easyStart = easyStart;
		}

		public String getEngineOilLevel() {
			return engineOilLevel;
		}

		public void setEngineOilLevel(String engineOilLevel) {
			this.engineOilLevel = engineOilLevel;
		}

		public String getNoLeaks() {
			return noLeaks;
		}

		public void setNoLeaks(String noLeaks) {
			this.noLeaks = noLeaks;
		}

		public String getRearViewMirror() {
			return rearViewMirror;
		}

		public void setRearViewMirror(String rearViewMirror) {
			this.rearViewMirror = rearViewMirror;
		}

		@Override
		public String toString() {
			return "EngineBodyField [easyStart=" + easyStart + ", engineOilLevel=" + engineOilLevel + ", noLeaks=" + noLeaks
					+ ", rearViewMirror=" + rearViewMirror + "]";
		}

	}

	public class CommentField {

	 	@ApiModelProperty(value = "vehicleFitToRide", required = true)
	    @JsonProperty(value = "vehicleFitToRide")
	    private String vehicleFitToRide = "";

	 	public CommentField() {
		}

		public String getVehicleFitToRide() {
			return vehicleFitToRide;
		}

		public void setVehicleFitToRide(String vehicleFitToRide) {
			this.vehicleFitToRide = vehicleFitToRide;
		}

		@Override
		public String toString() {
			return "CommentField [vehicleFitToRide=" + vehicleFitToRide + "]";
		}

	}

}

