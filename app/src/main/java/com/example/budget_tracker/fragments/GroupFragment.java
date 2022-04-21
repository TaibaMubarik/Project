package com.example.budget_tracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.budget_tracker.R;
import com.example.budget_tracker.adapters.GroupAdapter;
import com.example.budget_tracker.models.Account;
import com.example.budget_tracker.models.Group;

import java.util.ArrayList;
import java.util.Optional;

public class GroupFragment extends Fragment {
    private GroupAdapter groupAdapter;
    private ArrayList<Group> groups = new ArrayList<>();
    FirebaseFirestore database;
    private FirebaseAuth mAuth;
    private String accountId;
    String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group, null);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        ListView listView = v.findViewById(R.id.group_list);
        listView.setScrollingCacheEnabled(false);

        groupAdapter = new GroupAdapter(v.getContext(), groups);
        listView.setAdapter(groupAdapter);

        database.collection("Accounts").whereEqualTo("userID", currentUserId).get().addOnCompleteListener(task->{
            if (task.isSuccessful()) {
                QuerySnapshot accountSnapshot = task.getResult();
                if (null != accountSnapshot) {
                    Optional<Account> account = accountSnapshot.toObjects(Account.class).stream().findFirst();
                    if (account.isPresent()) {
                        accountId = account.get().getId();
                        database.collection("Groups").whereArrayContains("accountIdList", accountId).get().addOnSuccessListener(queryDocumentSnapshots->{
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Group group = documentSnapshot.toObject(Group.class);
                                groups.add(group);
                            }
                            groupAdapter.notifyDataSetChanged();
                        });
                    }
                }
            }
        });

        return v;
    }
}
