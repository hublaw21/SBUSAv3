package com.example.khubbart.mysbusaappv3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khubbart.mysbusaappv3.Model.Program;

import java.util.List;

public class ProgramSelectAdapter extends RecyclerView.Adapter<ProgramSelectAdapter.PSViewHolder> {

    private List<Program> programs;

    //STill need to retrieve the data to make this work

    //I have this in a separate Java class, trying it here with different name to see if it works
    public static class PSViewHolder extends RecyclerView.ViewHolder {

        public TextView programSelectCompetitionTextView;
        public TextView programSelectDisciplineTextView;
        public TextView programSelectLevelTextView;
        public TextView programSelectSegmentTextView;


        public PSViewHolder(final View itemView) {
            super(itemView);
            programSelectCompetitionTextView = itemView.findViewById(R.id.program_select_competition_item_text);
            programSelectDisciplineTextView = itemView.findViewById(R.id.program_select_discipline_item_text);
            programSelectLevelTextView = itemView.findViewById(R.id.program_select_level_item_text);
            programSelectSegmentTextView = itemView.findViewById(R.id.program_select_segment_item_text);
        }

        public void bindData(final ProgramSelectViewModel viewModel) {
            programSelectCompetitionTextView.setText(viewModel.getProgramSelectCompetitionText());
            programSelectDisciplineTextView.setText(viewModel.getProgramSelectDisciplineText());
            programSelectLevelTextView.setText(viewModel.getProgramSelectLevelText());
            programSelectSegmentTextView.setText(viewModel.getProgramSelectSegmentText());
        }
    }

    // Needed constructor for Adapter
    public ProgramSelectAdapter(List<Program> programs) {
        this.programs = programs;
    }

    //Return the size of the dataset invoked by layout manager
    @Override
    public int getItemCount() {
        return programs.size();
    }

    //Create new view when invoked by layout manager
    @Override
    public PSViewHolder onCreateViewHolder(ViewGroup viewGroup,int position) {
        //create new view (Do I need the 'finals' here?
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Am i using the correct item here?
        View view = inflater.inflate(R.layout.item_program_select, viewGroup, false);
        // Set the view's size, margin, paddings, and layout parameters
        //RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view);
        PSViewHolder vh = new PSViewHolder(view);
        return vh;
    }

    //Replace the contents of a view when invoked by layout manager
    public void onBindViewHolder(PSViewHolder pSViewHolder, int position) {
        // - get element at thi sposition from dataset
        // - raplce the contents of that view element
        //final String name = programs.get(position);
        pSViewHolder.programSelectCompetitionTextView.setText(programs.get(position).getCompetition());
        pSViewHolder.programSelectDisciplineTextView.setText(programs.get(position).getDiscipline());
        pSViewHolder.programSelectLevelTextView.setText(programs.get(position).getLevel());
        pSViewHolder.programSelectSegmentTextView.setText(programs.get(position).getSegment());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Do I need this?
    /*
    @Override
        public void onClick (View view){
            remove(position);
        }
    */

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_program_select;
    }

    // Do I need this
    /*
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((PSViewHolder) holder).bindData(programs.get(position));
    }
    */
}
