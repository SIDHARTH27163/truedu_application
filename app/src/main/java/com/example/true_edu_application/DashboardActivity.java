package com.example.true_edu_application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private static final int MENU_ITEM_1 = R.id.nav_item1;
    private static final int MENU_ITEM_2 = R.id.nav_item2;
    private static final int MENU_ITEM_3 = R.id.nav_item3;
    private static final int MENU_ITEM_4 = R.id.nav_item4;
    private SessionManager sessionManager;


    private static final String NAMESPACE1 = "http://tempuri.org/";
    private static final String METHOD_NAME1 = "set_bookPackage";
    private static final String SOAP_ACTION1 = "http://tempuri.org/set_bookPackage";
    private static final String URL1 = "http://trueedu.appilogics.in/WebService.asmx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(this);
        String course = "Computer Science Engineering";
        String sem = "1";


        // Create an instance of SoapRequestAsyncTask with the provided values
        SoapRequestAsyncTask soapRequestAsyncTask = new SoapRequestAsyncTask(course, sem);

        // Execute the SOAP request
        soapRequestAsyncTask.execute();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Toggle the navigation drawer when the toolbar icon is clicked
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                // Handle item clicks here
                if (id == MENU_ITEM_1) {
                    Intent intent1 = new Intent(DashboardActivity.this, update.class);
                    startActivity(intent1);
                    // Handle item 1 click
                    Log.d("sucees" , "itme 1");
                } else if (id == MENU_ITEM_2) {
                    // Handle item 2 click
//                    Intent intent2 = new Intent(MainActivity.this, YourNewActivity.class);
//                    startActivity(intent2);
                    Log.d("sucees" , "itme 1");
                } else if (id == MENU_ITEM_3) {
                    // Handle item 3 click
//                    Intent intent3 = new Intent(MainActivity.this, AnotherNewActivity.class);
//                    startActivity(intent3);
                    Log.d("sucees" , "itme 1");
                }else if (id== MENU_ITEM_4) {
                    Toast.makeText(getApplicationContext(), "Hi  Dear"+"logged out successfully", Toast.LENGTH_LONG).show();
                    sessionManager.setLoggedIn(false);
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    finish();

                }
                // Add more if-else blocks for other menu items as needed

                // Close the drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public class SoapRequestAsyncTask extends AsyncTask<Void, Void, String> {
        //        private TextView editTextFname;
        private static final String NAMESPACE = "http://tempuri.org/";
        private static final String URL = "http://trueedu.appilogics.in/WebService.asmx";
        private static final String METHOD_NAME = "getpackage_by_coursenane_sem";

        private String course;
        private String sem;
private  String pkgname;
        public SoapRequestAsyncTask(String course, String sem) {
            this.course = course;
            this.sem = sem;
            this.pkgname=pkgname;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String response = "";

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // Add parameters to the request
                PropertyInfo courseInfo = new PropertyInfo();
                courseInfo.setName("course");
                courseInfo.setValue(course);
                courseInfo.setType(String.class);
                request.addProperty(courseInfo);

                PropertyInfo semInfo = new PropertyInfo();
                semInfo.setName("sem");
                semInfo.setValue(sem);
                semInfo.setType(String.class);
                request.addProperty(semInfo);

                PropertyInfo pkgnameInfo = new PropertyInfo();
                pkgnameInfo.setName("pkgname");
                pkgnameInfo.setValue("Engineering");
                pkgnameInfo.setType(String.class);
                request.addProperty(pkgnameInfo);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);

                Object responseObj = envelope.getResponse();
                if (responseObj instanceof SoapPrimitive) {
                    SoapPrimitive soapPrimitive = (SoapPrimitive) responseObj;
                    response = soapPrimitive.toString();
                } else if (responseObj instanceof SoapObject) {
                    // Handle the specific structure of your response
                    SoapObject soapObject = (SoapObject) responseObj;


                    Object resultObj = soapObject.getProperty("getpackage_by_coursenane_semResult");
                    if (resultObj instanceof SoapPrimitive) {
                        SoapPrimitive resultPrimitive = (SoapPrimitive) resultObj;
                        response = resultPrimitive.toString();
                    } else {
                        response = resultObj.toString();
                    }
                }
                Log.d("res", response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("exception", e.getMessage());
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("responsed", result);

        }
    }
    protected void for_submit(String id ,String name ) {

        Log.d("Clicked PackID from function", id);
        Log.d("Clicked PackName function", name);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                Date startDate = dateFormat.parse("2023-09-01");
                Date endDate = dateFormat.parse("2023-09-30");
                    String formattedStartDate = dateFormat.format(startDate);
                    String formattedEndDate = dateFormat.format(endDate);
                SoapObject request = new SoapObject(NAMESPACE1, METHOD_NAME1);
                request.addProperty("email", "1@gmail.com");
                request.addProperty("bookid", "5");
                request.addProperty("strart_date", formattedStartDate);
                request.addProperty("end_date", formattedEndDate);
                request.addProperty("book_status", "true");
                request.addProperty("book_pkg", "18");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL1);

                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();




                    runOnUiThread(new Runnable() {


                        @Override
                        public void run() {
                            // Handle the response as needed

                            if(response!=null){

                                System.out.println(response);
                            }else{
                                System.out.println("success from voud");
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("exception from submit" , e.toString());
                }
            }
        }).start();



    }
}
