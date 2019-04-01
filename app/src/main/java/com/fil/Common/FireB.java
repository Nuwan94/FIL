package com.fil.Common;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireB {

    private static FirebaseDatabase mDatabase;
    private static DatabaseReference userDatabaseReference;
    private static DatabaseReference productDatabaseReference;
    private static DatabaseReference cartDatabaseReference;
    private static DatabaseReference historyDatabaseReference;
    private static DatabaseReference ordersDatabaseReference;

    private static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
//            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

    public static DatabaseReference getUserReference(){
        if(userDatabaseReference == null){
            userDatabaseReference = getDatabase().getReference("Users");
        }
        return userDatabaseReference;
    }


    public static DatabaseReference getProductReference(){
        if(productDatabaseReference == null){
            productDatabaseReference = getDatabase().getReference("Products");
        }
        return productDatabaseReference;
    }

    public static DatabaseReference getCartReference(){
        if(cartDatabaseReference == null){
            cartDatabaseReference = getDatabase().getReference("Carts");
        }
        return cartDatabaseReference;
    }

    public static DatabaseReference getHistoryReference(){
        if(historyDatabaseReference == null){
            historyDatabaseReference = getDatabase().getReference("History");
        }
        return historyDatabaseReference;
    }

    public static DatabaseReference getOrderReference(){
        if(ordersDatabaseReference == null){
            ordersDatabaseReference = getDatabase().getReference("Orders");
        }
        return ordersDatabaseReference;
    }

}
