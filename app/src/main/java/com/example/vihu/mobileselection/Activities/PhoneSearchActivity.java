package com.example.vihu.mobileselection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vihu.mobileselection.Models.BranchModel;
import com.example.vihu.mobileselection.Models.BrandModel;
import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.Other.Constant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneSearchActivity extends AppCompatActivity {

    private SqlHelper sqlHelper;
    private ArrayList<String> brands;
    private HashMap<String, Integer> brandList = new HashMap<String, Integer>();
    ArrayList<BrandModel> brandModelList;

    // Screen Control Parameters
    Spinner ddlSearchBrand;
    EditText txtModel;
    Spinner ddlSearchMake;
    EditText txtPrice;
    Spinner ddlSearchProcessor;
    Spinner ddlSearchRam;
    Spinner ddlSearchMemory;
    Spinner ddlSearchCamera;
    ArrayList<BranchModel> branchModelList;
    private String TAG = "Branches";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_search);
        sqlHelper = new SqlHelper(this);

        // Initialize Controls
        InitializeControls();

        // Fill All Spinners
        fillDropdownLists();
    }

    private void InitializeControls() {
        ddlSearchBrand = (Spinner)findViewById(R.id.ddlSearchBrand);
        txtModel = (EditText)findViewById(R.id.txtModel);
        ddlSearchMake = (Spinner)findViewById(R.id.ddlSearchMake);
        txtPrice = (EditText)findViewById(R.id.txtPrice);
        ddlSearchProcessor = (Spinner)findViewById(R.id.ddlSearchProcessor);
        ddlSearchRam = (Spinner)findViewById(R.id.ddlSearchRam);
        ddlSearchMemory = (Spinner)findViewById(R.id.ddlSearchMemory);
        ddlSearchCamera = (Spinner)findViewById(R.id.ddlSearchCamera);
    }

    private void fillDropdownLists() {
        Spinner spinner = (Spinner)findViewById(R.id.ddlSearchBrand);
        getAllBrands();
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, brands);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterBrand);

        spinner = (Spinner)findViewById(R.id.ddlSearchMake);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.make, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner)findViewById(R.id.ddlSearchProcessor);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.processor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner)findViewById(R.id.ddlSearchRam);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ram, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner)findViewById(R.id.ddlSearchMemory);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.memory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner)findViewById(R.id.ddlSearchCamera);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.camera, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void getAllBrands() {
        sqlHelper.open();
        brandModelList = sqlHelper.getAllBrands();
        sqlHelper.close();
        brands = new ArrayList<String>();
        if(brandModelList != null && brandModelList.size() > 0) {
            brands.add("Select");
            brandList.put("Select", 0);
            for(BrandModel brand : brandModelList) {
                brands.add(brand.getBrandName());
                brandList.put(brand.getBrandName(), brand.getBrandId());
            }
        }
    }

    public void btnSearch_OnClick(View view) {
        int brandId = brandList.get(ddlSearchBrand.getSelectedItem().toString().trim());
        String modelName = txtModel.getText().toString().trim();
        int make = ddlSearchMake.getSelectedItem().toString().trim().toLowerCase().equals(Constant.dropdownDefaultValue.toLowerCase()) ? 0 : Integer.parseInt(ddlSearchMake.getSelectedItem().toString().trim());
        double price = txtPrice.getText().toString().trim().equals("") ? 0 : Double.parseDouble(txtPrice.getText().toString().trim());
        String processor = ddlSearchProcessor.getSelectedItem().toString();
        String ram = ddlSearchRam.getSelectedItem().toString();
        String memory = ddlSearchMemory.getSelectedItem().toString();
        String camera = ddlSearchCamera.getSelectedItem().toString();
        sqlHelper.open();
        BrandModel brand;
        if(brandId == 0) {
            brand = new BrandModel(0, "");
        }
        else {
            brand = sqlHelper.getBrandByBrandId(brandId);
        }
        ProductModel model = new ProductModel(0, modelName, brand, make, price, processor, camera, ram, memory);
        ArrayList<ProductModel> products = sqlHelper.getAllProductsForSearch(model);
        sqlHelper.close();

        if(products != null && products.size() > 0) {
            Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
            intent.putExtra("brand", brandId);
            intent.putExtra("model", modelName);
            intent.putExtra("make", make);
            intent.putExtra("price", price);
            intent.putExtra("processor", processor);
            intent.putExtra("ram", ram);
            intent.putExtra("memory", memory);
            intent.putExtra("camera", camera);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No product found for this selection!", Toast.LENGTH_LONG).show();
        }
    }
}
