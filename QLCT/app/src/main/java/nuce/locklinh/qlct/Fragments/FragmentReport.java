package nuce.locklinh.qlct.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import nuce.locklinh.qlct.Activities.Home;
import nuce.locklinh.qlct.Model.Expense;
import nuce.locklinh.qlct.Model.Income;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReport extends Fragment {
    View vReport;
    BarChart barChart;
    public static int sumIncome, sumExpense;
    TextView tvSumIncome, tvSumExpense;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report, container, false);
        init();
        return vReport;
    }


    public void init() {
//        ((Home)getActivity()).getSupportActionBar().setTitle("Thống kê");
        getIncomeData();
        getExpenseData();
        tvSumIncome = vReport.findViewById(R.id.tvSumIncome);
        tvSumExpense = vReport.findViewById(R.id.tvSumExpense);

        barChart = vReport.findViewById(R.id.barChar);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        Description desc = new Description();
        desc.setText("");
        barChart.setDescription(desc);


        BarDataSet barDataSetIncome = new BarDataSet(getAllIncome(), "Thu");
        barDataSetIncome.setColors(Color.BLUE);

        BarDataSet barDataSetExpense = new BarDataSet(getAllExpense(), "Chi");
        barDataSetExpense.setColors(Color.GREEN);

        BarData data = new BarData(barDataSetIncome, barDataSetExpense);

        float groupSpace = 0.1f;
        float barSpace = 0.02f;
        float barWidth = 0.28f;
        int groupCount = 1; //perform the "explicit" grouping

        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);

        String[] months = new String[]{"0", "1", "2", "3", "4", "5", "6"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(false);
        xAxis.setLabelCount(groupCount);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace)* groupCount);

//        barChart.invalidate();
    }

    public ArrayList<BarEntry> getAllIncome(){
        final List<BarEntry> barEntriesIncome;
        barEntriesIncome = new ArrayList<>();
        barEntriesIncome.add(new BarEntry(1, 40f));

        return (ArrayList<BarEntry>) barEntriesIncome;
    }

    public ArrayList<BarEntry> getAllExpense(){
        final List<BarEntry> barEntriesExpense;
        barEntriesExpense = new ArrayList<>();
        barEntriesExpense.add(new BarEntry(1, 45f));

        return (ArrayList<BarEntry>) barEntriesExpense;
    }

    public void getIncomeData(){
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.getIncome(Home.username);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                sumIncome = 0;
                for (Income income: response.body().getData().getIncome()){
                    sumIncome = sumIncome + income.getIncomeAmount();
                }
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                DecimalFormat decimalFormat = new DecimalFormat("#,### đ", symbols);
                tvSumIncome.setText(decimalFormat.format(sumIncome));
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    public void getExpenseData(){
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.getExpense(Home.username);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                sumExpense = 0;
                for (Expense expense: response.body().getData().getExpense()){
                    sumExpense = sumExpense + expense.getExpenseAmount();
                }
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                DecimalFormat decimalFormat = new DecimalFormat("#,### đ", symbols);
                tvSumExpense.setText(decimalFormat.format(sumExpense));
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    public class MyXAxisValueFormatter extends IndexAxisValueFormatter{
        private String[] mValues;
        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value) {
            value = value/0.7f;
            value += 1;
            Log.d("aaa", String.valueOf(value));
            return mValues[(int) value];
        }
    }


}
