package com.example.app_14;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends Activity {
    private DBHelper mydb;
    TextView name;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;
    int id_To_Update = 0;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_contact);
        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextStreet);
        street = (TextView) findViewById(R.id.editTextEmail);
        place = (TextView) findViewById(R.id.editTextCity);
        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);
                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);
                phone.setText((CharSequence) phon);
                phone.setFocusable(false);
                phone.setClickable(false);
                email.setText((CharSequence) emai);
                email.setFocusable(false);
                email.setClickable(false);
                street.setText((CharSequence) stree);
                street.setFocusable(false);
                street.setClickable(false);
                place.setText((CharSequence) plac);
                place.setFocusable(false);
                place.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_contact_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);
                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);
                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);
                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);
                return true;
            case R.id.Delete_Contact:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Bạn có thực sự muốn xóa địa chỉ đang chọn?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Xóa thành công",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        com.example.app_14.MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Xác nhận xóa");
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateContact(id_To_Update, name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),
                            com.example.app_14.MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Cập nhật KHÔNG thành công",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertContact(name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Thêm mới thành công",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm mới KHÔNG thành công",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),
                        com.example.app_14.MainActivity.class);
                startActivity(intent);
            }
        }
    }
}