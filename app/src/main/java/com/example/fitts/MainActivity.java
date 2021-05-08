package com.example.fitts;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private static final int PERMISSION_REQUEST_CODE = 100 ;
    private RelativeLayout rlLayout;
    private Button btnStart;
    private Button btnEnd;
    private Spinner mySpinner;
    ArrayList<Integer> Levels = new ArrayList<Integer>();
    private int intCurrentLevel = 0;
    int intSampleCount = 30;
    int eachdistanceCount =10;
    int intDistance240Count =0;
    int intDistance440Count=0;
    int intDistance640Count=0;
    int intRows =0;
    int intColumns=0;
    int intHeight=0;
    int intWidth =0;
    int intend =0;
    private long startTime;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    int counter =-1;
    long previousTime;
    long temp ;
    long newTime;
    int distanceInPixel =0;
    String Mode = "";
    String filename ="";
    int intTargetX;
    int intTargetY;
    boolean ThumbComplete=false;
    boolean IndexComplete=false;
    String strCurrentTarget="";
    String insertData ="";
    boolean pauseCounter=false;
    List<String> categories = new ArrayList<String>();


        @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Spinner element
         mySpinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        mySpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements

        categories.add("Select");
        categories.add("Thumb");
        categories.add("Index");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        Levels.add(100);
        Levels.add(90);
        Levels.add(120);
        String nowDate = formatter.format(Calendar.getInstance().getTime());
        //Create the filename
        filename = "Fitts" + nowDate+".csv";
        //Header of the Row
        insertData = "Mode,Width,Amplitude,Trial,startTime,stopTime,Counter\n";
        btnEnd=findViewById(R.id.btnEnd);
        SetupLevel();
        btnStart.setOnClickListener(new View.OnClickListener() {
            //Start Button
            public void onClick(View v) {

                final GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setColor(Color.GREEN);

                btnStart.setBackground(gradientDrawable);

                startTime = Calendar.getInstance().getTimeInMillis();
                System.out.println("StartTime:" + getTimeButtonClicked(startTime));
                System.out.println("begin new");
                System.out.println(pauseCounter);
                RunStep();
                if(btnStart.getText()=="PAUSE") {
                    pauseCounter = true;
                    btnStart.setText("RESUME");
                    insertData=insertData+"The application is in pause state " + counter+"\n";
                    counter--;
                    disableLayout();
                }
                else {
                    btnStart.setText("PAUSE");
                    pauseCounter = false;
                    insertData=insertData+"The application is resume state do not take the next entry" + counter+"\n";
                    enableLayout();
                    mySpinner.setEnabled(false);
                }

                System.out.println("StartTime:" + getTimeButtonClicked(startTime));
                System.out.println("begin new");
                System.out.println(pauseCounter);
            }
        });
        // End Button
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteFile(insertData,MainActivity.this);
               System.exit(0);
            }
        });

    }

    private void SetupLevel()
    {
        switch(intCurrentLevel) {
            case 0:
                System.out.println("Level 0");
                ClearButtonView();
                btnStart = findViewById(R.id.btnStart);
                rlLayout = findViewById(R.id.rlLayout);
                btnStart.setBackgroundColor(Color.GREEN);
                intRows =12;
                intColumns=14  ;
                intHeight = 80;
                intWidth = 80;
                distanceInPixel = 240;
                intTargetX = getRandomNumber(0,intRows);
                intTargetY = getRandomNumber(0,intColumns);
                strCurrentTarget= "btn" + intTargetX + "-" + intTargetY;
                counter =0;
                CreateButtonSet();
                break;

            case 1:
               // System.out.println("Level 1");
                ClearButtonView();
                btnStart = findViewById(R.id.btnStart);
                rlLayout = findViewById(R.id.rlLayout);
                intRows =15;
                intColumns=12;
                intHeight=100;
                intWidth =100;
                distanceInPixel = 240;
                intTargetX = getRandomNumber(0,intRows);
                intTargetY = getRandomNumber(0,intColumns);
                strCurrentTarget= "btn" + intTargetX + "-" + intTargetY;
                counter =0;
                CreateButtonSet();
                break;
            case 2:
                //System.out.println("Level 2");
                ClearButtonView();
                btnStart = findViewById(R.id.btnStart);
                rlLayout = findViewById(R.id.rlLayout);
             //   btnEnd=findViewById(R.id.btnEnd);
                intRows =30;
                intColumns=24;
                intHeight=50;
                intWidth =50;
                distanceInPixel = 240;
                intTargetX = getRandomNumber(0,intRows);
                intTargetY = getRandomNumber(0,intColumns);
                strCurrentTarget= "btn" + intTargetX + "-" + intTargetY;
                counter =0;
                CreateButtonSet();
                break;
            case 3:
                System.out.println("Levels completed.");
                break;
            default:
                // code block
        }
    }

    private void RunStep()
    {

        if(distanceInPixel==240)
        {
            intDistance240Count++;
        }
        if(distanceInPixel==440)
        {
            intDistance440Count++;
        }
        if(distanceInPixel==640)
        {
            intDistance640Count++;
        }


        if(counter>intSampleCount)
        {
            counter = 0;
            intCurrentLevel++;

                if(intCurrentLevel>2)
                {

                    intCurrentLevel  =0;
                    if (Mode.equals("Thumb"))
                    {
                        mySpinner.setSelection(categories.indexOf("Index"));
                        Mode = "Index";
                        ThumbComplete=true;
                        System.out.println("The distance is 300 "+intDistance240Count);
                        System.out.println("The distance is 600 "+intDistance440Count);
                        System.out.println("The distance is 900 "+intDistance640Count);
                    }
                    else
                    {
                        mySpinner.setSelection(categories.indexOf("Thumb"));
                        Mode ="Thumb";
                        IndexComplete=true;

                        System.out.println("The distance is 300 "+intDistance240Count);
                        System.out.println("The distance is 600 "+intDistance440Count);
                        System.out.println("The distance is 900 "+intDistance640Count);
                    }
                }
                intDistance240Count=0;
                intDistance440Count=0;
                intDistance640Count=0;

                SetupLevel();

            }

            if(ThumbComplete && IndexComplete)
            {

                System.out.println("Thank You");
                alertDialogexit(" Thank You.\n .csv file is created in the Internal Storage. \n \n Click OK to exit.","Information");
                WriteFile(insertData,MainActivity.this);
                btnStart.setText("START");


            }




        View vTarget = GetButtonByTag(strCurrentTarget);
        //check global counter if less than NUMBER do below or exit
        //create random X and Y
        String substr = strCurrentTarget.substring(3, strCurrentTarget.length());
        String[] parts = substr.split("-");

        ArrayList<String> aTagList = GetListOfNextButtonTags(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),distanceInPixel);

        if(aTagList.isEmpty())
        {
            insertData= insertData + "The list the ignore the immediate next entry: " +aTagList.toString() + "postion :" + strCurrentTarget + "\n";
            System.out.println("The list the ignore the immediate next entry: " +aTagList.toString() + "postion :" + strCurrentTarget);
            strCurrentTarget = "btn0-0";
            counter--;
            insertData= insertData + "The list the ignore the immediate next entry: " +aTagList.toString() + "postion :" + strCurrentTarget + "\n";
        }
        String test = strCurrentTarget;

        if(aTagList.size()>0)
        {
            // return;
            int intTagSize = aTagList.size();
            Random rand = new Random();
            int intRandomId = rand.nextInt(intTagSize);

            //find button with tag "btnX-Y"
            strCurrentTarget = aTagList.get(intRandomId);
        }

        distanceInPixel =getDistance();// distance randomly

        View vNextTarget = GetButtonByTag(strCurrentTarget);
        if(vNextTarget != null)
        {
            //set target button gradient
            final GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(10);
            gradientDrawable.setColor(Color.RED);
            final Button btnTarget = (Button) vNextTarget;
            btnTarget.setBackground(gradientDrawable);

            int viewX = (int)vNextTarget.getX();
            int viewY = (int)vNextTarget.getY();

            String substr1 = strCurrentTarget.substring(3, strCurrentTarget.length());
            String[] parts1 = substr1.split("-");

            DrawCircle(Integer.parseInt(parts1[0]),Integer.parseInt(parts1[1]),distanceInPixel);
        }
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    private int getDistance()
    {
        if(distanceInPixel == 240)
            return 440;
        if(distanceInPixel== 440)
            return 640;
        if(distanceInPixel == 640)
            return 240;
        return 0;
    }
    private void DrawCircle(int intX, int intY, int intDistanceInPixel)
    {
        ClearCircle();
        ArrayList<String> theButtonTagset = GetListOfNextButtonTags(intX,intY,intDistanceInPixel);
        if(!theButtonTagset.isEmpty())
        {
            //set circle button gradient
            final GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(10);
            gradientDrawable.setColor(Color.LTGRAY);

            for(int intButtonIndex = 0; intButtonIndex<theButtonTagset.size(); intButtonIndex++)
            {
                View vTarget = GetButtonByTag(theButtonTagset.get(intButtonIndex));
                vTarget.setBackground(gradientDrawable);
            }
        }
    }

    private void ClearCircle()
    {
        //set default button gradient
        final GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setShape(GradientDrawable.RECTANGLE);
        gdDefault.setCornerRadius(10);
        gdDefault.setColor(Color.LTGRAY);

        //set all the buttons except target to default gradient
        for(int iRowIndex = 0; iRowIndex<intRows; iRowIndex++)
        {
            for(int iColIndex =0; iColIndex<intColumns; iColIndex++)
            {
                String strButtonTag = "btn" + iRowIndex + "-" + iColIndex;
                if(!strCurrentTarget.equals(strButtonTag))
                {
                    View vTarget = GetButtonByTag(strButtonTag);
                    vTarget.setBackground(gdDefault);
                }
            }
        }
    }

    private View GetButtonByTag(String strTag)
    {
        int intChildCount = rlLayout.getChildCount();
        for(int i=0; i<intChildCount; i++) {
            View vView = rlLayout.getChildAt(i);
            String strViewTag = String.valueOf(vView.getTag());
            if(strViewTag.equals(strTag))
            {
                return vView;
            }
        }
        return null;
    }

    private void ClearButtonView()
    {
        for(int iRowIndex = 0; iRowIndex<intRows; iRowIndex++)
        {
            for(int iColIndex =0; iColIndex<intColumns; iColIndex++)
            {
                String strButtonTag = "btn" + iRowIndex + "-" + iColIndex;
                View vTarget = GetButtonByTag(strButtonTag);
                if(vTarget !=null)
                {
                    rlLayout.removeView(vTarget);
                }
            }
        }
    }

    private void disableLayout()
    {
        int intChildCount = rlLayout.getChildCount();
        for(int i=1; i<intChildCount; i++) {
            View child = rlLayout.getChildAt(i);
            child.setEnabled(false);
        }
    }
    private void enableLayout()
    {
        int intChildCount = rlLayout.getChildCount();
        for(int i=1; i<intChildCount; i++) {
            View child = rlLayout.getChildAt(i);
            child.setEnabled(true);
        }
    }

    private  void disablespinner() {

            View child = rlLayout.getChildAt(1);
            child.setEnabled(false);

    }
    private  void enablespinner() {

        View child = rlLayout.getChildAt(1);
        child.setEnabled(true);

    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void CreateButtonSet()
    {
        for(int iRowIndex = 0; iRowIndex<intRows; iRowIndex++)
        {
            for(int iColIndex =0; iColIndex<intColumns; iColIndex++)
            {
                CreateButton(iRowIndex,iColIndex);
            }
        }
    }

    private void CreateButton(int intRow, int intColumn)
    {
        RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(intWidth,intHeight);

        lpParams.setMargins((intColumn*(10+intWidth)+20),(intRow* (intHeight+20))+700,20,10);
        Button btn1 = new Button(this);

        btn1.setTag("btn" + String.valueOf(intRow) + "-" + String.valueOf(intColumn));
        btn1.setLayoutParams(lpParams);
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(10);
        gradientDrawable.setColor(Color.LTGRAY);
        btn1.setBackground(gradientDrawable);


            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    view.setBackground(gradientDrawable);
                    String strTag = String.valueOf(view.getTag());
                    String strPosition = strTag.substring(3, strTag.length());
                    String[] aPosition = strPosition.split("-");
                    long clikTime = Calendar.getInstance().getTimeInMillis(); ;
                    int Error =0;
                    if (strCurrentTarget.equals(strTag)) {
                        String strTargetPosition = strTag.substring(3, strCurrentTarget.length());
                        String[] finalPosition = strTargetPosition.split("-");
                        long CurrentTime = Calendar.getInstance().getTimeInMillis();

                        System.out.println("Mode: " + Mode + " ,W : " + intWidth + ",Amplitude : " + distanceInPixel + " ,Success x: " + finalPosition[0] + " ,Success y:" + finalPosition[1] + " ,Time : " +startTime + "," + clikTime + "," + counter);
                        insertData = insertData + Mode.toString() + "," + intWidth + "," + distanceInPixel + "," + "Success " + ", " + startTime + "," + clikTime + "," + counter + "\n";
                        //
                        counter++;
                        RunStep();

                    } else {
                        // System.out.println("Error");
                        System.out.println("Mode: " + Mode + " ,W : " + intWidth + ",Amplitude : " + distanceInPixel + " , Error x:" + aPosition[0] + "Error y:" + aPosition[1]);
                        insertData = insertData + Mode.toString() + "," + intWidth + "," + distanceInPixel + "," + "Error. " + ", " + startTime + "," + clikTime + "," + counter + "Do not consider the next enter"  + "\n";
                        counter--;
                        if(Error == 0)
                        {
                            Error =1;
                            if (distanceInPixel == 240)
                                distanceInPixel = 640;
                            if (distanceInPixel == 440)
                                distanceInPixel = 240;
                            if (distanceInPixel == 640)
                                distanceInPixel = 440;
                        }

                    }
                }

            });

         rlLayout.addView(btn1);
    }

    // Function to get the time between two click
    private long getTimeButtonClicked(long time)
    {
        if(time ==startTime)
        {
            previousTime =time;
            newTime= time;
            temp =time;

        }
        else
        {
            newTime = time;
            temp = newTime - previousTime;
            previousTime = newTime;
        }

        return temp;
    }

    private ArrayList<String> GetListOfNextButtonTags(int ViewX, int  ViewY, int distanceInPixel)
    {
        ArrayList<String> setOfTag = new ArrayList<>();

        String strTargetButtonTag = "btn" + ViewX + "-" + ViewY;
        View vTarget = GetButtonByTag(strTargetButtonTag);

        int intTargetCenterX= (int)vTarget.getX();
        int intTargetCenterY=(int)vTarget.getY();

        for(int iRowIndex = 0; iRowIndex<intRows; iRowIndex++)
        {
            for(int iColIndex =0; iColIndex<intColumns; iColIndex++)
            {
                String strButtonTag = "btn" + iRowIndex + "-" + iColIndex;
                View vButton = GetButtonByTag(strButtonTag);

                int intButtonX = (int)vButton.getX();
                int intButtonY = (int)vButton.getY();
                int flag = 0;
                int intDistance = (int) Math.sqrt(((intTargetCenterX-intButtonX) * (intTargetCenterX-intButtonX)) + ((intTargetCenterY-intButtonY) * (intTargetCenterY-intButtonY)));
                int intWidthTolerance =(int) Math.floor((double)(intWidth/2));

                if(intDistance>distanceInPixel-intWidthTolerance && intDistance<distanceInPixel+intWidthTolerance)
                {
                    if (!strCurrentTarget.equals(strButtonTag))
                    {
                        setOfTag.add(strButtonTag);
                    }
                }
            }
        }

        return setOfTag;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

                if(parent.getItemAtPosition(position).toString().equals("Select"))
                {
                    alertDialog("Please select the INPUT Mode(Thumb/Index)","FITTS");
                    System.out.println("dialog");
                    btnStart.setEnabled(false);
                    mySpinner.setEnabled(true);
                }
                if(parent.getItemAtPosition(position).toString().equals("Thumb"))
                {
                    if(Mode.isEmpty())
                    {   btnStart.setEnabled(true);

                        alertDialog("Mode: Thumb\n" +
                                "\n" +
                                "1. Please use the device with one hand.\n" +
                                "2. Use the thumb of the same hand to click the buttons .\n" +
                                "3. Click on PAUSE at any point of time to pause.\n" +
                                "4. Click on RESUME at any point of time to resume.\n" +
                                "5. Click on EXIT at any point of time to exit.\n" +
                                "\n" +
                                "\n The app is a tapping game so keep tapping the red button on the screen :) \n" +
                                "To start the app, click START\n","Instruction");
                        mySpinner.setEnabled(false);




                    }
                    else
                    {   if(intend == 0)
                        {
                            alertDialog("Mode: Thumb\n" +
                                    "\n" +
                                    "1. Please use the device with one hand.\n" +
                                    "2. Use the thumb of the same hand to click the buttons.\n" +
                                    "3. Click on PAUSE at any point of time to pause.\n" +
                                    "4. Click on RESUME at any point of time to resume.\n" +
                                    "5. Click on EXIT at any point of time to exit.\n" +
                                    "\n" +
                                    "\n The app is a tapping game so keep tapping the red button on the screen :) \n" +
                                    "To Continue, click OK\n","Instruction");
                            intend=1;
                        }


                    }
                    Mode = parent.getItemAtPosition(position).toString();
                }
                if(parent.getItemAtPosition(position).toString().equals("Index"))
                {   btnStart.setEnabled(true);
                    if(Mode.isEmpty())
                    {
                        alertDialog( "Mode: Index Finger\n" +
                                    "\n" +
                                    "1. Please hold the device with one hand and use the index finger of the other hand to click the buttons.\n" +
                                    "2. Click on PAUSE at any point of time to pause.\n" +
                                    "3. Click on RESUME at any point of time to resume.\n" +
                                    "4. Click on EXIT at any point of time to exit.\n" +
                                    "\n" +
                                    "\n The app is a tapping game so keep tapping the red button on the screen :) \n" +
                                    "To start the app, click START\n","Instruction");
                            mySpinner.setEnabled(false);



                    }
                    else
                    {
                        if(intend ==0)
                        {
                            alertDialog( "Mode: Index Finger\n" +
                                    "\n" +
                                    "1. Please hold the device with one hand and use the index finger of the other hand to click the buttons.\n" +
                                    "2. Click on PAUSE at any point of time to pause.\n" +
                                    "3. Click on RESUME at any point of time to resume.\n" +
                                    "4. Click on EXIT at any point of time to exit.\n" +
                                    "\n" +
                                    "\n The app is a tapping game so keep tapping the red button on the screen :) \n" +
                                    "To Continue, click OK\n","Instruction");

                            mySpinner.setEnabled(false);
                            intend=1;
                        }
                    }
                    Mode = parent.getItemAtPosition(position).toString();
                }
        // Showing selected spinner item

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // to get the alert Message
    private void alertDialog(String msg, String Title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg);
        dialog.setTitle(Title);
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        //Toast.makeText(getApplicationContext(), "OK is clicked", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        dialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void alertDialogexit(String msg, String Title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg);
        dialog.setTitle(Title);

        dialog.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        System.exit(0);
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        dialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };


    // to create and write the file in internal storage
    private void WriteFile(String text,Context context)
    {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), filename);
        if (!file.mkdirs()) {
            System.out.println("Directory not created");

        }
        try {

            File gpxfile = new File(file, filename);
            FileWriter writer = new FileWriter(gpxfile);
            FileOutputStream fileinput = new FileOutputStream(gpxfile, true);
            writer.append(text);

           writer.close();

            System.out.println("File saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                }
                else
                {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}

