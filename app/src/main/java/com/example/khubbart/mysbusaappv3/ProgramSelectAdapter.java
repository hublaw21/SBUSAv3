package com.example.khubbart.mysbusaappv3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.Program;
import com.example.khubbart.mysbusaappv3.Model.Programv2;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.EventListener;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProgramSelectAdapter extends RecyclerView.Adapter<ProgramSelectAdapter.PSViewHolder> {

    private List<Programv2> programs;
    WeakReference<Context> mContextWeakReference;
    //private final OnViewHolderClickListener mItemClickListener;
    //abstract protected int getItemView();
    //abstract protected int[] getResIdOfInflatedViews();

    // Needed constructor for Adapter
    public ProgramSelectAdapter(List<Programv2> programs, Context context){
        this.programs = programs;
        this.mContextWeakReference = new WeakReference<Context>(context);
        //this.mItemClickListener = itemClickListener;
    }

    protected final Programv2 getItem(int position){
        return this.programs.get(position);
    }

    //Return the size of the dataset invoked by layout manager
    @Override
    public int getItemCount() {
        return programs.size();
    }

    //Create new view when invoked by layout manager
    @Override
    public final PSViewHolder onCreateViewHolder (ViewGroup viewGroup,int position) {
        Context context = mContextWeakReference.get();
        if (context != null) {
        /*    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_item, parent, false);
            return new MyViewHolder(itemView, context);
        }
        return null;
        */
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.item_program_select, viewGroup, false);
            PSViewHolder vh = new PSViewHolder(view, context);
            return vh;
        }
        return null;
    }

    //I have this in a separate Java class, trying it here with different name to see if it works
    public static class PSViewHolder extends RecyclerView.ViewHolder{

        //private final OnViewHolderClickListener mItemClickListener;
        public TextView programSelectCompetitionTextView;
        public TextView programSelectDisciplineTextView;
        public TextView programSelectLevelTextView;
        public TextView programSelectSegmentTextView;

        public LinearLayout linearLayout;

        public PSViewHolder(View itemView, final Context context) {
            super(itemView);
            /*
            this.mItemClickListener = itemClickListener;
            if(itemClickListener != null) {
                itemView.setOnClickListener(this);
            }
            */
            programSelectCompetitionTextView = itemView.findViewById(R.id.program_select_competition_item_text);
            programSelectDisciplineTextView = itemView.findViewById(R.id.program_select_discipline_item_text);
            programSelectLevelTextView = itemView.findViewById(R.id.program_select_level_item_text);
            programSelectSegmentTextView = itemView.findViewById(R.id.program_select_segment_item_text);
            linearLayout = itemView.findViewById(R.id.program_select_item);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //((ProgramSelectActivity) context).userItemClick(getAdapterPosition());
                }
            });
        }

        public void bindData(final ProgramSelectViewModel viewModel) {
            //Context context = mContextWeakReference.get();
            programSelectCompetitionTextView.setText(viewModel.getProgramSelectCompetitionText());
            programSelectDisciplineTextView.setText(viewModel.getProgramSelectDisciplineText());
            programSelectLevelTextView.setText(viewModel.getProgramSelectLevelText());
            programSelectSegmentTextView.setText(viewModel.getProgramSelectSegmentText());
        }
    }

    //Replace the contents of a view when invoked by layout manager
    public void onBindViewHolder(PSViewHolder pSViewHolder, int position) {
        // - get element at thi sposition from dataset
        // - raplce the contents of that view element
        //final String name = programs.get(position);
        //pSViewHolder.programSelectCompetitionTextView.setText(programs.get(position).getCompetition());
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
    /*
    @Override
    public void onClick(View v){
        if(mItemClickListener != null) {
            mItemClickListener.onClickAtItem(getAdapterPosition());
            Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_SHORT).show();
        }
    }
    */

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_program_select;
    }

        /*
    // Try to capture the program selected
    public interface ProgramSelectClickListener{
        void onClick(View view, int position);
    }
    */
    /*
    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ProgramSelectClickListener mListener;
        RowViewHolder(View view, ProgramSelectClickListener listener){
            super(view);
            mListener = listener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            mListener.onClick(view, getAdapterPosition());
            Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_SHORT).show();
        }
    }
    */
}
