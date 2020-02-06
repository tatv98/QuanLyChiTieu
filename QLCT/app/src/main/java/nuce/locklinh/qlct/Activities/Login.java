package nuce.locklinh.qlct.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import nuce.locklinh.qlct.Model.ResponseData;
import nuce.locklinh.qlct.Model.User;
import nuce.locklinh.qlct.R;
import nuce.locklinh.qlct.Remotes.GetData;
import nuce.locklinh.qlct.Remotes.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btn_signup,btn_confirm,btn_signin;
    EditText ed_username,ed_password,ed_confirm_pass_word,ed_age;
    EditText ed_user,ed_pass;
    Spinner sp_gender;
    CheckBox cb_saveuser;
    ArrayList<String> GendersList;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        btn_signup = findViewById(R.id.btn_sign_up);
        btn_signin = findViewById(R.id.btn_sign_in);
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);
        cb_saveuser = findViewById(R.id.cb_save_user);

        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        ed_user.setText(sharedPreferences.getString("user",""));
        ed_pass.setText(sharedPreferences.getString("pass", ""));
        cb_saveuser.setChecked(sharedPreferences.getBoolean("checked", false));
        btn_signin.setOnClickListener(this);
        btn_signup.setOnClickListener(this);

        GendersList = new ArrayList<>();
        GendersList.add("Nam");
        GendersList.add("Nữ");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_sign_up:
                show_sign_up();
                break;
            case R.id.btn_sign_in:
                login();



        }
    }

    private void login() {
        int result = 0;
        final String username = ed_user.getText().toString();
        final String password = ed_pass.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            Call<ResponseData> call = getData.getUser(username);
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    Log.d("login", response.body().toString());
                    for (User user: response.body().getData().getUser()){
                        if(user.getUserName().equals(username) && user.getUserPass().equals(password)){
                            Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                            if (cb_saveuser.isChecked()){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", username);
                                editor.putString("pass", password);
                                editor.putBoolean("checked", true);
                                editor.commit();
                            }
                            else {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("user");
                                editor.remove("pass");
                                editor.remove("checked");
                                editor.commit();
                            }
                            Intent intentHome = new Intent(Login.this, Home.class);
                            intentHome.putExtra("username", username);
                            startActivity(intentHome);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.d("loginf", t.getMessage());
                }
            });
        }else{
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void show_sign_up() {
        final Dialog dialog_sign_up= new Dialog(Login.this ) ;
        dialog_sign_up.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_sign_up.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
        dialog_sign_up.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        View view= LayoutInflater.from(Login.this).inflate(R.layout.activity_dangky,null,false);
        ed_username=view.findViewById(R.id.ed_username);
        ed_password= view.findViewById(R.id.ed_password);
        ed_confirm_pass_word=view.findViewById(R.id.ed_confirm);
        ed_age=view.findViewById(R.id.ed_age);
        sp_gender=view.findViewById(R.id.sp_gender);
        btn_confirm=view.findViewById(R.id.btn_sign_up);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checknewuser()){
                    User user = new User(ed_username.getText().toString(), ed_password.getText().toString(), Integer.parseInt(ed_age.getText().toString()), sp_gender.getSelectedItem().toString());
                    GetData getData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
                    Call<ResponseData> call = getData.postUser(user);
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            Toast.makeText(Login.this, "Đăng kí thành công!", Toast.LENGTH_LONG).show();
                            dialog_sign_up.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {

                        }
                    });
                }
                else Toast.makeText(Login.this, " Vui lòng kiểm tra lại ", Toast.LENGTH_SHORT).show();


            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Login.this,android.R.layout.simple_list_item_1,GendersList);
        sp_gender.setAdapter(arrayAdapter);
        dialog_sign_up.setContentView(view);
        dialog_sign_up.show();
    }

    private boolean checknewuser() {

        if(!TextUtils.isEmpty(ed_username.getText()) &&  !TextUtils.isEmpty(ed_password.getText()) && !TextUtils.isEmpty(ed_confirm_pass_word.getText())&& !TextUtils.isEmpty(ed_age.getText()))
        {
            if(ed_password.getText().toString().trim().equals(ed_confirm_pass_word.getText().toString().trim()))
                return true;
        }
        return false;
    }

}
