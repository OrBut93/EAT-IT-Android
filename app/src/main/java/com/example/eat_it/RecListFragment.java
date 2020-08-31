package com.example.eat_it;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.User.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RecListFragment extends Fragment {

    RecListAdapter adapter;
    RecyclerView list;
    List<Recommend> data = new LinkedList<Recommend>();
    RecommendListViewModel viewModel;
    LiveData<List<Recommend>> liveData;

//    Boolean isRefreshing = false;
    SwipeRefreshLayout swipeRefresh;

    interface Delegate{
        void onItemSelected(Recommend recommend);
    }

    Delegate parent;

    public RecListFragment() {
//        Model.instance.getAllRecommenda(new Model.Listener<List<Recommend>>() {
//            @Override
//            public void onComplete(List<Recommend> _data) {
//                data= _data;
//                if(adapter!=null){
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        NavController navController = Navigation.findNavController(list);
//
//        switch (item.getItemId()){
//            case R.id.login_now_button:
//                Log.d("TAG","fragment handle login menu");
//                navController.navigate(R.id.action_global_loginFragment);
//                return true;
//
//            case R.id.logout_btn:
//                Log.d("TAG","fragment handle logout menu");
//                UserFirebase.logout();
//                navController.navigate(R.id.action_global_recListFragment);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Delegate){
            parent = (Delegate) getActivity();
        } else{
            throw new RuntimeException(context.toString() + "must implement Delegate");
        }
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(RecommendListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec_list, container, false);
        Button loginBtn = view.findViewById(R.id.login_now_button);
        FirebaseAuth fauth = FirebaseAuth.getInstance();
//        if(fauth.getCurrentUser() !=null)
//        {
//            MenuItem item =
//            item.setVisible(false);
//        }
//
        list = view.findViewById(R.id.recommend_list_list);
        list.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        adapter = new RecListAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "ROW WAS CLICKED"+ position);
                Recommend recommend = data.get(position);
                parent.onItemSelected(recommend);
            }
        });
       
        
        swipeRefresh = view.findViewById(R.id.students_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh(new RecommendModel.CompListener() {
                    @Override
                    public void onComplete() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        swipeRefresh.setRefreshing(true);
        
        //Live data is getting from localDb
        liveData = viewModel.getData();
        
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Recommend>>() {
            @Override
            public void onChanged(List<Recommend> recommends) {
                data = recommends;
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent= null;
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{
        TextView idTv;
        TextView titleTv;
        TextView locationTv;
        TextView descriptionTv;
        ImageView imageView;
        Recommend recommend;

        public RecommendViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            idTv = itemView.findViewById(R.id.row_id);
            titleTv = itemView.findViewById(R.id.row_title_tv);
            locationTv = itemView.findViewById(R.id.row_location_tv);
            descriptionTv = itemView.findViewById(R.id.row_descroption_tv);
            imageView = itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onClick(position);
                        }
                    }
                }
            });
        }

        void bind(Recommend recommend){
            idTv.setText(recommend.id);
            titleTv.setText(recommend.title);
            locationTv.setText(recommend.location);
            descriptionTv.setText(recommend.description);
            if(recommend.avatar !=null && recommend.avatar!= "" ){
                Picasso.get().load(recommend.avatar).placeholder(R.drawable.avatar).into(imageView);
            } else{
                imageView.setImageResource(R.drawable.avatar);
            }
        }
    }

    interface OnItemClickListener{
        void onClick(int position);
    }

    class RecListAdapter extends RecyclerView.Adapter<RecommendViewHolder>{
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        // what happen when create a view of row
        public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.list_row, viewGroup, false);
            RecommendViewHolder viewHolder = new RecommendViewHolder(view,listener);
            return viewHolder;
        }

        //take a row and connect her data
        @Override
        public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
            Recommend recommend = data.get(position);
            holder.bind(recommend);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}