package nuce.locklinh.qlct.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Income {
    private boolean header;
    @SerializedName("income_id")
    @Expose
    private Integer incomeId;
    @SerializedName("income_title")
    @Expose
    private String incomeTitle;
    @SerializedName("income_content")
    @Expose
    private String incomeContent;
    @SerializedName("income_date")
    @Expose
    private String incomeDate;
    @SerializedName("income_amount")
    @Expose
    private Integer incomeAmount;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public Income(boolean header, String incomeDate) {
        this.header = header;
        this.incomeDate = incomeDate;
    }

    public Income(String incomeTitle, String incomeContent, String incomeDate, Integer incomeAmount, String userName) {
        this.incomeTitle = incomeTitle;
        this.incomeContent = incomeContent;
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
        this.userName = userName;
    }

    public Income(Integer incomeId, String incomeTitle, String incomeContent, String incomeDate, Integer incomeAmount, String userName) {
        this.incomeId = incomeId;
        this.incomeTitle = incomeTitle;
        this.incomeContent = incomeContent;
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
        this.userName = userName;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncomeTitle() {
        return incomeTitle;
    }

    public void setincomeTitle(String incomeTitle) {
        this.incomeTitle = incomeTitle;
    }

    public String getIncomeContent() {
        return incomeContent;
    }

    public void setIncomeContent(String incomeContent) {
        this.incomeContent = incomeContent;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Integer getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Integer incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", incomeTitle='" + incomeTitle + '\'' +
                ", incomeContent='" + incomeContent + '\'' +
                ", incomeDate='" + incomeDate + '\'' +
                ", incomeAmount=" + incomeAmount +
                ", userName='" + userName + '\'' +
                '}';
    }
}