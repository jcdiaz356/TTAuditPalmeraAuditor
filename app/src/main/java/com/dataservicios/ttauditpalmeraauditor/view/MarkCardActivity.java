package com.dataservicios.ttauditpalmeraauditor.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dataservicios.ttauditpalmeraauditor.R;
import com.dataservicios.ttauditpalmeraauditor.db.DatabaseManager;
import com.dataservicios.ttauditpalmeraauditor.model.AssistControl;
import com.dataservicios.ttauditpalmeraauditor.model.AuditRoadStore;
import com.dataservicios.ttauditpalmeraauditor.model.Poll;
import com.dataservicios.ttauditpalmeraauditor.model.PollDetail;
import com.dataservicios.ttauditpalmeraauditor.model.PollOption;
import com.dataservicios.ttauditpalmeraauditor.model.Route;
import com.dataservicios.ttauditpalmeraauditor.model.Store;
import com.dataservicios.ttauditpalmeraauditor.model.User;
import com.dataservicios.ttauditpalmeraauditor.repo.AssistControlRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.AuditRoadStoreRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.CompanyRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.PollOptionRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.PollRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.RouteRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.StoreRepo;
import com.dataservicios.ttauditpalmeraauditor.repo.UserRepo;
import com.dataservicios.ttauditpalmeraauditor.util.GPSTracker;
import com.dataservicios.ttauditpalmeraauditor.util.SessionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;

public class MarkCardActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String     LOG_TAG = MarkCardActivity.class.getSimpleName();
    private SessionManager          session;
    private GoogleMap               mMapInPut,mMapOutPut;
    private Activity                activity =  this;
    private int                     user_id;
    private int                     company_id;
    private User                    user;
    private AssistControl           assistControl;
    private UserRepo                userRepo ;
    private AssistControlRepo       assistControlRepo;
    private GPSTracker              gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_card);

        DatabaseManager.init(this);

        gpsTracker = new GPSTracker(activity);
        if(!gpsTracker.canGetLocation()){
            gpsTracker.showSettingsAlert();
        }


        session = new SessionManager(activity);
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;

        userRepo           = new UserRepo(activity);
        assistControlRepo  = new AssistControlRepo(activity);

        SupportMapFragment mapFragmentOutPut = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapOutPut);
        mapFragmentOutPut.getMapAsync(this);

        SupportMapFragment mapFragmentInPut = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInput);
        mapFragmentInPut.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapInPut = googleMap;
        mMapOutPut = googleMap;
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMapInPut.setMyLocationEnabled(true);
        mMapInPut.getUiSettings().setZoomControlsEnabled(true);
        mMapInPut.getUiSettings().setCompassEnabled(true);
        mMapInPut.getCameraPosition();

        mMapOutPut.setMyLocationEnabled(true);
        mMapOutPut.getUiSettings().setZoomControlsEnabled(true);
        mMapOutPut.getUiSettings().setCompassEnabled(true);
        mMapOutPut.getCameraPosition();
        //new StoreAuditActivity.loadMarkerPointMap().execute();
    }
}
