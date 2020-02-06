package nuce.locklinh.qlct.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nuce.locklinh.qlct.Activities.Home;
import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.Model.User;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUser extends Fragment implements View.OnClickListener{
    View vUser;
    Button btnUpdate, btnLogout;
    EditText edtUser, edtPass, edtAge, edtGender;
    public Integer user_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vUser = inflater.inflate(R.layout.fragment_user, container, false);
        init();
        return vUser;
    }

    private void init() {
//        ((Home)getActivity()).getSupportActionBar().setTitle("Người dùng");

        btnUpdate = vUser.findViewById(R.id.btnUpdate);
        btnLogout = vUser.findViewById(R.id.btnLogout);
        edtUser = vUser.findViewById(R.id.edtUser);
        edtPass = vUser.findViewById(R.id.edtPass);
        edtAge = vUser.findViewById(R.id.edtAge);
        edtGender = vUser.findViewById(R.id.edtGender);

        btnUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        getUser();
    }

    private void getUser() {
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.getUser(Home.username);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                for (User user: response.body().getData().getUser()){
                    edtUser.setText(user.getUserName());
                    edtPass.setText(user.getUserPass());
                    edtAge.setText(user.getUserAge().toString());
                    edtGender.setText(user.getUserGender());
                    user_id = user.getUserId();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpdate:
                updateUser();
                break;
            case R.id.btnLogout:
                getActivity().finish();
                break;
        }
    }

    private void updateUser() {
        User user = new User(user_id, edtUser.getText().toString(), edtPass.getText().toString(), Integer.parseInt(edtAge.getText().toString()), edtGender.getText().toString());
        Log.d("ahihi", user.toString());
        GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<ResponseData> call = getData.updateUser(user);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Toast.makeText(getActivity(), "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });

    }
}
