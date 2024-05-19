package com.littlelemon.bukharaadmin.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails():Serializable{
    var userUid:String? = null
    var userName:String? = null
    var foodName :MutableList<String>? = null
    var foodImage :MutableList<String>? = null
    var foodPrice :MutableList<String>? = null
    var foodQuantity :MutableList<Int>? = null
    var address :String? = null
    var totalPrice :String? = null
    var phoneNum :String? = null
    var orderAccepted :Boolean =false
    var paymentReceived :Boolean =false
    var itemPushKey :String? = null
    var currentTime :Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNum = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

//     fun describeContents(): Int {
//
//    }

//     fun writeToParcel(dest: Parcel, flags: Int) {
//
//    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}