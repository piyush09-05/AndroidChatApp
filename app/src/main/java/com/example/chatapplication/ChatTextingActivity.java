package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.Adapters.ChatAdapter;
import com.example.chatapplication.Models.MessageModel;
import com.example.chatapplication.databinding.ActivityChatTextingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatTextingActivity extends AppCompatActivity {
   ActivityChatTextingBinding binding;
   FirebaseAuth auth;
   FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatTextingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent in = getIntent();
        final String senderId = FirebaseAuth.getInstance().getUid();
        String ReceiverName = in.getStringExtra("userName");
        String ReceiverId = in.getStringExtra("recvId");
        TextView recvName = (TextView) findViewById(R.id.recvName);
        recvName.setText(ReceiverName);

        final ArrayList<MessageModel> messageModels = new ArrayList<MessageModel>();
        final ArrayList<String> textMessages = new ArrayList<String>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this, ReceiverId);

        binding.textRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.textRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + ReceiverId;
        final String receiverRoom = ReceiverId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    MessageModel model = snapshot1.getValue(MessageModel.class);
                                    model.setMessageID(snapshot1.getKey());
                                    messageModels.add(model);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.textMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.textMessage.setText("");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(ChatTextingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });




    }
}