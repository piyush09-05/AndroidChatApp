package com.example.chatapplication.Models;

public class Users {
    String userName, password, emailId, userId, lastMessage, status, profilePic;


    public Users(String userName, String password, String emailId, String userId, String lastMessage, String status, String profilePic) {
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
        this.profilePic = profilePic;
    }

    public Users(String userName, String password, String emailId) {
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Users(){

   }
}
