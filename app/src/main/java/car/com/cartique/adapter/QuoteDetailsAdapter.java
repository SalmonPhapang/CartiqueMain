package car.com.cartique.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import car.com.cartique.QuoteDetailsActivity;
import car.com.cartique.R;
import car.com.cartique.ResultsActivity;
import car.com.cartique.app.Config;
import car.com.cartique.model.Order;
import car.com.cartique.model.Quote;
import car.com.cartique.model.QuoteStatus;
import car.com.cartique.utility.NotificationGenerator;

public class QuoteDetailsAdapter extends RecyclerView.Adapter<QuoteDetailsAdapter.ViewHolder> {
    private Activity activity;
    private List<Quote> quoteItems;
    private Order order;
    private ViewHolder holder;
    private ProgressDialog progressBar;
    private DatabaseReference databaseReference;
    private static final String AMOUNT = "R0.00";

    private View v;

    public QuoteDetailsAdapter(Activity activity, List<Quote> quoteItems,Order order) {
        this.activity = activity;
        this.quoteItems = quoteItems;
        this.order = order;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = new ProgressDialog(this.activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            final Quote item = quoteItems.get(position);
            holder.clientName.setText(item.getClientName());
            if(item.getAmount().isEmpty() || item.getAmount() ==null)
                holder.amount.setText(AMOUNT);
                else
            holder.amount.setText("R"+item.getAmount());
            holder.status.setText(item.getQuoteStatus().toString());
            holder.orderDate.setText(item.getQuoteDate().toString());
            if (item.getQuoteStatus().name().equalsIgnoreCase(QuoteStatus.ACCEPTED.name()) ||item.getQuoteStatus().name().equalsIgnoreCase(QuoteStatus.DECLINED.name()) ){
                holder.btnDeclineQuote.setVisibility(View.GONE);
                holder.btnAcceptQuote.setVisibility(View.GONE);
            }

            holder.btnAcceptQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    progressBar.setMessage("Please wait a few seconds");
                    progressBar.setTitle("Sending Quoted amount for Paint job");
                    progressBar.show();
                    item.setQuoteStatus(QuoteStatus.ACCEPTED);
                    order.setAmount(item.getAmount());
                    order.setClientNotificationToken(item.getClientNotifictionToken());
                    databaseReference.child("Orders").child(order.getOrderID()).child("quotes")
                            .setValue(order.getQuotes()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sendAcceptedNotificationMessage(order);
                            progressBar.dismiss();
                            Snackbar.make(v, "Quote has been Accepted for order "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();
                            activity.recreate();
                        }
                    });
                }
            });
            holder.btnDeclineQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    progressBar.setMessage("Please wait a few seconds");
                    progressBar.setTitle("Declining Quoted amount for Paint job");
                    progressBar.show();
                    item.setQuoteStatus(QuoteStatus.DECLINED);
                    databaseReference.child("Orders").child(order.getOrderID()).child("quotes")
                            .setValue(order.getQuotes()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sendDeclinedNotificationMessage(order,position);
                            progressBar.dismiss();
                            Snackbar.make(v, "Quote has been declined for order "+order.getOrderNumber(), Snackbar.LENGTH_LONG).show();
                            activity.recreate();
                        }
                    });
                }
            });

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return quoteItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void sendAcceptedNotificationMessage(final Order order){
        databaseReference.child(Config.LEGACY_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String legacyKey = (String)dataSnapshot.getValue();
                NotificationGenerator notificationGenerator = new NotificationGenerator();
                String message = notificationGenerator.getFCMNotificationMessage(order,"Cartique",Config.ACCEPTED_QUOTATION + order.getOrderNumber());
                notificationGenerator.sendMessageToFcm(message,legacyKey);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendDeclinedNotificationMessage(final Order order, final int index){
        databaseReference.child(Config.LEGACY_KEY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String legacyKey = (String)dataSnapshot.getValue();
                NotificationGenerator notificationGenerator = new NotificationGenerator();
                String message = notificationGenerator.getFCMNotificationMessage(order,"Cartique",Config.DECLINED_QUOTATION + order.getOrderNumber(),index);
                notificationGenerator.sendMessageToFcm(message,legacyKey);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void insert(int position, Quote data) {
        quoteItems.add(position, data);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView amount;
        TextView status;
        TextView orderDate;
        AppCompatButton btnAcceptQuote;
        AppCompatButton btnDeclineQuote;

        ViewHolder(View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.txtquoteName);
            amount = itemView.findViewById(R.id.txtquoteAmount);
            status = itemView.findViewById(R.id.txtquoteStatus);
            orderDate= itemView.findViewById(R.id.txtquoteDate);
            btnAcceptQuote = itemView.findViewById(R.id.btnAcceptQuote);
            btnDeclineQuote = itemView.findViewById(R.id.btnDeclineQuote);
        }

    }
}

