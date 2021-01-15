package io.github.nearchos.assistant8998;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the
    // views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final View viewContainer;
        public final TextView textViewOptionNumber;
        public final TextView textViewOptionHeader;
        public final TextView textViewOptionDetails;
        public ViewHolder(View view) {
            super(view);
            this.viewContainer = view;
            this.textViewOptionNumber = itemView.findViewById(R.id.textViewOptionNumber);
            this.textViewOptionHeader = itemView.findViewById(R.id.textViewOptionHeader);
            this.textViewOptionDetails = itemView.findViewById(R.id.textViewOptionDetails);
        }
    }

    private final Context context;

    private final String [] headers;
    private final String [] details;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OptionsAdapter(final Context context, final boolean selectedGreek) {
        this.context = context;
        if(selectedGreek) {
            this.headers = context.getResources().getStringArray(R.array.optionHeaders_el);
            this.details = context.getResources().getStringArray(R.array.optionDetails_el);
        } else {
            this.headers = context.getResources().getStringArray(R.array.optionHeaders);
            this.details = context.getResources().getStringArray(R.array.optionDetails);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public OptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.option_view, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textViewOptionNumber.setText(String.format(Locale.ENGLISH, "%d", position+1));
        holder.textViewOptionHeader.setText(headers[position]);
        holder.textViewOptionDetails.setText(details[position]);
        holder.viewContainer.setOnClickListener(v -> select(position+1));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return headers.length;
    }

    private void select(final int option) {
        final Intent intent = new Intent(context, SubmitSmsActivity.class);
        intent.putExtra(SubmitSmsActivity.EXTRA_KEY_OPTION, option);
        context.startActivity(intent);
    }
}