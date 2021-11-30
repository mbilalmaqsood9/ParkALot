package com.example.parkinglot;

public class Parking {

    String parking_space_id,user_id,vehicle_id, start_time, end_time,
            hourly_rate_charged, created_at, updated_at, is_deleted, is_disabled;

    public Parking(){

    }

    public Parking(String parking_space_id,String user_id,String vehicle_id,String start_time,String end_time,
                   String hourly_rate_charged, String created_at, String updated_at, String is_deleted, String is_disabled){

        this.parking_space_id = parking_space_id;
        this.user_id = user_id;
        this.vehicle_id = vehicle_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.hourly_rate_charged = hourly_rate_charged;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_deleted = is_deleted;
        this.is_disabled = is_disabled;

    }
}
