import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;


// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PlaylistManager {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.


        Playlist playlist = new Playlist();


        JFrame frame = new JFrame("Playlist Manager");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setContentPane(panel);
        frame.setSize(500, 700);


        JLabel title = new JLabel("Playlist");
        panel.add(title);




        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> songDetailsList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(songDetailsList);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 150));
        panel.add(scrollPane);


        JButton displayDetails = new JButton("Display Details");
        panel.add(displayDetails);


        displayDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModel.clear();


                ArrayList<String> details = playlist.listSongDetails();


                for (String detail : details) {
                    listModel.addElement(detail);
                }
            }
        });




        JButton add = new JButton("Add song");
        panel.add(add);


        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{




                    String songName = JOptionPane.showInputDialog(null, "Enter song name:");
                    if(songName == null || songName.isEmpty()){
                        return;
                    }


                    String artistName = JOptionPane.showInputDialog(null, "Enter artist name:");
                    if(artistName == null || artistName.isEmpty()){
                        return;
                    }


                    String artistAgeStr = JOptionPane.showInputDialog(null, "Enter artist age:");
                    if(artistAgeStr == null || artistAgeStr.isEmpty()) {
                        return;
                    }
                    int artistAge = Integer.parseInt(artistAgeStr);


                    String artistNationality = JOptionPane.showInputDialog(null, "Enter artist nationality:");
                    if(artistNationality == null || artistNationality.isEmpty()){
                        return;
                    }


                    String albumTitle = JOptionPane.showInputDialog(null, "Enter album title:");
                    if(albumTitle == null || albumTitle.isEmpty()){
                        return;
                    }


                    String yearStr = JOptionPane.showInputDialog(null, "Enter album year:");
                    if(yearStr == null || yearStr.isEmpty()){
                        return;
                    }
                    int year = Integer.parseInt(yearStr);


                    String genre = JOptionPane.showInputDialog(null, "Enter genre:");
                    if(genre == null || genre.isEmpty()) {
                        return;
                    }


                    String profitStr = JOptionPane.showInputDialog(null, "Enter profit made (number):");
                    if(profitStr == null || profitStr.isEmpty()) {
                        return;
                    }
                    double profitMade = Double.parseDouble(profitStr);


                    String recordLabel = JOptionPane.showInputDialog(null, "Enter record label:");
                    if(recordLabel == null || recordLabel.isEmpty()){
                        return;
                    }


                    String songLengthStr = JOptionPane.showInputDialog(null, "Enter song length (minutes):");
                    if(songLengthStr == null || songLengthStr.isEmpty()) {
                        return;
                    }
                    double songLength = Double.parseDouble(songLengthStr);


                    String numberOfListenersStr = JOptionPane.showInputDialog(null, "Enter number of listeners:");
                    if(numberOfListenersStr == null || numberOfListenersStr.isEmpty()){
                        return;
                    }
                    int numberOfListeners = Integer.parseInt(numberOfListenersStr);


                    Artist artist = new Artist(artistName, artistAge, artistNationality);
                    Song song = new Song(year, albumTitle, genre, profitMade, recordLabel, artist, songLength, numberOfListeners, songName);




                    playlist.addSong(song);
                }catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input entered. Song not added.");
                }
            }
        });


        JButton remove = new JButton("Remove song");
        panel.add(remove);


        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter song name to remove:");
                if (name != null && !name.isEmpty()) {
                    playlist.removeSong(name);
                }
            }
        });


        JButton update = new JButton("Update song popularity");
        panel.add(update);


        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter song name to update its popularity:");
                if(name == null || name.isEmpty()) return;


                String listenersStr = JOptionPane.showInputDialog(frame, "Enter new number of listeners:");
                if(listenersStr == null || listenersStr.isEmpty()) return;


                try {
                    int newListeners = Integer.parseInt(listenersStr);
                    playlist.updateSongPopularity(name, newListeners);
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                }
            }
        });


        ImageIcon nextIcon = new ImageIcon("next.png");
        JButton nextButton = new JButton(nextIcon);
        panel.add(nextButton);


        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Song next = playlist.nextSong();
                if (next != null) {
                    System.out.println("Now playing: " + next.songName);
                }
            }
        });




        ImageIcon previousIcon = new ImageIcon("previous.png");
        JButton previousButton = new JButton(previousIcon);
        panel.add(previousButton);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Song previous = playlist.previousSong();
                if (previous != null) {
                    System.out.println("Now playing: " + previous.songName);;
                }
            }
        });


        ImageIcon playIcon = new ImageIcon("play.png");
        JButton playButton = new JButton(playIcon);
        panel.add(playButton);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = songDetailsList.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < playlist.songs.size()) {
                    Song selectedSong = playlist.songs.get(selectedIndex);
                    selectedSong.playSong();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a song to play.");
                }
            }
        });


        ImageIcon stopIcon = new ImageIcon("stop.png");
        JButton stopButton = new JButton(stopIcon);
        panel.add(stopButton);


        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = songDetailsList.getSelectedIndex();
                if (index >= 0 && index < playlist.songs.size()) {
                    Song selected = playlist.songs.get(index);
                    selected.stopSong();


                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a song first.");
                }
            }
        });




        JButton sort  = new JButton("Sort");
        panel.add(sort);


        sort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playlist.sortByName();
            }
        });


        JButton search = new JButton("Search");
        panel.add(search);


        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String songName = JOptionPane.showInputDialog("Enter the name of the song to search:");


                if (songName == null || songName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a song name.");
                    return;
                }


                Song found = playlist.findByName(songName);


                if (found != null) {
                    JOptionPane.showMessageDialog(frame, "Found: " + found.songName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Song not found.");
                }
            }
        });


        JButton saveToFileButton = new JButton("Save to File");
        panel.add(saveToFileButton);


        saveToFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = songDetailsList.getSelectedIndex();
                Song selectedSong = null;
                if (selectedIndex >= 0 && selectedIndex < playlist.songs.size()) {
                    selectedSong = playlist.songs.get(selectedIndex);
                }
                if (selectedSong != null) {
                    String fileName = JOptionPane.showInputDialog(frame, "Enter filename to save to:");
                    if (fileName != null && !fileName.trim().isEmpty()) {
                        playlist.uploadSongToFile(selectedSong, fileName.trim());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a song to save.");
                }
            }
        });


        JButton loadFromFileButton = new JButton("Upload from File");
        panel.add(loadFromFileButton);


        loadFromFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Enter the file name to load from:");
                String songName = JOptionPane.showInputDialog(frame, "Enter the song name to load:");


                if (fileName != null && songName != null &&
                        !fileName.equals("") && !songName.equals("")) {


                    Song loadedSong = playlist.uploadSongFromFile(songName, fileName);


                    if (loadedSong != null) {
                        playlist.addSong(loadedSong);
                        listModel.clear();
                        for (String detail : playlist.listSongDetails()) {
                            listModel.addElement(detail);
                        }
                        JOptionPane.showMessageDialog(frame, "Song loaded and added to playlist.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Song not found in the file.");
                    }
                }
            }
        });


        ImageIcon decorativeIcon = new ImageIcon("picture.webp");
        JLabel decorativeLabel = new JLabel(decorativeIcon);
        decorativeLabel.setPreferredSize(new Dimension(400, 300));


        panel.add(decorativeLabel);




        frame.setVisible(true);




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






    public ArrayList<String> listSongDetails() {
        ArrayList<String> details = new ArrayList<>();
        if (songs.size() == 0) {
            details.add("No songs found. This playlist is empty.");
        } else {
            for (Song song : songs) {
                String info = "Name: " + song.songName
                        + ", Album: " + song.albumTitle
                        + ", Year: " + song.year
                        + ", Genre: " + song.genre
                        + ", Length: " + song.songLength + " mins"
                        + ", Listeners: " + song.numberOflisteners
                        + ", Artist: " + song.artist.artistName
                        + ", Record Label: " + song.recordLabel;
                details.add(info);
            }
        }
        return details;
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


class Song extends Album{
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
        return year + "," + albumTitle + "," + genre + "," + profitMade + "," + recordLabel + "," +
                artist.artistName + "," + artist.age + "," + artist.nationality + "," +
                songName + "," + songLength + "," + numberOflisteners;
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


            String songName = parts[8];
            double songLength = Double.parseDouble(parts[9]);
            int numberOfListeners = Integer.parseInt(parts[10]);


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
