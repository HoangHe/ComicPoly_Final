package hehvph21007.poly.comicpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import hehvph21007.poly.comicpoly.API.UserDTO_API;
import hehvph21007.poly.comicpoly.Interface.UserInterface;
import hehvph21007.poly.comicpoly.models.SharedPreferencesUtil;
import hehvph21007.poly.comicpoly.models.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    TextView tvRegister, tvBoQua;
    EditText edtUserName, edtPassWord;
    Button btnLogin;
    CheckBox chkRememberPass;

    private SharedPreferences preferences, sharedPreferences ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvRegister = findViewById(R.id.tvRegister);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        chkRememberPass = findViewById(R.id.chkRemenberPass);
        tvBoQua = findViewById(R.id.tvBoQua);


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUserName.getText().toString().trim();
                String passWord = edtPassWord.getText().toString().trim();
                login(userName, passWord);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

    }


    void login( String userName, String passWord){


        if (TextUtils.isEmpty(userName)) {
            edtUserName.setError("Tên đăng nhập không được trống.");
            edtUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passWord)) {
            edtPassWord.setError("Mật khẩu không được trống");
            edtPassWord.requestFocus();
            return;
        }
        // Tạo Gson
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Sử dụng Interface
        UserInterface userInterface = retrofit.create(UserInterface.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        userDTO.setPassWord(passWord);

        Call<UserDTO_API> call = userInterface.Login(userDTO);
        call.enqueue(new Callback<UserDTO_API>() {
            @Override
            public void onResponse(Call<UserDTO_API> call, Response<UserDTO_API> response) {
                if (response.isSuccessful()) {
                    UserDTO_API responseDTO = response.body();
                    UserDTO userDTO = responseDTO.getUser();
                    if (userDTO != null) {


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", responseDTO.getToken());
                        editor.putString("id_user", userDTO.get_id());
                        editor.putString("fullName", userDTO.getFullName());
                        editor.putString("email", userDTO.getEmail());

                        SharedPreferencesUtil.putObject(LoginActivity.this, "userDTO", userDTO);
                        editor.apply();
                        rememberUser(userName, passWord, chkRememberPass.isChecked());
                        Toast.makeText(LoginActivity.this, responseDTO.getMsg(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, responseDTO.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO_API> call, Throwable t) {
                Log.e("LoginActivity", "Login failed", t);
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!status){
            // Xóa Tinhf Trạng Lưu Trữ Trước Đó
            editor.clear();
        }else{
            // Lưu dữ liệu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        // Lưu Lại Toàn Bộ
        editor.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK) {
            String user = data.getStringExtra("user");
            String pass = data.getStringExtra("pass");

            Log.d("PUT", "onActivityResult: " + user + pass);
            if (user != null && pass != null) {
                edtUserName.setText(user);
                edtPassWord.setText(pass);
            }
        }else{
            preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
            edtUserName.setText(preferences.getString("USERNAME",""));
            edtPassWord.setText(preferences.getString("PASSWORD",""));
            chkRememberPass.setChecked(preferences.getBoolean("REMEMBER",false));
        }
    }

}