package com.example.twopayd2c.data.model.travel.flight.airReprintresponse

import com.google.gson.annotations.SerializedName

data class RetailerDetail(@SerializedName("sub_Retailer_CompanyName")
                          val subRetailerCompanyName: String = "",
                          @SerializedName("retailer_Id")
                          val retailerId: String = "",
                          @SerializedName("retailer_Name")
                          val retailerName: String = "",
                          @SerializedName("retailer_Email_Id")
                          val retailerEmailId: String = "",
                          @SerializedName("retailer_Landmark")
                          val retailerLandmark: String = "",
                          @SerializedName("operator_Name")
                          val operatorName: String = "",
                          @SerializedName("retailer_Mobile_Number")
                          val retailerMobileNumber: String = "",
                          @SerializedName("bookedBy_Operator_Name")
                          val bookedByOperatorName: String = "",
                          @SerializedName("retailer_Address")
                          val retailerAddress: String = "",
                          @SerializedName("retailer_Area")
                          val retailerArea: String = "",
                          @SerializedName("retailer_Pincode")
                          val retailerPincode: String = "",
                          @SerializedName("retailer_PANno")
                          val retailerPANno: String = "",
                          @SerializedName("retailer_City")
                          val retailerCity: String = "",
                          @SerializedName("retailer_GSTno")
                          val retailerGSTno: String = "",
                          @SerializedName("retailer_State")
                          val retailerState: String = "",
                          @SerializedName("sub_Retailer_Id")
                          val subRetailerId: String = "",
                          @SerializedName("retailer_CompanyName")
                          val retailerCompanyName: String = "")