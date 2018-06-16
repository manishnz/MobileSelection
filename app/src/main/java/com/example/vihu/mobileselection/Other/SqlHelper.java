package com.example.vihu.mobileselection.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vihu.mobileselection.Models.BranchMobileModel;
import com.example.vihu.mobileselection.Models.BranchModel;
import com.example.vihu.mobileselection.Models.BrandModel;
import com.example.vihu.mobileselection.Models.CustomerModel;
import com.example.vihu.mobileselection.Models.OrderItemModel;
import com.example.vihu.mobileselection.Models.OrderModel;
import com.example.vihu.mobileselection.Models.ProductModel;
import com.example.vihu.mobileselection.Models.RoleModel;
import com.example.vihu.mobileselection.Models.StaffModel;
import com.example.vihu.mobileselection.Models.TempCartModel;
import com.example.vihu.mobileselection.Models.UserModel;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SqlHelper extends SQLiteOpenHelper {

    // Tables Name
    private static final String tblRole = "tblRole";
    private static final String tblUser = "tblUser";
    private static final String tblUserRole = "tblUserRole";
    private static final String tblBranch = "tblBranch";
    private static final String tblStaff = "tblStaff";
    private static final String tblCustomer = "tblCustomer";
    private static final String tblBrand = "tblBrand";
    private static final String tblModel = "tblModel";
    private static final String tblBranchMobile = "tblBranchMobile";
    private static final String tblOrder = "tblOrder";
    private static final String tblOrderItem = "tblOrderItem";
    private static final String tblTempCart = "tblTempCart";

    //----------- Role Table Detail
    private static final String colRoleId = "RoleId";
    private static final String colRoleName = "RoleName";

    //----------- User Table Detail
    private static final String colUserId = "UserId";
    private static final String colUserName = "UserName";
    private static final String colUserPassword = "Password";
    private static final String colUserIsActive = "IsActive";
    private static final String colUserRole = "RoleId";

    //----------- UserRole Table Detail
    private static final String colUserRoleId = "UserRoleId";
    private static final String colUserRoleUser = "UserId";
    private static final String colUserRoleRole = "RoleId";

    //----------- Branch Table Detail
    private static final String colBranchId = "BranchId";
    private static final String colBranchName = "BranchName";

    //----------- Staff Table Detail
    private static final String colStaffId = "StaffId";
    private static final String colStaffName = "Name";
    private static final String colStaffBranch = "BranchId";
    private static final String colStaffUser = "UserId";

    //----------- Customer Table Detail
    private static final String colCustomerId = "CustomerId";
    private static final String colCustomerName = "Name";
    private static final String colCustomerEmail = "Email";
    private static final String colCustomerPhone = "Phone";
    private static final String colCustomerAddress = "Address";
    private static final String colCustomerZip = "Zip";
    private static final String colCustomerUser = "UserId";

    //----------- Brand Table Detail
    private static final String colBrandId = "BrandId";
    private static final String colBrandName = "BrandName";

    //----------- Model Table Detail
    private static final String colModelId = "ModelId";
    private static final String colModelName = "ModelName";
    private static final String colModelMake = "Make";
    private static final String colModelPrice = "Price";
    private static final String colModelProcessor = "Processor";
    private static final String colModelRAM = "RAM";
    private static final String colModelStorage = "Storage";
    private static final String colModelCamera = "Camera";
    private static final String colModelBrand = "BrandId";

    //----------- BranchMobile Table Detail
    private static final String colBranchMobileId = "BranchMobileId";
    private static final String colBranchMobileQuantity = "Quantity";
    private static final String colBranchMobileBranch = "BranchId";
    private static final String colBranchMobileModel = "ModelId";

    //----------- Order Table Detail
    private static final String colOrderId = "OrderId";
    private static final String colOrderAmount = "Amount";
    private static final String colOrderDate = "OrderDate";
    private static final String colOrderLoyaltyPoints = "LoyaltyPoints";
    private static final String colOrderCustomer = "CustomerId";
    private static final String colOrderBranch = "BranchId";

    //----------- Order Item Table Detail
    private static final String colOrderItemId = "OrderItemId";
    private static final String colOrderItemQuantity = "Quantity";
    private static final String colOrderItemOrder = "OrderId";
    private static final String colOrderItemModel = "ModelId";

    //----------- TempCart Table Detail
    private static final String colTempCartId = "CartId";
    private static final String colTempCartQuantity = "Quantity";
    private static final String colTempCartModel = "ModelId";

    // ---- End Table Details

    // ------ All Models Object
    private RoleModel role;
    private ArrayList<RoleModel> roles;
    private UserModel user;
    private BranchModel branch;
    private ArrayList<BranchModel> branches;
    private StaffModel staff;
    private CustomerModel customer;
    private BrandModel brand;
    private ArrayList<BrandModel> brands;
    private ProductModel product;
    private ArrayList<ProductModel> products;
    private BranchMobileModel branchMobile;
    private OrderModel order;
    private ArrayList<TempCartModel> tempCartItems;

    // Database related variables
    private static final String databaseName = "MobileStore.db";
    private static final int databaseEdition = 1;
    private final Context context;
    private SQLiteDatabase db;

    // Constructor
    public SqlHelper(Context context) {
        super(context, databaseName,null,databaseEdition);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + tblRole + "(" + colRoleId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colRoleName + " VARCHAR(255))");
            db.execSQL("CREATE TABLE " + tblUser + "(" + colUserId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colUserName + " VARCHAR(255)," + colUserPassword + " VARCHAR(255), " +
                    colUserIsActive + " BIT," + colUserRole + " INTEGER, FOREIGN KEY(" +
                    colUserRole + ") REFERENCES " + tblRole + "(" + colRoleId + "))");
            db.execSQL("CREATE TABLE " + tblUserRole + "(" + colUserRoleId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colUserRoleUser + " INTEGER," + colUserRoleRole + " INTEGER, FOREIGN KEY(" +
                    colUserRoleUser + ") REFERENCES " + tblUser + "(" + colUserId + "), FOREIGN KEY(" +
                    colUserRoleRole + ") REFERENCES " + tblRole + "(" + colRoleId + "))");
            db.execSQL("CREATE TABLE " + tblBranch + "(" + colBranchId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colBranchName + " VARCHAR(255))");
            db.execSQL("CREATE TABLE " + tblStaff + "(" + colStaffId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colStaffName + " VARCHAR(255)," + colStaffBranch + " INTEGER," +
                    colStaffUser + " INTEGER, FOREIGN KEY(" + colStaffBranch + ") REFERENCES " +
                    tblBranch + "(" + colBranchId + "), FOREIGN KEY(" + colStaffUser + ") REFERENCES " +
                    tblUser + "(" + colUserId + "))");
            db.execSQL("CREATE TABLE " + tblCustomer + "(" + colCustomerId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colCustomerName + " VARCHAR(50)," + colCustomerEmail + " VARCHAR(50)," +
                    colCustomerPhone + " VARCHAR(10)," + colCustomerAddress + " VARCHAR(255)," +
                    colCustomerZip + " VARCHAR(6)," + colCustomerUser + " INTEGER, FOREIGN KEY(" +
                    colCustomerUser + ") REFERENCES " + tblUser + "(" + colUserId + "))");
            db.execSQL("CREATE TABLE " + tblBrand + "(" + colBrandId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colBrandName + " VARCHAR(255))");
            db.execSQL("CREATE TABLE " + tblModel + "(" + colModelId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colModelName + " VARCHAR(255), " + colModelMake + " INTEGER," + colModelPrice + " DOUBLE, " +
                    colModelProcessor + " VARCHAR(255)," + colModelRAM + " VARCHAR(255)," +
                    colModelStorage + " VARCHAR(255)," + colModelCamera + " VARCHAR(255),"+
                    colModelBrand + " INTEGER, FOREIGN KEY(" + colModelBrand + ") REFERENCES " +
                    tblBrand + "(" + colBrandId + "))");
            db.execSQL("CREATE TABLE " + tblBranchMobile + "(" + colBranchMobileId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colBranchMobileQuantity + " INTEGER," + colBranchMobileBranch + " INTEGER," +
                    colBranchMobileModel + " INTEGER, FOREIGN KEY(" + colBranchMobileBranch + ") REFERENCES " +
                    tblBranch + "(" + colBranchId + "), FOREIGN KEY(" + colBranchMobileModel + ") REFERENCES " +
                    tblModel + "(" + colModelId + "))");
            db.execSQL("CREATE TABLE " + tblTempCart + "(" + colTempCartId + " VARCHAR(255)," +
                    colTempCartQuantity + " INTEGER," + colTempCartModel + " INTEGER, FOREIGN KEY(" +
                    colTempCartModel + ") REFERENCES " + tblModel + "(" + colModelId + "))");
            db.execSQL("CREATE TABLE " + tblOrder + "(" + colOrderId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colOrderAmount + " DOUBLE, " + colOrderDate + " DATE," + colOrderLoyaltyPoints + " INTEGER," +
                    colOrderCustomer + " INTEGER," + colOrderBranch + " INTEGER, FOREIGN KEY(" + colOrderCustomer + ") REFERENCES " +
                    tblCustomer + "(" + colCustomerId + "), FOREIGN KEY(" + colOrderBranch + ") REFERENCES " +
                    tblBranch + "(" + colBranchId + "))");
            db.execSQL("CREATE TABLE " + tblOrderItem + "(" + colOrderItemId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    colOrderItemQuantity + " INTEGER," + colOrderItemOrder + " INTEGER," +
                    colOrderItemModel + " INTEGER, FOREIGN KEY(" + colOrderItemOrder + ") REFERENCES " +
                    tblOrder + "(" + colOrderId + "), FOREIGN KEY(" + colOrderItemModel + ") REFERENCES " +
                    tblModel + "(" + colModelId + "))");

            // Write initial data for which screen is not provided
//            if(databaseEdition == 1) {
//                insertInitialData();
//            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertInitialData() {
        // Insert Roles
        insertRole(new RoleModel(0,RoleConstant.Customer));
        insertRole(new RoleModel(0,RoleConstant.BM));
        insertRole(new RoleModel(0,RoleConstant.DM));
        insertRole(new RoleModel(0,RoleConstant.EM));

        // Insert Branches
        insertBranch(new BranchModel(0,"City Centre"));
        insertBranch(new BranchModel(0,"Northcote"));
        insertBranch(new BranchModel(0,"Glenfield"));
        insertBranch(new BranchModel(0,"Rotorua"));
        insertBranch(new BranchModel(0,"Takapuna"));
        insertBranch(new BranchModel(0,"Devonport"));
        insertBranch(new BranchModel(0,"Albany"));
        insertBranch(new BranchModel(0,"Forrest Hill"));

        // Insert Staff Users
        try {
            insertUser(new UserModel(0, "cityBm", CommonLogic.hashPassword("cityBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "cityDm", CommonLogic.hashPassword("cityDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "cityEm", CommonLogic.hashPassword("cityEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "northBm", CommonLogic.hashPassword("northBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "northDm", CommonLogic.hashPassword("northDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "northEm", CommonLogic.hashPassword("northEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "glenBm", CommonLogic.hashPassword("glenBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "glenDm", CommonLogic.hashPassword("glenDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "glenEm", CommonLogic.hashPassword("glenEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "rotoBm", CommonLogic.hashPassword("rotoBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "rotoDm", CommonLogic.hashPassword("rotoDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "rotoEm", CommonLogic.hashPassword("rotoEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "takaBm", CommonLogic.hashPassword("takaBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "takaDm", CommonLogic.hashPassword("takaDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "takaEm", CommonLogic.hashPassword("takaEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "devonBm", CommonLogic.hashPassword("devonBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "devonDm", CommonLogic.hashPassword("devonDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "devonEm", CommonLogic.hashPassword("devonEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "albaBm", CommonLogic.hashPassword("albaBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "albaDm", CommonLogic.hashPassword("albaDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "albaEm", CommonLogic.hashPassword("albaEm@123"), getRoleByRoleId(4), true));
            insertUser(new UserModel(0, "forrBm", CommonLogic.hashPassword("forrBm@123"), getRoleByRoleId(2), true));
            insertUser(new UserModel(0, "forrDm", CommonLogic.hashPassword("forrDm@123"), getRoleByRoleId(3), true));
            insertUser(new UserModel(0, "forrEm", CommonLogic.hashPassword("forrEm@123"), getRoleByRoleId(4), true));
        }
        catch (NoSuchAlgorithmException e) {

        }

        // Insert Staff
        insertStaff(new StaffModel(0, "Bm City Centre", getBranchByBranchId(1), getUserById(1)));
        insertStaff(new StaffModel(0, "Dm City Centre", getBranchByBranchId(1), getUserById(2)));
        insertStaff(new StaffModel(0, "Em City Centre", getBranchByBranchId(1), getUserById(3)));
        insertStaff(new StaffModel(0, "Bm Northcote", getBranchByBranchId(2), getUserById(4)));
        insertStaff(new StaffModel(0, "Dm Northcote", getBranchByBranchId(2), getUserById(5)));
        insertStaff(new StaffModel(0, "Em Northcote", getBranchByBranchId(2), getUserById(6)));
        insertStaff(new StaffModel(0, "Bm Glenfield", getBranchByBranchId(3), getUserById(7)));
        insertStaff(new StaffModel(0, "Dm Glenfield", getBranchByBranchId(3), getUserById(8)));
        insertStaff(new StaffModel(0, "Em Glenfield", getBranchByBranchId(3), getUserById(9)));
        insertStaff(new StaffModel(0, "Bm Rotorua", getBranchByBranchId(4), getUserById(10)));
        insertStaff(new StaffModel(0, "Dm Rotorua", getBranchByBranchId(4), getUserById(11)));
        insertStaff(new StaffModel(0, "Em Rotorua", getBranchByBranchId(4), getUserById(12)));
        insertStaff(new StaffModel(0, "Bm Takapuna", getBranchByBranchId(5), getUserById(13)));
        insertStaff(new StaffModel(0, "Dm Takapuna", getBranchByBranchId(5), getUserById(14)));
        insertStaff(new StaffModel(0, "Em Takapuna", getBranchByBranchId(5), getUserById(15)));
        insertStaff(new StaffModel(0, "Bm Devonport", getBranchByBranchId(6), getUserById(16)));
        insertStaff(new StaffModel(0, "Dm Devonport", getBranchByBranchId(6), getUserById(17)));
        insertStaff(new StaffModel(0, "Em Devonport", getBranchByBranchId(6), getUserById(18)));
        insertStaff(new StaffModel(0, "Bm Albany", getBranchByBranchId(7), getUserById(19)));
        insertStaff(new StaffModel(0, "Dm Albany", getBranchByBranchId(7), getUserById(20)));
        insertStaff(new StaffModel(0, "Em Albany", getBranchByBranchId(7), getUserById(21)));
        insertStaff(new StaffModel(0, "Bm Forrest Hill", getBranchByBranchId(8), getUserById(22)));
        insertStaff(new StaffModel(0, "Dm Forrest Hill", getBranchByBranchId(8), getUserById(23)));
        insertStaff(new StaffModel(0, "Em Forrest Hill", getBranchByBranchId(8), getUserById(24)));

        // Insert Brands
        insertBrand(new BrandModel(0, "iPhone"));
        insertBrand(new BrandModel(0, "Samsung"));
        insertBrand(new BrandModel(0, "Motorola"));
        insertBrand(new BrandModel(0, "Nokia"));
        insertBrand(new BrandModel(0, "Huawei"));
        insertBrand(new BrandModel(0, "Mi"));
        insertBrand(new BrandModel(0, "Sony"));
        insertBrand(new BrandModel(0, "Alcatel"));

        // Insert Models
        insertModel(new ProductModel(0,"Galaxy S6", getBrandByBrandName("Samsung"), 2016,750,"1.6 GHz", "12 MP", "4 GB", "32 GB"));
        insertModel(new ProductModel(0,"Galaxy S7", getBrandByBrandName("Samsung"), 2017,950,"1.8 GHz", "12 MP", "4 GB", "64 GB"));
        insertModel(new ProductModel(0,"Galaxy S8", getBrandByBrandName("Samsung"), 2017,1250,"2.4 GHz", "13 MP", "6 GB", "64 GB"));
        insertModel(new ProductModel(0,"Galaxy S9", getBrandByBrandName("Samsung"), 2018,1550,"2.6 GHz", "16 MP", "8 GB", "128 GB"));
        insertModel(new ProductModel(0,"Galaxy Note 6", getBrandByBrandName("Samsung"), 2016,850,"1.6 GHz", "12 MP", "4 GB", "32 GB"));
        insertModel(new ProductModel(0,"Galaxy Note 7", getBrandByBrandName("Samsung"), 2017,1050,"1.8 GHz", "12 MP", "4 GB", "64 GB"));
        insertModel(new ProductModel(0,"Galaxy Note 8", getBrandByBrandName("Samsung"), 2017,1350,"2.4 GHz", "13 MP", "6 GB", "64 GB"));
        insertModel(new ProductModel(0,"Galaxy Note 9", getBrandByBrandName("Samsung"), 2018,1650,"2.6 GHz", "16 MP", "8 GB", "128 GB"));

        // Insert Branch Mobile -- Inventory
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(1), getProductByModelId(1), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(1), getProductByModelId(4), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(2), getProductByModelId(2), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(2), getProductByModelId(5), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(3), getProductByModelId(3), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(3), getProductByModelId(7), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(4), getProductByModelId(6), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(4), getProductByModelId(8), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(5), getProductByModelId(1), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(5), getProductByModelId(5), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(6), getProductByModelId(2), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(6), getProductByModelId(7), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(7), getProductByModelId(3), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(7), getProductByModelId(8), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(8), getProductByModelId(4), 50));
        insertBranchMobile(new BranchMobileModel(0, getBranchByBranchId(8), getProductByModelId(6), 50));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tblOrderItem);
        db.execSQL("drop table if exists " + tblOrder);
        db.execSQL("drop table if exists " + tblBranchMobile);
        db.execSQL("drop table if exists " + tblTempCart);
        db.execSQL("drop table if exists " + tblModel);
        db.execSQL("drop table if exists " + tblBrand);
        db.execSQL("drop table if exists " + tblCustomer);
        db.execSQL("drop table if exists " + tblStaff);
        db.execSQL("drop table if exists " + tblBranch);
        db.execSQL("drop table if exists " + tblUserRole);
        db.execSQL("drop table if exists " + tblUser);
        db.execSQL("drop table if exists " + tblRole);
        onCreate(db);
    }

    public void open() {
        db = getWritableDatabase();
        // To enable foreign key constraint
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    public void close() {
        db.close();
    }

    //----------- Insert Table Records Functionality

    public boolean insertRole(RoleModel role) {
        ContentValues values = new ContentValues();
        values.put(colRoleName, role.getRoleName());
        return db.insert(tblRole, null, values) != -1;
    }

    public boolean insertUser(UserModel user) {
        ContentValues values = new ContentValues();
        values.put(colUserName, user.getUserName());
        values.put(colUserPassword, user.getPassword());
        values.put(colUserIsActive, user.isActive());
        values.put(colUserRole, user.getRole().getRoleId());
        return db.insert(tblUser, null, values) != -1;
    }

    public boolean insertBranch(BranchModel branch) {
        ContentValues values = new ContentValues();
        values.put(colBranchName, branch.getBranchName());
        return db.insert(tblBranch, null, values) != -1;
    }

    public boolean insertStaff(StaffModel staff) {
        ContentValues values = new ContentValues();
        values.put(colStaffName, staff.getStaffName());
        values.put(colStaffBranch, staff.getBranch().getBranchId());
        values.put(colStaffUser, staff.getUser().getUserId());
        return db.insert(tblStaff, null, values) != -1;
    }

    public boolean insertCustomer(CustomerModel customer) {
        ContentValues values = new ContentValues();
        values.put(colCustomerName, customer.getName());
        values.put(colCustomerEmail, customer.getEmail());
        values.put(colCustomerPhone, customer.getPhone());
        values.put(colCustomerAddress, customer.getAddress());
        values.put(colCustomerZip, customer.getZip());
        values.put(colCustomerUser, customer.getUser().getUserId());
        return db.insert(tblCustomer, null, values) != -1;
    }

    public boolean insertBrand(BrandModel brand) {
        ContentValues values = new ContentValues();
        values.put(colBrandName, brand.getBrandName());
        return db.insert(tblBrand, null, values) != -1;
    }

    public boolean insertModel(ProductModel Model) {
        ContentValues values = new ContentValues();
        values.put(colModelName, Model.getModelName());
        values.put(colModelMake, Model.getMake());
        values.put(colModelPrice, Model.getPrice());
        values.put(colModelProcessor, Model.getProcessor());
        values.put(colModelRAM, Model.getRam());
        values.put(colModelStorage, Model.getStorage());
        values.put(colModelCamera, Model.getCamera());
        values.put(colModelBrand, Model.getBrand().getBrandId());
        return db.insert(tblModel, null, values) != -1;
    }

    public boolean insertTempCart(TempCartModel tempCart) {
        ContentValues values = new ContentValues();
        values.put(colTempCartId, tempCart.getCartId());
        values.put(colTempCartQuantity, tempCart.getQuantity());
        values.put(colTempCartModel, tempCart.getModel().getModelId());
        return db.insert(tblTempCart, null, values) != -1;
    }

    public boolean insertBranchMobile(BranchMobileModel branchMobile) {
        ContentValues values = new ContentValues();
        values.put(colBranchMobileQuantity, branchMobile.getQuantity());
        values.put(colBranchMobileBranch, branchMobile.getBranch().getBranchId());
        values.put(colBranchMobileModel, branchMobile.getModel().getModelId());
        return db.insert(tblBranchMobile, null, values) != -1;
    }

    public boolean insertOrder(OrderModel order) {
        ContentValues values = new ContentValues();
        values.put(colOrderAmount, order.getAmount());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        values.put(colOrderDate, format.format(order.getOrderDate()));
        values.put(colOrderLoyaltyPoints, order.getLoyaltyPoints());
        values.put(colOrderCustomer, order.getCustomer().getCustomerId());
        values.put(colOrderBranch, order.getBranch().getBranchId());
        return db.insert(tblOrder, null, values) != -1;
    }

    public boolean insertOrderItem(OrderItemModel item) {
        ContentValues values = new ContentValues();
        values.put(colOrderItemQuantity, item.getQuantity());
        values.put(colOrderItemOrder, item.getOrder().getOrderId());
        values.put(colOrderItemModel, item.getModel().getModelId());
        return db.insert(tblOrderItem, null, values) != -1;
    }

    //----------- End Of Insert Table Records Functionality

    //----------- All Select Queries

    public ArrayList<RoleModel> getAllRoles() {
        Cursor cursor = db.query(tblRole, null, null, null, null, null, null);
        roles = null;
        if(cursor != null && cursor.getCount() > 0) {
            roles = new ArrayList<RoleModel>();
            while (cursor.moveToNext()) {
                roles.add(new RoleModel(cursor.getInt(cursor.getColumnIndex(colRoleId)), cursor.getString(cursor.getColumnIndex(colRoleName))));
            }
        }
        return roles;
    }

    public RoleModel getRoleByRoleId(int roleId) {
        Cursor cursor = db.query(tblRole, null, colRoleId + "=?", new String[] {String.valueOf(roleId)}, null, null, null);
        role = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            role = new RoleModel(roleId, cursor.getString(cursor.getColumnIndex(colRoleName)));
        }
        return role;
    }

    public RoleModel getRoleByRoleName(String roleName) {
        Cursor cursor = db.query(tblRole, null, colRoleName + "=?", new String[] {roleName}, null, null, null);
        role = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            role = new RoleModel(cursor.getInt(cursor.getColumnIndex(colRoleId)), roleName);
        }
        return role;
    }

    public UserModel getUserById(int userId) {
        Cursor cursor = db.query(tblUser, null, colUserId + "=?", new String[] {String.valueOf(userId)}, null, null, null);
        user = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new UserModel(userId, cursor.getString(cursor.getColumnIndex(colUserName)),
                    cursor.getString(cursor.getColumnIndex(colUserPassword)),
                    getRoleByRoleId(cursor.getInt(cursor.getColumnIndex(colUserRole))),
                    cursor.getInt(cursor.getColumnIndex(colUserIsActive)) != 0);
        }
        return user;
    }

    public UserModel getUserByUserName(String userName) {
        Cursor cursor = db.query(tblUser, null, colUserName + "=?", new String[] {userName}, null, null, null);
        user = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new UserModel(cursor.getInt(cursor.getColumnIndex(colUserId)), userName,
                    cursor.getString(cursor.getColumnIndex(colUserPassword)),
                    getRoleByRoleId(cursor.getInt(cursor.getColumnIndex(colUserRole))),
                    cursor.getInt(cursor.getColumnIndex(colUserIsActive)) != 0);
        }
        return user;
    }

    public UserModel getUserByUserNameAndPassword(String userName, String password) {
        Cursor cursor = db.query(tblUser, null, colUserName + "=? AND " + colUserPassword + "=?", new String[] {userName, password}, null, null, null);
        user = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new UserModel(cursor.getInt(cursor.getColumnIndex(colUserId)), userName, password,
                    getRoleByRoleId(cursor.getInt(cursor.getColumnIndex(colUserRole))),
                    cursor.getInt(cursor.getColumnIndex(colUserIsActive)) != 0);
        }
        return user;
    }

    public UserModel getLatestUser() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tblUser + " WHERE " + colUserId + " = (SELECT last_insert_rowid() AS " + colUserId + " FROM " + tblUser + ")", null);
        user = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new UserModel(cursor.getInt(cursor.getColumnIndex(colUserId)),
                    cursor.getString(cursor.getColumnIndex(colUserName)),
                    cursor.getString(cursor.getColumnIndex(colUserPassword)),
                    getRoleByRoleId(cursor.getInt(cursor.getColumnIndex(colUserRole))),
                    cursor.getInt(cursor.getColumnIndex(colUserIsActive)) != 0);
        }
        return user;
    }

    public BranchModel getBranchByBranchId(int branchId) {
        Cursor cursor = db.query(tblBranch, null, colBranchId + "=?", new String[] {String.valueOf(branchId)}, null, null, null);
        branch = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            branch = new BranchModel(branchId, cursor.getString(cursor.getColumnIndex(colBranchName)));
        }
        return branch;
    }

    public ArrayList<BranchModel> getAllBranches() {
        Cursor cursor = db.query(tblBranch, null, null, null, null, null, null);
        branches = null;
        if(cursor != null && cursor.getCount() > 0) {
            branches = new ArrayList<BranchModel>();
            while (cursor.moveToNext()) {
                branches.add(new BranchModel(cursor.getInt(cursor.getColumnIndex(colBranchId)),
                        cursor.getString(cursor.getColumnIndex(colBranchName))));
            }
        }
        return branches;
    }

    public StaffModel getStaffByUserId(int userId) {
        Cursor cursor = db.query(tblStaff, null, colStaffUser + "=?", new String[] {String.valueOf(userId)}, null, null, null);
        staff = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            staff = new StaffModel(cursor.getInt(cursor.getColumnIndex(colStaffId)), cursor.getString(cursor.getColumnIndex(colStaffName)),
                    getBranchByBranchId(cursor.getInt(cursor.getColumnIndex(colStaffBranch))),
                    getUserById(userId));
        }
        return staff;
    }

    public CustomerModel getCustomerByCustomerId(int customerId) {
        Cursor cursor = db.query(tblCustomer, null, colCustomerId + "=?", new String[] {String.valueOf(customerId)}, null, null, null);
        customer = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            customer = new CustomerModel(customerId, cursor.getString(cursor.getColumnIndex(colCustomerName)),
                    cursor.getString(cursor.getColumnIndex(colCustomerEmail)), cursor.getString(cursor.getColumnIndex(colCustomerPhone)),
                    cursor.getString(cursor.getColumnIndex(colCustomerAddress)), cursor.getString(cursor.getColumnIndex(colCustomerZip)),
                    getUserById(cursor.getInt(cursor.getColumnIndex(colStaffUser))));
        }
        return customer;
    }

    public CustomerModel getCustomerByUserId(int userId) {
        Cursor cursor = db.query(tblCustomer, null, colCustomerUser + "=?", new String[] {String.valueOf(userId)}, null, null, null);
        customer = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            customer = new CustomerModel(cursor.getInt(cursor.getColumnIndex(colCustomerId)),
                    cursor.getString(cursor.getColumnIndex(colCustomerName)), cursor.getString(cursor.getColumnIndex(colCustomerEmail)),
                    cursor.getString(cursor.getColumnIndex(colCustomerPhone)), cursor.getString(cursor.getColumnIndex(colCustomerAddress)),
                    cursor.getString(cursor.getColumnIndex(colCustomerZip)), getUserById(userId));
        }
        return customer;
    }

    public BrandModel getBrandByBrandId(int brandId) {
        Cursor cursor = db.query(tblBrand, null, colBrandId + "=?", new String[] {String.valueOf(brandId)}, null, null, null);
        brand = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            brand = new BrandModel(brandId, cursor.getString(cursor.getColumnIndex(colBrandName)));
        }
        return brand;
    }

    public BrandModel getBrandByBrandName(String brandName) {
        Cursor cursor = db.query(tblBrand, null, colBrandName + "=?", new String[] {brandName}, null, null, null);
        brand = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            brand = new BrandModel(cursor.getInt(cursor.getColumnIndex(colBrandId)), brandName);
        }
        return brand;
    }

    public ArrayList<BrandModel> getAllBrands() {
        Cursor cursor = db.query(tblBrand, null, null, null, null, null, null);
        brands = null;
        if(cursor != null && cursor.getCount() > 0) {
            brands = new ArrayList<BrandModel>();
            while (cursor.moveToNext()) {
                brands.add(new BrandModel(cursor.getInt(cursor.getColumnIndex(colBrandId)),
                        cursor.getString(cursor.getColumnIndex(colBrandName))));
            }
        }
        return brands;
    }

    public ProductModel getProductByModelId(int modelId) {
        Cursor cursor = db.query(tblModel, null, colModelId + "=?", new String[] {String.valueOf(modelId)}, null, null, null);
        product = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            product = new ProductModel(modelId, cursor.getString(cursor.getColumnIndex(colModelName)),
                    getBrandByBrandId(cursor.getInt(cursor.getColumnIndex(colModelBrand))),
                    cursor.getInt(cursor.getColumnIndex(colModelMake)), cursor.getDouble(cursor.getColumnIndex(colModelPrice)),
                    cursor.getString(cursor.getColumnIndex(colModelProcessor)), cursor.getString(cursor.getColumnIndex(colModelCamera)),
                    cursor.getString(cursor.getColumnIndex(colModelRAM)), cursor.getString(cursor.getColumnIndex(colModelStorage)));
        }
        return product;
    }

    public ArrayList<ProductModel> getAllProducts() {
        Cursor cursor = db.query(tblModel, null, null, null, null, null, null);
        products = null;
        if(cursor != null && cursor.getCount() > 0) {
            products = new ArrayList<ProductModel>();
            while (cursor.moveToNext()) {
                products.add(new ProductModel(cursor.getInt(cursor.getColumnIndex(colModelId)),
                    cursor.getString(cursor.getColumnIndex(colModelName)),
                    getBrandByBrandId(cursor.getInt(cursor.getColumnIndex(colModelBrand))),
                    cursor.getInt(cursor.getColumnIndex(colModelMake)), cursor.getDouble(cursor.getColumnIndex(colModelPrice)),
                    cursor.getString(cursor.getColumnIndex(colModelProcessor)), cursor.getString(cursor.getColumnIndex(colModelCamera)),
                    cursor.getString(cursor.getColumnIndex(colModelRAM)), cursor.getString(cursor.getColumnIndex(colModelStorage))));
            }
        }
        return products;
    }

    public ArrayList<ProductModel> getAllProductsByBrand(int brandId) {
        Cursor cursor = db.query(tblModel, null, colModelBrand + "=?", new String[] {String.valueOf(brandId)}, null, null, null);
        products = null;
        if(cursor != null && cursor.getCount() > 0) {
            products = new ArrayList<ProductModel>();
            while (cursor.moveToNext()) {
                products.add(new ProductModel(cursor.getInt(cursor.getColumnIndex(colModelId)),
                        cursor.getString(cursor.getColumnIndex(colModelName)),
                        getBrandByBrandId(cursor.getInt(cursor.getColumnIndex(colModelBrand))),
                        cursor.getInt(cursor.getColumnIndex(colModelMake)), cursor.getDouble(cursor.getColumnIndex(colModelPrice)),
                        cursor.getString(cursor.getColumnIndex(colModelProcessor)), cursor.getString(cursor.getColumnIndex(colModelCamera)),
                        cursor.getString(cursor.getColumnIndex(colModelRAM)), cursor.getString(cursor.getColumnIndex(colModelStorage))));
            }
        }
        return products;
    }

    public ArrayList<ProductModel> getAllProductsForSearch(ProductModel model) {
        String blank = "";
        String whereClause = "";
        whereClause += model.getBrand().getBrandId() > 0 ? colModelBrand + "=? OR " : blank;
        whereClause += model.getModelName().trim().equals(blank) ? blank : colModelName + " LIKE ? OR ";
        whereClause += model.getMake() > 0 ? colModelMake + ">=? OR " : blank;
        whereClause += model.getPrice() > 0 ? colModelPrice + "<=? OR " : blank;
        whereClause += model.getProcessor().trim().equals(Constant.dropdownDefaultValue) ? blank : colModelProcessor + "=? OR ";
        whereClause += model.getRam().trim().equals(Constant.dropdownDefaultValue) ? blank : colModelRAM + "=? OR ";
        whereClause += model.getStorage().trim().equals(Constant.dropdownDefaultValue) ? blank : colModelStorage + "=? OR ";
        whereClause += model.getCamera().trim().equals(Constant.dropdownDefaultValue) ? blank : colModelCamera + "=? OR ";
        if(whereClause.trim().length() > 3) {
            whereClause = whereClause.substring(0, whereClause.length()-4);
        }
        int argsLength = whereClause.split(" OR ").length;
        String[] args = new String[argsLength];
        int argIndex = 0;
        if(model.getBrand().getBrandId() > 0) {
            args[argIndex] = String.valueOf(model.getBrand().getBrandId());
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(!model.getModelName().trim().equals(blank)) {
            args[argIndex] = "%" + model.getModelName() + "%";
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(model.getMake() > 0) {
            args[argIndex] = String.valueOf(model.getMake());
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(model.getPrice() > 0) {
            args[argIndex] = String.valueOf(model.getPrice());
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(!model.getProcessor().trim().equals(Constant.dropdownDefaultValue)) {
            args[argIndex] = model.getProcessor();
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(!model.getRam().trim().equals(Constant.dropdownDefaultValue)) {
            args[argIndex] = model.getRam();
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(!model.getStorage().trim().equals(Constant.dropdownDefaultValue)) {
            args[argIndex] = model.getStorage();
            argIndex = argIndex == (argsLength - 1) ? argIndex : argIndex + 1;
        }
        if(!model.getCamera().trim().equals(Constant.dropdownDefaultValue)) {
            args[argIndex] = model.getCamera();
        }
        if(whereClause.trim().equals(blank)) {
            whereClause = null;
            args = null;
        }
        Cursor cursor = db.query(tblModel, null, whereClause, args, null, null, null);
        products = null;
        if(cursor != null && cursor.getCount() > 0) {
            products = new ArrayList<ProductModel>();
            while (cursor.moveToNext()) {
                products.add(new ProductModel(cursor.getInt(cursor.getColumnIndex(colModelId)),
                        cursor.getString(cursor.getColumnIndex(colModelName)),
                        getBrandByBrandId(cursor.getInt(cursor.getColumnIndex(colModelBrand))),
                        cursor.getInt(cursor.getColumnIndex(colModelMake)), cursor.getDouble(cursor.getColumnIndex(colModelPrice)),
                        cursor.getString(cursor.getColumnIndex(colModelProcessor)), cursor.getString(cursor.getColumnIndex(colModelCamera)),
                        cursor.getString(cursor.getColumnIndex(colModelRAM)), cursor.getString(cursor.getColumnIndex(colModelStorage))));
            }
        }
        return products;
    }

    public BranchMobileModel getBranchProductQuantity(int branchId, int modelId) {
        Cursor cursor = db.query(tblBranchMobile, null, colBranchMobileBranch + "=? AND " + colBranchMobileModel + "=?", new String[] {String.valueOf(branchId), String.valueOf(modelId)}, null, null, null);
        branchMobile = null;
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            branchMobile = new BranchMobileModel(cursor.getInt(cursor.getColumnIndex(colBranchMobileId)), getBranchByBranchId(branchId),
                    getProductByModelId(modelId), cursor.getInt(cursor.getColumnIndex(colBranchMobileQuantity)));
        }
        return branchMobile;
    }

    public ArrayList<TempCartModel> getTempCartItems(String cartId) {
        Cursor cursor = db.query(tblTempCart, null, colTempCartId + "=?", new String[] {cartId}, null, null, null);
        tempCartItems = null;
        if(cursor != null && cursor.getCount() > 0) {
            tempCartItems = new ArrayList<TempCartModel>();
            while (cursor.moveToNext()) {
                tempCartItems.add(new TempCartModel(cartId, cursor.getInt(cursor.getColumnIndex(colTempCartQuantity)),
                        getProductByModelId(cursor.getInt(cursor.getColumnIndex(colTempCartModel)))));
            }
        }
        return tempCartItems;
    }

    public OrderModel getLatestOrder() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tblOrder + " WHERE " + colOrderId + " = (SELECT last_insert_rowid() AS " + colOrderId + " FROM " + tblOrder + ")", null);
        order = null;
        if(cursor != null && cursor.getCount() > 0) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
            cursor.moveToFirst();
            try {
                order = new OrderModel(cursor.getInt(cursor.getColumnIndex(colOrderId)),
                        getCustomerByCustomerId(cursor.getInt(cursor.getColumnIndex(colOrderCustomer))),
                        cursor.getDouble(cursor.getColumnIndex(colOrderAmount)),
                        dateFormatter.parse(cursor.getString(cursor.getColumnIndex(colOrderDate))),
                        getBranchByBranchId(cursor.getInt(cursor.getColumnIndex(colOrderBranch))),
                        cursor.getInt(cursor.getColumnIndex(colOrderLoyaltyPoints)));
            }
            catch (Exception e) {

            }
        }
        return order;
    }

    //----------- End Of Select Queries

    //----------- Update Rows

    public boolean updateUserAcitveStatus(UserModel user) {
        ContentValues values = new ContentValues();
        values.put(colUserIsActive, user.isActive());
        return db.update(tblUser, values, colUserId + "=?", new String[] {String.valueOf(user.getUserId())}) > 0;
    }

    public boolean updateModel(ProductModel model) {
        ContentValues values = new ContentValues();
        values.put(colModelPrice, model.getPrice());
        return db.update(tblModel, values, colModelId + "=?", new String[] {String.valueOf(model.getModelId())}) > 0;
    }

    public boolean updateBranchModelQuantity(BranchMobileModel branchMobile) {
        ContentValues values = new ContentValues();
        values.put(colBranchMobileQuantity, branchMobile.getQuantity());
        return db.update(tblBranchMobile, values, colBranchMobileId + "=?", new String[] {String.valueOf(branchMobile.getBranchMobileId())}) > 0;
    }

    public boolean updateTempCartQuantity(TempCartModel cartModel) {
        ContentValues values = new ContentValues();
        values.put(colTempCartQuantity, cartModel.getQuantity());
        return db.update(tblTempCart, values, colTempCartId + "=? AND " + colTempCartModel + "=?", new String[] {String.valueOf(cartModel.getCartId()), String.valueOf(cartModel.getModel().getModelId())}) > 0;
    }

    //----------- End Of Updation

    //------------ Delete Rows

    public boolean deleteCartItems(String cartId) {
        return db.delete(tblTempCart, colTempCartId + "=?", new String[] {cartId}) > 0;
    }

    //------------ End of Deletion
}
