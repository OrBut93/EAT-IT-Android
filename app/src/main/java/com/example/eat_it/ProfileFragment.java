package com.example.eat_it;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.User.User;
import com.example.eat_it.model.User.UserFirebase;
import com.example.eat_it.model.User.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.LinkedList;
import java.util.List;
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

    private ProfileViewModel viewModel;

    View view;
    RecommendsListAdapter adapter;
    RecyclerView profileOutfitsList;
    List<Recommend> profileRecommendsData = new LinkedList<Recommend>();

    RecListFragment.Delegate parent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = UserModel.instance.getCurrentUser();

        if (user != null) {
            TextView userName = view.findViewById(R.id.user_profile_user_name);
            userName.setText(user.name);
        }

        profileOutfitsList = view.findViewById(R.id.profile_recommends_list);
        profileOutfitsList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        profileOutfitsList.setLayoutManager(layoutManager);

        adapter = new RecommendsListAdapter();
        profileOutfitsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecListFragment.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Recommend recommend = profileRecommendsData.get(position);
                parent.onItemSelected(recommend);
            }
        });

        LiveData<List<Recommend>> liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Recommend>>() {
            @Override
            public void onChanged(List<Recommend> outfits) {
                profileRecommendsData = outfits;
                adapter.notifyDataSetChanged();
            }
        });

        View logoutButton = view.findViewById(R.id.user_profile_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                viewModel.logout();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.recListFragment);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecListFragment.Delegate) {
            parent = (RecListFragment.Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + "profile recommends list parent activity must implement delegate");
        }

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    class RecommendsListAdapter extends RecyclerView.Adapter<RecListFragment.RecommendViewHolder> {
        private RecListFragment.OnItemClickListener listener;

        void setOnItemClickListener(RecListFragment.OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public RecListFragment.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, parent, false);
            RecListFragment.RecommendViewHolder viewHolder = new RecListFragment.RecommendViewHolder(view, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecListFragment.RecommendViewHolder holder, int position) {
            Recommend recommend = profileRecommendsData.get(position);
            holder.bind(recommend);
        }


        @Override
        public int getItemCount() {
            return profileRecommendsData.size();
        }
    }

}