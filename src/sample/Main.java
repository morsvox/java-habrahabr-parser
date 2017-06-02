package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxStarter(primaryStage, "");
    }

    private void fxStarter(Stage primaryStage, String type){
        VBox vbox = new VBox();
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
        btns.add("php");
        btns.add("java");
        btns.add("python");
        btns.add("top");

        for (int i = 0; i < btns.size(); i++) {
            String name = btns.get(i);
            Button php = new Button(name);
            php.setWrapText(true);
            //вешаем событие на клик
            php.setOnAction(actionEvent ->  {
                fxStarter(primaryStage,name);
            });
            //пихаем в вертикальный список кнопку
            vbox.getChildren().add(php);
        }

        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private static Elements parse(String type){
        String url = "";
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
                case "top":
                    url = "top/";
                    break;
            }
        }
        Document doc = null;
        try {
            doc = Jsoup.connect("http://habrahabr.ru/"+url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.select(".post__title_link");
    }
}
