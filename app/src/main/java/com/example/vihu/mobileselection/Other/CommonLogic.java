package com.example.vihu.mobileselection.Other;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonLogic {

    public static void setLoginStatus(Context context, boolean loginStatus) {
        SharedPreferences loginStatusPreference = context.getSharedPreferences("loginStatus", 0);
        SharedPreferences.Editor editor = loginStatusPreference.edit();
        editor.putBoolean("loginStatus", loginStatus);
        editor.commit();
    }

    public static boolean getLoginStatus(Context context) {
        SharedPreferences loginStatusPreference = context.getSharedPreferences("loginStatus", 0);
        return loginStatusPreference.getBoolean("loginStatus",false);
    }

    public static void setOrderStatus(Context context, boolean orderStatus) {
        SharedPreferences orderStatusPreference = context.getSharedPreferences("orderStatus", 0);
        SharedPreferences.Editor editor = orderStatusPreference.edit();
        editor.putBoolean("orderStatus", orderStatus);
        editor.commit();
    }

    public static boolean getOrderStatus(Context context) {
        SharedPreferences orderStatusPreference = context.getSharedPreferences("orderStatus", 0);
        return orderStatusPreference.getBoolean("orderStatus",false);
    }

    public static void setCartItem(Context context, int cartCount) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartCount", 0);
        SharedPreferences.Editor editor = cartCountPreference.edit();
        editor.putInt("cartCount", cartCount);
        editor.commit();
    }

    public static int getCartItem(Context context) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartCount", 0);
        return cartCountPreference.getInt("cartCount",0);
    }

    public static void setCartId(Context context, String cartId) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartId", 0);
        SharedPreferences.Editor editor = cartCountPreference.edit();
        editor.putString("cartId", cartId);
        editor.commit();
    }

    public static String getCartId(Context context) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartId", 0);
        return cartCountPreference.getString("cartId","");
    }

    public static void setUserId(Context context, int userId) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartId", 0);
        SharedPreferences.Editor editor = cartCountPreference.edit();
        editor.putInt("userId", userId);
        editor.commit();
    }

    public static int getUserId(Context context) {
        SharedPreferences cartCountPreference = context.getSharedPreferences("cartId", 0);
        return cartCountPreference.getInt("userId",0);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(password.getBytes());
        return new BigInteger(1, digest.digest()).toString(16);
    }
}
