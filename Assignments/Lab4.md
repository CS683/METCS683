# Lab4
## Objectives
- Learn how to use room database and repository
- Learn how to use shared preferences
## Setup
Download the example zip files from the discussion forum the "Module 3 Lecture Notes and Examples" thread. Unzip them and run them in your Android studio. To complete lab4, you will need to particuarly use files in the datalayer and videmodel package.  
## Description
Based on the example we discuss in this module, add the following features into your previous lab3 submission:
- Database Implementation
  - Use a database to store Project data. It is easier to use Room as we discussed in the module. Modify your Project data class to be an entity class. Add Database and Dao class as well as repository into your previous lab3 submission. 
  - Complete the add project functionality, so that the new project can be added into the database and displayed on the screen. You will need to change previous viewmodel, adapter and fragment to make this work. This is already implemented in the example code. Try to add these into your own lab to make it work.
  - Make sure the favorite setting is also stored correctly in the database whenever the user changes it. 
- Show Favorite Projects
 - Add a "show favorite projects only" checkbox (or toggle button or switch) on the project list screen.
 - This setting (whether it will show only favorite projects) should be stored into and received from the shared preferences so that it can be remembered across different sessions.
 - If it is set, only list all favorite projects from the database. (this is a little bit harder and is optional. To implement this one, you need to change the observer onChanged() call back when observing the project list.)
## What to submit: 
- On blackboard
  - A lab report fie in pdf or doc, including 
   - your github repo URL link
   - a description of how you designed and implemented the above requirements. Please also report any issues you have encountered and the screenshots to show the execution result.
  - A zip file of your source code
- On github
  - Commit both your lab report (in pdf, doc or md) and code
