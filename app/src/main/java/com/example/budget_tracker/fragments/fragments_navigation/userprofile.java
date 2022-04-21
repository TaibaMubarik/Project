package com.example.budget_tracker.fragments.fragments_navigation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.budget_tracker.activities.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.budget_tracker.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.StateSet.TAG;

public class userprofile extends Fragment {

    private UserprofileViewModel mViewModel;
    FirebaseAuth mAuth;




    public static userprofile newInstance() {
        return new userprofile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userprofile_fragment, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserprofileViewModel.class);



        mAuth = FirebaseAuth.getInstance();
        TextView tvname = getView().findViewById(R.id.user_profile_name);
        TextView tvEmail = getView().findViewById(R.id.user_profile_email);
        Button edit=getView().findViewById(R.id.editProfile);

        tvname.setText(mAuth.getCurrentUser().getDisplayName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        String uid=user.getUid();
//        String name=user.getDisplayName();




    }

//This we are using to edit the Usnername
    public void editname(View view){


        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("Accounts")
                .document("0823e7f4-f91c-4086-b7e7-2161811031c5");

        Map<String,Object> map=new HashMap<>();

        map.put("name","ASD");
        docRef.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {



            }
        });



    }

}
