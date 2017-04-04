package com.example.zender.zenderswim1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;

public class MainActivity extends AppCompatActivity {
    //@Override
    static boolean isModeMetric = true;
    EditText field_mass;
    EditText field_height;
    TextView out;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button confirm = (Button)findViewById(R.id.button);
        confirm.setOnClickListener(okButtonListener);
        Switch unitSwitch = (Switch)findViewById(R.id.unitSwitch);
        unitSwitch.setOnCheckedChangeListener(switchListener);
        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar)findViewById(R.id.tb_more);
        setSupportActionBar(tb);
        field_mass = (EditText)findViewById(R.id.et_mass);
        field_height = (EditText)findViewById(R.id.et_height);
        out = (TextView)findViewById(R.id.tv_result);
        if (savedInstanceState != null){
            field_mass.setText(savedInstanceState.getString("last_mass"));
            field_height.setText(savedInstanceState.getString("last_height"));
            String last_bmi = savedInstanceState.getString("last_bmi");
            printResult(last_bmi);
        }
    }

    private float getFloatFromField(EditText field){
        float toGet = 0;
        try {
            toGet = Float.valueOf(field.getText().toString());
        }
        catch (NumberFormatException e){
            //Toast.makeText(getApplicationContext(), (CharSequence)getString(R.string.no_input_exc),Toast.LENGTH_SHORT ).show();
        }
        return toGet;
    }

    private View.OnClickListener okButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            float mass = getFloatFromField(field_mass);
            float height = getFloatFromField(field_height);
            ICountBMI count = isModeMetric ? new CountBMIForKGM() : new CountBMIForImp();
            float bmi = 0;
            try {
                bmi = count.countBMI(mass, height);
            }
            catch (IllegalArgumentException e){
                Toast.makeText(getApplicationContext(), (CharSequence)e.getMessage(),Toast.LENGTH_SHORT ).show();
            }
            DecimalFormat df = new DecimalFormat("#0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            if(count.isValidMass(mass) && count.isValidHeight(height)) {
                printResult(df.format(bmi));
                SharedPreferences toSave = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = toSave.edit();
                editor.putString(getString(R.string.last_mass), field_mass.getText().toString());
                editor.putString(getString(R.string.last_height), field_height.getText().toString());
                editor.putString(getString(R.string.last_bmi), out.getText().toString());
                editor.commit();
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isModeMetric = !isChecked;
            Switch unitSwitch = (Switch)findViewById(R.id.unitSwitch);
            unitSwitch.setText( isModeMetric ? R.string.unit_switch_metr : R.string.unit_switch_imp);
            TextView unitHeight = (TextView)findViewById(R.id.unit_height);
            unitHeight.setText( isModeMetric ? R.string.unit_height_metr : R.string.unit_height_imp);
            TextView unitMass = (TextView)findViewById(R.id.unit_mass);
            unitMass.setText( isModeMetric ? R.string.unit_mass_metr : R.string.unit_mass_imp);
            EditText et_mass = (EditText)findViewById(R.id.et_mass);
            et_mass.setText("");
            EditText et_height = (EditText)findViewById(R.id.et_height);
            et_height.setText("");
            out.setText("");
        }
    };
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString(getString(R.string.last_mass), field_mass.getText().toString());
        outState.putString(getString(R.string.last_height), field_height.getText().toString());
        outState.putString(getString(R.string.last_bmi), out.getText().toString());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                //STUFF
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.menu_load:
                SharedPreferences toLoad = getPreferences(Context.MODE_PRIVATE);
                field_height.setText(toLoad.getString(getString(R.string.last_height), ""));
                field_mass.setText(toLoad.getString(getString(R.string.last_mass), ""));
                String loaded = toLoad.getString(getString(R.string.last_bmi), "");
                printResult(loaded);
                return true;
            case R.id.menu_share:
                String bmi = out.getText().toString();
                if(bmi != "") {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.share + out.getText().toString() + R.string.exc_mark);
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nothing to share!",Toast.LENGTH_SHORT ).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void printResult(String value) {
        out.setText(value);
        if (value != "") {
            value = value.replace(',', '.');
            float bmi = Float.valueOf(value);
            if (bmi == 0) out.setTextColor(Color.BLACK);
            else if (bmi > 20 && bmi <= 25) out.setTextColor(Color.rgb(0, 180, 0));
            else if (bmi > 16 && bmi <= 30) out.setTextColor(Color.rgb(180, 180, 0));
            else out.setTextColor(Color.rgb(180, 0, 0));
        }
    }
}
