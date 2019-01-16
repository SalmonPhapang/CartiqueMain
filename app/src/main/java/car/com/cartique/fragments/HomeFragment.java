package car.com.cartique.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import car.com.cartique.R;
import car.com.cartique.custom.CustomMenuAdapter;
import car.com.cartique.model.GridMenu;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GridView gridView;
    private CustomMenuAdapter menuAppAdapter;
    private ArrayList<GridMenu> gridMenuArrayList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gridMenuArrayList = new ArrayList<>();
        gridMenuArrayList.add(new GridMenu("Service", R.mipmap.ic_menu_repair_circular));
        gridMenuArrayList.add(new GridMenu("Paint", R.mipmap.ic_menu_paint_circular));
        gridMenuArrayList.add(new GridMenu("Profile", R.mipmap.ic_menu_user_circular));
        gridMenuArrayList.add(new GridMenu("Calender", R.mipmap.ic_menu_calender_circular));
        gridMenuArrayList.add(new GridMenu("About", R.mipmap.ic_menu_help_circular));
        gridMenuArrayList.add(new GridMenu("Search", R.mipmap.ic_menu_search_circular));


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = v.findViewById(R.id.grid_view_image_text);
        menuAppAdapter = new CustomMenuAdapter(gridMenuArrayList, getActivity());
        gridView.setAdapter(menuAppAdapter);

        return v;
    }

}
