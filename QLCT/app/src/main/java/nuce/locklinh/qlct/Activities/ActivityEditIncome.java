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

import nuce.locklinh.qlct.Model.Income;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import nuce.locklinh.qlct.Utils.DateFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditIncome extends AppCompatActivity implements View.OnClickListener {
    TextView tvDate, tvTitle;
    EditText edtContent, edtAmount;
    Spinner spnGroup;
    Button btnEdit, btnCancel;
    ImageView btnDelete;
    String income_content, income_date, income_title;
    int income_amount, income_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_edit_layout);
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


        Intent intentGetIncome = getIntent();
        Bundle bundleIncome = intentGetIncome.getExtras();
        if (bundleIncome != null){
            income_id = bundleIncome.getInt("income_id");
            income_amount = bundleIncome.getInt("income_amount");
            income_title = bundleIncome.getString("income_title");
            income_content = bundleIncome.getString("income_content");
            income_date = bundleIncome.getString("income_date");

            tvTitle.setText(income_title);
            edtContent.setText(income_content);
            edtAmount.setText(String.valueOf(income_amount));
            tvDate.setText(income_date);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnConfirm:
                editIncome();
                break;
            case R.id.btnTrash:
                deleteIncome();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void deleteIncome() {
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.deleteIncome(income_id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("1111", response.body().toString());
                Toast.makeText(ActivityEditIncome.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void editIncome() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        DateFormat date = new DateFormat();
        try {
            income_date = date.Format(sdf.format(new Date()));
            Log.d("123456", income_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Income income = new Income(income_id, tvTitle.getText().toString(), edtContent.getText().toString(), income_date, Integer.parseInt(edtAmount.getText().toString()), Home.username);
        Log.d("11111", income.toString());
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.updateIncome(income);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("1111", response.body().toString());
                Toast.makeText(ActivityEditIncome.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
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
