# CS683 Lab1 

## Description

In this lab, you will  
- learn how to use Android studio to create a simple "hello android" Android application.
- learn how to use git/github with Android studio
- explore the lifecycle of an activity.
- learn how to effectively prompt AI to generate code and evaluate AI-generated code  
 
The first task is to create a simple "hello android" application using Android studio template, and modify the code to monitor the activity lifecycle. You need to add some log information using Log.d() in each callback function of the main activity.  You can find the detailed code in the lecture notes and at 
https://github.com/PacktPublishing/How-to-Build-Android-Apps-with-Kotlin/tree/master/Chapter02/Exercise2.01. 
Explore when these methods will be called by performing different actions such as starting the app, closing the app, 
starting another app, pressing the back button, bringing the app from the background, rotating the screen, etc. 
Document your findings in your lab report.

The second task is to use the Gemini agent integrated within Android Studio to build a simple "dice rolling" application. You shall define detailed requirements, write effective prompts to generate code, debug code to ensure it can run properly, and review code to have a basic understanding of the generated code. Document your AI usage and reflection in the report.

You shall commit your code to the github repo and include the repo link in your report. 

## What to submit: 
- A lab report in pdf or doc or md. The report should include
  - your github link,
  - a brief description of your experience with Android studio/AVD and Git/Github for this lab, particularly if you encountered any difficulties and issues,
  - a table shown as below to describe your findings of the activity life cycles, such as the order of callbacks under different actions,
and any screenshots to show as evidences of your findings.
    |Action| call backs in order|
    |-|-|
    |open the app| onCreate -> onStart -> onResume |
    |close the app|...|
    |press the home button| ...|
    |...|...|
  - A clear description of all functionalities your dice rolling app has and the screenshots of its execution results. 
  - The AI usage log includes prompt history and a reflection that evaluates the AI's utlity, the correctness of its output and overall quality of the generated code.
- A short video to explain AI-generated code to show your basic understanding 

