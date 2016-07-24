package com.example.tuckingfypos.joinslab;

/**
 * Created by TuckingFypos on 7/24/16.
 */
public class Job {
    private String mSSN;
    private String mCompany;
    private int mSalary;
    private int mExperience;

    public Job(String mSSN, String mCompany, int mSalary, int mExperience) {
        this.mSSN = mSSN;
        this.mCompany = mCompany;
        this.mSalary = mSalary;
        this.mExperience = mExperience;
    }

    public String getmSSN() {
        return mSSN;
    }

    public void setmSSN(String mSSN) {
        this.mSSN = mSSN;
    }

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public int getmSalary() {
        return mSalary;
    }

    public void setmSalary(int mSalary) {
        this.mSalary = mSalary;
    }

    public int getmExperience() {
        return mExperience;
    }

    public void setmExperience(int mExperience) {
        this.mExperience = mExperience;
    }
}
