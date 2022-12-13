package net.smallacademy.authenticatorapp.holadapter;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.smallacademy.authenticatorapp.model.DownModeReviewer;
import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.activity.Reviewer;

import java.util.ArrayList;

public class MyAdapterReviewer extends RecyclerView.Adapter<MyViewHolderReviewer> {

    Reviewer reviewer;
    ArrayList<DownModeReviewer> downModelStrands;

    public MyAdapterReviewer(Reviewer reviewer, ArrayList<DownModeReviewer> downModelStrands) {
        this.reviewer = reviewer;
        this.downModelStrands = downModelStrands;
    }

    @NonNull
    @Override
    public MyViewHolderReviewer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(reviewer.getBaseContext());
        View view = layoutInflater.inflate(R.layout.elementsreviewer, null, false);

        return new MyViewHolderReviewer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolderReviewer myViewHolderReviewer, final int i) {

        myViewHolderReviewer.mName.setText(downModelStrands.get(i).getName());
        myViewHolderReviewer.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(myViewHolderReviewer.mName.getContext(), downModelStrands.get(i).getName(),".pdf",DIRECTORY_DOWNLOADS, downModelStrands.get(i).getLink());
                Toast.makeText(myViewHolderReviewer.itemView.getContext(), "File Downloaded.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);

    }


    @Override
    public int getItemCount() {
        return downModelStrands.size();
    }
}
