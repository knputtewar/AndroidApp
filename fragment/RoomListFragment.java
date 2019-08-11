package com.example.hotelandroid.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hotelandroid.R;
import com.example.hotelandroid.activity.RoomDetails;
import com.example.hotelandroid.adapter.RoomAdapter;
import com.example.hotelandroid.model.Room;
import com.example.hotelandroid.utils.Constants;
import com.example.hotelandroid.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class RoomListFragment extends Fragment implements RoomAdapter.Listener {

    ProgressDialog dialog;
    RecyclerView recyclerView;

    ArrayList<Room> rooms = new ArrayList<>();
    RoomAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("please loading wait");
        dialog.setMessage("Application is loading Product.....");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list,null);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        adapter = new RoomAdapter(getActivity(),rooms,this);
        recyclerView.setAdapter(adapter);
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        getRooms();
    }

    private void getRooms(){
        final String url= Utils.getUrl(Constants.ROUTE_Room);
        dialog.show();
        Ion.with(getActivity())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(e != null)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error while loading the services", Toast.LENGTH_SHORT).show();

                        }else {

                            Log.d("response", result.toString());
                            if (result.get("status").getAsString().equals("success")) {
                                JsonArray array = result.get("data").getAsJsonArray();
                                for (int index = 0; index < array.size(); index++) {
                                    Log.d("checking", result.toString());
                                    JsonObject object = array.get(index).getAsJsonObject();

                                    Room room = new Room();
                                    room.setRoomno(object.get("roomno").getAsInt());
                                    room.setRoomtype(object.get("roomtype").getAsString());
                                    room.setPrice(object.get("price").getAsBigDecimal());
                                    room.setStatus(object.get("status").getAsString());
                                    room.setImagename(object.get("imagename").getAsString());
                                    //room.setProductRating(object.get("productRating").getAsFloat());
                                    rooms.add(room);
                                }
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), "Error while loading the services", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                });

    }

    @Override
    public void onClick(int index) {

        Room room = rooms.get(index);
        Intent intent = new Intent(getActivity(), RoomDetails.class);
        intent.putExtra("roomno",room.getRoomno());
        startActivity(intent);

    }


}
