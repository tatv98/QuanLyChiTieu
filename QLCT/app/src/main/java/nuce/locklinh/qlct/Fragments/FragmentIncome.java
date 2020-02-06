package nuce.locklinh.qlct.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import nuce.locklinh.qlct.Activities.ActivityAddIncome;
import nuce.locklinh.qlct.Adapters.AdapterIncome;
import nuce.locklinh.qlct.Activities.Home;
import nuce.locklinh.qlct.Model.Income;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentIncome extends Fragment {
    private static final int REQUEST_CODE_ADD = 111;
    public static final int REQUEST_CODE_EDIT = 123;
    List<Income> listIncome;
    RecyclerView gvListIncome;
    View vIncome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vIncome = inflater.inflate(R.layout.fragment_income, container, false);
        init();
        return vIncome;
    }

    private void init() {
        setHasOptionsMenu(true);
//        ((Home)getActivity()).getSupportActionBar().setTitle("Thu");

        listIncome = new ArrayList<>();
        gvListIncome = vIncome.findViewById(R.id.vgListIncome);

        getAllIncome();

    }
    public void getAllIncome(){
        listIncome.clear();
        final GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.getIncome(Home.username);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String date = "";
                for (Income income: response.body().getData().getIncome()){
                    income.setHeader(false);
                    String temp = income.getIncomeDate();
                    if (income.getIncomeDate().equals(date)){
                        listIncome.add(income);
                    }else {
                        Income incomeHeader = new Income(true, income.getIncomeDate());
                        listIncome.add(incomeHeader);
                        listIncome.add(income);
                    }
                    date = temp;

                }
                AdapterIncome adapterIncome = new AdapterIncome(getActivity(), listIncome);
                @SuppressLint("WrongConstant") RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
                gvListIncome.setLayoutManager(layoutManager);
                gvListIncome.setItemAnimator(new DefaultItemAnimator());
                gvListIncome.setAdapter(adapterIncome);
                adapterIncome.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itemIncome = menu.add(1, R.id.itemAddIncome, 1, "Thêm khoản thu");
        itemIncome.setIcon(R.drawable.ic_income);
        itemIncome.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAddIncome:
                startActivityForResult(new Intent(getActivity(), ActivityAddIncome.class), REQUEST_CODE_ADD);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD || requestCode == REQUEST_CODE_EDIT){
            if (resultCode == Activity.RESULT_OK) {
                getAllIncome();
            }
        }
    }
}
