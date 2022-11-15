package com.example.chatapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Models.MessageModel;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;



public class ChatAdapter extends  RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;
    String ReceiverId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String ReceiverId) {
        this.messageModels = messageModels;
        this.context = context;
        this.ReceiverId = ReceiverId;

    }

    @Override
    public int getItemViewType(int position) {
       if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
           return SENDER_VIEW_TYPE;
       }
       else{
           return RECEIVER_VIEW_TYPE;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
            }
        }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       MessageModel messageModel = messageModels.get(position);

       if(holder.getClass() == SenderViewHolder.class){
           ((SenderViewHolder)holder ).senderText.setText(messageModel.getMessage());

       }
       else{
           ((ReceiverViewHolder)holder).receiverText.setText(messageModel.getMessage());
       }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderText, senderTime;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);

        }

    }
    public static class ReceiverViewHolder extends  RecyclerView.ViewHolder{
        TextView receiverText, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverText = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);

        }
    }

    }






