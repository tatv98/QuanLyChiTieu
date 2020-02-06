package nuce.locklinh.qlct.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nuce.locklinh.qlct.Model.Expense;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import nuce.locklinh.qlct.Utils.DateFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditExpense extends AppCompatActivity implements View.OnClickListener {
    TextView tvDate, tvTitle;
    EditText edtContent, edtAmount;
    Spinner spnGroup;
    Button btnEdit, btnCancel;
    ImageView btnDelete;
    String expense_content, expense_date, expense_title;
    int expense_amount, expense_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_edit_layout);
        init();
    }
    private void init() {
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        edtContent = findViewById(R.id.edtContent);
        edtAmount = findViewById(R.id.edtAmount);
        spnGroup = findViewById(R.id.spnGroup);
        btnEdit = findViewById(R.id.btnConfirm);
        btnDelete = findViewById(R.id.btnTrash);
        btnCancel = findViewById(R.id.btnCancel);


        btnEdit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        Intent intentGetExpense = getIntent();
        Bundle bundleExpense = intentGetExpense.getExtras();
        if (bundleExpense != null){
            expense_id = bundleExpense.getInt("expense_id");
            expense_amount = bundleExpense.getInt("expense_amount");
            expense_title = bundleExpense.getString("expense_title");
            expense_content = bundleExpense.getString("expense_content");
            expense_date = bundleExpense.getString("expense_date");

            tvTitle.setText(expense_title);
            edtContent.setText(expense_content);
            edtAmount.setText(String.valueOf(expense_amount));
            tvDate.setText(expense_date);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnConfirm:
                editExpense();
                break;
            case R.id.btnTrash:
                deleteExpense();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void deleteExpense() {
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.deleteExpense(expense_id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("1111", response.body().toString());
                Toast.makeText(ActivityEditExpense.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void editExpense() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        DateFormat date = new DateFormat();
        try {
            expense_date = date.Format(sdf.format(new Date()));
            Log.d("123456", expense_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Expense expense = new Expense(expense_id, tvTitle.getText().toString(), edtContent.getText().toString(), expense_date, Integer.parseInt(edtAmount.getText().toString()), Home.username);
        Log.d("11111", expense.toString());
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.updateExpense(expense);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("1111", response.body().toString());
                Toast.makeText(ActivityEditExpense.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
}
