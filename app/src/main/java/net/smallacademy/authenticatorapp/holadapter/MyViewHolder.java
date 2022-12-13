package net.smallacademy.authenticatorapp.holadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.smallacademy.authenticatorapp.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mName, mDownload;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.name);
        mDownload=itemView.findViewById(R.id.down);

    }
}
