package nuce.locklinh.qlct.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("user_age")
    @Expose
    private Integer userAge;
    @SerializedName("user_gender")
    @Expose
    private String userGender;

    public User(String user_name, String user_pass, Integer user_age, String user_gender) {
        this.userName = user_name;
        this.userPass = user_pass;
        this.userAge = user_age;
        this.userGender = user_gender;
    }

    public User(Integer userId, String userName, String userPass, Integer userAge, String userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
        this.userAge = userAge;
        this.userGender = userGender;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}