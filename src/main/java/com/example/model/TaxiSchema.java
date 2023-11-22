package com.example.model;

import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public TaxiSchema(int rideId, int vendorId, Timestamp pickupTime, Timestamp dropTime, int pickupLocationId, int dropLocationId, String cabNumber, String driverLicenseNumber, int passengerCount, Double tripDistance, int rateCodeId, int paymentType, Double totalAmount, Double fareAmount, Double extra, Double mtaTax, Double tipAmount, Double tollsAmount, Double improvementSurcharge) {
        RideId = rideId;
        VendorId = vendorId;
        PickupTime = pickupTime;
        DropTime = dropTime;
        PickupLocationId = pickupLocationId;
        DropLocationId = dropLocationId;
        CabNumber = cabNumber;
        DriverLicenseNumber = driverLicenseNumber;
        PassengerCount = passengerCount;
        TripDistance = tripDistance;
        RateCodeId = rateCodeId;
        PaymentType = paymentType;
        TotalAmount = totalAmount;
        FareAmount = fareAmount;
        Extra = extra;
        MtaTax = mtaTax;
        TipAmount = tipAmount;
        TollsAmount = tollsAmount;
        ImprovementSurcharge = improvementSurcharge;
    }
}
