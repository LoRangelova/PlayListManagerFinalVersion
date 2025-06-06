//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JButton next  = new JButton("Next");
        panel.add(next);

        JButton previous  = new JButton("Previous");
        panel.add(previous);

        JButton sort  = new JButton("Sort");
        panel.add(sort);

        JButton displayDetails = new JButton("Display Details");
        panel.add(displayDetails);

        JButton search = new JButton("Search");
        panel.add(search);

        JTextField searchBytext = new JTextField();
        panel.add(searchBytext);

    }
}

class Album{
    int year;
    String title;
    String genre;
    Double profitMade;
    String recordLabel;
    Artist artist;

    public Album(int year, String title, String genre, Double profitMade, String recordLabel, Artist artist) {

        this.year = year;
        this.title = title;
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
    double songLength;
    int numberOflisteners;

    public Song(int year, String title, String genre, Double profitMade, String recordLabel, Artist artist, double songLength, int numberOflisteners) {
        super(year, title, genre, profitMade, recordLabel, artist);
        this.songLength = songLength;
        this.numberOflisteners = numberOflisteners;
    }
}