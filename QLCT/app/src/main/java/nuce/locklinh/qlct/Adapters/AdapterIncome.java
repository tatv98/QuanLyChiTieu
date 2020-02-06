package nuce.locklinh.qlct.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import nuce.locklinh.qlct.Activities.ActivityEditIncome;
import nuce.locklinh.qlct.Activities.Home;
import nuce.locklinh.qlct.Model.Income;
import nuce.locklinh.qlct.R;

import static nuce.locklinh.qlct.Fragments.FragmentIncome.REQUEST_CODE_EDIT;


public class AdapterIncome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Income> listIncome;

    public AdapterIncome(Context context, List<Income> listIncome) {
        this.context = context;
        this.listIncome = listIncome;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_custom_row_header_layout, parent, false);
                return new HeaderViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_custom_row_content_layout, parent, false);
                return new RowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Income income = listIncome.get(position);
        if (income != null){
            if (!income.isHeader()){
                ((RowViewHolder) holder).ivThumbnail.setImageResource(R.drawable.ic_income);
                ((RowViewHolder) holder).tvTitle.setText(income.getIncomeTitle());
                ((RowViewHolder) holder).tvContent.setText(income.getIncomeContent());

                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator(',');
                DecimalFormat decimalFormat = new DecimalFormat("#,### đ", symbols);
                ((RowViewHolder) holder).tvAmount.setText(decimalFormat.format(income.getIncomeAmount()));
                ((RowViewHolder) holder).vgForeground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentIncome = new Intent(context, ActivityEditIncome.class);
                        Bundle bundleIncome = new Bundle();
                        bundleIncome.putInt("income_id", income.getIncomeId());
                        bundleIncome.putString("income_title", income.getIncomeTitle());
                        bundleIncome.putString("income_content", income.getIncomeContent());
                        bundleIncome.putString("income_date", income.getIncomeDate());
                        bundleIncome.putInt("income_amount", income.getIncomeAmount());
                        intentIncome.putExtras(bundleIncome);
                        ((Home)context).startActivityForResult(intentIncome, REQUEST_CODE_EDIT);

                    }
                });
            }else {
                ((HeaderViewHolder) holder).tvDate.setText(income.getIncomeDate());
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (listIncome != null){
            Income income = listIncome.get(position);
            if (income != null){
                if (!income.isHeader()){
                    return 1;
                }
                else return 0;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (listIncome == null) return 0;
        return listIncome.size();
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail;
        TextView tvTitle, tvContent, tvAmount;
        RelativeLayout vgForeground;
        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            vgForeground = itemView.findViewById(R.id.vgForeground);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
