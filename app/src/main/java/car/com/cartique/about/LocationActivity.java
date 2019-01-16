package car.com.cartique.about;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bhargavms.dotloader.DotLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import car.com.cartique.R;
import car.com.cartique.adapter.FeedListAdapter;
import car.com.cartique.adapter.LocationAdapter;
import car.com.cartique.decorator.MyDividerItemDecoration;
import car.com.cartique.model.Client;

public class LocationActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    DotLoader bar;
    private RecyclerView recyclerView;
    private LocationAdapter listAdapter;
    private List<String> locationtList = new ArrayList<>();
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        FirebaseApp.initializeApp(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_location);
        recyclerView = findViewById(R.id.list);
        listAdapter = new LocationAdapter(this, locationtList);
        recyclerView.setAdapter(listAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        bar = findViewById(R.id.prgload);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Clients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String,Client>> genericTypeIndicator = new GenericTypeIndicator<Map<String,Client>>() {
                };
                Map<String,Client> clientItems = dataSnapshot.getValue(genericTypeIndicator);
                TreeSet<String> sortedList = new TreeSet<>();
                sortedList.add("All");
                for (Client client:clientItems.values()) {
                    sortedList.add(client.getCity());
                }
                locationtList.addAll(sortedList);
                listAdapter.notifyDataSetChanged();
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
