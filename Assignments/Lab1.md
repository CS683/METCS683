# CS683 Lab1 

## Description

In this lab, you will  
- learn how to use Android studio to create a simple "hello world" Android application.
- learn how to use git/github with Android studio
- explore the lifecycle of an activity.

You will need to create a simple "hello world" application using Android studio template, and modify the code to monitor the activity lifecycle, 
and commit your code into your local repositoy and then push them to github. You need to add some log information using Log.d() in each of the 7 
callback functions of the main activity.  You can find the detailed code in the lecture notes and at 
https://github.com/PacktPublishing/How-to-Build-Android-Apps-with-Kotlin/tree/master/Chapter02/Exercise2.01. 
Explore when these methods will be called by performing different actions such as starting the app, closing the app, 
starting another app, pressing the back button, bringing the app from the background, rotating the screen, etc. 
Document your findings in your lab report. 

## What to submit: 

A lab report in pdf or doc or md. (include your github link, a brief description of your experience 
with Android studio/AVD and Git/Github for this lab, particularly if you encountered any difficulties and issues, 
a table to describe your findings of the activity life cycles, such as the order of callbacks under different actions,
and any screenshots to show as evidences of your findings.
|Action| call backs in order|
|-|-|
|open the app| onCreate -> onStart -> onResume |
|close the app|...|
|press the home button| ...|
|...|...|

