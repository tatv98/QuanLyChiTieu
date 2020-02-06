package nuce.locklinh.qlct.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nuce.locklinh.qlct.Adapters.AdapterShowListGroup;
import nuce.locklinh.qlct.Model.Expense;
import nuce.locklinh.qlct.Model.Group;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import nuce.locklinh.qlct.Utils.DateFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddExpense extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE_ADDEXPENSE = 112;
    TextView tvDate;
    EditText edtContent, edtAmount;
    Spinner spnGroup;
    Button btnAdd, btnCancel;
    List<Group> listGroup;
    AdapterShowListGroup adapterShowListGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_add_layout);
        init();
        showAllGroup();
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void init() {
        tvDate = findViewById(R.id.tvDate);
        edtContent = findViewById(R.id.edtContent);
        edtAmount = findViewById(R.id.edtAmount);
        spnGroup = findViewById(R.id.spnGroup);
        btnAdd = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(new Date()));
    }
    private void showAllGroup(){
        listGroup = new ArrayList<>();
        Group gEating = new Group(R.drawable.eating, "Ăn uống");
        Group gMove = new Group(R.drawable.car, "Di chuyển");
        Group gTravel = new Group(R.drawable.travel, "Du lịch");
        Group gShopping = new Group(R.drawable.shoppingbasket, "Mua sắm");
        listGroup.add(gEating);
        listGroup.add(gMove);
        listGroup.add(gTravel);
        listGroup.add(gShopping);
        adapterShowListGroup = new AdapterShowListGroup(this, R.layout.layout_custom_spiner_group, listGroup);
        spnGroup.setAdapter(adapterShowListGroup);
        adapterShowListGroup.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnConfirm:
                int positionGroup = spnGroup.getSelectedItemPosition();
                int thumbnail = listGroup.get(positionGroup).getmThumbnail();
                String expense_title = listGroup.get(positionGroup).getmGroup();
                String expense_content = edtContent.getText().toString();
                String expense_date = tvDate.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                DateFormat date = new DateFormat();
                try {
                    expense_date = date.Format(sdf.format(new Date()));
                    Log.d("123456", expense_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (expense_title == null || expense_title.equals("")){
                    Toast.makeText(this, "Bạn chưa chọn nhóm!", Toast.LENGTH_SHORT).show();
                    spnGroup.requestFocus();
                } else if (edtAmount.getText().toString() == null || edtAmount.getText().toString().equals("")){
                    Toast.makeText(this, "Bạn chưa nhập số tiền!", Toast.LENGTH_SHORT).show();
                    edtAmount.requestFocus();
                } else {
                    int amount = Integer.parseInt(edtAmount.getText().toString());
                    Expense expense = new Expense(expense_title, expense_content, expense_date, amount, Home.username);
                    Log.d("1111", expense.toString());

                    GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
                    Call<ResponseData> call = service.postExpense(expense);
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            Log.d("1111", response.body().toString());
                            Toast.makeText(ActivityAddExpense.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }
}
