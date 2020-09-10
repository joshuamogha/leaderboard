package com.gwijiapp.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gwijiapp.leaderboard.configs.AppConfig;

import java.util.HashMap;
import java.util.Map;

public class SubmitProjectActivity extends AppCompatActivity {
    private EditText etEmailAddress, etFirstName, etLastName, etLinkToGithubProject;
    private String emailAddress, firstName, lastName, linkToGithubProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        etEmailAddress = findViewById(R.id.et_email_address);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etLinkToGithubProject = findViewById(R.id.et_github_link);


        firstName = etFirstName.getText().toString().trim();
        lastName = etLastName.getText().toString().trim();
        emailAddress = etEmailAddress.getText().toString().trim();
        linkToGithubProject = etLinkToGithubProject.getText().toString().trim();

        Button submitProjectBtn = findViewById(R.id.submit_project_btn);
        submitProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.confirm_dialog, null);
        Button yesBtn = view.findViewById(R.id.post_project_btn);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postProjectData(firstName, lastName, emailAddress, linkToGithubProject);

                alertDialog.dismiss();
            }
        });


    }

    private void errorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.error_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void successDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.success_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return true;
        }
    }


    private void postProjectData(final String firstName, final String lastName, final String emailAddress, final String linkToGithubProject) {

//
//        if (firstName.isEmpty()) {
//            etFirstName.setError("First name is required");
//            return;
//        } else if (lastName.isEmpty()) {
//            etLastName.setError("Last name is required");
//            return;
//
//
//        } else if (emailAddress.isEmpty()) {
//            etLastName.setError("Email Address is required");
//            return;
//
//
//        } else if (linkToGithubProject.isEmpty()) {
//            etLastName.setError("Github Link Project is required");
//            return;
//
//
//        }

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.POST_PROJECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                successDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                successDialog();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("entry.1824927963", emailAddress);
                params.put("entry.1877115667", firstName);
                params.put("entry.2006916086", lastName);
                params.put("entry.284483984", linkToGithubProject);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}