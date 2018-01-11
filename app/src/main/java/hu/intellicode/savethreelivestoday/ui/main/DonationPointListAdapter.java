package hu.intellicode.savethreelivestoday.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.map.DonationPoint;

/**
 * Displays a list of DonationPoints to the user. Set onClickListener on each item,
 * to open MapActivity.
 */

class DonationPointListAdapter
        extends RecyclerView.Adapter<DonationPointListAdapter.DonationPointHolder> {

    /**
     * Custom OnClickListener. The Activity will handle the click event,
     * and the Adapter will send the clicked DonationPoint to it.
     */
    public interface OnDonationPointListItemClickListener extends View.OnClickListener {

        void onClick(View v, DonationPoint donationPoint);
    }

    private List<DonationPoint> donationPointList;

    private OnDonationPointListItemClickListener clickListener;

    /**
     * The one and only constructor of the class
     *
     * @param donationPointList to display
     * @param clickListener     which handles the list item clicks
     */
    public DonationPointListAdapter(List<DonationPoint> donationPointList,
                                    OnDonationPointListItemClickListener clickListener) {
        this.donationPointList = donationPointList;
        this.clickListener = clickListener;
    }

    /**
     * Custom ViewHolder for the DonationPointListAdapter. It has two TextViews: to display
     * the name and the address of a DonationPoint.
     */
    static class DonationPointHolder extends RecyclerView.ViewHolder {

        TextView donationPointName;
        TextView donationPointAddress;

        public DonationPointHolder(View itemView) {
            super(itemView);
            donationPointName = itemView.findViewById(R.id.tv_donation_point_name);
            donationPointAddress = itemView.findViewById(R.id.tv_donation_point_address);
        }
    }

    /**
     * Creates a new ViewHolder for a list item.
     *
     * @param parent   of the ViewHolder
     * @param viewType is not used in the method
     * @return the new DonationPointHolder.
     */
    @Override
    public DonationPointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_item, parent, false);
        return new DonationPointHolder(view);
    }

    /**
     * Set the name and address of the DonationPoint on the given position.
     *
     * @param holder   of the item
     * @param position of the item
     */
    @Override
    public void onBindViewHolder(DonationPointHolder holder, int position) {
        DonationPoint item = donationPointList.get(position);
        holder.donationPointName.setText(item.getName());
        holder.donationPointAddress.setText(item.getAddress());
        // TODO handle click events
    }

    /**
     * @return the number of items in the list. If the list equals null, return 0.
     */
    @Override
    public int getItemCount() {
        if (donationPointList != null) {
            return donationPointList.size();
        } else {
            return 0;
        }
    }
}