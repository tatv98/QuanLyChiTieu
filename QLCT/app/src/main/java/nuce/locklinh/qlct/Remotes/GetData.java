package nuce.locklinh.qlct.Remotes;

import nuce.locklinh.qlct.Model.Expense;
import nuce.locklinh.qlct.Model.Income;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetData {
    @GET("/user")
    Call<ResponseData> getUsers();
    @GET("/user/{user_name}")
    Call<ResponseData> getUser(@Path("user_name") String user_name);
    @POST("/user")
    Call<ResponseData> postUser(@Body User user);

    @POST("/user/update")
    Call<ResponseData> updateUser(@Body User user);



    @GET("/income/{user_name}")
    Call<ResponseData> getIncome(@Path("user_name") String user_name);

    @POST("/income/add")
    Call<ResponseData> postIncome(@Body Income income);

    @POST("/income/update")
    Call<ResponseData> updateIncome(@Body Income income);

    @DELETE("/income/{income_id}")
    Call<ResponseData> deleteIncome(@Path("income_id") Integer income_id);




    @GET("/expense/{user_name}")
    Call<ResponseData> getExpense(@Path("user_name") String user_name);

    @POST("/expense/add")
    Call<ResponseData> postExpense(@Body Expense expense);

    @POST("/expense/update")
    Call<ResponseData> updateExpense(@Body Expense expense);

    @DELETE("/expense/{expense_id}")
    Call<ResponseData> deleteExpense(@Path("expense_id") Integer expense_id);

}
