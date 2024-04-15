package com.example.ticketmaster;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.EventViewHolder> {

    private List<Ticket> tickets;

    public void setEvents(List<Ticket> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (tickets != null && position < tickets.size()) {
            Ticket ticket = tickets.get(position);
            holder.bind(ticket);
        }
    }

    @Override
    public int getItemCount() {
        return tickets != null ? tickets.size() : 0;
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewEventName;
        private TextView textViewStartDate;
        private TextView textViewPriceRange;
        private Button buyTicketsButton;
        private ImageView imageViewEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.name_text_view);
            textViewStartDate = itemView.findViewById(R.id.start_date_text_view);
            textViewPriceRange = itemView.findViewById(R.id.price_range_text_view);
            buyTicketsButton = itemView.findViewById(R.id.purchase_tickets_button);
            imageViewEvent = itemView.findViewById(R.id.event_image_view);

            // Set OnClickListener for the button
            buyTicketsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve the URL associated with the event
                    String url = (String) buyTicketsButton.getTag();
                    if (url != null && !url.isEmpty()) {
                        // Open the URL when the button is clicked
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void bind(Ticket ticket) {
            textViewEventName.setText(ticket.getName());
            textViewStartDate.setText("Start Date: " + ticket.getStartDate());
            textViewPriceRange.setText("Price Range: " + ticket.getPriceRange());
            // Load image using Picasso
            Picasso.get().load(ticket.getImageUrl()).into(imageViewEvent);

            // Set the URL as a tag for the button
            buyTicketsButton.setTag(ticket.getTicketmasterUrl());
        }
    }
}
