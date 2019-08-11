package com.example.hotelandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelandroid.R;
import com.example.hotelandroid.model.Room;
import com.example.hotelandroid.utils.Utils;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>  {



    public interface Listener{
        public void onClick (int index);
    }
    private final Context context;
    private final ArrayList<Room> rooms;
    private final Listener listener ;

    public RoomAdapter(Context context, ArrayList<Room> rooms,Listener listener) {
        this.context = context;
        this.rooms = rooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_room,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Room room = rooms.get(i);
        viewHolder.textRoomno.setText(""+room.getRoomno());
        viewHolder.textRoomType.setText(room.getRoomtype());
        viewHolder.textRoomPrice.setText(""+room.getPrice());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(i);
            }
        });

        String imageUrl = Utils.getUrl(room.getImagename());
        Ion.with(context)
                .load(imageUrl)
                .withBitmap()
                .intoImageView(viewHolder.imageView);
        Log.d("imagename" , imageUrl );


    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textRoomno, textRoomType,textRoomPrice,textRoomStatus;
        ImageView imageView;

        View view;
        ViewHolder(View view)
        {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.imageView);
            textRoomno = view.findViewById(R.id.textRoomNo);
            textRoomType = view.findViewById(R.id.textRoomType);
            textRoomPrice = view.findViewById(R.id.textRoomPrice);
            textRoomStatus = view.findViewById(R.id.textRoomStatus);
        }

    }

}
