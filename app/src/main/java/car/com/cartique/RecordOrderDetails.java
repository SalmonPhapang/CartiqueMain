package car.com.cartique;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import car.com.cartique.app.Config;
import car.com.cartique.model.Order;
import car.com.cartique.utility.NotificationGenerator;

public class RecordOrderDetails extends AppCompatActivity {
private TextView txtClientName;
    private TextView txtOrderType;
    private TextView txtOrderStatus;
    private TextView txtOrderDate;
    private TextView txtOrderNumber;
    private TextView txtOrderAmount;
    private TextView txtMake;
    private TextView txtModel;
    private TextView txtYear;
    private TextView txtColor;
    private Order order;
    private AppCompatButton btnAcceptDates;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_records_details);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        order =(Order) getIntent().getSerializableExtra("Order");
        txtClientName = findViewById(R.id.txtUserName);
        txtOrderNumber = findViewById(R.id.txtOrderNumber);
        txtOrderType = findViewById(R.id.txtOrderType);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtOrderStatus = findViewById(R.id.txtOrderStatus);
        txtOrderAmount = findViewById(R.id.txtOrderAmount);
        txtMake = findViewById(R.id.txtCarMake);
        txtModel = findViewById(R.id.txtCarModel);
        txtYear = findViewById(R.id.txtCarYear);
        txtColor = findViewById(R.id.txtCarColor);
        btnAcceptDates = findViewById(R.id.btnAcceptDate);

        txtClientName.setText(order.getClientName());
        txtOrderNumber.setText(order.getOrderNumber());
        txtOrderType.setText(order.getOrderType().toString());
        txtOrderDate.setText(order.getOrderDate().toString());
        txtOrderStatus.setText(order.getOrderStatus().toString());
        if (order.getAmount()==null || order.getAmount().isEmpty()){
            txtOrderAmount.setText("Not Quoted");
        } else{
            txtOrderAmount.setText(order.getAmount());
        }
        txtModel.setText(order.getCar().getModel());
        txtMake.setText(order.getCar().getMake());
        txtYear.setText(order.getCar().getYear());
        txtColor.setText(order.getCar().getColor());
        LinearLayout comments = findViewById(R.id.commentsLayout);
        LinearLayout dateLayout = findViewById(R.id.datesLayout);
        if(order.getScheduledDate()== null){
            addRadioButtons(order);
            comments.setVisibility(View.GONE);
            dateLayout.setVisibility(View.VISIBLE);
        }
        else {
            TextView scheduleDate = findViewById(R.id.txt_schedule_date);
            scheduleDate.setText(getResources().getString(R.string.text_schedule_logbook) +" "+ order.getScheduledDate()+"\n");
            for (String log:order.getLogBook()){
                scheduleDate.setText(scheduleDate.getText().toString()+ log);
            }
            comments.setVisibility(View.VISIBLE);
            dateLayout.setVisibility(View.GONE);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Submitting Date");
        progressDialog.setMessage("Great choice!");
        btnAcceptDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (order.getScheduledDate()!=null){
                    progressDialog.show();
                    databaseReference.child("Orders").child(order.getOrderID()).child("scheduledDate")
                            .setValue(order.getScheduledDate()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            sendNotificationMessage(order);
                            Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
                            intent.putExtra("message",getResources().getString(R.string.text_dates_message));
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Snackbar.make(v, "Oops! Something went wrong, please try again "+order.getOrderNumber(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                }else {
                   Snackbar snackbar =  Snackbar.make(v, "Please select the desired date first ", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).setActionTextColor(Color.GREEN).show();
                }
            }
        });
    }
    public void sendNotificationMessage(final Order order){
        databaseReference.child(Config.LEGACY_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String legacyKey = (String)dataSnapshot.getValue();
                NotificationGenerator notificationGenerator = new NotificationGenerator();
                String message = notificationGenerator.getFCMNotificationMessage(order,"Cartique",Config.NEW_SCHEDUALED_DATE + order.getOrderNumber());
                notificationGenerator.sendMessageToFcm(message,legacyKey);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_details:
                Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
                intent.putExtra("Order", order);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addRadioButtons(final Order order) {
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            int number = order.getAvailableDates().size();
            for (int i = 1; i <= number; i++) {
                final RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText(order.getAvailableDates().get(i-1).toString());
                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        order.setScheduledDate(new Date(rdbtn.getText().toString()));
                    }
                });
                ll.addView(rdbtn);
            }
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
        }
    }
    public void addRadioButtons(int number) {
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                ll.addView(rdbtn);
            }
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
        }
    }
}
