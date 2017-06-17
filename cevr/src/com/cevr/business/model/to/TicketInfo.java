package com.cevr.business.model.to;

import java.util.Date;

import com.cevr.component.util.DateUtil;

public class TicketInfo {
    
    private String tab;
    
    private String userName;
    
    private String userTel;
    
    private String userEmail;
    
    private String carId;
    
    private String fromIp;
    
    private int ticketNum;
    
    private Date ticketTime;
    
    private String ticketTypeId;
    
    private String imgType;
    
    private String todayTime;
    
    private String groupId;
    
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
    
    public String getTicketTypeId() {
        return ticketTypeId;
    }
    
    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }
    
    public String getImgType() {
        return imgType;
    }
    
    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
    
    public String getTodayTime() {
        return DateUtil.nowStr("yyyy-MM-dd");
    }
    
    public String getGroupId() {
        return groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public String getTab() {
        return tab;
    }
    
    public void setTab(String tab) {
        this.tab = tab;
    }
    
    @Override
    public String toString() {
        return "TicketInfo [userName=" + userName + ", userTel=" + userTel + ", userEmail=" + userEmail + ", carId=" + carId + ", fromIp=" + fromIp + ", ticketNum=" + ticketNum + ", ticketTime=" + ticketTime + ", ticketTypeId=" + ticketTypeId + ", imgType=" + imgType + ", todayTime=" + todayTime
            + ", groupId=" + groupId + "]";
    }
}
