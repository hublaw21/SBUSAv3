package com.example.khubbart.mysbusaappv3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khubbart.mysbusaappv3.Model.Program;

import java.util.List;

public class ProgramSelectAdapter extends RecyclerView.Adapter {

    List<Program> programs;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ProgramSelectViewModel(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ProgramSelectViewHolder) holder).bindData(programs.get(position));
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_program_select;
    }

}
