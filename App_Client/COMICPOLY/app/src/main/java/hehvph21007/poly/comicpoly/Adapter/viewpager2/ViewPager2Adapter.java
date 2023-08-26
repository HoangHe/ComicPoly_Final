package hehvph21007.poly.comicpoly.Adapter.viewpager2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hehvph21007.poly.comicpoly.Fragment.FavoriteFragment;
import hehvph21007.poly.comicpoly.Fragment.Home_Fragment;
import hehvph21007.poly.comicpoly.Fragment.Profile_Fragment;
import hehvph21007.poly.comicpoly.models.UserDTO;


public class ViewPager2Adapter extends FragmentStateAdapter {
    private UserDTO userDTO; // Biến để lưu trữ thông tin người dùng

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, UserDTO userDTO) {
        super(fragmentActivity);
        this.userDTO = userDTO;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("VIEW", "createFragment: " + userDTO);
        switch (position) {

            // Truyền thông tin userData vào Home_Fragment
            case 1:
                return FavoriteFragment.newInstance(userDTO); // Truyền thông tin userData vào FavoriteFragment
            case 2:
                return Profile_Fragment.newInstance(userDTO); // Truyền thông tin userData vào Profile_Fragment
            default:
                return Home_Fragment.newInstance(userDTO); // Truyền thông tin userData vào mặc định fragment
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Vì chỉ có 3 fragment nên chỉ trả về 3
    }
}
