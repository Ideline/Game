import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Highscore{

    public Highscore(){
       this.mapHighscore = new ArrayList<HighscoreEntry>();
       this.totalHighscore = new ArrayList<HighscoreEntry>();
       this.level = level;
    }

    private ArrayList<HighscoreEntry> mapHighscore;
    private ArrayList<HighscoreEntry> totalHighscore;
    private int level = 0;

    public ArrayList<HighscoreEntry> getMapHighscore() {
        return mapHighscore;
    }

    public ArrayList<HighscoreEntry> getTotalHighscore() {
        return totalHighscore;
    }

    public void createHighscoreLists(){
        try {
            String path = Paths.get(".").toAbsolutePath().normalize().toString();
            String tempMapHighscore = new String(Files.readAllBytes(Paths.get(path + "/maps/map2/2mapScoreEasy.map")));
            String tempTotalHighscore = new String(Files.readAllBytes(Paths.get(path + "/maps/totalHighscoreEasy.map")));

            ArrayList<String> mapHighscoreTemp = new ArrayList<String>(Arrays.asList(tempMapHighscore.split("\r\n")));
            ArrayList<String> totalHighscoreTemp = new ArrayList<String>(Arrays.asList(tempTotalHighscore.split("\r\n")));

            for(String a : mapHighscoreTemp) {
                String[] mapParts = a.split(":");
                mapHighscore.add(new HighscoreEntry(mapParts[0], Integer.parseInt((mapParts[1]).replace(" ", ""))));
            }

            for(String a : totalHighscoreTemp) {
                String[] mapParts = a.split(":");
                totalHighscore.add(new HighscoreEntry(mapParts[0], Integer.parseInt((mapParts[1]).replace(" ", ""))));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNewMapHighScore(){
        int highscore = Game.stats.getMapScore();
        if(mapHighscore == null || (mapHighscore != null && mapHighscore.size() < 5)) {
            mapHighscore.add(new HighscoreEntry(Game.playerName, highscore));
            sortHighscoreLists();
            Game.highscore.printToMapHighscoreFile();
            return true;
        }
        else {
            for (int i = 0; i < mapHighscore.size(); i++) {
                if(highscore > mapHighscore.get(i).getHighscore()){
                    mapHighscore.remove(4);
                    mapHighscore.add(new HighscoreEntry(Game.playerName, highscore));
                    sortHighscoreLists();
                    Game.highscore.printToMapHighscoreFile();
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isNewTotalHighScore(){
        Game.stats.setTotalScore();
        int highscore = Game.stats.getTotalScore();
        if(totalHighscore == null || (totalHighscore != null && totalHighscore.size() < 5)) {
            totalHighscore.add(new HighscoreEntry(Game.playerName, highscore));
            sortHighscoreLists();
            printToTotalHighscoreFile();
            return true;
        }
        else {
            for (int i = 0; i < totalHighscore.size(); i++) {
                if(highscore > totalHighscore.get(i).getHighscore()){
                    totalHighscore.remove(4);
                    totalHighscore.add(new HighscoreEntry(Game.playerName, highscore));
                    sortHighscoreLists();
                    printToTotalHighscoreFile();
                    return true;
                }
            }
            return false;
        }
    }


    public void sortHighscoreLists(){
        Collections.sort(mapHighscore);
        Collections.sort(totalHighscore);
    }

    // https://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
    public void printToMapHighscoreFile(){

        String FILENAME = Paths.get(".").toAbsolutePath().normalize().toString() + "/maps/map2/2mapScoreEasy.map";
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            String content = "";
            for(int i = 0; i < mapHighscore.size(); i++){
                content = content + mapHighscore.get(i);
            }

            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void printToTotalHighscoreFile(){

        String FILENAME = Paths.get(".").toAbsolutePath().normalize().toString() + "/maps/totalHighscoreEasy.map";
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            String content = "";
            for(int i = 0; i < totalHighscore.size(); i++){
                content = content + totalHighscore.get(i);
            }

            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String toString(ArrayList<HighscoreEntry> highscoreList){

        String highscore = "";
        for(int i = 0; i < highscoreList.size(); i++){
            highscore = highscore + highscoreList.get(i);
        }
        return highscore;
    }
}
