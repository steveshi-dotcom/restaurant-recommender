/**
 * @author Steve Shi
 * @version 11/26/2021
 * To run the application copy and paste the following into the terminal
 *  javac --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender.java
 *  java --module-path="javafx-sdk-11.0.2/lib" --add-modules=javafx.controls recommender
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class recommender extends Application {

    private BorderPane myPane = new BorderPane();
    private ArrayList<restaurant> originalRestaurantsList = obtainRestaurantList();
    private ArrayList<restaurant> restaurantsListOption = originalRestaurantsList;
    private int currentIndex = 0;
    private int maxIndex = originalRestaurantsList.size();
    private HBox myPaneContent;

    public BorderPane getPane() {

        // Create the original borderpane for all the contents
        myPane.setPadding(new Insets(40, 40, 50, 60));
        BackgroundFill myPaneFill = new BackgroundFill(Color.PEACHPUFF, CornerRadii.EMPTY, Insets.EMPTY);
        myPane.setBackground(new Background(myPaneFill));

        // The title of the scene with a cute sandwich pic next to it(TOP)
        Label myPaneTitle = createPaneTitle();
        myPane.setTop(myPaneTitle);
        BorderPane.setAlignment(myPaneTitle, Pos.CENTER);

        // The center content to show the restaurants(CENTER)
        // Show the first restaurant which is the Krusty Krab :)
        myPaneContent = showRestaurantInfo(originalRestaurantsList, currentIndex);
        myPane.setCenter(myPaneContent);

        // Nodes such as Choice to select different options to filter out restaurant(BOTTOM)
        GridPane bottomInputs = new GridPane();
        bottomInputs.setPadding(new Insets(15, 15, 15, 15));
        bottomInputs.setHgap(30);
        bottomInputs.setVgap(20);

        Label distanceLabel = getDistanceLabel();
        ChoiceBox<Double> distanceChoiceBox = getDistanceChoiceBox();
        Label priceLabel = getPriceLabel();
        ChoiceBox<String> priceChoiceBox = getPriceChoiceBox();
        Button showOptions = new Button("Show Restaurant Options!");
        Button nextOption = new Button("Next restaurant");

        bottomInputs.add(distanceLabel, 1,1);
        bottomInputs.add(priceLabel, 2, 1);
        bottomInputs.add(distanceChoiceBox, 1, 2);
        bottomInputs.add(priceChoiceBox, 2, 2);
        bottomInputs.add(showOptions, 3, 2);
        bottomInputs.add(nextOption, 4, 2);

        myPane.setBottom(bottomInputs);
        BorderPane.setAlignment(bottomInputs, Pos.TOP_RIGHT);

        showOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent arg0) {
                System.out.println("Set on action invoked");
                if (distanceChoiceBox.getSelectionModel().isEmpty() || priceChoiceBox.getSelectionModel().isEmpty()) {
                    distanceChoiceBox.getSelectionModel().clearSelection();
                    priceChoiceBox.getSelectionModel().clearSelection();
                    Alert noOptionsAvailable = new Alert(AlertType.ERROR, "OOPS! No restaurant filter", ButtonType.OK);
                    noOptionsAvailable.show();
                    return;
                }

                ArrayList<restaurant> returnList = new ArrayList<>();
                double pickedDistance = distanceChoiceBox.getValue();
                String pickedPrice = priceChoiceBox.getValue();
                distanceChoiceBox.getSelectionModel().clearSelection();
                priceChoiceBox.getSelectionModel().clearSelection();

                for (int i = 0; i < originalRestaurantsList.size(); i++) {
                    if (originalRestaurantsList.get(i).getDistance() <= pickedDistance &&
                        originalRestaurantsList.get(i).getAvgCost().equals(pickedPrice)) {
                            returnList.add(originalRestaurantsList.get(i));
                    }
                }
                if (returnList.size() == 0) {
                    Alert noOptionsAvailable = new Alert(AlertType.INFORMATION, "OOPS! No restaurant with current filter", ButtonType.OK);
                    noOptionsAvailable.show();
                } else {
                    maxIndex = returnList.size();
                    currentIndex = 0;
                    restaurantsListOption = returnList;
                }

                changeRestaurantInfo(restaurantsListOption, 0);
            }
        });
        nextOption.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg0) {
                if (currentIndex + 1 != maxIndex) {
                    System.out.println("AMx:" + maxIndex);
                    currentIndex += 1;
                    System.out.println(currentIndex);
                    changeRestaurantInfo(restaurantsListOption, currentIndex);
                } else {
                    Alert nothingBehind = new Alert(AlertType.NONE, "OOPSIE End of the list", ButtonType.OK);
                    nothingBehind.show();
                }
            }
        });

        return myPane;
    }

    ///////////////////////////////////////////////////////////////
    // Create the title along with the cute sandwich pic (TOP)
    private Label createPaneTitle() {
        // Create the imageview used for the label
        Image cuteSandwich = new Image("cuteSandwich.jpeg");
        ImageView cuteSandwichView = new ImageView(cuteSandwich);
        cuteSandwichView.setFitWidth(50);
        cuteSandwichView.setFitHeight(50);

        // Creat the label with the text and the cute sandwich pic
        Label returnPaneTitle = new Label("Restaurant Recommender", cuteSandwichView);
        returnPaneTitle.setGraphic(cuteSandwichView);
        returnPaneTitle.setTextFill(Color.BLACK);
        returnPaneTitle.setStyle("-fx-font-size: 28pt;");
        return returnPaneTitle;
    }

    // Create list of restaurants in Atlanta (CENTER)
    private ArrayList<restaurant> obtainRestaurantList() {
        ArrayList<restaurant> restaurantList = new ArrayList<restaurant>();
        restaurantList.add(new restaurant(0,"Krabby Patty", "Krusty Krab", "Home of the Krabby Patties", 3.0, "$$$", "Nasty Patty"));
        restaurantList.add(new restaurant(1, "Miller Union", "999 Brady Avenue", "Southest Food", 0.9, "$$$", "Seasonal Vegetable Plate"));
        restaurantList.add(new restaurant(2, "Staplehouse", "541 Edgewood Avenue", "Personality Driven", 3.9, "$$$$", "King Crab with vege"));
        restaurantList.add(new restaurant(3, "Masterpiece", "3940 Buford Highway", "Refined Sichuan", 23.1, "$$", "Dried Fried Eggplant"));
        restaurantList.add(new restaurant(4, "Spring", "36 Mill Street", "Seasonal Southern food", 17.6, "$$$", "pan-roasted halibut"));
        restaurantList.add(new restaurant(5, "Sushi Hayakawa", "5979 Buford Highway", "Outstanding Sushi", 15.9, "$$$$", "omakase"));
        restaurantList.add(new restaurant(6, "Bacchanalia", "1460 Ellsworth Industrial Boulevard", "Seasonal New American plates", 2.5, "$$$$", "Crab Fritter"));
        restaurantList.add(new restaurant(7, "Boccalupo", "753 Edgewood Avenue", "Pasta Royalty", 4.3, "$$", "Black Spaghetti w/mushroom"));
        restaurantList.add(new restaurant(8, "B’s Cracklin’BBQ", "725 Ponce de Leon", "Smoking BBQ", 3.0, "$", "Pecan-wood smoked ribs"));
        restaurantList.add(new restaurant(9, "Kimball House", "303 East Howard Avenue", "Hot Steak", 9.0, "$$$", "Carolina Gold rice middlins"));
        restaurantList.add(new restaurant(10, "LanZhou Ramen", "5231 Buford Highway", "Noodle capital of China", 15.7, "$", "Hand-pulled Noodle"));
        restaurantList.add(new restaurant(11, "Tiny Lou's", "789 Ponce de Leon Avenue", "Revolutionary French Cooking", 4.9, "$$$", "Brown Butter Blondie"));
        restaurantList.add(new restaurant(12, "Root Baking Co", "675 Ponce de Leon Avenue", "Swoon Worthy Bread", 2.8, "$", "Moroccan chickpea soup"));
        restaurantList.add(new restaurant(13, "Banshee", "1271 Glenwood Avenue", "Seasonality", 7.2, "$$", "Fry bread with pepperoni butter"));
        restaurantList.add(new restaurant(14, "Lazy Betty", "1530 DeKalb Avenue", "Creative Meal", 6.2, "$$$$", "Steak & Eggs"));
        restaurantList.add(new restaurant(15, "El Tesoro", "1374 Arkwright Place", "Tacos, burritos, tamele", 7.5, "$", "rajas with mushroom and squash"));
        restaurantList.add(new restaurant(16, "D92 Korean BBQ", "225 East Trinity Place", "Korean BBQ", 8.9, "$$", "Smoky Yuzu Margarita"));
        restaurantList.add(new restaurant(17, "Watchman’s Seafood and Spirits", "99 Krog Street", "Oyster & Cocktail", 4.0, "$$", "Air Mail"));
        restaurantList.add(new restaurant(18, "The White Bull", "123 East Court Square", "Moveable Feast", 8.9, "$$", "housemade sfincione bread"));
        restaurantList.add(new restaurant(19, "Nina & Rafi", "661 Auburn Avenue", "New Jersey Native Pizza", 4.1, "$$", "O4W Pizza"));
        restaurantList.add(new restaurant(20 , "La Mixteca Tamale House", "1185 Old Peachtree Road, Suwanee", "Tamale", 28.0, "$", "Philly Cheesecake"));
        restaurantList.add(new restaurant(21, "Floataway Cafe", "1123 Zonolite Road", "Dish conjuring Greece", 5.5, "$$", "Ocupus with chickpea"));
        restaurantList.add(new restaurant(22, "Canoe", "4199 Paces Ferry Road", "Variety of Vege", 7.6, "$$$", "California Asparagus Salad"));
        restaurantList.add(new restaurant(23, "Sotto Sotto", "313 North Highland Avenue", "Tasty Italian", 4.3, "$$", "tortelli di Michelangelo Buonarroti"));
        restaurantList.add(new restaurant(24, "Kyma", "3085 Piedmont Road", "Greek Feast", 7.5, "$$$", "Wood-grilled Octupus"));
        restaurantList.add(new restaurant(25, "La Grotta Ristorante Italiano", "2637 Peachtree Road", "Welcome Throwback", 4.4, "$$$", "Classic Past and veal"));
        restaurantList.add(new restaurant(26, "Octopus Bar", "560 Gresham Avenue", "Cool Octopus", 7.3, "$$", "Iciles radish"));
        restaurantList.add(new restaurant(27, "Ticonderoga Club", "99 Krog Street", "Ingenious Bartender", 4.0, "$$$", "Clam Rools"));
        restaurantList.add(new restaurant(28, "Bon Ton", "674 Myrtle Street", "Beef & seafood", 2.1, "$$", "Nashville hot oyster roll"));
        restaurantList.add(new restaurant(29, "8Arm", "710 Ponce de Leon Avenue", "Vega's food", 2.8, "$$", "Oyster mushroom"));
        restaurantList.add(new restaurant(30, "Poor Hendrix", "2371 Hosea L. Williams Drive", "For young stylish neighbor", 9.3, "$$", "Elevates peanut butter mousse"));
        restaurantList.add(new restaurant(31, "Gunshow", "924 Garrett Street", "Intriging dishes", 6.3, "$$$", "Hawaiian paté en croute with pineapple"));
        restaurantList.add(new restaurant(32, "Twisted Soul Cookhouse & Pours", "1133 Huff Road", "Twisted Food", 2.0, "$$$", "hoisin oxtails with shallot-ginger roasted bok choy"));
        restaurantList.add(new restaurant(33, "Argosy", "470 Flat Shoals Avenue", "Variety of meals", 7.0, "$$", "Shaoling Wing w/Tokyo mayo"));
        restaurantList.add(new restaurant(34, "Le Fat", "935 Marietta Street", "Vietnamese Food", 1.0, "$$", "Bao with soft-shell crab, bacon, sambal major"));
        restaurantList.add(new restaurant(35, "The Optimist", "914 Howell Mill Road", "Stylish Seafood", 0.9, "$$$", "Salmon with chorizo"));
        restaurantList.add(new restaurant(36, "Busy Bee", "810 Martin Luther King Drive", "Soul food", 3.3, "$", "Smothered Pork Chops"));
        restaurantList.add(new restaurant(37, "Tassili’s Raw Reality", "1059 Ralph D. Abernathy Boulevard", "Mandingo Wrap", 6.4, "$", "Superspicy, sor-marinated kale"));
        restaurantList.add(new restaurant(38, "Empire State South", "999 Peachtree Street", "Souther food", 1.6, "$$$", "Farm egg on crispy rice"));
        restaurantList.add(new restaurant(39, "The General Muir", "1540 Avenue Place", "Matzo Soup", 6.0, "$$", "Piled-high pastarami on rye"));
        restaurantList.add(new restaurant(40, "One Eared Stag", "1029 Edgewood Avenue", "Dumpster salad", 5.1, "$$$", "Taxidermied deer"));
        restaurantList.add(new restaurant(41, "Chicken + Beer", "Concourse D, Gate 5", "Southern fried", 13.0, "$$", "Airport wings"));
        restaurantList.add(new restaurant(42, "La Tavola", "992 Virginia Avenue", "Virginia-Highlanders", 3.4, "$$", "Housemade buttermilk ricotta"));
        restaurantList.add(new restaurant(43, "Bread & Butterfly", "290 Elizabeth Street", "Frech Cafe", 4.3, "$$", "Garlic sausage over lentils"));
        restaurantList.add(new restaurant(44, "The Federal", "1050 Crescent Avenue", "Bistro Steak house", 1.7, "$$$", "Pork Schnitzel with onion salad"));
        restaurantList.add(new restaurant(45, "No. 246", "129 East Ponce de Leon Avenue", "Fry Italian Spot", 8.8, "$$", "Rigatoni Bolognese"));
        restaurantList.add(new restaurant(46, "Snackboxe Bistro", "6035 Peachtree Road", "Laotian Food", 16.0, "$", "Peerless laap"));
        restaurantList.add(new restaurant(47, "Heirloom Market BBQ", "2243 Akers Mill Road", "Korean Cooking", 9.9, "$", "Southern BBQ with marinades and spice"));
        restaurantList.add(new restaurant(48, "Chai Pani", "406 West Ponce de Leon Avenue", "Spicy Indian food", 8.4, "$", "Okra fries"));
        restaurantList.add(new restaurant(49, "Community Q BBQ", "1361 Clairmont Road", "Fine Dining of BBQ", 9.3, "$", "Creamy three mac and cheese"));
        restaurantList.add(new restaurant(50, "Taqueria del Sol", "2165 Cheshire Bridge Road", "Mexican cuisine", 7.3, "$", "Fried chicken tacos with lime-jalapeno mayonaise"));
        restaurantList.add(new restaurant(51, "Rising Son", "124 North Avondale Road", "Breakfast and Lunch", 14.1, "$", "Cheese grits with vegtable"));
        restaurantList.add(new restaurant(52, "Fox Bros. Bar-B-Q", "1238 DeKalb Avenue", "Top-barcebecue", 5.5, "$", "Frito Pie"));
        restaurantList.add(new restaurant(53, "Arepa Mia", "10 North Clarendon Avenue", "arepas", 14.2, "$", "Pabellon"));
        restaurantList.add(new restaurant(54, "Taqueria Don Sige", "1720 Vesta Avenue", "Tiny Stip Mall", 11.8, "$", "Traditional tacos w/ onion, cilantro, lime and radish"));
        restaurantList.add(new restaurant(55, "Desta Ethiopian Kitchen", "3086 Briarcliff Road", "Ethiopian restaurant", 9.2, "$", "Kitfo"));
        restaurantList.add(new restaurant(56, "Atlas", "88 West Paces Ferry Road", "Food Museum", 6.0, "$$$$", "Pillowy mushroom agnolotti"));
        restaurantList.add(new restaurant(57, "Aria", "490 East Paces Ferry Road", "Monumental restaurant", 5.9, "$$$$", "Beet salad"));
        restaurantList.add(new restaurant(58, "Tomo", "3630 Peachtree Road", "Japense cuising", 9.2, "$$$$", "Naito's omakase"));
        restaurantList.add(new restaurant(59, "Restaurant Eugene", "2277 Peachtree Road", "Farm to tablecloth", 3.6, "$$$$", "Pane roasted duck liver"));
        restaurantList.add(new restaurant(60, "Bones", "3130 Piedmont Road", "Big wine + steak", 6.1, "$$$$", "Lump-crab cocktail"));
        restaurantList.add(new restaurant(61, "Mamak", "5150 Buford Highway", "Malaysia food", 14.0, "$", "Hainanese chicken"));
        restaurantList.add(new restaurant(62, "Woo Nam Jeong(Stone Bowl House)", "5953 Buford Highway", "Homestyle Korean", 15.8, "$", "Sizzling dolsots"));
        restaurantList.add(new restaurant(63, "El Rey del Taco", "5288 Buford Highway", "King of taco", 15.5, "$", "Steak to goat tacos"));
        restaurantList.add(new restaurant(64, "Food Terminal", "5000 Buford Highway", "Hip-hop food hall", 13.9, "$", "Thai Chili Pan Mee"));
        restaurantList.add(new restaurant(65, "Yet Tuh", "3042 Oakcliff Road", "Homestyle Korean", 15.8, "$", "Kimchi Pancakes"));
        restaurantList.add(new restaurant(66, "Taquería La Oaxaqueña", "605 Mount Zion Road", "Best Mexican", 17.0, "$", "Tlayuda"));
        restaurantList.add(new restaurant(67, "Rumi’s Kitchen", "6112 Roswell Road", "Persian food", 15.0, "$$$", "Kashk badenjoon"));
        restaurantList.add(new restaurant(68, "9292 Korean Barbecue", "3360 Satellite Boulevard", "Mini Empire", 23.6, "$$", "Banchan"));
        restaurantList.add(new restaurant(69, "Osteria Mattone", "1095 Canton Street", "Roman cuising", 23.5, "$$", "Plump agnolotti di oxo"));
        restaurantList.add(new restaurant(70, "Nam Phuong", "5495 Jimmy Carter Boulevard", "Vietnamese Cuisine", 17.6, "$", "Bone in steam chicken over shredded vege"));
        restaurantList.add(new restaurant(71, "Porch Light Latin Kitchen", "300 Village Green Circle", "Puerto Rican cooking", 14.0, "$$", "Whole citrius-brined, beer can chicken"));
        restaurantList.add(new restaurant(72, "Tasty China", "585 Franklin Gateway", "Sichuan Cuisine", 14.0, "$$", "Mild chicken with three type of mushrooms"));
        restaurantList.add(new restaurant(73, "Seed Kitchen & Bar", "1311 Johnson Ferry Road, Marietta", "Sophisticated Cocktail", 19.7, "$$$", "Chicken schnitzel"));
        restaurantList.add(new restaurant(74, "Kaiser’s Chophouse", "5975 Roswell Road", "Uptown and Downtown meats", 14.8, "$$$", "Ribeye cap"));
        restaurantList.add(new restaurant(75, "O4W Pizza", "3117 Main Street", "New Jery Native Pizza", 12.2, "$$", "Classic round pies"));
        return restaurantList;
    }

    // Creat the main content used to show the information about the restaurant (CENTER)
    private HBox showRestaurantInfo(ArrayList<restaurant> listE, int wantedIndex) {
        HBox mainContent = new HBox(30);
        mainContent.setAlignment(Pos.CENTER);

        Image forRestaurantImage = new Image("deluxe_krabbyP.jpeg");        // Dummy pic holder for later pictures
        ImageView showRestaurantImage = new ImageView(forRestaurantImage);
        showRestaurantImage.setFitWidth(175);
        showRestaurantImage.setFitHeight(175);

        Label textContent = new Label(listE.get(wantedIndex).toString());
        textContent.setTextFill(Color.BLACK);

        mainContent.getChildren().addAll(textContent, showRestaurantImage);
        return mainContent;
    }

    private HBox changeRestaurantInfo(ArrayList<restaurant> listE, int wantedIndex) {
        System.out.println("Changing resta......" + wantedIndex + "  " + listE.size());
        HBox mainContent = new HBox(30);
        
        Label textContent = new Label(listE.get(wantedIndex).toString());
        textContent.setTextFill(Color.BLACK);
        ImageView imageContent = getRestaurantImage(listE, wantedIndex);

        myPaneContent.getChildren().set(0, textContent);
        myPaneContent.getChildren().set(1, imageContent);

        return mainContent;
    }
    private ImageView getRestaurantImage(ArrayList<restaurant> listE, int wantedIndex) {
        System.out.println("HKJkdsf");
        Image wantedIndexRestaImage = new Image("/restaurant75BestListPicture/restaImage" + listE.get(wantedIndex).getRank() + ".jpeg");
        ImageView showRestaurantImage = new ImageView(wantedIndexRestaImage);
        showRestaurantImage.setFitWidth(200);
        showRestaurantImage.setFitHeight(200);

        return showRestaurantImage;
    }

    // Nodes where the user can click on, drop down or pick to filter out restaurants (BOTTOM)
    private Label getDistanceLabel() {
        Label myDistanceLabel = new Label("How far away(miles)?");
        myDistanceLabel.setStyle("-fx-font-size: 11pt;");
        myDistanceLabel.setAlignment(Pos.CENTER_RIGHT);
        return myDistanceLabel;
    }
    private Label getPriceLabel() {
        Label myPriceLabel = new Label("How much?");
        myPriceLabel.setStyle("-fx-font-size: 11pt;");
        myPriceLabel.setAlignment(Pos.CENTER_RIGHT);
        return myPriceLabel;
    }
    private ChoiceBox<Double> getDistanceChoiceBox() {
        ChoiceBox<Double> myDistance = new ChoiceBox<>();
        myDistance.getItems().add(1.0);
        myDistance.getItems().add(3.0);
        myDistance.getItems().add(5.0);
        myDistance.getItems().add(10.0);
        myDistance.getItems().add(20.0);
        return myDistance;
    }
    private ChoiceBox<String> getPriceChoiceBox() {
        ChoiceBox<String> myPrice = new ChoiceBox<>();
        myPrice.getItems().add("$");
        myPrice.getItems().add("$$");
        myPrice.getItems().add("$$$");
        myPrice.getItems().add("$$$$");
        return myPrice;
    }


    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restaurant Recommender in Atlanta");
        primaryStage.setScene(new Scene(getPane(), 800, 450));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}