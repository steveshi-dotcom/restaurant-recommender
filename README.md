# restaurant-recommender Introduction
 A simple restaurant recommender for 75 best restaurants located in Atlanta from the list https://www.atlantamagazine.com/50bestrestaurants/
 
 The application will show the name of the restaurant, address, description, distance from Georgia Tech, average cost of the food,
 and the most popular dish the restaurant is known for. There will be filters in the bottom of the screen showing the miles, and 
 average cost in which users can use to filter out.

# Add deependencies if on a IDE
First download the JavaFx package: javafx-sdk-11.0.2 from https://gluonhq.com/products/javafx/
Move the sdk into current directory restaurant-recommender
On IDE such as VS Code add the dependencies using the current relative path: javafx-sdk-11.0.2/lib

# Running the application
Now that the package is inside the current directory, run:
javac --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender.java
java --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender
to start up the GUI