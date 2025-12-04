import java.util.LinkedList;
import java.util.Objects;

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
     *Invariant: rep.size() % 2 == 0
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
        //Requires that source is a PlaylistManager1L to access its rep
        if (!(source instanceof PlaylistManager1L)) {
            // Fallback: use the public kernel methods to move elements out of source
            PlaylistManager temp = this.newInstance();
            // move elements one-by-one from source into temp, then from temp into this
            while (!source.isEmpty()) {
                // we need a way to inspect title/artist pair from source;
                // assume playNext() removes the first song (consumes), so we cannot reconstruct easily.
                // Therefore, prefer that source is same implementation; otherwise throw for clarity.
                throw new IllegalArgumentException(
                    "transferFrom requires source to be a PlaylistManager1L (same implementation)");
            }
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
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < this.rep.size(); i += 2) {
            sb.append("{");
            sb.append(this.rep.get(i));
            sb.append(" - ");
            sb.append(this.rep.get(i + 1));
            sb.append("}");
            if (i + 2 < this.rep.size()) {
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
