package com.example.tuckingfypos.joinslab;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView; //listview for data return
    TextView mTextView; //textview for single return
    Button mButtonAdd,  mButtonSalary, mButtonBoston, mButtonCompany;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listViewForReturn);
        mTextView = (TextView) findViewById(R.id.aTextView);
        mButtonAdd = (Button) findViewById(R.id.buttonToAdd);
        mButtonSalary = (Button) findViewById(R.id.buttonToSalary);
        mButtonBoston = (Button) findViewById(R.id.buttonToBoston);
        mButtonCompany = (Button) findViewById(R.id.buttonToSame);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                Toast.makeText(MainActivity.this, "Data added!", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseHelper helper = DatabaseHelper.getInstance(MainActivity.this);
                Cursor cursor = helper.getMacysEmployees();
                CursorAdapter adapter = new CursorAdapter(MainActivity.this,cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER){

                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME));
                        String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME));
                        String fullName = firstName + " "+lastName + " ";
                        textView.setText(fullName);
                    }
                };

                mListView.setAdapter(adapter);

            }
        });
        mButtonBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseHelper helper = DatabaseHelper.getInstance(MainActivity.this);
                Cursor cursor = helper.getBostonCompanies();
                CursorAdapter adapter = new CursorAdapter(MainActivity.this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER){

                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setText(cursor.getString(cursor.getColumnIndex(helper.COL_COMPANY)));
                    }
                };

                mListView.setAdapter(adapter);
            }
        });
        mButtonSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper helper = DatabaseHelper.getInstance(MainActivity.this);
                TextView textView = (TextView) findViewById(R.id.aTextView);
                String result = helper.getHighestSalary();
                textView.setText(result);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
                builder.setView(dialogLayout);


                builder.setTitle("Tables-lab");

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("ADD DATA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();


                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "DATA ADDED", Toast.LENGTH_SHORT).show();
                        Employee employee = new Employee();
                        EditText edittext1 = (EditText) dialogLayout.findViewById(R.id.ssnEditText);
                        EditText edittext2 = (EditText) dialogLayout.findViewById(R.id.firstNameEditText);
                        EditText edittext3 = (EditText) dialogLayout.findViewById(R.id.lastNameEditText);
                        EditText edittext4 = (EditText) dialogLayout.findViewById(R.id.dobEditText);
                        EditText edittext5 = (EditText) dialogLayout.findViewById(R.id.cityEditText);
                        if (edittext1.getText().toString().length() == 0) {
                            edittext1.setError("Please type in your SSN");
                        } else if (edittext2.getText().toString().length() == 0) {
                            edittext2.setError("Please type in your first name");
                        } else if (edittext3.getText().toString().length() == 0) {
                            edittext3.setError("Please type in your last name");
                        } else if (edittext4.getText().toString().length() == 0) {
                            edittext4.setError("Please type in your year of birth");
                        } else if (edittext5.getText().toString().length() == 0) {
                            edittext5.setError("Please type in your city");
                        } else {
                            employee.setmSSN((edittext1.getText().toString()));
                            employee.setmFirstName(edittext2.getText().toString());
                            employee.setmLastName(edittext3.getText().toString());
                            employee.setmYear(Integer.parseInt(edittext4.getText().toString()));
                            employee.setmCity(edittext5.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });

                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "works!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addData() {

        DatabaseHelper helper = DatabaseHelper.getInstance(MainActivity.this);

        Employee anEmployee1 = new Employee("123045678", "John", "Smith", 1973, "NY");
        Employee anEmployee2 = new Employee("123045679", "David", "McWill", 1982, "Seattle");
        Employee anEmployee3 = new Employee("123045680", "Katerina", "Wise", 1973, "Boston");
        Employee anEmployee4 = new Employee("123045681", "Donald", "Lee", 1992, "London");
        Employee anEmployee5 = new Employee("123045682", "Gary", "Henwood", 1987, "Las Vegas");
        Employee anEmployee6 = new Employee("123045683", "Anthony", "Bright", 1963, "Seattle");
        Employee anEmployee7 = new Employee("123045684", "William", "Newey", 1995, "Boston");
        Employee anEmployee8 = new Employee("123045685", "Melony", "Smith", 1970, "Chicago");

        Job aJob1 = new Job("123045678", "Fuzz", 60, 1);
        Job aJob2 = new Job("123045679", "GA", 70, 2);
        Job aJob3 = new Job("123045680", "Little Place", 120, 5);
        Job aJob4 = new Job("123045681", "Macy's", 78, 3);
        Job aJob5 = new Job("123045682", "New Life", 65, 1);
        Job aJob6 = new Job("123045683", "Believe", 158, 6);
        Job aJob7 = new Job("123045684", "Macy's", 200, 8);
        Job aJob8 = new Job("123045685", "Stop", 299, 12);


        helper.insertRowEmployeeTable(anEmployee1);
        helper.insertRowEmployeeTable(anEmployee2);
        helper.insertRowEmployeeTable(anEmployee3);
        helper.insertRowEmployeeTable(anEmployee4);
        helper.insertRowEmployeeTable(anEmployee5);
        helper.insertRowEmployeeTable(anEmployee6);
        helper.insertRowEmployeeTable(anEmployee7);
        helper.insertRowEmployeeTable(anEmployee8);

        helper.insertRowJobTable(aJob1);
        helper.insertRowJobTable(aJob2);
        helper.insertRowJobTable(aJob3);
        helper.insertRowJobTable(aJob4);
        helper.insertRowJobTable(aJob5);
        helper.insertRowJobTable(aJob6);
        helper.insertRowJobTable(aJob7);
        helper.insertRowJobTable(aJob8);

        helper.close();


    }

}


