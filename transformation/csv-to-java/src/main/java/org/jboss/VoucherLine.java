package org.jboss;

import java.io.Serializable;
import java.util.Date;

public class VoucherLine implements Serializable {

	private String voucherId;
        private String propertyId;
        private String projectId;
	private int actualsItemNumber;
	private String actualsItemDescription;
	private String accountCode;
	private double actualsItemAmount;
	private String paymentId;
	private double paymentAmount;

    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("EbuilderVoucher :");
        sBuilder.append("\n\tvoucherId = "+voucherId);
        sBuilder.append("\n\tpropertyId = "+propertyId);
        sBuilder.append("\n\tprojectId = "+projectId);
        sBuilder.append("\n\tactualsItemNumber = "+actualsItemNumber);
        sBuilder.append("\n\tactualsItemDescription = "+actualsItemDescription);
        sBuilder.append("\n\taccountCode = "+accountCode);
        sBuilder.append("\n\tactualsItemAmount = "+actualsItemAmount);
        sBuilder.append("\n\tpaymentId = "+paymentId);
        sBuilder.append("\n\tpaymentAmount = "+paymentAmount);
        return sBuilder.toString();
    }

	/**
	 * @return the voucherId
	 */
	public String getVoucherId() {
		return voucherId;
	}
    public String getProperty() {
        return propertyId;
    }
    public String getProjectId() {
        return projectId;
    }

	/**
	 * @return the actualsItemNumber
	 */
	public int getActualsItemNumber() {
		return actualsItemNumber;
	}
	/**
	 * @return the actualsItemDescription
	 */
	public String getActualsItemDescription() {
		return actualsItemDescription;
	}
	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * @return the actualsItemAmount
	 */
	public double getActualsItemAmount() {
		return actualsItemAmount;
	}
	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * @return the paymentAmount
	 */
	public double getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param voucherId the voucherId to set
	 */
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
        public void setPropertyId(String x) {
        propertyId = x;
    }
    public void setProjectId(String x) {
        projectId = x;
    }

	/**
	 * @param actualsItemNumber the actualsItemNumber to set
	 */
	public void setActualsItemNumber(int actualsItemNumber) {
		this.actualsItemNumber = actualsItemNumber;
	}
	/**
	 * @param actualsItemDescription the actualsItemDescription to set
	 */
	public void setActualsItemDescription(String actualsItemDescription) {
		this.actualsItemDescription = actualsItemDescription;
	}
	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	/**
	 * @param actualsItemAmount the actualsItemAmount to set
	 */
	public void setActualsItemAmount(double actualsItemAmount) {
		this.actualsItemAmount = actualsItemAmount;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	
}
