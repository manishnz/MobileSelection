package com.example.vihu.mobileselection.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vihu.mobileselection.Models.TempCartModel;
import com.example.vihu.mobileselection.R;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<TempCartModel> {

    private final Context context;
    private final List<TempCartModel> cartItems;

    public OrderListAdapter(@NonNull Context context, @NonNull List<TempCartModel> cartItems) {
        super(context, -1, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.order_list, parent, false);
        TextView tvwOrderModel = (TextView) rowView.findViewById(R.id.tvwOrderModel);
        TextView tvwOrderBrand = (TextView) rowView.findViewById(R.id.tvwOrderBrand);
        TextView tvwOrderPrice = (TextView) rowView.findViewById(R.id.tvwOrderPrice);
        tvwOrderModel.setText(cartItems.get(position).getModel().getModelName());
        tvwOrderBrand.setText(cartItems.get(position).getModel().getBrand().getBrandName());
        tvwOrderPrice.setText("$" + String.valueOf(cartItems.get(position).getModel().getPrice()));
        Spinner spinner = (Spinner)rowView.findViewById(R.id.ddlQuantity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.order_Quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(String.valueOf(cartItems.get(position).getQuantity()));
        spinner.setSelection(spinnerPosition);
        spinner.setEnabled(false);
        return rowView;
    }
}
