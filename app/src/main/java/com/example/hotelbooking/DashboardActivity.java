package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Spinner spinnerPlace, spinnerRoomType;
    TextView checkIn, checkOut;
    Button btnCalculate;
    EditText etAdults, etChildrens, etRooms;
    TextView tvLocation, tvPrice, tvVAT, tvServiceCharge, tvTotalPrice;

    private String[] place = {"Kathmandu", "Pokhara", "Chitwan"};
    private String[] roomType = {"Deluxe", "A/C", "Platinum"};
    private boolean date1 = false;
    private boolean date2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );

        spinnerPlace = findViewById( R.id.spinnerPlace );
        spinnerRoomType = findViewById( R.id.spinnerRoomType );
        checkIn = findViewById( R.id.tvCheckIn );
        checkOut = findViewById( R.id.tvCheckOut );
        etAdults = findViewById( R.id.etAdults );
        etChildrens = findViewById( R.id.etChildren );
        etRooms = findViewById( R.id.etRoom );
        tvLocation = findViewById( R.id.tvLocation );
        tvPrice = findViewById( R.id.tvPrice );
        tvVAT = findViewById( R.id.tvVAT );
        tvServiceCharge = findViewById( R.id.tvServiceCharge );
        tvTotalPrice = findViewById( R.id.tvTotalPrice );
        btnCalculate = findViewById( R.id.btnCalculate );

        ArrayAdapter placeAdapter = new ArrayAdapter(
                this,
                R.layout.spinner,
                place
        );

        ArrayAdapter roomAdapter = new ArrayAdapter(
                this,
                R.layout.spinner,
                roomType
        );
        spinnerPlace.setAdapter( placeAdapter );
        spinnerRoomType.setAdapter( roomAdapter );

        checkIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
                date1 = true;
            }
        } );

        checkOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();
                date2 = true;
            }
        } );

        btnCalculate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty( checkIn.getText().toString() )) {
                    checkIn.setError( "Please select checked in date!" );
                }
                if (TextUtils.isEmpty( checkOut.getText().toString() )) {
                    checkIn.setError( "Please select check out date!" );
                }

                int RoomPrice = 0;
                float price, totalPrice, VAT, serviceCharge;

                int totalRooms = Integer.parseInt( etRooms.getText().toString() );

                String roomType = spinnerRoomType.getSelectedItem().toString();
                String place = spinnerPlace.getSelectedItem().toString();
                String checkInDate = checkIn.getText().toString();
                String checkOutDate = checkOut.getText().toString();

                SimpleDateFormat checkDate = new SimpleDateFormat( "dd/MM/yyyy" );

                try {
                    Date checkin = checkDate.parse( checkInDate );
                    Date checkout = checkDate.parse( checkOutDate );

                    long difference = checkout.getTime() - checkin.getTime();
                    long differentDays = difference / (24 * 60 * 60 * 1000);
                    int days = (int) differentDays;

                    if (days < 0) {
                        checkOut.setText( null );
                        checkOut.setError( "Select date after check-in date" );
                    } else {

                        if (roomType == "Deluxe") {
                            RoomPrice = 2000;
                        } else if (roomType == "A/C") {
                            RoomPrice = 3000;
                        } else if (roomType == "Platinum") {
                            RoomPrice = 4000;
                        }

                        price = RoomPrice * days * totalRooms;
                        VAT = (13 * price) / 100;
                        serviceCharge = (10 * price) / 100;
                        totalPrice = price + VAT + serviceCharge;

                        tvLocation.setText( "Location: " + place );
                        tvPrice.setText( "Price: " + price );
                        tvVAT.setText( "VAT: " + VAT );
                        tvServiceCharge.setText( "Service Charge: " + serviceCharge );
                        tvTotalPrice.setText( "Total Price: " + totalPrice );
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        } );

    }

    private void loadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get( Calendar.YEAR );
        int month = c.get( Calendar.MONTH );
        int day = c.get( Calendar.DAY_OF_MONTH );

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, year, month, day
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        if (date1 == true) {
            checkIn.setText( date );
            date1 = false;
        } else if (date2 == true) {
            checkOut.setText( date );
            date1 = false;
        }
    }
}

