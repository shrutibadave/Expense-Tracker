package com.example.myapplication.ui.gallery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private DatePickerDialog dp, dp1;
    private Button dbutton;
    private Button bd;
    private Button btn;
    private TextView text;
    private EditText e1,e2;


    String check,sm;

    // ArrayList<String> item=new ArrayList<String>();
    List item;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        initDatePicker();
        initDatePicker1();
        dbutton = root.findViewById(R.id.button3);
        bd = root.findViewById(R.id.button4);
        text=root.findViewById(R.id.text1);
        e1=root.findViewById(R.id.editTextNumber8);
        e2=root.findViewById(R.id.editTextNumber9);

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp1.show();
            }
        });
        btn = root.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                checkdays();
                checkdb();
                System.out.println(item);


            }
        });


        dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp.show();
            }
        });



        return root; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkdays() {

        item=new ArrayList();
        String to = dbutton.getText().toString();
        String from = bd.getText().toString();
        String w[] = to.split(" ");
        String w1[] = from.split(" ");
        //for month
        int num = getNum(w[0]);
        int num1 = getNum(w1[0]);
        //for  day

        int da = Integer.parseInt(w[1]);
        int da1 = Integer.parseInt(w1[1]);

        //for year

        int year = Integer.parseInt(w[2]);
        int year1 = Integer.parseInt(w1[2]);
        while ((da != da1) || (year != year1) || (num != num1)) {


            YearMonth yearMonthObject = YearMonth.of((Integer.parseInt(w[2])), num);
            int daysInMonth = yearMonthObject.lengthOfMonth();


            if (da <= daysInMonth) {

                sm=getMonthFormat(num);
                check=sm+" "+da+" "+year;
                da++;

            } else {
                if (num < 12) {
                    sm=getMonthFormat(num);
                    num++;
                    da = 1;
                } else {
                    num = 1;
                    sm=getMonthFormat(num);
                    year++;
                    da = 1;
                }
            }
            item.add(check);
        }




    }

    private int getNum(String s) {
        if (s.equals("JAN"))
            return 1;
        if (s.equals("FEB"))
            return 2;
        if (s.equals("MARCH"))
            return 3;
        if (s.equals("APRIL"))
            return 4;
        if (s.equals("MAY"))
            return 5;
        if (s.equals("JUNE"))
            return 6;
        if (s.equals("JULY"))
            return 7;
        if (s.equals("AUG"))
            return 8;
        if (s.equals("SEPT"))
            return 9;
        if (s.equals("OCT"))
            return 10;
        if (s.equals("NOV"))
            return 11;
        if (s.equals("DEC"))
            return 12;
        return 0;


    }

    public void initDatePicker1() {
        DatePickerDialog.OnDateSetListener dateListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString1(dayOfMonth, month, year);
                //  System.out.println(date);

                bd.setText(date);


            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        dp1 = new DatePickerDialog(getContext(), style, dateListner, year, month, day);


    }

    private String makeDateString1(int dayOfMonth, int month, int year) {
        return getMonthFormat1(month) + " " + dayOfMonth + " " + year;

    }

    private String getMonthFormat1(int month) {
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
        return getMonthFormat1(month) + " " + dayOfMonth + " " + year;

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



    public void checkdb()
    {
        DatabaseReference refernce1 = FirebaseDatabase.getInstance().getReference().child("message");

        refernce1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int amt=0;
                for( DataSnapshot snap:snapshot.getChildren())
                {
                    String f1=snap.getValue().toString();
                    String  te=e2.getText().toString();
                    String  te1=e1.getText().toString();
                    float part=Float.parseFloat(te)/Float.parseFloat(te1);


                    //    System.out.println(f1);
                    String []s1=f1.split(":");
                    if(item.contains(s1[0]))
                    {
                        amt=amt+Integer.parseInt(s1[1]);

                        System.out.println("Ow bhai finally mil gaya"+amt);

                        text.setText("Your Share is "+Math.abs(amt-part));
                    }


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error");

            }
        });

    }
}