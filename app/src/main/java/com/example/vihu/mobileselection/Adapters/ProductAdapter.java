package com.example.vihu.mobileselection.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vihu.mobileselection.Activities.ProductActivity;
import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductModel> {

    private final ArrayList<ProductModel> products;
    private final Context context;

    public ProductAdapter(Context context, ArrayList<ProductModel> products) {
        super(context, -1, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_layout, null);
        }
        TextView tvwProdBrand = (TextView)convertView.findViewById(R.id.tvwProdBrand);
        TextView tvwProdModel = (TextView)convertView.findViewById(R.id.tvwProdModel);
        TextView tvwProdPrice = (TextView)convertView.findViewById(R.id.tvwProdPrice);
        tvwProdBrand.setText(products.get(position).getBrand().getBrandName());
        tvwProdModel.setText(products.get(position).getModelName());
        tvwProdPrice.setText("$" + String.valueOf(products.get(position).getPrice()));
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.ivwProduct);
        onProductClick(imageView, products.get(position));
        return convertView;
    }

    private void onProductClick(ImageView imageView, final ProductModel product) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("modelId", product.getModelId());
                context.startActivity(intent);
            }
        });
    }
}
