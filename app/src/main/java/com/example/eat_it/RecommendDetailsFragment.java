package com.example.eat_it;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eat_it.model.Recommend;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendDetailsFragment extends Fragment {

    private Recommend recommend;
    TextView id;
    TextView title;
    TextView location;
    TextView description;
    TextView avatar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommend_details, container, false);
        id = view.findViewById(R.id.rec_details_id);
        title= view.findViewById(R.id.rec_details_title);
        location = view.findViewById(R.id.rec_details_location);
        description = view.findViewById(R.id.rec_details_description);

        if (recommend!=null){
            update_display();
        }
        return view;
    }

    private void update_display() {
        id.setText(recommend.id);
        title.setText(recommend.title);
        location.setText(recommend.location);
        description.setText(recommend.description);
    }

    public RecommendDetailsFragment() {
        // Required empty public constructor
    }

    public void setRecommend(Recommend recommend) {
        this.recommend=recommend;
        if(title!=null){
            update_display();
        }
    }
}