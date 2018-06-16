package com.example.vihu.mobileselection.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.vihu.mobileselection.Models.BranchMobileModel;
import com.example.vihu.mobileselection.Models.BrandModel;
import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.Models.StaffModel;
import com.example.vihu.mobileselection.Models.UserModel;
import com.example.vihu.mobileselection.Other.CommonLogic;
import com.example.vihu.mobileselection.Other.RoleConstant;
import com.example.vihu.mobileselection.Other.SqlHelper;
import com.example.vihu.mobileselection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ManageProductActivity extends AppCompatActivity {

    SqlHelper sqlHelper;

    private ArrayList<String> brands;
    private HashMap<String, Integer> brandList = new HashMap<String, Integer>();
    ArrayList<BrandModel> brandModelList;
    BrandModel brand;

    private ArrayList<String> models;
    private HashMap<String, Integer> modelList = new HashMap<String, Integer>();
    ArrayList<ProductModel> productModelList;
    ProductModel model;

    BranchMobileModel branchMobile;
    StaffModel staff;
    UserModel user;

    boolean isEm, isDm, isBm;

    EditText txtProdModel, txtProdPrice, txtProdQuantity;
    Spinner ddlProdBrand, ddlProdModel, ddlProdMake, ddlProdProcessor, ddlProdCamera, ddlProdRam, ddlProdMemory;
    TableRow trQuantity, trProdModelText, trProdModelDrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        sqlHelper = new SqlHelper(this);
        sqlHelper.open();
        user = sqlHelper.getUserById(CommonLogic.getUserId(this));
        sqlHelper.close();
        if(user != null) {
            // Initialize all controls
            initializeControls();

            if(user.getRole().getRoleName().toLowerCase().equals(RoleConstant.EM.toLowerCase())) {
                isEm = true;
            }
            else if(user.getRole().getRoleName().toLowerCase().equals(RoleConstant.DM.toLowerCase())) {
                isDm = true;
            }
            else {
                isBm = true;
            }
            // Fill all dropdowns
            fillDropdownLists();

            // Set dropdown event listener
            eventHandler();

            // Make control visible according to staff role
            setControlVisibility();
            // Make control enable according to staff role
            if(!isEm) {
                setControlEnability();
            }
        }
    }

    private void eventHandler() {
        ddlProdBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand = position == 0 ? null : brandModelList.get(position - 1);
                fillModelForBrand(brandList.get(ddlProdBrand.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ddlProdModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = position == 0 ? null : productModelList.get(position - 1);
                fillModelDetail();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillModelDetail() {
        clearNonChangableControls();
        if(this.model != null) {
            ddlProdMake.setSelection(((ArrayAdapter) ddlProdMake.getAdapter()).getPosition(String.valueOf(model.getMake())));
            txtProdPrice.setText(String.valueOf(model.getPrice()));
            ddlProdProcessor.setSelection(((ArrayAdapter) ddlProdProcessor.getAdapter()).getPosition(String.valueOf(model.getProcessor())));
            ddlProdRam.setSelection(((ArrayAdapter) ddlProdRam.getAdapter()).getPosition(String.valueOf(model.getRam())));
            ddlProdMemory.setSelection(((ArrayAdapter) ddlProdMemory.getAdapter()).getPosition(String.valueOf(model.getStorage())));
            ddlProdCamera.setSelection(((ArrayAdapter) ddlProdCamera.getAdapter()).getPosition(String.valueOf(model.getCamera())));
            if (isBm) {
                sqlHelper.open();
                staff = sqlHelper.getStaffByUserId(user.getUserId());
                if (staff != null) {
                    branchMobile = sqlHelper.getBranchProductQuantity(staff.getBranch().getBranchId(), modelList.get(ddlProdModel.getSelectedItem().toString()));
                    if (branchMobile != null) {
                        txtProdQuantity.setText(String.valueOf(branchMobile.getQuantity()));
                    }
                }
                sqlHelper.close();
            }
        }
    }

    private void fillModelForBrand(int brandId) {
        if(!isEm) {
            sqlHelper.open();
            productModelList = sqlHelper.getAllProductsByBrand(brandId);
            sqlHelper.close();
            models = new ArrayList<String>();
            if (productModelList != null && productModelList.size() > 0) {
                models.add("Select");
                modelList.put("Select", 0);
                for (ProductModel product : productModelList) {
                    models.add(product.getModelName());
                    modelList.put(product.getModelName(), product.getModelId());
                }
            }
            ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, models);
            adapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlProdModel.setAdapter(adapterModel);
        }
    }

    private void setControlEnability() {
        ddlProdMake.setEnabled(false);
        ddlProdProcessor.setEnabled(false);
        ddlProdCamera.setEnabled(false);
        ddlProdRam.setEnabled(false);
        ddlProdMemory.setEnabled(false);
        if(isBm) {
            txtProdPrice.setEnabled(false);
        }
    }

    private void setControlVisibility() {
        if(isEm) {
            trProdModelDrop.setVisibility(View.GONE);
            trQuantity.setVisibility(View.GONE);
        }
        else if(isDm) {
            trProdModelText.setVisibility(View.GONE);
            trQuantity.setVisibility(View.GONE);
        }
        else {
            trProdModelText.setVisibility(View.GONE);
        }
    }

    private void initializeControls() {
        ddlProdBrand = (Spinner)findViewById(R.id.ddlProdBrand);
        ddlProdModel = (Spinner)findViewById(R.id.ddlProdModel);
        ddlProdMake = (Spinner)findViewById(R.id.ddlProdMake);
        ddlProdProcessor = (Spinner)findViewById(R.id.ddlProdProcessor);
        ddlProdRam = (Spinner)findViewById(R.id.ddlProdRam);
        ddlProdMemory = (Spinner)findViewById(R.id.ddlProdMemory);
        ddlProdCamera = (Spinner)findViewById(R.id.ddlProdCamera);
        txtProdModel = (EditText)findViewById(R.id.txtProdModel);
        txtProdQuantity = (EditText)findViewById(R.id.txtProdQuantity);
        txtProdPrice = (EditText)findViewById(R.id.txtProdPrice);
        trQuantity = (TableRow)findViewById(R.id.trQuantity);
        trProdModelDrop = (TableRow)findViewById(R.id.trProdModelDrop);
        trProdModelText = (TableRow)findViewById(R.id.trProdModelText);
    }

    private void fillDropdownLists() {
        getAllBrands();
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, brands);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdBrand.setAdapter(adapterBrand);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.make, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdMake.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.processor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdProcessor.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ram, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdRam.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.memory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdMemory.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.camera, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlProdCamera.setAdapter(adapter);

        if(!isEm) {
            getAllModels();
            ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, models);
            adapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlProdModel.setAdapter(adapterModel);
        }
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

    private void getAllModels() {
        sqlHelper.open();
        productModelList = sqlHelper.getAllProducts();
        sqlHelper.close();
        models = new ArrayList<String>();
        if(productModelList != null && productModelList.size() > 0) {
            models.add("Select");
            modelList.put("Select", 0);
            for(ProductModel product : productModelList) {
                models.add(product.getModelName());
                modelList.put(product.getModelName(), product.getModelId());
            }
        }
    }

    public void btnSubmit_OnClick(View view) {
        if(user != null) {
            boolean fieldValidation = true;
            Pattern numberPattern = Pattern.compile("^(\\\\d{0,9}\\\\.\\\\d{1,4}|\\\\d{1,9})$");
            CharSequence seqPrice = txtProdPrice.getText().toString();
            if (ddlProdBrand.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select a brand!", Toast.LENGTH_LONG).show();
            } else if (trProdModelDrop.getVisibility() == View.VISIBLE && ddlProdModel.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())) {
//                if(isEm) {
//                    if (txtProdModel.getText().toString().trim().equals("")) {
//                        fieldValidation = false;
//                        Toast.makeText(getApplicationContext(), "Please fill model name!", Toast.LENGTH_LONG).show();
//                    }
//                }
//                else {
                    fieldValidation = false;
                    Toast.makeText(getApplicationContext(), "Please select a model!", Toast.LENGTH_LONG).show();
//                }
            } else if (trProdModelText.getVisibility() == View.VISIBLE && txtProdModel.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill model name!", Toast.LENGTH_LONG).show();
            } else if (ddlProdMake.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select make of product!", Toast.LENGTH_LONG).show();
            } else if (txtProdPrice.getText().toString().trim().equals("")) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill product price!", Toast.LENGTH_LONG).show();
            } else if (numberPattern.matcher(seqPrice).matches()) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please fill only numeric value in product price!", Toast.LENGTH_LONG).show();
            } else if (ddlProdProcessor.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select product processor!", Toast.LENGTH_LONG).show();
            } else if (ddlProdRam.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())) {
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select product RAM!", Toast.LENGTH_LONG).show();
            } else if(ddlProdMemory.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())){
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select product internal memory!", Toast.LENGTH_LONG).show();
            } else if(ddlProdCamera.getSelectedItem().toString().trim().toLowerCase().equals("Select".toLowerCase())){
                fieldValidation = false;
                Toast.makeText(getApplicationContext(), "Please select product camera!", Toast.LENGTH_LONG).show();
            } else if (trQuantity.getVisibility() == View.VISIBLE && txtProdQuantity.getText().toString().trim().equals("")) {
                if(isBm) {
                    fieldValidation = false;
                    Toast.makeText(getApplicationContext(), "Please fill product price!", Toast.LENGTH_LONG).show();
                }
            }
            if(fieldValidation) {
                sqlHelper.open();
                if(isEm) {
                    int make = Integer.parseInt(ddlProdMake.getSelectedItem().toString().trim());
                    Double price = Double.parseDouble(txtProdPrice.getText().toString().trim());
                    ProductModel product = new ProductModel(0, txtProdModel.getText().toString().trim(),
                            brand, make, price, ddlProdProcessor.getSelectedItem().toString(), ddlProdCamera.getSelectedItem().toString(),
                            ddlProdRam.getSelectedItem().toString(), ddlProdMemory.getSelectedItem().toString());
                    sqlHelper.insertModel(product);
                }
                else if(isDm) {
                    Double price = Double.parseDouble(txtProdPrice.getText().toString().trim());
                    model.setPrice(price);
                    sqlHelper.updateModel(model);
                }
                else {
                    int quantity = Integer.parseInt(txtProdQuantity.getText().toString().trim());
                    if(branchMobile == null) {
                        branchMobile = new BranchMobileModel(0, staff.getBranch(), model, quantity);
                        sqlHelper.insertBranchMobile(branchMobile);
                    }
                    else {
                        branchMobile.setQuantity(quantity);
                        sqlHelper.updateBranchModelQuantity(branchMobile);
                    }
                }
                sqlHelper.close();
                clearControls();
                Toast.makeText(getApplicationContext(), "Product Saved Successfully!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clearControls() {
        ddlProdBrand.setSelection(0);
        if(!isEm) {
            ddlProdModel.setSelection(0);
        }
        clearNonChangableControls();
    }

    private void clearNonChangableControls() {
        txtProdModel.setText("");
        txtProdQuantity.setText("");
        txtProdPrice.setText("");
        ddlProdMake.setSelection(0);
        ddlProdProcessor.setSelection(0);
        ddlProdCamera.setSelection(0);
        ddlProdRam.setSelection(0);
        ddlProdMemory.setSelection(0);
    }
}
