package com.example.eat_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eat_it.model.Model;
import com.example.eat_it.model.Recommend;

import java.util.List;

public class RecommendListActivity extends AppCompatActivity {

    RecyclerView list;
    List<Recommend> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        data= Model.instance.getAllRec();

        list = findViewById(R.id.recommend_list);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        RecListAdapter adapter = new RecListAdapter();
       list.setAdapter(adapter);

       adapter.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onClick(int position) {
               Log.d("TAG", "ROW WAS CLICKED"+ position);
           }
       });
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView locationTv;
        TextView descriptionTv;
        ImageView imageView;


        public RecommendViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
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
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
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