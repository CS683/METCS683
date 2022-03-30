# Lab3
## Description 
Based on the example we discussed in this module, add the following features into your previous lab2 submission:
- The ProjectList Screen
  - Add a ProjectList screen to list all projects in your project using RecycleView and cardview. You can just display the project title in the list as we did in the lecture example.
  - Add the communication between the projectlist screen and your project detail screen that you completed in lab2, so that when you click on a project in the ProjectList screen, the corresponding project detail will be displayed, including project title, description, authors, links and whether you like the project. (You can choose to use either navigation with arguments or ViewModel)
- The AddProject Screen
  - Add another AddProject screen to allow the user to add a new project and detailed information about that project into the system. You can implement it using either Activity or Fragment. It will be easier to implement it using Activity.
  - Connect the AddProject Screen to the Project. For example, add a "+" Button in the ProjectList Screen, when clicking on it, it goes to the AddProject screen. If you implement it using Activity, you can use a simple intent to switch to the AddProject screen.
  - On the AddProject screen, you can use a submit button. When it is clicked, the new project should be added, and it will switch back to the ProjectList screen. (optional)
## What to submit:
- A lab report including the explanation of your design and implementaion, and the screenshots for testing results.
- A zip file of your source code
