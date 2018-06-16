package com.example.vihu.mobileselection.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.vihu.mobileselection.Adapters.ProductAdapter;
import com.example.vihu.mobileselection.Models.BrandModel;
import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    SqlHelper sqlHelper;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        sqlHelper = new SqlHelper(this);
        extras = getIntent().getExtras();
        fillProductAdapter();
    }

    private void fillProductAdapter() {
        GridView grdProductList = (GridView)findViewById(R.id.grdProductList);
        sqlHelper.open();
        int brandId = extras.getInt("brand");
        BrandModel brand;
        if(brandId == 0) {
            brand = new BrandModel(0, "");
        }
        else {
            brand = sqlHelper.getBrandByBrandId(brandId);
        }
        ProductModel model = new ProductModel(0, extras.getString("model"), brand,
                extras.getInt("make"), extras.getDouble("price"), extras.getString("processor"), extras.getString("camera"),
                extras.getString("ram"), extras.getString("memory"));
        ArrayList<ProductModel> products = sqlHelper.getAllProductsForSearch(model);
        sqlHelper.close();
        if(products != null && products.size() > 0) {
            ProductAdapter adapter = new ProductAdapter(this, products);
            grdProductList.setAdapter(adapter);
        }
    }
}
