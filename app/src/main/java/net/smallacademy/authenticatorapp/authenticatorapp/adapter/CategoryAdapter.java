package net.smallacademy.authenticatorapp.authenticatorapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.smallacademy.authenticatorapp.R;
import net.smallacademy.authenticatorapp.authenticatorapp.QuizSplashActivity;
import net.smallacademy.authenticatorapp.authenticatorapp.activity.SetsActivity;
import net.smallacademy.authenticatorapp.authenticatorapp.db.QuizDb;
import net.smallacademy.authenticatorapp.authenticatorapp.models.CategoryModel;
import net.smallacademy.authenticatorapp.authenticatorapp.pref.Quiz;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {


    private List<CategoryModel> categoryList;
    Activity activity;
    TextView txt;

    public CategoryAdapter(List<CategoryModel> categoryList, Activity activity) {
        this.categoryList = categoryList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false);
        } else {
            view = convertView;
        }

        Quiz quiz = new Quiz(activity);
        String sub = categoryList.get(position).getId().toLowerCase();

        txt = view.findViewById(R.id.catName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String cats = ;

                Quiz quiz = new Quiz(activity);
                String sub = categoryList.get(position).getId().toLowerCase();

                if (sub.startsWith("math") && quiz.getQuiz(QuizDb.msta).contains("1")) {

                    Toast.makeText(parent.getContext(), "Already Taken", Toast.LENGTH_SHORT).show();

                } else if (sub.startsWith("eng") && quiz.getQuiz(QuizDb.esta).contains("1")) {

                    Toast.makeText(parent.getContext(), "Already Taken", Toast.LENGTH_SHORT).show();

                } else if (sub.startsWith("sci") && quiz.getQuiz(QuizDb.ssta).contains("1")) {

                    Toast.makeText(parent.getContext(), "Already Taken", Toast.LENGTH_SHORT).show();

                } else if ((sub.startsWith("fil") || sub.startsWith("ph")) && quiz.getQuiz(QuizDb.fsta).contains("1")) {

                    Toast.makeText(parent.getContext(), "Already Taken", Toast.LENGTH_SHORT).show();

                } else {
                    QuizSplashActivity.selected_cat_index = position;
                    Intent intent = new Intent(parent.getContext(), SetsActivity.class);
                    intent.putExtra("cat", categoryList.get(position).getId());
                    parent.getContext().startActivity(intent);
                }

            }
        });
        ((TextView) view.findViewById(R.id.catName)).setText(categoryList.get(position).getId());

        if (sub.startsWith("math") && quiz.getQuiz(QuizDb.msta).contains("1")) {

            txt.setBackgroundColor(Color.parseColor("#C80000"));

        } else if (sub.startsWith("eng") && quiz.getQuiz(QuizDb.esta).contains("1")) {

            txt.setBackgroundColor(Color.parseColor("#C80000"));

        } else if (sub.startsWith("sci") && quiz.getQuiz(QuizDb.ssta).contains("1")) {

            txt.setBackgroundColor(Color.parseColor("#C80000"));

        } else if ((sub.startsWith("fil") || sub.startsWith("ph")) && quiz.getQuiz(QuizDb.fsta).contains("1")) {

            txt.setBackgroundColor(Color.parseColor("#C80000"));

        }

        return view;
    }
}
