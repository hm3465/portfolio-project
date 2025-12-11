import java.util.LinkedList;
import java.util.Objects;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementation of the PlaylistManager component.
 *
 * Convention:
 *   - this.rep is a LinkedList<String> that stores alternating song fields:
 *       [ title0, artist0, title1, artist1, ..., titleN-1, artistN-1 ]
 *   - rep.size() is always even (0, 2, 4, ...).
 *
 * Correspondence:
 *   - The abstract playlist is the sequence of songs:
 *       for k in 0..(n-1): song k has title = rep.get(2*k), artist = rep.get(2*k + 1)
 *
 * Rationale:
 *   - Using a LinkedList<String> keeps insertion/removal at both ends O(1)
 *     and matches the "queue of fields" representation you described.
 */
public class PlaylistManager1L extends PlaylistManagerSecondary {

    /**
     * Invariant: rep.size() % 2 == 0
     */
    private LinkedList<String> rep;

    /**
     * Constructor: create empty playlist
     */
    public PlaylistManager1L() {
        this.rep = new LinkedList<>();
        assert this.rep.size() % 2 == 0 : "Representation invariant violated: size must be even";
    }

    @Override
    public PlaylistManager1L newInstance() {
        return new PlaylistManager1L();
    }

    @Override
    public void transferFrom(PlaylistManager source){
        if (source == this) {
            return;
        }

        // Requires that source is a PlaylistManager1L to access its rep
        if (!(source instanceof PlaylistManager1L)) {
            throw new IllegalArgumentException(
                "transferFrom requires source to be a PlaylistManager1L (same implementation)");
        }

        PlaylistManager1L src = (PlaylistManager1L) source;
        this.rep = src.rep;
        src.rep = new LinkedList<>();
    }

    @Override
    public void addSong(String title, String artist) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(artist, "artist must not be null");
        if (title.isEmpty()) {
            throw new IllegalArgumentException("title must not be empty");
        }
        if (artist.isEmpty()) {
            throw new IllegalArgumentException("artist must not be empty");
        }

        this.rep.addLast(title);
        this.rep.addLast(artist);
        assert this.rep.size() % 2 == 0 : "Representation invariant violated after addSong";
    }

    @Override
    public void removeSong(String title) {
        Objects.requireNonNull(title, "title must not be null");
        if (title.isEmpty()) {
            throw new IllegalArgumentException("title must not be empty");
        }

        for (int i = 0; i < this.rep.size(); i += 2) {
            String t = this.rep.get(i);
            if (t.equalsIgnoreCase(title)) {
                this.rep.remove(i);
                this.rep.remove(i);
                assert this.rep.size() % 2 == 0 : "Representation invariant violated after removeSong";
                return;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.rep.isEmpty();
    }

    @Override
    public void playNext() {
        if (this.rep.isEmpty()) {
            throw new IllegalStateException("playNext: playlist is empty");
        }
        String title = this.rep.removeFirst();
        String artist = this.rep.removeFirst();
        System.out.println("Playing: " + title + " by " + artist);
        assert this.rep.size() % 2 == 0 : "Representation invariant violated after playNext";
    }

    @Override
    public void shuffle() {
        if (this.rep.isEmpty()) {
            return;
        }

        // Convert pairs to a temporary list for shuffling
        ArrayList<String> temp = new ArrayList<>(this.rep);

        int numSongs = temp.size() / 2;
        for (int i = numSongs - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));

            // Swap song i with song j
            int id1 = i * 2;
            int id2 = j * 2;

            // Swap title
            String tempTitle = temp.get(id1);
            temp.set(id1, temp.get(id2));
            temp.set(id2, tempTitle);

            // Swap artist
            String tempArtist = temp.get(id1 + 1);
            temp.set(id1 + 1, temp.get(id2 + 1));
            temp.set(id2 + 1, tempArtist);
        }

        // Replace the rep with shuffled version
        this.rep = new LinkedList<>(temp);

        System.out.println("Playlist shuffled!");
        assert this.rep.size() % 2 == 0 : "Representation invariant violated after shuffle";
    }

    @Override
    public void sortByArtist() {
        if (this.rep.isEmpty()) {
            return;
        }

        // Extract songs into a sortable structure
        class SongPair implements Comparable<SongPair> {
            String title;
            String artist;

            SongPair(String title, String artist) {
                this.title = title;
                this.artist = artist;
            }

            @Override
            public int compareTo(SongPair other) {
                return this.artist.toLowerCase().compareTo(other.artist.toLowerCase());
            }
        }

        ArrayList<SongPair> songs = new ArrayList<>();
        for (int i = 0; i < this.rep.size(); i += 2) {
            songs.add(new SongPair(this.rep.get(i), this.rep.get(i + 1)));
        }

        // Sort by artist
        Collections.sort(songs);

        // Rebuild the rep with sorted songs
        this.rep.clear();
        for (SongPair song : songs) {
            this.rep.addLast(song.title);
            this.rep.addLast(song.artist);
        }

        System.out.println("Playlist sorted by artist!");
        assert this.rep.size() % 2 == 0 : "Representation invariant violated after sort";
    }

    @Override
    public int size() {
        return this.rep.size() / 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < this.rep.size(); i += 2) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("{");
            sb.append(this.rep.get(i));
            sb.append(" - ");
            sb.append(this.rep.get(i + 1));
            sb.append("}");
        }
        sb.append(" ]");
        return sb.toString();
    }
}