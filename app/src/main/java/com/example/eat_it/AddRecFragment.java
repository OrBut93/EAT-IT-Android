package com.example.eat_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.eat_it.model.Recommend;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.StoreModel;
import com.example.eat_it.model.User.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Document;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecFragment extends Fragment {

    public AddRecFragment() {}

    AddRecViewModel mViewModel;
    View view;
    ImageView imageView;
    Bitmap imageBitmap;
    TextView idTv;
    TextView OwnerIdTv;
    TextView OwnerNameTv;
    TextView titleTV;
    TextView locationTv;
    TextView descriptionTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            view =  inflater.inflate(R.layout.fragment_add_rec, container, false);

            imageView= view.findViewById(R.id.new_rec_image);
            Button takePhotoBtn = view.findViewById(R.id.new_rec_takePhoto_btn);
            takePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                }
            });
            titleTV = view.findViewById(R.id.new_rec_title);
            locationTv = view.findViewById(R.id.new_rec_location);
            descriptionTV= view.findViewById(R.id.new_rec_description);


            Button saveBtn = view.findViewById(R.id.new_rec_save_btn);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveRecommend();
                }
            });
            return view;
        }
        else{
              AlertDialogFragment.newInstance("Sorry","you must login befor");
        }

        return view;
    }
    void saveRecommend(){
        final String id ;
        String OwnerId;
        String OwnerName = "";
        final String title= titleTV.getText().toString();
        final String location= locationTv.getText().toString();
        final String description= descriptionTV.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        final String userId = auth.getCurrentUser().getUid();



        Date date = new Date();
        StoreModel.uploadImage(imageBitmap, "OR_photo" + date.getTime(), new StoreModel.Listener() {
            @Override
            public void onSuccess(final String url) {
                Log.d("TAG","url: " + url);
                Recommend recommend = new Recommend(userId,"", title, location,description, url);
                RecommendModel.instance.addRec(recommend, new RecommendModel.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        NavController navCtrl = Navigation.findNavController(view);
                        navCtrl.navigateUp();
                    }
                });
            }

            @Override
            public void onFail() {
//                progressbr.setVisibility(View.INVISIBLE);
                Snackbar mySnackbar = Snackbar.make(view,R.string.fail_to_save_recommend, Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        });


    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;

    void takePhoto(){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
           Bundle extras = data.getExtras();
           imageBitmap = rotateImage((Bitmap) extras.get("data"));
           imageView.setImageBitmap(imageBitmap);
        }
    }

    public static Bitmap rotateImage(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(view);

        switch (item.getItemId()){
            case R.id.login_now_button:
                Log.d("TAG","fragment handle login menu");
                navController.navigate(R.id.action_global_loginFragment);
                return true;

            case R.id.logout_btn:
                Log.d("TAG","fragment handle logout menu");
                navController.navigate(R.id.action_global_recListFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}