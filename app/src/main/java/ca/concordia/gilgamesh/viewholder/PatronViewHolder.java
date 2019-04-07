package ca.concordia.gilgamesh.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.concordia.gilgamesh.R;
import ca.concordia.gilgamesh.models.PostPatron;

public class PatronViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public TextView patronNameValueTextView;
    public TextView patronIdValueTextView;
    public TextView patronEmailValueTextView;
    public TextView patronUsernameValueTextView;
    public TextView patronNumSavedMachinesValueTextView;
    public TextView patronNumOwnedMachinesValueTextView;

    public PatronViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);

        patronNameValueTextView = itemView.findViewById(R.id.patronNameValueTextView);
        patronIdValueTextView = itemView.findViewById(R.id.patronIdValueTextView);
        patronEmailValueTextView = itemView.findViewById(R.id.patronEmailValueTextView);
        patronUsernameValueTextView = itemView.findViewById(R.id.patronUsernameValueTextView);
        patronNumSavedMachinesValueTextView = itemView.findViewById(R.id.patronNumSavedMachinesValueTextView);
        patronNumOwnedMachinesValueTextView = itemView.findViewById(R.id.patronNumOwnedMachinesValueTextView);
    }

    public void bindToPost(PostPatron post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);

        starView.setOnClickListener(starClickListener);

        patronNameValueTextView.setText(post.username);
        patronIdValueTextView.setText(post.uid);
        patronEmailValueTextView.setText(post.email);
        patronUsernameValueTextView.setText(post.name);
        patronNumSavedMachinesValueTextView.setText("NumSavedMachinesPlaceholder");
        patronNumOwnedMachinesValueTextView.setText("NumOwnedMachinesPlaceholder");
    }
}
