package oop;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Quiz extends Application {
    List<String> definitsioonid_küsimused = Arrays.asList("Maatriks, mille ridade ja veergude arv on võrdne, m=n", "Mis on determinandi väärtus kahe võrdse või võrdelise rea korral?",
            "Maatriks mille peadiagonaali elemendid on kõik ühed ja kõrvaldiagonaali elemendid 0-d nimetatakse?","Ruutmaatriks, mille determinant võrdub 0-ga nimetatakse?",
            "Maatriksil eksisteerib pöördmaatriks parajasti siis, kui maatriks on ?", "nullmaatriksi astak on?", "Süsteem, millel lahend puudub nimetatakse",
            "Paarisfunktsiooni graafik on sümmetriline mis telje suhtes?", "limx->0 sinx/x=",
            "limx->inf(1+1/x)**x=", "Konstandi tuletis on alati", "(u/v)'=");
    List<String>  definitsioonid_vastused = Arrays.asList("ruutmaatriks","0","ühikmaatriksiks", "singulaarseks", "regulaarne",
            "0", "vastuoluliseks", "y", "1", "e", "0", "(u'v-uv')/v**2");


    List<String> küsimused = new LinkedList<String>();
    List<String> vastused = new LinkedList<String>();

    GridPane grid = new GridPane();
    Label silt = new Label();
    TextField vastuseVäli = new TextField();
    TextField tagasiside = new TextField();

    //vBox skoori ja küsimuse siltide jaoks
    VBox vBox = new VBox(10);


    Label skoor = new Label();
    Label mitmes = new Label();

    Stage pealava = new Stage();


    Samaväärsused samaväärsused =  new DEF(küsimused, vastused, 0);
    @Override
    public void start(Stage pealava) {
        väärtusta();
        int mitmesküsimus = 1;
        küsi(mitmesküsimus);
    }

    private void väärtusta() {
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


        grid.add(vastuseVäli, 0,3);
        grid.add(tagasiside, 0,2);
        skoor.setText("Skoor: "+samaväärsused.getSkoor());
        mitmes.setText("Küsimus: 1");

        //siltide paigutamine
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
        return;
    }

    public void küsi(int i) {
        if(i-1==3){
            pealava.close();
            return;
        }else {


            int pikkus = vastused.size();
            int indeks = samaväärsused.suvalineKüsimus(pikkus);
            String küsimus = küsimused.remove(indeks); //väärtustame küsimuse ja vastuse ning koheselt eemaldame listist
            String vastus = vastused.remove(indeks);
            silt.setText(küsimus);

            vastuseVäli.setOnKeyPressed(new EventHandler<KeyEvent>() {//kasutaja sisestab vastuse
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        tagasiside.setText(samaväärsused.kontrolliVastus(vastuseVäli.getText(), vastus));
                        skoor.setText("Skoor: "+samaväärsused.getSkoor());
                        mitmes.setText("Küsimus: "+(i+1));
                        vastuseVäli.setText("");
                        küsi(i+1);

                    }
                }
            });
        }



    }

    public static void main(String[] args) {
        launch(args);
    }






}
