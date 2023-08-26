package hehvph21007.poly.comicpoly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://192.168.43.32:3000/api/";
    public static final String SOCKET_URI = "http://192.168.43.32:3000";
    private SharedPreferences preferences ;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(SOCKET_URI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        if (token != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            },3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            },3000);
        }
        // mở kết nối
        mSocket.connect();
        // lắng nghe sự kiện
        mSocket.on("New Comic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_sv_send = (String) args[0];
                        // hiện notify lê status
                        postNotify("Truyện mới", data_sv_send);
                    }
                });

            }
        });

    }
    void postNotify(String title, String content){
        // Khởi tạo layout cho Notify
        Notification customNotification = new NotificationCompat.Builder(MainActivity.this, NotifyConfig.CHANEL_ID)
                .setSmallIcon(R.drawable.log_c)
                .setContentTitle( title )
                .setContentText(content)
                .setAutoCancel(true)

                .build();
        // Khởi tạo Manager để quản lý notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

        // Cần kiểm tra quyền trước khi hiển thị notify
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            // Gọi hộp thoại hiển thị xin quyền người dùng
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
            Toast.makeText(MainActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
            return; // thoát khỏi hàm nếu chưa được cấp quyền
        }
        // nếu đã cấp quyền rồi thì sẽ vượt qua lệnh if trên và đến đây thì hiển thị notify
        // mỗi khi hiển thị thông báo cần tạo 1 cái ID cho thông báo riêng
        int id_notiy = (int) new Date().getTime();// lấy chuỗi time là phù hợp
        //lệnh hiển thị notify
        notificationManagerCompat.notify(id_notiy , customNotification);
    }

}