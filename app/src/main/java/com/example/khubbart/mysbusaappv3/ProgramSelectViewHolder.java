package com.example.khubbart.mysbusaappv3;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ProgramSelectViewHolder extends RecyclerView.ViewHolder {

    public TextView programSelectItemTextView;

    public ProgramSelectViewHolder(final View itemView) {
        super(itemView);
        programSelectItemTextView = itemView.findViewById(R.id.program_select_competition_item_text);
    }

    public void bindData(final ProgramSelectViewModel viewModel) {
        programSelectItemTextView.setText(viewModel.getProgramSelectCompetitionText());
    }
}
