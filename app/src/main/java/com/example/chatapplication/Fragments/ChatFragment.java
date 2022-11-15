package com.example.chatapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.chatapplication.Adapters.UsersAdapter;
import com.example.chatapplication.ChatTextingActivity;
import com.example.chatapplication.Models.Users;
import com.example.chatapplication.R;
import com.example.chatapplication.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }

    FragmentChatBinding binding;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> userIDList = new ArrayList<String>();



    FirebaseDatabase database;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




       database = FirebaseDatabase.getInstance();
       DatabaseReference ref = database.getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users.getUserName());
                        userIDList.add(users.getUserId());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        binding = FragmentChatBinding.inflate(inflater,container,false);
        database = FirebaseDatabase.getInstance();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);



        binding.chatListView.setAdapter(adapter);

        binding.chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              String UserName = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(getContext(), ChatTextingActivity.class);
                intent.putExtra("userName", UserName);
                intent.putExtra("recvId", userIDList.get(i));
                startActivity(intent);
            }
        });


        return binding.getRoot();

    }
}