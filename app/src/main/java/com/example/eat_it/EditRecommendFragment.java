package com.example.eat_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.example.eat_it.model.RecommendFirebase;
import com.example.eat_it.model.RecommendModel;
import com.example.eat_it.model.StoreModel;
import com.example.eat_it.model.User.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditRecommendFragment extends Fragment {

    public EditRecommendFragment() {}

    View view;
    ImageView imageView;
    Bitmap imageBitmap;
    TextView idTv;
    TextView OwnerIdTv;
    TextView OwnerNameTv;
    TextView titleTV;
    TextView locationTv;
    TextView descriptionTV;
    private Recommend recommend;
    RecommendFirebase fire;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_edit_recommend, container, false);
        recommend = EditRecommendFragmentArgs.fromBundle(getArguments()).getRecommend();


        Button takePhotoBtn = view.findViewById(R.id.edit_rec_takePhoto_btn);
        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        titleTV = view.findViewById(R.id.edit_rec_title);
        locationTv = view.findViewById(R.id.edit_rec_location);
        descriptionTV= view.findViewById(R.id.edit_rec_description);
        imageView= view.findViewById(R.id.edit_rec_image);

        titleTV.setText(recommend.title);
        locationTv.setText(recommend.location);
        descriptionTV.setText(recommend.description);
        Picasso.get().load(recommend.avatar).placeholder(R.drawable.avatar).into(imageView);

        Button saveBtn = view.findViewById(R.id.edit_rec_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecommend();
            }
        });
        return view;


    }
    void saveRecommend(){

        final String title= titleTV.getText().toString();
        final String location= locationTv.getText().toString();
        final String description= descriptionTV.getText().toString();


        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String userId = auth.getCurrentUser().getUid();

        ////////////////////////
        Date date = new Date();

        if (imageBitmap != null) {

            StoreModel.uploadImage(imageBitmap, "OR_photo" + date.getTime(), new StoreModel.Listener() {
                @Override
                public void onSuccess(final String url) {
                    Log.d("TAG", "url: " + url);
                    final Recommend newRecommend = new Recommend(userId, "", title,location, description,url);

                    if (!recommend.id.isEmpty()) {
                        newRecommend.setId(recommend.id);
                    }

                    fire.updateRecommend(newRecommend, new RecommendModel.CompListener() {
                        @Override
                        public void onComplete() {
                            NavController navController = Navigation.findNavController(view);

                            if (!recommend.id.isEmpty())
                                navController.navigate(R.id.profileFragment);
                            else
                                navController.navigateUp();
                        }

                    });
                }

                @Override
                public void onFail() {
//                progressbr.setVisibility(View.INVISIBLE);
                    Snackbar mySnackbar = Snackbar.make(view, R.string.fail_to_save_recommend, Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }
            });
        }
        else {
            AlertDialogFragment dialogFragment= AlertDialogFragment.newInstance("Sorry","you must load photo");
            dialogFragment.show(getChildFragmentManager(), "TAG");
        }








        /////////////////////






//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseFirestore fb = FirebaseFirestore.getInstance();
//
//        final String userId = auth.getCurrentUser().getUid();
//
//        Date date = new Date();
//        StoreModel.uploadImage(imageBitmap, "OR_photo" + date.getTime(), new StoreModel.Listener() {
//            @Override
//            public void onSuccess(final String url) {
//                Log.d("TAG","url: " + url);
//                recommend.title= title;
//                recommend.location = location;
//                recommend.description=description;
//                Picasso.get().load(recommend.avatar).placeholder(R.drawable.avatar).into(imageView);
////                Recommend recommend = new Recommend(userId,"", title, location,description, url);
//                RecommendModel.instance.updateRecommend(recommend, new RecommendModel.Listener<Boolean>() {
//                    @Override
//                    public void onComplete(Boolean data) {
//                        NavController navCtrl = Navigation.findNavController(view);
//                        navCtrl.navigateUp();
//                    }
//                });
//            }
//
//            @Override
//            public void onFail() {
////                progressbr.setVisibility(View.INVISIBLE);
//                Snackbar mySnackbar = Snackbar.make(view,R.string.fail_to_save_recommend, Snackbar.LENGTH_LONG);
//                mySnackbar.show();
//            }
//        });


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