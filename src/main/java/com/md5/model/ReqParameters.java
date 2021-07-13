package com.md5.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ReqParameters {
	
	private String returnUrl;
	private String txnAmount;
	private String grn;
	private String remitterName;
	private String remmiterAddress;
	private String pan;
	private String payMode;
	private ArrayList<String> hoa;
	private ArrayList<BigDecimal> amount;
	private String bankCode;
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getGrn() {
		return grn;
	}
	public void setGrn(String grn) {
		this.grn = grn;
	}
	public String getRemitterName() {
		return remitterName;
	}
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}
	public String getRemmiterAddress() {
		return remmiterAddress;
	}
	public void setRemmiterAddress(String remmiterAddress) {
		this.remmiterAddress = remmiterAddress;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public ArrayList<String> getHoa() {
		return hoa;
	}
	public void setHoa(ArrayList<String> hoa) {
		this.hoa = hoa;
	}
	public ArrayList<BigDecimal> getAmount() {
		return amount;
	}
	public void setAmount(ArrayList<BigDecimal> amount) {
		this.amount = amount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Override
	public String toString() {
		return "ReqParameters [returnUrl=" + returnUrl + ", txnAmount=" + txnAmount + ", grn=" + grn + ", remitterName="
				+ remitterName + ", remmiterAddress=" + remmiterAddress + ", pan=" + pan + ", payMode=" + payMode
				+ ", hoa=" + hoa + ", amount=" + amount + ", bankCode=" + bankCode + "]";
	}

}
