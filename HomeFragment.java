package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DatePickerDialog dp;
    private Button dbutton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initDatePicker();
        final TextView textView = root.findViewById(R.id.text_home);
        dbutton = root.findViewById(R.id.button3);
        dbutton.setText(getToday());


        Button b = root.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = root.findViewById(R.id.editTextNumber);
                String s = edit.getText().toString();
                //Format formatter = new SimpleDateFormat("dd-MM-yy");
               // String s1 = formatter.format(new Date());
                String s1=dbutton.getText().toString();

               // System.out.println(s1);
                String p = s1 + ":" + s;
               // System.out.println(p);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message").push();
                myRef.setValue(p);
                Toast.makeText(getContext(), "Expense Added Succeefully", Toast.LENGTH_SHORT).show();




            }
        });
        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp.show();
            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private String getToday() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //System.out.println(makeDateString(day, month, year));
        return (makeDateString(day, month, year));


    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
              //  System.out.println(date);
                dbutton.setText(date);


            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        dp = new DatePickerDialog(getContext(), style, dateListner, year, month, day);


    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;

    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MARCH";
        if (month == 4)
            return "APRIL";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JULY";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEPT";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";

    }

}




/*

 EditText edit=root.findViewById(R.id.editTextNumber);
                String s=edit.getText().toString();



                Format formatter=new SimpleDateFormat("dd-MM-yy");
                String s1=formatter.format(new Date());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");//.push();

                 msg=s1+":"+s;

                myRef.setValue(msg);
                System.out.println(s1);
 */