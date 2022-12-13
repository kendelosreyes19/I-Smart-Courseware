package net.smallacademy.authenticatorapp.holadapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.smallacademy.authenticatorapp.R;

public class MyViewHolderStrands extends RecyclerView.ViewHolder {

    TextView mName;TextView mLink;
    Button mDownload;
    public MyViewHolderStrands(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.nameStrand);
        mLink=itemView.findViewById(R.id.linkStrand);

    }
}
