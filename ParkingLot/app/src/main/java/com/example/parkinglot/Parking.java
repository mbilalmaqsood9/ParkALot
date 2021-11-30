package com.example.parkinglot;

public class Parking {

    private String parking_space_id,user_id,vehicle_id, start_time, end_time,
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

    public String getParking_space_id() {
        return parking_space_id;
    }

    public void setParking_space_id(String parking_space_id) {
        this.parking_space_id = parking_space_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHourly_rate_charged() {
        return hourly_rate_charged;
    }

    public void setHourly_rate_charged(String hourly_rate_charged) {
        this.hourly_rate_charged = hourly_rate_charged;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getIs_disabled() {
        return is_disabled;
    }

    public void setIs_disabled(String is_disabled) {
        this.is_disabled = is_disabled;
    }
}
