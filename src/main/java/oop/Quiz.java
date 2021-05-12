package oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Quiz extends Application {
    /*List<String> definitsioonid_küsimused = Arrays.asList("Maatriks, mille ridade ja veergude arv on võrdne, m=n", "Mis on determinandi väärtus kahe võrdse või võrdelise rea korral?",
            "Maatriks mille peadiagonaali elemendid on kõik ühed ja kõrvaldiagonaali elemendid 0-d nimetatakse?","Ruutmaatriks, mille determinant võrdub 0-ga nimetatakse?",
            "Maatriksil eksisteerib pöördmaatriks parajasti siis, kui maatriks on ?", "nullmaatriksi astak on?", "Süsteem, millel lahend puudub nimetatakse",
            "Paarisfunktsiooni graafik on sümmetriline mis telje suhtes?", "limx->0 sinx/x=",
            "limx->inf(1+1/x)**x=", "Konstandi tuletis on alati", "(u/v)'=");
            */
    List<String>  definitsioonid_vastused = Arrays.asList("ruutmaatriks","0","ühikmaatriksiks", "singulaarseks", "regulaarne",
            "0", "vastuoluliseks", "y", "1", "e", "0", "(u'v-uv')/v**2");

    List<String> definitsioonid_küsimused = getDefinitsioonid_küsimused();




    List<String> küsimused = new LinkedList<String>();
    List<String> vastused = new LinkedList<String>();


    GridPane grid0 = new GridPane(); //avaleht
    GridPane grid1 = new GridPane(); //kokkuvõte


    GridPane grid = new GridPane();
    Label silt = new Label();
    TextField vastuseVäli = new TextField();
    TextField tagasiside = new TextField();
    Label viga = new Label();

    //vBox skoori ja küsimuse siltide jaoks
    VBox vBox = new VBox(10);


    Label skoor = new Label();
    Label mitmes = new Label();

    Label kokkuvõte;
    Button välju;
    Button teeUuesti;
    Scene stseen1;
    HBox nupud;
    VBox kv;

    Stage pealava = new Stage();




    Samaväärsused samaväärsused =  new DEF(küsimused, vastused, 0);

    public Quiz() throws IOException {
    }

    @Override
    public void start(Stage pealava) {

        avaleht();

    }
    public void avaleht(){
        Label alguseInfo = new Label();
        alguseInfo.setWrapText(true);
        Button alusta = new Button("Alusta");

        alguseInfo.resize(100, 50);
        alguseInfo.setText("Testis küsitakse 10 küsimust. Kui oled valmis, vajuta 'Alusta'");


        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(20);
        v.getChildren().addAll(alguseInfo, alusta );
        v.prefHeightProperty().bind(grid0.heightProperty());
        v.prefWidthProperty().bind(grid0.widthProperty());

        alusta.prefWidthProperty().bind(v.widthProperty().divide(3));
        alusta.prefHeightProperty().bind(v.heightProperty().divide(7));




        grid0.setAlignment(Pos.CENTER);
        grid0.add(v, 0, 0);
        Scene stseen0 = new Scene(grid0, 350, 250);
        alusta.setOnMouseClicked(event -> väärtusta());
        pealava.setScene(stseen0);
        pealava.show();




    }
    public void väärtusta() {


        küsimused.addAll(definitsioonid_küsimused);
        vastused.addAll(definitsioonid_vastused);

        grid.setAlignment(Pos.CENTER); //gridi positsioon

        ColumnConstraints col1 = new ColumnConstraints(300);
        col1.setPercentWidth(75);
        RowConstraints row1 = new RowConstraints(100);
        row1.setPercentHeight(75);//read ja veerud reageerivad akna suuruse muutustele (täidavad alati 75%)
        grid.getRowConstraints().add(row1);
        grid.getColumnConstraints().add(col1);
        silt.setWrapText(true); //ridade murdmine
        silt.prefHeightProperty().bind(grid.heightProperty());


        grid.add(vastuseVäli, 0,3);
        grid.add(tagasiside, 0,2);
        skoor.setText("Skoor: "+samaväärsused.getSkoor());
        mitmes.setText("Küsimus: 1");

        //siltide paigutamine
        vBox.getChildren().add(viga);
        vBox.getChildren().addAll(skoor, mitmes);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(silt);

        grid.add(vBox, 0, 0);
        //grid.setGridLinesVisible(true);

        Scene stseen1 = new Scene(grid, 350, 250, Color.SNOW);

        pealava.setTitle("Test");
        pealava.setResizable(true);
        pealava.setScene(stseen1);
        pealava.show();
        alusta();
        return;
    }
    public void alusta(){
        int mitmesküsimus = 1;
        küsi(mitmesküsimus);
    }
    public void küsi(int i) {
        if(i-1==3){
           // pealava.close();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(); //
            }
            kokkuvõte(i);
            return;
        }else {


            int pikkus = vastused.size();
            int indeks = samaväärsused.suvalineKüsimus(pikkus-1);
            String küsimus = küsimused.remove(indeks); //väärtustame küsimuse ja vastuse ning koheselt eemaldame listist
            String vastus = vastused.remove(indeks);
            silt.setText(küsimus);


                vastuseVäli.setOnKeyPressed(new EventHandler<KeyEvent>() {//kasutaja sisestab vastuse
                    public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                String sisestus = vastuseVäli.getText();
                                try {
                                    tagasiside.setText(samaväärsused.kontrolliVastus(sisestus, vastus));
                                    skoor.setText("Skoor: " + samaväärsused.getSkoor());
                                    mitmes.setText("Küsimus: " + (i + 1));
                                    vastuseVäli.setText("");
                                    viga.setText("");
                                    küsi(i + 1);


                                } catch (NullSisestusErind e) {
                                    viga.setText("Sisestatud vigane info! " + e.getMessage());
                                    System.out.println("Sisestatud vigane info! " + e.getMessage());
                                    sisestus = vastuseVäli.getText();

                                }

                            }

                    }
                });
            }



    }
    public void kokkuvõte(int mitmes){
        kokkuvõte = new Label("Test läbi. "); //ajutiselt kuniks logifail pole veel valmis
        //kokkuvõte = new Label("Test läbi. Tulemus on salvestatud faili tulemus.txt");
        välju = new Button("Välju");
        teeUuesti = new Button("Tee Uuesti");

        välju.setOnMouseClicked(event -> pealava.close());
        teeUuesti.setOnMouseClicked(event -> uusMäng());

        nupud = new HBox();
        nupud.setAlignment(Pos.CENTER);
        nupud.setSpacing(20);
        kv = new VBox();
        kv.setSpacing(20);
        nupud.getChildren().addAll(välju, teeUuesti);
        kv.getChildren().addAll(kokkuvõte, nupud);

        grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.add(kv, 0, 0);
        stseen1 = new Scene(grid1, 350, 250);
        pealava.setScene(stseen1);
        pealava.show();

    }
    public void uusMäng(){
        pealava.close();
        küsimused.clear();
        vastused.clear();
        //küsimused.addAll(definitsioonid_küsimused);
        //vastused.addAll(definitsioonid_vastused);
        grid = new GridPane();
        silt = new Label();
        vastuseVäli = new TextField();
        tagasiside = new TextField();
        vBox = new VBox(10);
        skoor = new Label();
        mitmes = new Label();
        pealava = new Stage();
        samaväärsused =  new DEF(küsimused, vastused, 0);

        väärtusta();

    }

    public static void main(String[] args) {
        launch(args);
    }
    public  static ArrayList<String> getDefinitsioonid_küsimused() throws IOException {
        ArrayList<String> definitsioonid_küsimused = new ArrayList<>();
        try(BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\karli\\IdeaProjects\\OOP\\Projekt1\\src\\must-ruut-develop\\src\\küsimused.txt"))){
            String line;
            while((line = in.readLine())!=null){
                String[] pair = line.split(";");
                definitsioonid_küsimused.add(pair[0]);
            }
        }
        return definitsioonid_küsimused;
    }





}
