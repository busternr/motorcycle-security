package org.elsys.motorcycle_security.activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.elsys.motorcycle_security.Fragments.AddDevice;
import org.elsys.motorcycle_security.Fragments.AllDevices;
import org.elsys.motorcycle_security.Fragments.ChangePassword;
import org.elsys.motorcycle_security.Fragments.CurrentDevice;
import org.elsys.motorcycle_security.Fragments.HistoryForDifferentDays;
import org.elsys.motorcycle_security.R;
import org.elsys.motorcycle_security.http.Api;
import org.elsys.motorcycle_security.models.Device;
import org.elsys.motorcycle_security.models.DeviceConfiguration;
import org.elsys.motorcycle_security.models.Globals;
import org.elsys.motorcycle_security.models.GpsCordinates;
import org.elsys.motorcycle_security.services.LocationCheckerJob;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.job.JobInfo.BACKOFF_POLICY_LINEAR;


public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CurrentDevice.OnFragmentInteractionListener, OnMapReadyCallback {
    private boolean isParked;
    private JobScheduler jobScheduler;
    private TextView parkingStatusText;
    private TextView currentDeviceText;
    private int counter;
    private GoogleMap mMap;
    private Marker deviceInUseMarker;
    private Button notStolenButton;
    private int countOfRequests = 0;

    private String calculateDateForMenu(int day) {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date - 86400000 * day);
    }

    private String calculateDateForServer(int day) {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        return sdf.format(date - 86400000 * day);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        if(isAuthorized) {
            setGlobals();
            currentDeviceText.setText("Current device: " + Globals.deviceInUse);
            if(isParked) parkingStatusText.setText("Status: " + "Parked");
            if(!isParked) parkingStatusText.setText("Status: " + "NOT parked");
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            notStolenButton =  findViewById(R.id.NotStolenBtn);
            notStolenButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Api api = Api.RetrofitInstance.create();
                    DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                    deviceConfiguration.setDeviceId(Globals.deviceInUse);
                    deviceConfiguration.setStolen(false);
                    api.updateStolenStatus(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                        @Override
                        public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                        @Override
                        public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                    });
                    Intent myIntent = new Intent(v.getContext(),Main.class);
                    startActivity(myIntent);
                }
            });
            notStolenButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem nav_history_day_1 = menu.findItem(R.id.nav_history_day_1);
        MenuItem nav_history_day_2 = menu.findItem(R.id.nav_history_day_2);
        MenuItem nav_history_day_3 = menu.findItem(R.id.nav_history_day_3);
        View headerView = navigationView.getHeaderView(0);
        currentDeviceText = headerView.findViewById(R.id.current_device_text);
        parkingStatusText = headerView.findViewById(R.id.parking_status_text);
        jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            Toast toast = Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_LONG);
            toast.show();
        }
        final boolean isAuthorized = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isAuthorized", false);
        if (!isAuthorized) {
            Intent myIntent = new Intent(this,LoginRegister.class);
            startActivity(myIntent);
        }
        else if(isAuthorized) {
            setGlobals();
            currentDeviceText.setText("Current device: " + Globals.deviceInUse);
            if(isParked) parkingStatusText.setText("Status: " + "Parked");
            if(!isParked) parkingStatusText.setText("Status: " + "NOT parked");
            Long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-DD");
            nav_history_day_1.setTitle(calculateDateForMenu(0));
            nav_history_day_2.setTitle(calculateDateForMenu(1));
            nav_history_day_3.setTitle(calculateDateForMenu(2));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.park);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Api api = Api.RetrofitInstance.create();
                if(isParked == false) {
                    isParked = true;
                    scheduleJob();
                    api.getGPSCoordinates(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<GpsCordinates>() {
                        @Override
                        public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                            if (response.isSuccessful()) {
                                final GpsCordinates gpsCordinates = response.body();
                                Device device = new Device();
                                device.setDeviceId(Globals.deviceInUse);
                                device.setParkedX(gpsCordinates.getX());
                                device.setParkedY(gpsCordinates.getY());
                                api.updateParkedCordinates(Globals.authorization, device).enqueue(new Callback<Device>() {
                                    @Override
                                    public void onResponse(Call<Device> call, Response<Device> response) {}
                                    @Override
                                    public void onFailure(Call<Device> call, Throwable t) { }
                                });
                            }
                        }
                        @Override
                        public void onFailure(Call<GpsCordinates> call, Throwable t) {
                        }
                    });
                    parkingStatusText.setText("Status: " + "Parked");
                    Snackbar.make(view, "Vehicle is now parked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else if(isParked == true) {
                    isParked = false;
                    jobScheduler.cancelAll();
                    parkingStatusText.setText("Status: " + "NOT parked");
                    Snackbar.make(view, "Vehicle is now NOT parked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                deviceConfiguration.setDeviceId(Globals.deviceInUse);
                deviceConfiguration.setParked(isParked);
                api.updateParkingStatus(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                    @Override
                    public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                    @Override
                    public void onFailure(Call<DeviceConfiguration> call, Throwable t) { }
                });
            }
        });
    }

    final Handler handler = new Handler();
    Timer timer = new Timer(false);
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updatePos();
                }
            });
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final float zoomlevel = 18;
        int numberOfUserDevices = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getInt("Number of devices", 1);
        final Api api = Api.RetrofitInstance.create();
        for (counter = 0; counter <= numberOfUserDevices; counter++) {
            final String deviceId = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Device " + counter ,"");
            api.getGPSCoordinates(Globals.authorization, deviceId).enqueue(new Callback<GpsCordinates>() {
                @Override
                public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                    if (response.isSuccessful()) {
                        GpsCordinates gpsCordinates = response.body();
                        LatLng currLocation = new LatLng(gpsCordinates.getX(), gpsCordinates.getY());
                        if(deviceId.matches(Globals.deviceInUse)) deviceInUseMarker = mMap.addMarker(new MarkerOptions().position(currLocation).title(deviceId));
                        else mMap.addMarker(new MarkerOptions().position(currLocation).title(deviceId));
                        if(deviceId.equals(Globals.deviceInUse)) mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoomlevel));
                    }
                }
                @Override
                public void onFailure(Call<GpsCordinates> call, Throwable t) {
                }
            });
        }
        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                if (response.isSuccessful()) {
                    DeviceConfiguration deviceConfiguration = response.body();
                    Globals.isStolen = deviceConfiguration.isStolen();
                    if(deviceConfiguration.isStolen()) {
                        notStolenButton.setVisibility(View.VISIBLE);
                        timer.schedule(timerTask, 1000, 10000);
                    }
                }
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
    }
    void updatePos() {
        final float zoomlevel = 18;
        final Api api = Api.RetrofitInstance.create();
        api.getGPSCoordinates(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<GpsCordinates>() {
            @Override
            public void onResponse(Call<GpsCordinates> call, Response<GpsCordinates> response) {
                if (response.isSuccessful()) {
                    if(countOfRequests >= 10) {
                        countOfRequests = 0;
                        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                                if (response.isSuccessful()) {
                                    DeviceConfiguration deviceConfiguration = response.body();
                                    Globals.isStolen = deviceConfiguration.isStolen();
                                }
                            }
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
                            }
                        });
                    }
                    if(Globals.isStolen == false) {
                        DeviceConfiguration deviceConfiguration = new DeviceConfiguration();
                        deviceConfiguration.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration.setStolen(false);
                        api.updateStolenStatus(Globals.authorization, deviceConfiguration).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        DeviceConfiguration deviceConfiguration2 = new DeviceConfiguration();
                        deviceConfiguration2.setDeviceId(Globals.deviceInUse);
                        deviceConfiguration2.setTimeOut(150000);
                        api.updateTimeOut(Globals.authorization, deviceConfiguration2).enqueue(new Callback<DeviceConfiguration>() {
                            @Override
                            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {}
                            @Override
                            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {}
                        });
                        timer.cancel();
                    }
                    countOfRequests++;
                    GpsCordinates gpsCordinates = response.body();
                    LatLng currLocation = new LatLng(gpsCordinates.getX(), gpsCordinates.getY());
                    deviceInUseMarker.remove();
                    deviceInUseMarker = mMap.addMarker(new MarkerOptions().position(currLocation).title(Globals.deviceInUse + " speed:" + Double.toString(gpsCordinates.getSpeed()) + "km/h"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, zoomlevel));
                }
            }
            @Override
            public void onFailure(Call<GpsCordinates> call, Throwable t) {
            }
        });
    }

    private void scheduleJob() {
        final ComponentName name = new ComponentName(this, LocationCheckerJob.class);
        JobInfo jobInfo = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo= new JobInfo.Builder(1, name)
                    .setMinimumLatency(1000*5)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setBackoffCriteria(1000, BACKOFF_POLICY_LINEAR )
                    .build();
        }
        else {
            jobInfo= new JobInfo.Builder(1, name)
                    .setPeriodic(1000*5)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build();
        }
        jobScheduler.schedule(jobInfo);
    }

    private void setGlobals() {
        Globals.deviceInUse = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Current device in use", "");
        Globals.authorization = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("Authorization", "");
        Api api = Api.RetrofitInstance.create();
        api.getDeviceConfiguration(Globals.authorization, Globals.deviceInUse).enqueue(new Callback<DeviceConfiguration>() {
            @Override
            public void onResponse(Call<DeviceConfiguration> call, Response<DeviceConfiguration> response) {
                DeviceConfiguration deviceConfiguration = response.body();
                isParked = deviceConfiguration.isParked();
                if(isParked) scheduleJob();
                Globals.isStolen = deviceConfiguration.isStolen();
            }
            @Override
            public void onFailure(Call<DeviceConfiguration> call, Throwable t) {
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri){
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("HERE", "");
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_history_day_1) {
            fragment = new HistoryForDifferentDays();
            Bundle bundle=new Bundle();
            bundle.putString("day", calculateDateForServer(0));
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_history_day_2) {
            fragment = new HistoryForDifferentDays();
            Bundle bundle=new Bundle();
            bundle.putString("day", calculateDateForServer(1));
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_history_day_3) {
            fragment = new HistoryForDifferentDays();
            Bundle bundle=new Bundle();
            bundle.putString("day", calculateDateForServer(2));
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_current_device) {
            fragment = new CurrentDevice();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_all_devices) {
            fragment = new AllDevices();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_add_device) {
            fragment = new AddDevice();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_change_password) {
            fragment = new ChangePassword();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Main").commit();
        }
        if (id == R.id.nav_logout) {
          getSharedPreferences("PREFERENCE", 0).edit().clear().apply();
          Globals.deviceInUse = null;
          Globals.authorization = null;
          Globals.isStolen = false;
          Intent myIntent = new Intent(Main.this, Login.class);
          startActivity(myIntent);
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

//MENU BUTTON LISTENER IS NOT WORKING CURRENTLY MUST FIX !!!