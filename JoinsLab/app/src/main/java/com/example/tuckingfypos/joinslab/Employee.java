package com.example.tuckingfypos.joinslab;

/**
 * Created by TuckingFypos on 7/23/16.
 */
public class Employee {
    private String mSSN;
    private String mFirstName;
    private String mLastName;
    private int mYear;
    private String mCity;

    public Employee(){
    }

    public Employee(String mSSN, String mFirstName, String mLastName, int mYear, String mCity) {
        this.mSSN = mSSN;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mYear = mYear;
        this.mCity = mCity;
    }

    public String getmSSN() {
        return mSSN;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public int getmYear() {
        return mYear;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmSSN(String mSSN) {
        this.mSSN = mSSN;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }
}
