package com.cevr.business.model.to;

import java.util.Date;

public class TicketInfo {
	
	String userName;
	String userTel;
	String userEmail;
	String carId;
	String fromIp;
	int ticketNum;
	Date ticketTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getFromIp() {
		return fromIp;
	}
	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public Date getTicketTime() {
		return new Date();
	}
	@Override
	public String toString() {
		return "TicketInfo [userName=" + userName + ", userTel=" + userTel + ", userEmail=" + userEmail + ", carId="
				+ carId + ", fromIp=" + fromIp + ", ticketNum=" + ticketNum + ", ticketTime=" + ticketTime + "]";
	}
}
