package com.fil.Model;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String contactNumber;
    private String securityQuestion;
    private String answer;
    private String userType;

    public User() {
    }

    public User(String firstName, String lastName, String userName, String contactNumber, String securityQuestion, String answer, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
