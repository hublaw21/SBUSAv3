package com.example.khubbart.mysbusaappv3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khubbart.mysbusaappv3.Model.ElementItem;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AdapterElementScoring extends RecyclerView.Adapter<AdapterElementScoring.ElementScoringViewHolder> {

    private List<ElementItem> elementScores;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;
    public void setOnSeekBarChangeListener (SeekBar.OnSeekBarChangeListener onSeekBarChangeListener){
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    //Constructor
    public AdapterElementScoring(List<ElementItem> elementScores) {
        this.elementScores = elementScores;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ElementScoringViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView elementID;
        public TextView ticToggle;
        public TextView elementBase;
        public TextView bonusToggle;
        public SeekBar elementGOEbar;
        public TextView elementGOE;
        public TextView elementScore;
        public View layout;

        public Double tempDouble;

        NumberFormat numberFormat = new DecimalFormat("###.00");
        NumberFormat numberFormatGOE = new DecimalFormat("#.0");


        public ElementScoringViewHolder(View v) {
            super(v);
            layout = v;
            elementID = v.findViewById(R.id.elementIdRow);
            ticToggle = v.findViewById(R.id.elementTicToggle);
            elementBase = v.findViewById(R.id.elementBaseRow);
            bonusToggle = v.findViewById(R.id.elementBonusToggle);
            elementGOEbar = v.findViewById(R.id.seekBarElement);
            elementGOE = v.findViewById(R.id.elementGOERow);
            elementScore = v.findViewById(R.id.elementScoreRow);
        }
    }

    //DO I need this?
    /*
    public void add(String elementID, boolean ticToggle, Double elementBase, boolean bonusToggle, Double elementGOEbar, Double elementGOE, Double elementScore) {
        elementScores.add(elementID, boolean ticToggle, Double elementBase, boolean bonusToggle, Double elementGOEbar, Double elementGOE, Double elementScore);
        notifyItemInserted(position);
    }
    */

    //Do I need this?
    /*
    public void remove(int position) {
        elementScores.remove(position);
        notifyItemRemoved(position);
    }
    */

    // Create new views (invoked by the layout manager)
    @Override
    public ElementScoringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_element_scoring, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ElementScoringViewHolder viewHolder = new ElementScoringViewHolder(itemView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementScoringViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final NumberFormat numberFormat = new DecimalFormat("##0.00");
        final NumberFormat numberFormatGOE = new DecimalFormat("0.0");

        final int pos = position;

        holder.elementID.setText(elementScores.get(position).getElementID());
        //holder.ticToggle.setText(elementScores.get(position).isTicToggle());
        holder.elementBase.setText(numberFormat.format(elementScores.get(position).getElementBase()));
        //holder.bonusToggle.setText(elementScores.get(position).isBonusToggle());
        int tempInt = 50;
        holder.elementGOEbar.setMax(100); // need to set initial location of seeker bar
        holder.elementGOEbar.setProgress(tempInt); // need to set initial location of seeker bar
        double tempDouble = 0.0;
        elementScores.get(position).setElementGOE(tempDouble); // initialize the value at 0.0
        holder.elementGOE.setText(numberFormatGOE.format(elementScores.get(position).getElementGOE()));
        holder.elementScore.setText(numberFormat.format(elementScores.get(position).getElementScore()));
        //Do I need this?
        /*
        {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        */

        //holder.txtFooter.setText("Footer: " + name);

        //Adapt this for buttons and seekerbar
        /*
        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkedItems = "";
                count = 0;
                CheckBox cb = (CheckBox) v;
                Units contact = (Units) cb.getTag();
                contact.setIsSelected(cb.isChecked());
                for (int i = 0; i < stList.size(); i++) {
                    if (stList.get(i).isSelected()) {
//                            checkedItems = checkedItems + stList.get(i).getStrUnitName() + ",";
                        count++;
                    }
                }
//                    Toast.makeText(v.getContext(), checkedItems, Toast.LENGTH_LONG).show();
                int questionPerUnit = 0;
                int reminder = 0;
                if (count != 0) {
                    questionPerUnit = Max / count;
                    reminder = Max % count;
                }
                for (int i = 0; i < stList.size(); i++) {
                    if (stList.get(i).isSelected()) {
                        stList.get(i).setNoOfquestions(questionPerUnit);
                    } else {
                        stList.get(i).setNoOfquestions(0);
                    }
                }
                for (int i = 0; i < reminder; i++) {
                    if (stList.get(i).isSelected()) {
                        int previous = stList.get(i).getNoOfquestions();
                        stList.get(i).setNoOfquestions(previous + 1);
                    }
                }
                notifyDataSetChanged();
            }
        });
        */

        holder.elementGOEbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar bar) {
                int progress = bar.getProgress();
                calcElementScore(progress, position, holder);
            }

            public void onStartTrackingTouch(SeekBar bar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean) {
                int progress = bar.getProgress();
                calcElementScore(progress, position, holder);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return elementScores.size();
    }

    //A basic method to get total technical value
    /*
    public Double sumElements() {
        double sumElements = 0;
        for (int i = 0; i < requiredElements; i++) {
            if (elementScore[i] != null) {
                sumElements += elementScore[i];
            }
        }
        return sumElements;
    }
    */

    //calculate each elements score
    public void calcElementScore(int progress, int position, ElementScoringViewHolder holder) {
        NumberFormat numberFormat = new DecimalFormat("###.00");
        NumberFormat numberFormatGOE = new DecimalFormat("#.0");
        //Convert progress to raw GOE +/-
        Double tempGOE = (double) progress;
        tempGOE = tempGOE / 10 - 5;
        //Calc GOE for element
        Double tempBase = elementScores.get(position).getElementBase();
        Double tempScore = tempBase*(1 + tempGOE/10);
        //Update items
        elementScores.get(position).setElementGOE(tempGOE);
        holder.elementGOE.setText(numberFormatGOE.format(elementScores.get(position).getElementGOE()));
        elementScores.get(position).setElementScore(tempScore);
        holder.elementScore.setText(numberFormat.format(elementScores.get(position).getElementScore()));
    }

}
