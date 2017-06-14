package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxStarter(primaryStage, "");
    }

    private void fxStarter(Stage primaryStage, String type){
        VBox vbox = new VBox();
        HBox hmenu = new HBox();
        Group root = new Group();
        Elements newsHeadlines = parse(type);
        for (Element link : newsHeadlines) {
            String linkHref = link.attr("href");
            String linkText = link.text();

            //создаем кнопку с текстом и ставим параметр сокращать текст
            Button btn = new Button(linkText);
            btn.setWrapText(true);
            //вешаем событие на клик
            btn.setOnAction(actionEvent ->  {
                getHostServices().showDocument(linkHref);
            });
            //пихаем в вертикальный список кнопку
            vbox.getChildren().add(btn);

        }

        ArrayList<String> btns = new ArrayList<String>();
        btns.add("habrahabr");
        btns.add("php");
        btns.add("java");
        btns.add("python");
        btns.add("top");

        btns.add("geektimes");
        btns.add("DIY");
        btns.add("Гаджеты");
        btns.add("Лайфхаки");
        btns.add("IOT");
        btns.add("Android");

        for (int i = 0; i < btns.size(); i++) {
            String name = btns.get(i);
            String link = name;
            if(Objects.equals(name, "reboot")){
                link = "";
            }
            Button btn = new Button(name);
            btn.setWrapText(true);
            //вешаем событие на клик
            String finalLink = link;
            btn.setOnAction(actionEvent ->  {
                fxStarter(primaryStage, finalLink);
            });
            //пихаем в вертикальный список кнопку
            hmenu.getChildren().add(btn);
        }

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(30, 10, 10, 10));
        hmenu.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        root.getChildren().add(vbox);
        root.getChildren().add(hmenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("habrahabr&geektimes");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private static Elements parse(String type){
        String url = "all/";
        String webDomen = "habrahabr.ru";
        if( !type.isEmpty() ){
            switch (type){
                case "php":
                    url = "hub/php/";
                    break;
                case "java":
                    url = "hub/java/";
                    break;
                case "python":
                    url = "hub/python/";
                    break;
                case "geektimes":
                    webDomen = "geektimes.ru";
                    break;
                case "DIY":
                    webDomen = "geektimes.ru";
                    url = "hub/DIY/";
                    break;
                case "Гаджеты":
                    webDomen = "geektimes.ru";
                    url = "hub/gadgets/";
                    break;
                case "Лайфхаки":
                    webDomen = "geektimes.ru";
                    url = "hub/lifehacks/";
                    break;
                case "IOT":
                    webDomen = "geektimes.ru";
                    url = "hub/internet_of_things/";
                    break;
                case "Android":
                    webDomen = "geektimes.ru";
                    url = "hub/android/";
                    break;
                case "habrahabr":
                    url = "all/";
                    break;
            }
        }
        Document doc = null;
        try {
            doc = Jsoup.connect("http://"+webDomen+"/"+url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        return doc.select(".post__title_link");
    }
}
