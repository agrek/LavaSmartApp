package ca.concordia.gilgamesh.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.concordia.gilgamesh.R;
import ca.concordia.gilgamesh.models.PostLocation;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public TextView locationNameValueTextView;
    public TextView locationIdValueTextView;
    public TextView locationManagerValueTextView;
    public TextView locationQrValueTextView;
    public TextView locationNumMachinesValueTextView;

    public LocationViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);

        locationNameValueTextView = itemView.findViewById(R.id.locationNameValueTextView);
        locationIdValueTextView = itemView.findViewById(R.id.locationIdValueTextView);
        locationManagerValueTextView = itemView.findViewById(R.id.locationManagerValueTextView);
        locationQrValueTextView = itemView.findViewById(R.id.locationQrValueTextView);
        locationNumMachinesValueTextView = itemView.findViewById(R.id.locationNumMachinesValueTextView);
    }

    public void bindToPost(PostLocation post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);

        starView.setOnClickListener(starClickListener);

        locationNameValueTextView.setText(post.name);
        locationIdValueTextView.setText(post.name);
        locationManagerValueTextView.setText(post.manager);
        locationQrValueTextView.setText(post.qr);
        locationNumMachinesValueTextView.setText("NumMachinesPlaceholder");
    }
}
