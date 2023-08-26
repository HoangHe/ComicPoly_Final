package hehvph21007.poly.comicpoly.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hehvph21007.poly.comicpoly.R;
import hehvph21007.poly.comicpoly.models.UserDTO;


public class FavoriteFragment extends Fragment {

UserDTO userDTO;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(UserDTO userDTO) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putParcelable("userData", userDTO);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}