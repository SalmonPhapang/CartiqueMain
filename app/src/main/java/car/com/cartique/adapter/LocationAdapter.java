package car.com.cartique.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import car.com.cartique.R;
import car.com.cartique.app.Config;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private Activity activity;
    private List<String> locationitems;
    private ViewHolder holder;
    private View v;
       public LocationAdapter(Activity activity, List<String> locationList){
        this.activity = activity;
        this.locationitems = locationList;
        }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loction_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String item = locationitems.get(position);
        holder.name.setText(item);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeLocationStringInPref(item);
                Toast.makeText(activity.getApplicationContext(),"Location has been changed",Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationitems.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.locationName);
        }

    }
    private void storeLocationStringInPref(String location) {
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.LOCATION_STRING,location);
        editor.commit();
    }
}
