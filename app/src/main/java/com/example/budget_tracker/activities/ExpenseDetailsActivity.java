package com.example.budget_tracker.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budget_tracker.models.Group;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.budget_tracker.R;
import com.example.budget_tracker.models.Account;
import com.example.budget_tracker.models.Expense;
import com.example.budget_tracker.adapters.GroupAdapter;

import java.util.ArrayList;


public class ExpenseDetailsActivity extends AppCompatActivity {

//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    String uid = user.getUid();
//    String name = user.getDisplayName();


    FirebaseFirestore database;
    FirebaseAuth mAuth;
    String currentUserId;
    private Expense expense;
    private TextView title;
    private TextView category;
    private TextView date;
    private TextView buyer;
    private TextView totalAmount;
    private ListView expenseUserslistview;
    private ArrayList<String> names = new ArrayList<>();
    private Account buyerObject;
    private ImageView categoryImage;
    private TextView grou;
    private TextView groupName;
    public TextView namegroup;

    TextView mydata;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);


        FirebaseApp.initializeApp(this);
        database = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        database = FirebaseFirestore.getInstance();


        mydata=findViewById(R.id.expense_detail_group);
        title = findViewById(R.id.expense_detail_title);
        category = findViewById(R.id.expense_detail_category);
        date = findViewById(R.id.expense_detail_date);
        buyer = findViewById(R.id.expense_detail_buyer);
        totalAmount = findViewById(R.id.expense_detail_totalAmount);
        expenseUserslistview = findViewById(R.id.expense_details_listview);
        categoryImage = findViewById(R.id.expense_detail_category_pic);
        
        Bundle bundle = getIntent().getExtras();


        //Getting expense from expense list view
        expense = (Expense) bundle.getSerializable("expense");





       queryData();
        title.setText(expense.getTitle());
        //grou.setText(expense.g);
        //groupName.setText(expense.getGroupName());

        date.setText(expense.getDate());
        category.setText(expense.getCategory());
        Long totalAmountL = (Math.round(expense.getAmount()));
        totalAmount.setText(totalAmountL.toString() + " SEK");

        // Setting the picture for each category
        switch (expense.getCategory()){
            case "Grocery": categoryImage.setImageResource(R.drawable.grocery); break;
            case "Clothes": categoryImage.setImageResource(R.drawable.clothes); break;
            case "Transportation": categoryImage.setImageResource(R.drawable.transportation); break;
            case "Recurring": categoryImage.setImageResource(R.drawable.recurring); break;
            case "Eat out": categoryImage.setImageResource(R.drawable.eat); break;
            case "Utility": categoryImage.setImageResource(R.drawable.utility); break;
            case "Membership": categoryImage.setImageResource(R.drawable.membership); break;
            case "Other": categoryImage.setImageResource(R.drawable.other); break;
        }

        // Create a listView for people who are involved in expense
        ExpenseUsersAdapter adapter = new ExpenseUsersAdapter();
        expenseUserslistview.setAdapter(adapter);

        // Get the buyer name from database regard to Account id of buyer saved in expense object
        database.collection("Accounts").whereEqualTo("id", expense.getPayerAccountId()).limit(1).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots) {
                            buyerObject = queryDocumentSnapshot.toObject(Account.class);
                            buyer.setText(buyerObject.getOwnerName());
                        }
                    }
                });
        // Find the name for ids who are involved in expense
        for(String expenseAccountId : expense.getExpenseAccountIds()){
            database.collection("Accounts").whereEqualTo("id", expenseAccountId).limit(1).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots) {
                                Account expenseUserObject = queryDocumentSnapshot.toObject(Account.class);
                                names.add(expenseUserObject.getOwnerName());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

   
    // Custom adapter for people who are involved in expense
    class ExpenseUsersAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return expense.getExpenseAccountIds().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.expense_users_list, null);
            ImageView buyerOrNot = convertView.findViewById(R.id.buyer_image);
            TextView tvName = convertView.findViewById(R.id.expense_detail_list_name);

            if(names.size() > position && buyerObject != null){

                String b = expense.getPayerAccountId();
                String c = expense.getExpenseAccountIds().get(position);
                String d = names.get(position);

                // If the person is buyer or not put adifferent picture for it
                if(buyerObject.getOwnerName().equals(names.get(position))){
                    buyerOrNot.setImageResource(R.drawable.buyer);
                } else {
                    buyerOrNot.setImageResource(R.drawable.no_buyer);
                }
            }

            // Due to sync problem checks and prevent index out of bonds
            if(names.size() > position){
                tvName.setText(names.get(position));
            }
            return convertView;
        }
    }

public void queryData(){


    Task<QuerySnapshot> query=database.collection("Groups").whereEqualTo("name","Lahore").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            StringBuilder builder=new StringBuilder();

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                Group group=documentSnapshot.toObject(Group.class);
                builder.append("" +group.getGroupName()+ "\n");

            }

            mydata.setText(builder);



        }

    });
}

}
