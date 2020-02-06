package nuce.locklinh.qlct.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @SerializedName("expense")
    @Expose
    private List<Expense> expense = null;

    public List<Expense> getExpense() {
        return expense;
    }

    public void setExpense(List<Expense> expense) {
        this.expense = expense;
    }


    @SerializedName("income")
    @Expose
    private List<Income> income = null;

    public List<Income> getIncome() {
        return income;
    }

    public void setIncome(List<Income> income) {
        this.income = income;
    }


    @Override
    public String toString() {
        return "Data{" +
                "user=" + user +
                ", expense=" + expense +
                ", income=" + income +
                '}';
    }
}