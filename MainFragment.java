package com.example.myapplication.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainFragment extends Fragment {


    private MainViewModel galleryViewModel;
    ListView ls;
    String p;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main, container, false);
      //  final TextView textView = root.findViewById(R.id.textView);
       // TextView text=root.findViewById(R.id.textView21);
        ls=root.findViewById(R.id.list);
        ArrayList<String> item=new ArrayList<>();
        DatabaseReference refernce= FirebaseDatabase.getInstance().getReference().child("message");

        refernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot snap:snapshot.getChildren())
                {
                  String s=snap.getValue().toString();
                  System.out.println("MAin"+s);
                String[]  s1=s.split(":");
             //  System.out.println(s1[1]);
                    //text.setText("on"+" "+s1[0]);*/
                    p="Your Expense on "+s1[0]+":"+s1[1];
                   item.add(p);
                 //  System.out.println(item.get(0));

                }



                System.out.println(item.size());
                System.out.println("HEllo ji");
                ArrayAdapter<String> arr=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,item);
                ls.setAdapter(arr);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return root;
    }
}
