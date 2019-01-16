package car.com.cartique;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import car.com.cartique.model.Order;
import car.com.cartique.model.OrderStatus;

public class OrderStatusActivity extends AppCompatActivity {
    private TextView txtStatusClientName;
    private TextView txtStatusOrderNumber;
    private DatabaseReference databaseReference;
    private OrderStatus status;
    private ProgressBar initiatedHorizontalProgressbar;
    private ProgressBar preparationHorizontalProgressbar;
    private ProgressBar inprogressHorizontalProgressbar;
    private ProgressBar finishedHorizontalProgressbar;
    private ProgressBar collectionHorizontalProgressbar;
    private ProgressBar collectedHorizontalProgressbar;

    private ProgressBar initiatedCurcularProgressbar;
    private ProgressBar preparationCurcularProgressbar;
    private ProgressBar inprogressCurcularProgressbar;
    private ProgressBar finishedCurcularProgressbar;
    private ProgressBar collectionCurcularProgressbar;
    private ProgressBar collectedCurcularProgressbar;

    private AppCompatImageView initialStatusActive;
    private AppCompatImageView inprogressStatusActive;
    private AppCompatImageView preparationStatusActive;
    private AppCompatImageView finishedStatusActive;
    private AppCompatImageView collectionStatusActive;
    private AppCompatImageView collectedStatusActive;

    private AppCompatTextView txtStatusTime;
    private AppCompatTextView txtStatusDate;
    private AppCompatTextView txtStatusDetails;
    private AppCompatTextView txtStatusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status_activity);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        txtStatusClientName = findViewById(R.id.txtstatusClientName);
        txtStatusOrderNumber = findViewById(R.id.txtstatusOrderNumber);
        txtStatusTime = findViewById(R.id.txtStatusTime);
        txtStatusDate = findViewById(R.id.txtStatusDate);
        txtStatusDetails = findViewById(R.id.txtStatusDetails);
        txtStatusMessage = findViewById(R.id.txtStatusMessage);

        Intent i = getIntent();
        final Order order = (Order)i.getSerializableExtra("Order");
        txtStatusOrderNumber.setText(order.getOrderNumber());
        txtStatusClientName.setText(order.getClientName());
        OrderStatus orderStatus = order.getOrderStatus();

        initiatedCurcularProgressbar = findViewById(R.id.initiatedStatusCurcularProgressbar);
        preparationHorizontalProgressbar = findViewById(R.id.preparationStatusHorizontalProgressbar);
        inprogressHorizontalProgressbar = findViewById(R.id.inprogressHorizontalStatusProgressbar);
        finishedHorizontalProgressbar = findViewById(R.id.finishedHorizontalStatusProgressbar);
        collectionHorizontalProgressbar = findViewById(R.id.collectionHorizontalStatusProgressbar);
        collectedHorizontalProgressbar = findViewById(R.id.collectedHorizontalStatusProgressbar);

        preparationCurcularProgressbar = findViewById(R.id.preparationCurcularStatusProgressbar);
        inprogressCurcularProgressbar = findViewById(R.id.inprogressCurcularStatusProgressbar);
        finishedCurcularProgressbar = findViewById(R.id.finishedCicularStatusProgressbar);
        collectionCurcularProgressbar = findViewById(R.id.collectionCircularStatusProgressbar);
        collectedCurcularProgressbar = findViewById(R.id.collectedCircularStatusProgressbar);

        initialStatusActive = findViewById(R.id.initialStatusActive);
        preparationStatusActive = findViewById(R.id.preparationStatusActive);
        inprogressStatusActive = findViewById(R.id.inprogresstatusActive);
        finishedStatusActive = findViewById(R.id.finishedStatusActive);
        collectionStatusActive = findViewById(R.id.collectionStatusActive);
        collectedStatusActive = findViewById(R.id.collectedStatusActive);

        switch(orderStatus){
            case INITIATED:
                preparationHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                txtStatusMessage.setText(getResources().getString(R.string.initial_status_msg));
                break;
            case PREPARATION:
                inprogressHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                txtStatusMessage.setText(getResources().getString(R.string.preparation_status_msg));
                break;
            case INPROGRESS:
                finishedHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                txtStatusMessage.setText(getResources().getString(R.string.inprogess_status_msg));
                break;
            case FINISHED:
                collectionHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                finishedHorizontalProgressbar.setProgress(100);
                txtStatusMessage.setText(getResources().getString(R.string.finished_status_msg));
                break;
            case COLLECTION:
                collectedHorizontalProgressbar.setIndeterminate(true);
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                collectionStatusActive.setVisibility(View.VISIBLE);
                txtStatusMessage.setText(getResources().getString(R.string.collection_status_msg));
                break;
            case COLLECTED:
                initialStatusActive.setVisibility(View.VISIBLE);
                preparationStatusActive.setVisibility(View.VISIBLE);
                inprogressStatusActive.setVisibility(View.VISIBLE);
                finishedStatusActive.setVisibility(View.VISIBLE);
                collectionStatusActive.setVisibility(View.VISIBLE);
                collectedStatusActive.setVisibility(View.VISIBLE);
                preparationHorizontalProgressbar.setProgress(100);
                inprogressHorizontalProgressbar.setProgress(100);
                finishedHorizontalProgressbar.setProgress(100);
                collectionHorizontalProgressbar.setProgress(100);
                txtStatusMessage.setText(getResources().getString(R.string.collected_status_msg));
                break;
        }
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate= dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String formatedTime = timeFormat.format(currentDate);

        txtStatusTime.setText(formatedTime);
        txtStatusDate.setText(formattedDate);
        txtStatusDetails.setText(orderStatus.name());
    }
}
