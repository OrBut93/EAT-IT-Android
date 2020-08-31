package com.example.eat_it;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eat_it.model.User.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView fullName = view.findViewById(R.id.user_profile_user_name);
        if(auth.getCurrentUser()!=null)
        {
            String UserId= auth.getCurrentUser().getUid();
            final DocumentReference documentReference = fb.collection("users").document(UserId);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    fullName.setText(documentSnapshot.getString("name"));
                }
            });
        }



//        UserFirebase userFirebase = new UserFirebase();
//        String userName = auth.getCurrentUser().getDisplayName().toString();

        return view;
    }
}