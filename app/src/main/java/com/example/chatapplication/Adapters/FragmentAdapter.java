package com.example.chatapplication.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.Fragments.CallsFragment;
import com.example.chatapplication.Fragments.ChatFragment;
import com.example.chatapplication.Fragments.StatusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {


    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChatFragment();
            case 1: return new StatusFragment();
            case 2: return  new CallsFragment();
            default:return new ChatFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if(position == 0){
            title = "CHATS";
        }
        else if(position == 1){
            title = "STATUS";
        }
        else{
            title = "CALLS";
        }
        return title;
    }
}
