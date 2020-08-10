package com.example.eat_it;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eat_it.model.Model;
import com.example.eat_it.model.Recommend;

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

    interface Delegate{
        void onItemSelected(Recommend recommend);
    }
    Delegate parent;

    public RecListFragment() {
        Model.instance.getAllRecommenda(new Model.Listener<List<Recommend>>() {
            @Override
            public void onComplete(List<Recommend> _data) {
                data= _data;
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Delegate){
            parent = (Delegate) getActivity();
        } else{
            throw new RuntimeException(context.toString() + "must implement Delegate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec_list, container, false);

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