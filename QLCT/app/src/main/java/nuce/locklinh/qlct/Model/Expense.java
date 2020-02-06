package nuce.locklinh.qlct.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expense {
    private boolean header;
    @SerializedName("expense_id")
    @Expose
    private Integer expenseId;
    @SerializedName("expense_title")
    @Expose
    private String expenseTitle;
    @SerializedName("expense_content")
    @Expose
    private String expenseContent;
    @SerializedName("expense_date")
    @Expose
    private String expenseDate;
    @SerializedName("expense_amount")
    @Expose
    private Integer expenseAmount;
    @SerializedName("user_name")
    @Expose
    private String userName;


    public Expense(boolean header, String expenseDate) {
        this.header = header;
        this.expenseDate = expenseDate;
    }

    public Expense(String expenseTitle, String expenseContent, String expenseDate, Integer expenseAmount, String userName) {
        this.expenseTitle = expenseTitle;
        this.expenseContent = expenseContent;
        this.expenseDate = expenseDate;
        this.expenseAmount = expenseAmount;
        this.userName = userName;
    }

    public Expense(Integer expenseId, String expenseTitle, String expenseContent, String expenseDate, Integer expenseAmount, String userName) {
        this.expenseId = expenseId;
        this.expenseTitle = expenseTitle;
        this.expenseContent = expenseContent;
        this.expenseDate = expenseDate;
        this.expenseAmount = expenseAmount;
        this.userName = userName;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseTitle() {
        return expenseTitle;
    }

    public void setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
    }

    public String getExpenseContent() {
        return expenseContent;
    }

    public void setExpenseContent(String expenseContent) {
        this.expenseContent = expenseContent;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Integer getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Integer expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expenseTitle='" + expenseTitle + '\'' +
                ", expenseContent='" + expenseContent + '\'' +
                ", expenseDate='" + expenseDate + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", userName='" + userName + '\'' +
                '}';
    }
}