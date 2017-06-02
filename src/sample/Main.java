package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox vbox = new VBox();
        Elements newsHeadlines = parse();
        for (Element link : newsHeadlines) {
            String linkHref = link.attr("href");
            String linkText = link.text();

            //создаем кнопку с текстом и ставим пораметр сокращать текст
            Button btn = new Button(linkText);
            btn.setWrapText(true);
            //вешаем событие на клик
            btn.setOnAction(actionEvent ->  {
                getHostServices().showDocument(linkHref);
            });
            //пихаем в вертикальный список кнопку
            vbox.getChildren().add(btn);

        }
        vbox.setSpacing(10);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static Elements parse(){
        Document doc = null;
        try {
            doc = Jsoup.connect("http://habrahabr.ru/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc.select(".post__title_link");
    }
}
