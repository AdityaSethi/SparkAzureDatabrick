package com.example.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaxiSchema {
    int RideId;
    int VendorId;
    Timestamp PickupTime;
    Timestamp DropTime;
    int PickupLocationId;
    int DropLocationId;
    String CabNumber;
    String DriverLicenseNumber;
    int PassengerCount;
    Double TripDistance;
    int RateCodeId;
    int PaymentType;
    Double TotalAmount;
    Double FareAmount;
    Double Extra;
    Double MtaTax;
    Double TipAmount;
    Double TollsAmount;
    Double ImprovementSurcharge;



}
