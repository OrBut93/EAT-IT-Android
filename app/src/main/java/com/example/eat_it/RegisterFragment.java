package com.example.eat_it;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eat_it.model.User.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        View registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                TextView email = view.findViewById(R.id.register_user_email);
                TextView password = view.findViewById(R.id.register_user_password);
                TextView name = view.findViewById(R.id.register_user_name);

                if(email.getText().toString().isEmpty()){
                    email.setError("Email is Require");
                    return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("password is Require");
                    return;
                }

                if(password.length() < 6)
                {
                    password.setError("Password must be more than 6 characters");
                }

                mViewModel.register(
                        email.getText().toString(),
                        password.getText().toString(),
                        name.getText().toString(),
                        new UserModel.Listener<Boolean>() {
//                            @SuppressLint("ResourceType")
                            @Override
                            public void onComplete(Boolean data) {
                                if (data) {
                                    ///// navigate to home page ?
                                    NavController navController = Navigation.findNavController(view);
                                    navController.navigate(R.id.action_global_recListFragment);
                                }
                            }
                        });
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

}






















//
//public class RegisterFragment extends Fragment {
//
//    private RegisterViewModel mViewModel;
//    EditText fullName, email, pass;
//    Button RegisterBtn;
//    FirebaseAuth fAuth;
//    View view;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_register, container, false);
//        fullName = view.findViewById(R.id.register_user_name);
//        email = view.findViewById(R.id.register_user_email);
//        pass = view.findViewById(R.id.register_user_password);
//        fAuth = FirebaseAuth.getInstance();
//
//        RegisterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String _email = email.getText().toString().trim();
//                String _pass = pass.getText().toString().trim();
//                String _name = fullName.getText().toString().trim();
//
//                if(TextUtils.isEmpty(_email)){
//                    email.setError("Email is Require");
//                    return;
//                }
//                if(TextUtils.isEmpty(_pass)){
//                    pass.setError("password is Require");
//                    return;
//                }
//
//                if(_pass.length() < 6)
//                {
//                    pass.setError("Password must be more than 6 characters");
//                }
//
//                // register the user in firebase
//                mViewModel.register(_email,_pass,_name,new UserModel.Listener<Boolean>() {
//                    @Override
//                    public void onComplete(Boolean data) {
//                        if (data) {
//                            NavController navController = Navigation.findNavController(view);
//                            navController.navigateUp();
//                            navController.navigateUp();
//                        }
//                    }
//                });
//
////                fAuth.createUserWithEmailAndPassword(_email,_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if(task.isSuccessful()){
////                            startActivity(new Intent(get));
////                        }else{
////
////                        }
////                    }
////                });
//            }
//        });
//
//
//        return view;
//    }
//
//        public static RegisterFragment newInstance() {
//        return new RegisterFragment();
//    }
////
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        final View view = inflater.inflate(R.layout.fragment_register, container, false);
////        View registerButton = view.findViewById(R.id.register_button);
////        registerButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View buttonView) {
////                TextView email = view.findViewById(R.id.register_user_email);
////                TextView password = view.findViewById(R.id.register_user_password);
////                TextView name = view.findViewById(R.id.register_user_name);
////                mViewModel.register(
////                        email.getText().toString(),
////                        password.getText().toString(),
////                        name.getText().toString(),
////                        new UserModel.Listener<Boolean>() {
////                            @Override
////                            public void onComplete(Boolean data) {
////                                if (data) {
////                                    NavController navController = Navigation.findNavController(view);
////                                    navController.navigateUp();
////                                    navController.navigateUp();
////                                }
////                            }
////                        });
////            }
////        });
////
////        return view;
////    }
////
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
//        // TODO: Use the ViewModel
//    }
//}