package nuce.locklinh.qlct.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nuce.locklinh.qlct.Activities.ActivityAddExpense;
import nuce.locklinh.qlct.Adapters.AdapterExpense;
import nuce.locklinh.qlct.Activities.Home;
import nuce.locklinh.qlct.Model.Expense;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentExpense extends Fragment {
    public static final int REQUEST_CODE_ADD = 112;
    public static final int REQUEST_CODE_EDIT = 113;
    View vExpense;
    RecyclerView gvListExpense;
    List<Expense> listExpense;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vExpense = inflater.inflate(R.layout.fragment_expense, container, false);
        init();
        return vExpense;
    }

    private void init() {
        setHasOptionsMenu(true);
//        ((Home)getActivity()).getSupportActionBar().setTitle("Chi");
        listExpense = new ArrayList<>();
        gvListExpense = vExpense.findViewById(R.id.vgListExpense);

        getAllExpense();
    }

    private void getAllExpense() {
        listExpense.clear();
        final GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.getExpense(Home.username);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String date = "";
                for (Expense expense: response.body().getData().getExpense()){
                    Log.d("aaa", expense.toString());
                    expense.setHeader(false);
                    String temp = expense.getExpenseDate();
                    if (expense.getExpenseDate().equals(date)){
                        listExpense.add(expense);
                    }else {
                        Expense expenseHeader = new Expense(true, expense.getExpenseDate());
                        listExpense.add(expenseHeader);
                        listExpense.add(expense);
                    }
                    date = temp;

                }
                AdapterExpense adapterExpense = new AdapterExpense(getActivity(), listExpense);
                @SuppressLint("WrongConstant") RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
                gvListExpense.setLayoutManager(layoutManager);
                gvListExpense.setItemAnimator(new DefaultItemAnimator());
                gvListExpense.setAdapter(adapterExpense);
                adapterExpense.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itemExpense = menu.add(1, R.id.itemAddExpense, 1, "Thêm khoản chi");
        itemExpense.setIcon(R.drawable.ic_expense);
        itemExpense.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAddExpense:
                startActivityForResult(new Intent(getActivity(), ActivityAddExpense.class), REQUEST_CODE_ADD);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDIT){
            if (resultCode == Activity.RESULT_OK) {
                getAllExpense();
            }
        }
    }
}
