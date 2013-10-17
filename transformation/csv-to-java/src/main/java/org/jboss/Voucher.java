package org.jboss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Voucher implements Serializable {

	private String voucherId;
	private String propertyId;
	private String projectId;
	private String projectName;
	private String voucherType;
	private String managementEntity;
	private String businessUnit;
	private String commitmentNumber;
	private String vendorInvoiceNumber;
	private Date vendorInvoiceDate;
	private String companyNumber;
	private String Description;
	private String vendorLocationCode;
	private double invoiceAmount;
	private ArrayList<VoucherLine> voucherLines;

	public String getVoucherId() {
		return voucherId;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public String getManagementEntity() {
		return managementEntity;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public String getCommitmentNumber() {
		return commitmentNumber;
	}
	public String getVendorInvoiceNumber() {
		return vendorInvoiceNumber;
	}
	public Date getVendorInvoiceDate() {
		return vendorInvoiceDate;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}
	public String getDescription() {
		return Description;
	}
	public String getVendorLocationCode() {
		return vendorLocationCode;
	}
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public ArrayList<VoucherLine> getLines() {
		return voucherLines;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public void setManagementEntity(String managementEntity) {
		this.managementEntity = managementEntity;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public void setCommitmentNumber(String commitmentNumber) {
		this.commitmentNumber = commitmentNumber;
	}
	public void setVendorInvoiceNumber(String vendorInvoiceNumber) {
		this.vendorInvoiceNumber = vendorInvoiceNumber;
	}
	public void setVendorInvoiceDate(Date vendorInvoiceDate) {
		this.vendorInvoiceDate = vendorInvoiceDate;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public void setVendorLocationCode(String vendorLocationCode) {
		this.vendorLocationCode = vendorLocationCode;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public void setVoucherLines(ArrayList<VoucherLine> voucherLines) {
		this.voucherLines = voucherLines;
	}
	
}
