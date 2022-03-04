# restaurant-recommender Introduction
 A simple restaurant recommender for 75 best restaurants located in Atlanta from the list 
 https://www.atlantamagazine.com/50bestrestaurants/
 
 The application consist of
 - name of the restaurant
 - address
 - description
 - distance from Georgia Tech
 - average cost of the food,
 - popular dish the restaurant is known for 
 - Filters in the bottom of the screen showing the miles, and average cost in which users can use to filter out.

 ![Example 1](/IntroductionImages/example1.png)

 ![Example 2](/IntroductionImages/example2.png)


# Add deependencies if on a IDE
1) First download the JavaFx package: javafx-sdk-11.0.2 from https://gluonhq.com/products/javafx/
2) Move the sdk into current directory restaurant-recommender
On IDE such as VS Code and add the reference to the jar files using the current relative path: javafx-sdk-11.0.2/lib

# Running the application
Now that the package is inside the current directory, open up terminal and run the following commands to start the gui:
- javac --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender.java
- java --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender

