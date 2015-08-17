package com.js.applauncherdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.slim.slimlauncher.util.AppHelper;
import com.slim.slimlauncher.util.ShortcutPickHelper;

import java.net.URISyntaxException;

public class MainActivity extends ActionBarActivity {
    private ShortcutPickHelper mPicker;
    private String mUriToLaunch;
    private String mName;
    private TextView mLaunchAppTV;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_CANCELED
                && resultCode != Activity.RESULT_CANCELED) {
            mPicker.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            mPicker = new ShortcutPickHelper(this, new ShortcutPickHelper.OnPickListener() {
                @Override
                public void shortcutPicked(String uri, String friendlyName, boolean isApplication) {
                    if (uri == null) {
                        return;
                    }
                    mName = AppHelper.getFriendlyNameForUri(MainActivity.this, MainActivity.this.getPackageManager(), uri);
                    mUriToLaunch = uri;
                    mLaunchAppTV.setText(mName + ":  " + mUriToLaunch);
                    Toast.makeText(MainActivity.this, "selected: "+mName, Toast.LENGTH_LONG).show();
                }
            });
        mLaunchAppTV = (TextView) findViewById(R.id.app_to_launch);
    }


    public void onClickSelectApp(View v) {
        Toast.makeText(this, "Select App", Toast.LENGTH_LONG).show();
        mPicker.pickShortcut(null, null, 0); // TODO fix params
    }

    public void onClickLaunchApp(View v) throws URISyntaxException {
        // TODO handle exception
        if (mUriToLaunch == null) {
            Toast.makeText(this, "Please choose the app to launch", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = Intent.parseUri(mUriToLaunch, Intent.URI_ANDROID_APP_SCHEME);
            startActivity(intent);
            Toast.makeText(this, "Launching " + mName, Toast.LENGTH_LONG).show();
        }
    }
}