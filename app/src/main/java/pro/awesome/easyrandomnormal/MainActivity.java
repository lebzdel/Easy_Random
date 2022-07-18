package pro.awesome.easyrandomnormal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import pro.awesome.easyrandomnormal.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {


    String[] data = {"Custom", "Coin", "Dice", "Zero - Ten", "One - Ten", "A hundred"};
    int[] froms = {-1, 0, 1, 0, 1, 1};
    int[] tos = {-1, 1, 6, 10, 10, 100};
    int count = 6, us_count = 0;

    int from = 0, to = 100, res;
    String FromStringChange = "0", ToStringChange = "100";

    SharedPreferences sPref;;
    final String data_saved = "data", from_saved = "from", to_saved = "to";

    private AdView mAdView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadArrs();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView RnValue = (TextView) findViewById(R.id.RandomDate);
        TextView FromValue = (TextView) findViewById(R.id.FromDate);
        TextView ToValue = (TextView) findViewById(R.id.ToDate);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        // выделяем элемент
        int i = 1;

        while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
        if ((i + 1 == count) & (from != froms[i] || tos[i] != to)) {
            spinner.setSelection(0);
        }
        else {
            spinner.setSelection(i);
        }
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                int ch = spinner.getSelectedItemPosition();
                if (ch != 0) {
                    from = froms[ch];
                    to = tos[ch];
                }

                FromValue.setText(String.valueOf(from));
                ToValue.setText(String.valueOf(to));

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        Button btn = (Button) findViewById(R.id.RandButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = Integer.parseInt(FromValue.getText().toString());
                to = Integer.parseInt(ToValue.getText().toString());
                int i = 1;
                Handler handler = new Handler();
                while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
                if ((i + 1 == count) & (from != froms[i] || tos[i] != to)) {
                    spinner.setSelection(0);
                }
                else {
                    spinner.setSelection(i);
                }

                if (to >= from) {
                    if (to >= 0 & from >= 0) {
                        int delayms = 10;
                        for (int j = 0; j <= 18; j++) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    res = from + (int) (Math.random() * (to - from + 1));
                                    RnValue.setText(String.valueOf(res));
                                }
                            }, delayms);
                            delayms += (int) (j * j);
                        }

                        res = from + (int) (Math.random() * (to - from + 1));
                        RnValue.setText(String.valueOf(res));
                    }
                    else {
                        Toast.makeText(getBaseContext(), "\"From\" and \"To\" must be non-negative", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "\"To\" must be greater than \"From\"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FromValue.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                FromValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b) {
                            from = Integer.parseInt(FromValue.getText().toString());
                            to = Integer.parseInt(ToValue.getText().toString());

                            int i = 1;

                            while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
                            if ((i + 1 == count) & (from != froms[i] || tos[i] != to)) {
                                spinner.setSelection(0);
                            }
                            else {
                                spinner.setSelection(i);
                            }
                        }
                    }
                });
                return false;
            }
        });

        ToValue.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ToValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b) {
                            from = Integer.parseInt(FromValue.getText().toString());
                            to = Integer.parseInt(ToValue.getText().toString());

                            int i = 1;

                            while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
                            if ((i + 1 == count) & (from != froms[i] || tos[i] != to)) {
                                spinner.setSelection(0);
                            }
                            else {
                                spinner.setSelection(i);
                            }
                        }
                    }
                });
                return false;
            }
        });

    Button addbutt = (Button) findViewById(R.id.addbutton);
    addbutt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            from = Integer.parseInt(FromValue.getText().toString());
            to = Integer.parseInt(ToValue.getText().toString());

            int i = 1;

            while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
            if ((i + 1 == count) & (from != froms[i] || tos[i] != to)) {
                data = Arrays.copyOf(data, data.length + 1);
                data[count] = "User's type " + String.valueOf(count - 5);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, data);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                spinner.setAdapter(adapter);
                spinner.setSelection(count);
                froms = Arrays.copyOf(froms, froms.length + 1);
                froms[count] = from;
                tos = Arrays.copyOf(tos, tos.length + 1);
                tos[count] = to;
                count++;
                saveArrs();
            }
            else {
                Toast.makeText(getBaseContext(), "Type exists", Toast.LENGTH_SHORT).show();
            }
        }
    });


        Button delbutt = (Button) findViewById(R.id.delbutton);
        delbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from = Integer.parseInt(FromValue.getText().toString());
                to = Integer.parseInt(ToValue.getText().toString());

                int i = 1;

                while ((i + 1 != count) & (froms[i] != from || tos[i] != to)) { i++; }
                if (i >= 1 & i <= 5) {
                    Toast.makeText(getBaseContext(), "You can't delete default type", Toast.LENGTH_SHORT).show();
                }
                else if (i > 5 & (i + 1) <= count) {
                    String[] buf = {};
                    int[] buf_f = {}, buf_t = {};
                    int user_count = 1;
                    for (int j = 0; j < data.length; j++) {
                        if (j <= 5) {
                            if (i != j) {
                                buf = Arrays.copyOf(buf, buf.length + 1);
                                buf_f = Arrays.copyOf(buf_f, buf_f.length + 1);
                                buf_t = Arrays.copyOf(buf_t, buf_t.length + 1);
                                if (j < i) {
                                    buf[j] = data[j];
                                    buf_f[j] = froms[j];
                                    buf_t[j] = tos[j];
                                } else {
                                    buf[j] = data[j + 1];
                                    buf_f[j] = froms[j + 1];
                                    buf_t[j] = tos[j + 1];
                                }
                            }
                        }
                        else {
                            if (i != j) {
                                buf = Arrays.copyOf(buf, buf.length + 1);
                                buf_f = Arrays.copyOf(buf_f, buf_f.length + 1);
                                buf_t = Arrays.copyOf(buf_t, buf_t.length + 1);
                                if (j < i) {
                                    buf[j] = "User's type " + user_count;
                                    user_count++;
                                    buf_f[j] = froms[j];
                                    buf_t[j] = tos[j];
                                } else {
                                    buf[j - 1] = "User's type " + user_count;
                                    user_count++;
                                    buf_f[j - 1] = froms[j];
                                    buf_t[j - 1] = tos[j];
                                }
                            }
                        }
                    }
                    user_count = 0;
                    count--;
                    data = Arrays.copyOf(buf, buf.length);
                    froms = Arrays.copyOf(buf_f, buf_f.length);
                    tos = Arrays.copyOf(buf_t, buf_t.length);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_item, data);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(i - 1);
                }
                else {
                    Toast.makeText(getBaseContext(), "Choose a type", Toast.LENGTH_SHORT).show();
                }
                saveArrs();
            }
        });

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }

    @Override
    protected void onDestroy() {
        saveArrs();
    }


    private void saveArrs() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        for (int i = 1; i < data.length; i++) {
            ed.putString("Data" + i, data[i]);
            ed.putInt("Froms" + i, froms[i]);
            ed.putInt("Tos" + i, tos[i]);
        }
        ed.putInt("Counter", count);
        ed.commit();
    }

    private void loadArrs() {
        sPref = getPreferences(MODE_PRIVATE);
        count = sPref.getInt("Counter", count);
        froms = Arrays.copyOf(froms, count);
        tos = Arrays.copyOf(tos, count);
        data = Arrays.copyOf(data, count);
        froms[0] = -1; tos[0] = -1; data[0] = "Custom";
        for (int i = 1; i < count; i++) {
            data[i] = sPref.getString("Data" + i, data[i]);
            froms[i] = sPref.getInt("Froms" + i, froms[i]);
            tos[i] = sPref.getInt("Tos" + i, tos[i]);
        }
    }


}


