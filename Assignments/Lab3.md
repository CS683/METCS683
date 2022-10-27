# Lab3
## Objectives
- Learn how to use recyler view to display a collection of data
- Learn how to use navigation destination arguments OR view models for fragment communication. 
## Setup
Download two example code from the blackboard. Unzip them and run them in your Android studio. To complete lab3, you can refer to any one of the example code. For simiplicity, you can just use the first example: ProjectportalRecycler.zip. If you also want to learn how to use ViewModel, you can use the second example. 
1. ProjectPortalRecycler.zip: this one only implements recycler view and navigation with argumented destination. You can use the code in this zip file for your lab3. In particular, you can copy all files related to recyclerview (e.g.  ProjListRecycleViewFragment.kt and MyProjListRecyclerViewAdapter.kt, fragment_proj_list_recycler_view.xml and fragment_projitem.xml) into your lab2 folder, and modify your existing code (such as edit fragment, detail fragment, and navigtation graph) based on the example code. 
2. ProjectPortalRecyclerMVVMTest.zip: this one is much more complicated. It implements not only recyclerview implementation, but also the viewmodel, livedata and adaption to different screen size, as well as espresso test. For your lab3, you can copy all files related to recyclerview and view model in the example code to your lab2 folder and then modify your existing code based on the example code. 
 
## Description 
Based on either one of the two examples mentioned above, add the following features into your previous lab2 submission:
- The ProjectList Screen
  - Add a ProjectList screen to list all projects in your project using RecycleView and cardview. You can just display the project title in the list as we did in the lecture example.
  - Add the communication between the projectlist screen and your project detail screen that you completed in lab2, so that when you click on a project in the ProjectList screen, the corresponding project detail will be displayed, including project title, description, authors, links and whether you like the project. (You can choose to use either navigation with arguments or ViewModel). If you implement the project detail screen as an activity, you may need to change it to a fragment. 
- The AddProject Screen
  - Add another AddProject screen to allow the user to add a new project and detailed information about that project into the system. You can implement it using either Activity or Fragment. It will be easier to implement it using Activity.
  - Connect the AddProject Screen to the Project. For example, add a "+" Button in the Project List Screen, when clicking on it, it goes to the AddProject screen. If you implement it using Activity, you can use a simple intent to switch to the AddProject screen. If you implement it using Fragment, you can add that into the navigation graph. 
  - On the AddProject screen, you can use a submit button. When it is clicked, the new project should be added into the project list, and it will switch back to the ProjectList screen with the new added project displayed. (this is an optional feature. )

## What to submit:
- A lab report including the explanation of your design and implementaion, and the screenshots for testing results.
- A zip file of your source code
