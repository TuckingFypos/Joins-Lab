package com.example.tuckingfypos.joinslab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TuckingFypos on 7/24/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "employees.db";
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COL_PK_ID = "_id";
    public static final String COL_SSN = "SSN";
    public static final String COL_FIRST_NAME = "First";
    public static final String COL_LAST_NAME = "Last";
    public static final String COL_YOB = "year";
    public static final String COL_CITY = "city";
    public static final String JOB_TABLE_NAME = "jobs";
    public static final String COL_ID_JOB = "_id";
    public static final String COL_COMPANY = "Company";
    public static final String COL_SALARY = "Salary";
    public static final String COL_EXPERIENCE = "Experience";
    public static final String COL_EMP_SSN = "empSSN";

    private static DatabaseHelper mInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_EMPLOYEES);
        sqLiteDatabase.execSQL(SQL_CREATE_JOBS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private static final String SQL_CREATE_EMPLOYEES = "CREATE TABLE " +
            EMPLOYEE_TABLE_NAME + " (" +
            COL_PK_ID+" INTEGER PRIMARY KEY, "+
            COL_SSN + " TEXT, "+
            COL_FIRST_NAME + " TEXT," +
            COL_LAST_NAME + " TEXT," +
            COL_YOB + " INTEGER," +
            COL_CITY + " TEXT"+")";
    private static final String SQL_CREATE_JOBS = "CREATE TABLE " +
            JOB_TABLE_NAME + " (" +
            COL_ID_JOB + " INTEGER PRIMARY KEY, " +
            COL_EMP_SSN +" TEXT, "+
            COL_COMPANY + " TEXT, " +
            COL_SALARY + " INTEGER, " +
            COL_EXPERIENCE + " INTEGER," +
            "FOREIGN KEY(" + COL_EMP_SSN +") REFERENCES " +
            EMPLOYEE_TABLE_NAME + "(" + COL_SSN + ") )";
    private static final String SQL_DELETE_EMPLOYEES = "DROP TABLE IF EXISTS " + EMPLOYEE_TABLE_NAME;
    private static final String SQL_DELETE_JOBS = "DROP TABLE IF EXISTS " + JOB_TABLE_NAME;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }


    public void insertRowEmployeeTable(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SSN, employee.getmSSN());
        values.put(COL_FIRST_NAME, employee.getmFirstName());
        values.put(COL_LAST_NAME, employee.getmLastName());
        values.put(COL_YOB, employee.getmYear());
        values.put(COL_CITY, employee.getmCity());
        db.insertOrThrow(EMPLOYEE_TABLE_NAME, null, values);
        db.close();
    }

    public void insertRowJobTable(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMP_SSN,job.getmSSN());
        values.put(COL_COMPANY, job.getmCompany());
        values.put(COL_SALARY, job.getmSalary());
        values.put(COL_EXPERIENCE, job.getmExperience());
        db.insertOrThrow(JOB_TABLE_NAME, null, values);
        db.close();

    }

    public Cursor getMacysEmployees() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+ JOB_TABLE_NAME+"."+
                COL_ID_JOB+", "+
                COL_FIRST_NAME + ", " + COL_LAST_NAME + " FROM " + EMPLOYEE_TABLE_NAME + " JOIN " + JOB_TABLE_NAME +
                " ON " + JOB_TABLE_NAME + "." + COL_EMP_SSN + " = " + EMPLOYEE_TABLE_NAME + "." + COL_SSN +
                " WHERE " + COL_COMPANY + " LIKE 'Macy%'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


    public Cursor getBostonCompanies() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + JOB_TABLE_NAME+"."+
                COL_ID_JOB+", "+COL_COMPANY + " FROM " + JOB_TABLE_NAME + " JOIN " + EMPLOYEE_TABLE_NAME +
                " ON " + JOB_TABLE_NAME + "." + COL_EMP_SSN + " = " + EMPLOYEE_TABLE_NAME + "." + COL_SSN +
                " WHERE " + COL_CITY + " LIKE 'Boston'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public String getHighestSalary(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String query = "SELECT "+
                COL_COMPANY+" FROM "+JOB_TABLE_NAME+
                " WHERE "+COL_SALARY+" = (SELECT MAX("+COL_SALARY+") FROM "+JOB_TABLE_NAME+")";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                result+=cursor.getString(cursor.getColumnIndex(COL_COMPANY));
                cursor.moveToNext();
            }
        }
        return result;
    }


}
