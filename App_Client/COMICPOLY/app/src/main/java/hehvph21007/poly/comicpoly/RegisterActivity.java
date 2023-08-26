package hehvph21007.poly.comicpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URISyntaxException;

import hehvph21007.poly.comicpoly.API.UserDTO_API;
import hehvph21007.poly.comicpoly.Interface.UserInterface;
import hehvph21007.poly.comicpoly.models.UserDTO;
import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
public class RegisterActivity extends AppCompatActivity {
    private TextView tvLogin;
    EditText edtUserName, edtPassWord,edtCheckPassWord, edtEmail, edtFullName;
    Button btnRegister;
    private SharedPreferences sharedPreferences ;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(MainActivity.SOCKET_URI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvLogin = findViewById(R.id.tvLogin);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtCheckPassWord = findViewById(R.id.edtCheckPassWord);
        edtEmail = findViewById(R.id.edtEmail);
        edtFullName = findViewById(R.id.edtFullName);
        btnRegister = findViewById(R.id.btnRegister);

        sharedPreferences = getSharedPreferences("MyAcount", Context.MODE_PRIVATE);
        mSocket.connect();
// lắng nghe su kien
        mSocket.on("New Comic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String data_sv_send = (String) args[0];
                        Toast.makeText(RegisterActivity.this, "Server trả về: " + data_sv_send,
                                Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }


    void Register(){
        String userName = edtUserName.getText().toString().trim();
        String passWord = edtPassWord.getText().toString().trim();
        String checkPass = edtCheckPassWord.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();

        if(TextUtils.isEmpty(fullName)){
            edtFullName.setError("Họ và tên không được để trống");
            edtFullName.requestFocus();
            return;
        }

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
        if(TextUtils.isEmpty(email)){
            edtEmail.setError("Email Không được để trống");
            edtEmail.requestFocus();
            return;
        }

        if (userName.length() < 6) {
            edtUserName.setError("Tên đăng nhập phải lớn hơn 6 kí tự");
            edtUserName.requestFocus();
            return;
        }
        if (passWord.length() < 6 ) {
            edtPassWord.setError("Mật khẩu phải lớn hơn 6 kí tự");
            edtPassWord.requestFocus();
            return;
        }
        if (userName.matches(".*[^a-zA-Z0-9].*")) {
            edtUserName.setError("Tên đăng nhập không được chứa kí tự đặc biệt");
            edtUserName.requestFocus();
            return;
        }

        if (passWord.matches(".*[^a-zA-Z0-9].*")) {
            edtPassWord.setError("Mật khẩu không được chứa kí tự đặc biệt");
            edtPassWord.requestFocus();
            return;
        }

        if(!passWord.equals(checkPass)){
            edtCheckPassWord.setError("Mật khẩu không trùng khớp");
            edtCheckPassWord.requestFocus();
            return;
        }


        // Kiểm tra định dạng email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            edtEmail.setError("Email sai định dạng");
            edtEmail.requestFocus();
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
        userDTO.setEmail(email);
        userDTO.setFullName(fullName);

        Log.d("USER", "Register: "+ userDTO );

        Call<UserDTO> call = userInterface.Register(userDTO);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                        if (response.code() == 201) {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            mSocket.emit("New User", userDTO.getUserName());

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    // Chuyển dữ liệu sang LoginActivity
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("user", userDTO.getUserName());
                                    intent.putExtra("pass", userDTO.getPassWord());
                                    Log.d("DATA_USER", "run: " +  userDTO.getUserName() + "\n" + userDTO.getPassWord());
                                    setResult(RESULT_OK, intent);
                                    startActivityForResult(intent, 111);
                                    finish();
                                }
                            }, 2000);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("Register", "Register failed", t);
                Toast.makeText(RegisterActivity.this, "Đăng kí thất bại." , Toast.LENGTH_SHORT).show();
            }
        });
    }


}