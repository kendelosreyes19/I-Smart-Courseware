package net.smallacademy.authenticatorapp.holadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.smallacademy.authenticatorapp.R;

public class MyViewHolderReviewer extends RecyclerView.ViewHolder {

    TextView mName;TextView mDownload;

    public MyViewHolderReviewer(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.name);
        mDownload=itemView.findViewById(R.id.down);

    }
}
