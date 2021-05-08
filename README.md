# Fitts-law using Color block Game 
Fitts Law defines fixed amplitude with fixed size of the block as user starts with the application 

In the application two input device : Thumb and Index finger performance to hit the target red spot is evaluated.
This application is designed for Nexus7 Tablet

## To Modify the app to fit in as per your device, modify the following detail in the MainActivity.java file.

### To modify the No.of trial : 

 int intSampleCount = 1;         // Set This value
 
 ### To Modify the No. of rows and column in each level :
 
 intRows =10;            // Set No.of rows as per your device
 
 intColumns=8 ;         //Set No.of columns as per your device

 ### To Modify the Height and Width of each block:
 
intHeight = 150;                                                // Set height in pixel
                
intWidth = 150;                                                 // Set width in pixel

### This application would generate a .csv file which would be generated in internal storge of application folder.

## The below diagram is level1, level2 and level 3 of the game( Easy to Difficult)
<img src= https://user-images.githubusercontent.com/56184814/117523247-69754d00-af7d-11eb-827b-15be70f17670.png width="200"/>
<img src= https://user-images.githubusercontent.com/56184814/117523242-5bbfc780-af7d-11eb-80e1-bf0fdcd4ae30.png width="200"/>
<img src= https://user-images.githubusercontent.com/56184814/117523203-1ac7b300-af7d-11eb-972b-a8b686446426.png width="200"/>

#  License: 
Apache License 2.0

Attribution : Saumya Omanakuttan
