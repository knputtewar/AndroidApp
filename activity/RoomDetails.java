package com.example.hotelandroid.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelandroid.R;
import com.example.hotelandroid.model.Room;
import com.example.hotelandroid.utils.Constants;
import com.example.hotelandroid.utils.Utils;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomDetails extends AppCompatActivity {


    ProgressDialog dialog;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textRoomNo)
    TextView textRoomNo;
    @BindView(R.id.textRoomType)
    TextView textRoomType;
    @BindView(R.id.textRoomPrice)
    TextView textRoomPrice;
    @BindView(R.id.textRoomStatus)
    TextView textRoomStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Room Details");
        actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setTitle("please loading wait");
        dialog.setMessage("Application is loading Product.....");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private  void showDetails(Room room)
    {
        textRoomNo.setText(""+room.getRoomno());
        textRoomType.setText(room.getRoomtype());
        textRoomPrice.setText(""+room.getPrice());
        textRoomStatus.setText(room.getStatus());
        //textRoomRating.setText(room.getRoomRating());

        //textProductType.setText(product.getProductType());

        String url = Utils.getUrl(room.getImagename());
        Ion.with(this)
                .load(url)
                .withBitmap()
                .intoImageView(imageView);
        dialog.dismiss();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getProductDetails();
    }

    private void getProductDetails() {
        int roomno = getIntent().getIntExtra("roomno", 0);
        String url = Utils.getUrl(Constants.ROUTE_Room + "/" + roomno);

        dialog.show();

        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("Details", result.toString());
                        if (result.get("status").getAsString().equals("success")) {

                            JsonObject object = result.get("data").getAsJsonObject();

                            Room room = new Room();
                            room.setRoomno(object.get("roomno").getAsInt());
                            room.setRoomtype(object.get("roomtype").getAsString());
                            room.setPrice(object.get("price").getAsBigDecimal());
                            room.setStatus(object.get("status").getAsString());
                            room.setImagename(object.get("imagename").getAsString());

                            showDetails(room);
                        }
                    }

                });

    }
}
