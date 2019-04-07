package ca.concordia.gilgamesh.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.concordia.gilgamesh.R;
import ca.concordia.gilgamesh.models.PostMachine;

public class MachineViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public TextView machineLocationValueTextView;
    public TextView machineIdValueTextView;
    public TextView machineStatusValueTextView;
    public TextView machineQrValueTextView;

    public MachineViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        authorView = itemView.findViewById(R.id.postAuthor);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);

        machineLocationValueTextView = itemView.findViewById(R.id.machineLocationValueTextView);
        machineIdValueTextView = itemView.findViewById(R.id.machineIdValueTextView);
        machineStatusValueTextView = itemView.findViewById(R.id.machineStatusValueTextView);
        machineQrValueTextView = itemView.findViewById(R.id.machineQrValueTextView);
    }

    public void bindToPost(PostMachine post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);

        starView.setOnClickListener(starClickListener);

        machineLocationValueTextView.setText(post.location);
        machineIdValueTextView.setText(post.id);
        machineStatusValueTextView.setText(post.status);
        machineQrValueTextView.setText(post.qr);
    }
}

