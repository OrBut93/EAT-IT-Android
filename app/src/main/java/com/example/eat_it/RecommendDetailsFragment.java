package com.example.eat_it;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.RecommendFirebase;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendDetailsFragment extends Fragment {

    private Recommend recommend;
    TextView ownerId;
    TextView title;
    TextView location;
    TextView description;
    ImageView imageUrl;
     private  RecommendDetailsViewModel viewModel;
     RecommendFirebase fire;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recommend_details, container, false);
        ownerId = view.findViewById(R.id.rec_details_recommendId);
        title= view.findViewById(R.id.rec_details_title);
        location = view.findViewById(R.id.rec_details_location);
        description = view.findViewById(R.id.rec_details_description);
        imageUrl = view.findViewById(R.id.rec_details_image);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        recommend = RecommendDetailsFragmentArgs.fromBundle(getArguments()).getRecommend();
        if (recommend!=null){
            update_display();
        }

        View editBtn = view.findViewById(R.id.edit_btn);
        View deleteBtn = view.findViewById(R.id.delete_btn);

        if (ownerId!=null && ownerId.getText().toString().equals((auth.getCurrentUser().getUid()))) {
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }


//        editBtn.setOnClickListener(new View.OnClickListener() {
//            NavController navController = Navigation.findNavController(view);
//
//            @Override
//            public void onClick(View v) {
//                NavDirections updatedDirections = RecommendDetailsFragmentDirections.actionGlobalAddRecFragment();
//                navController.navigate(updatedDirections);
//            }
//        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fire.deleteRecommend(recommend.id);
                Log.d("TAG", "delete clicked");
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","edit recommend");
            }
        });


        View closeBtn = view.findViewById(R.id.rec_details_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navCtrl = Navigation.findNavController(v);
                navCtrl.popBackStack();
            }
        });

        return view;
    }

    private void update_display() {
        ownerId.setText(recommend.Ownerid);
        title.setText(recommend.title);
        location.setText(recommend.location);
        description.setText(recommend.description);
        if (recommend.avatar != null && !recommend.avatar.isEmpty())
            Picasso.get().load(recommend.avatar).placeholder(R.drawable.avatar).into(imageUrl);
        else
            imageUrl.setImageResource(R.drawable.avatar);

    }

    public RecommendDetailsFragment() {
        // Required empty public constructor
    }

//    public void setRecommend(Recommend recommend) {
//        this.recommend=recommend;
//        if(title!=null){
//            update_display();
//        }
//    }
}