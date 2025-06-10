//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PlaylistManager {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.


     JFrame frame = new JFrame("Playlist Manager");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setVisible(true);

        JLabel title = new JLabel("Playlist");
        panel.add(title);

        JButton displayDetails = new JButton("Display Details");
        panel.add(displayDetails);

        JButton add = new JButton("Add song");
        panel.add(add);

        JButton remove = new JButton("Remove song");
        panel.add(remove);

        JButton update = new JButton("Update song popularity");
        panel.add(update);

        JButton next  = new JButton("Next");
        panel.add(next);

        JButton previous  = new JButton("Previous");
        panel.add(previous);

        JButton play = new JButton("Play");
        panel.add(play);

        JButton stop = new JButton("Stop");
        panel.add(stop);

        JButton sort  = new JButton("Sort");
        panel.add(sort);

        JButton search = new JButton("Search");
        panel.add(search);

        JTextField searchBytext = new JTextField();
        panel.add(searchBytext);

    }
}

class Playlist {
    ArrayList<Song> songs;
    int currentIndex = -1;

    public Playlist() {
        songs = new ArrayList<>();
    }
    public void addSong(Song song1){
        songs.add(song1);
        System.out.println("Song added");
    }

    public void removeSong(String name){
        for(int i =0; i<songs.size(); i++) {
            if (songs.get(i).songName.equalsIgnoreCase(name)){
                songs.remove(i);
                System.out.println("Song removed");
            }
            else{
                System.out.println("Song not found");
            }
        }
    }

    public void updateSongPopularity(String name, int newNumberOfListeners){
        for(Song song1: songs){
            if(song1.songName.equalsIgnoreCase(name)){
                song1.numberOflisteners = newNumberOfListeners;
                System.out.println("Popularity updated");
            }
            else{
                System.out.println("Song not found");
            }
        }
    }

    public void listSongs(){
        if(songs.size() == 0){
            System.out.println("No songs found. This playlist is empty.");
        }
        for(Song song1: songs){
            System.out.println(song1);
        }
    }

    public void uploadSongToFile(Song song, String fileName){
        try{
            FileWriter writer = new FileWriter(fileName, true);

                writer.write(song.toString());

            writer.close();
            System.out.println(" Song saved to " + fileName);
        } catch(IOException e){
            System.out.println(e);
        }
    }

    public Song uploadSongFromFile(String wantedSongName, String fileName){
        try{
            File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Song song = Song.uploadFromFile(line);
            if (song != null && song.songName.equalsIgnoreCase(wantedSongName)) {
                scanner.close();
                return song;
            }
        }
        scanner.close();
        System.out.println(" Song not found.");
    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
    return null;
}
public void sortByName(){
        for(int i = 0; i < songs.size()-1; i++){
            int minIndex = i;
            for(int j = i + 1; j < songs.size(); j++){
                Song current = songs.get(j);
                Song minimum = songs.get(minIndex);

                if(current.songName.compareToIgnoreCase(minimum.songName)< 0){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                Song temp = songs.get(i);
                songs.set(i, songs.get(minIndex));
                songs.set(minIndex, temp);
            }
        }

        System.out.println("Playlist sorted alphabetically");
}

public Song findByName(String songName){
        for( Song song1: songs){
            if(song1.songName.equalsIgnoreCase(songName)){
                return song1;
            }

        }
    System.out.println("Song not found");
    return null;
}

public Song nextSong(){
        if(songs.size()==0){
            System.out.println("The playlist is empty");
            return null;
        }
        currentIndex = currentIndex + 1;
        if(currentIndex >= songs.size()){
            currentIndex = 0;
        }
        return songs.get(currentIndex);
}

public Song previousSong(){
    if(songs.size()==0){
        System.out.println("The playlist is empty");
        return null;
    }
    currentIndex = currentIndex - 1;
    if(currentIndex<0){
        currentIndex = songs.size() - 1;
    }

    return songs.get(currentIndex);
}

    }


class Album{
    int year;
     String albumTitle;
     String genre;
     Double profitMade;
     String recordLabel;
     Artist artist;

    public Album(int year, String albumTitle, String genre, Double profitMade, String recordLabel, Artist artist) {

        this.year = year;
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.profitMade = profitMade;
        this.recordLabel = recordLabel;
        this.artist = artist;
    }
        }

class Artist{
    String artistName;
    int age;
    String nationality;

    public Artist(String artistName, int age, String nationality) {
        this.artistName = artistName;
        this.age = age;
        this.nationality = nationality;
    }

}

class Song extends  Album{
    String songName;
    double songLength;
    int numberOflisteners;
    boolean isPlayling = false;

    public Song(int year, String albumTitle, String genre, Double profitMade, String recordLabel, Artist artist, double songLength, int numberOflisteners, String songName) {
        super(year, albumTitle, genre, profitMade, recordLabel, artist);
        this.songLength = songLength;
        this.numberOflisteners = numberOflisteners;
        this.songName = songName;
    }

    public String toString(){
        return super.toString() + "," + songName + "," + songLength + "," + numberOflisteners;
    }

    public static Song uploadFromFile(String line){
        String[] parts = line.split(",");
        if(parts.length != 11){
            System.out.println("Invalid file format");
            return null;
        }
        try{
            int year = Integer.parseInt(parts[0]);
            String albumTitle = parts[1];
            String genre = parts[2];
            double profitMade = Double.parseDouble(parts[3]);
            String recordLabel = parts[4];

            String artistName = parts[5];
            int artistAge = Integer.parseInt(parts[6]);
            String artistNationality = parts[7];
            Artist artist = new Artist(artistName, artistAge, artistNationality);

            int numberOfListeners = Integer.parseInt(parts[8]);
            double songLength = Double.parseDouble(parts[9]);
            String songName = parts[10];

            return new Song(year, albumTitle, genre, profitMade, recordLabel, artist, songLength, numberOfListeners, songName);
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }
    public void playSong(){
        if(!isPlayling){
            isPlayling = true;
            System.out.println("Playing " + songName);
        }else{
            System.out.println(songName + " is already playing");
        }
    }
    public void stopSong(){
        if(isPlayling){
            isPlayling = false;
            System.out.println("Stopping " + songName);
        }else{
            System.out.println(songName + " is not playing");
        }
    }
}
