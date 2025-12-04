import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Implementation of PlaylistManager using only OSU components.
 *
 * Representation:
 *   rep = < title0, artist0, title1, artist1, ..., titleN-1, artistN-1 >
 *
 * Convention:
 *   rep.length() is even.
 *
 * Correspondence:
 *   playlist = sequence of songs where
 *       song k has title = rep.entry(2*k)
 *                       artist = rep.entry(2*k + 1)
 */
public class PlaylistManager1L extends PlaylistManagerSecondary {

    /**
     * Representation: uses OSU Sequence<String>
     * Invariant: rep.length() % 2 == 0
     */
    private Sequence<String> rep;

    /**
     * Constructor: initialize empty playlist.
     */
    public PlaylistManager1L() {
        this.rep = new Sequence1L<>();
        assert this.rep.length() % 2 == 0 : "Invariant violated: size must be even";
    }
    @Override
    public PlaylistManager1L newInstance() {
        return new PlaylistManager1L();
    }

    @Override
    public void transferFrom(PlaylistManager source) {
        if (source == this) {
            return;
        }

        if (!(source instanceof PlaylistManager1L)) {
            throw new IllegalArgumentException(
                "transferFrom requires a PlaylistManager1L (same implementation)"
            );
        }

        PlaylistManager1L src = (PlaylistManager1L) source;

        this.rep = src.rep;
        src.rep = new Sequence1L<>();
    }

    @Override
    public void addSong(String title, String artist) {

        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Invalid title");
        }
        if (artist == null || artist.equals("")) {
            throw new IllegalArgumentException("Invalid artist");
        }

        // add to end of sequence
        this.rep.add(this.rep.length(), title);
        this.rep.add(this.rep.length(), artist);

        assert this.rep.length() % 2 == 0 : "Invariant violated after addSong";
    }

    @Override
    public void removeSong(String title) {

        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Invalid title");
        }

        boolean removed = false;

        for (int i = 0; i < this.rep.length() && !removed; i += 2) {

            String storedTitle = this.rep.entry(i);

            if (storedTitle.equalsIgnoreCase(title)) {
                this.rep.remove(i);
                this.rep.remove(i);
                removed = true;
            }
        }

        assert this.rep.length() % 2 == 0 : "Invariant violated after removeSong";
    }

    @Override
    public boolean isEmpty() {
        return this.rep.length() == 0;
    }

    @Override
    public void playNext() {
        if (this.rep.length() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }

        String title = this.rep.remove(0);
        String artist = this.rep.remove(0);

        System.out.println("Playing: " + title + " by " + artist);

        assert this.rep.length() % 2 == 0 : "Invariant violated after playNext";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");

        for (int i = 0; i < this.rep.length(); i += 2) {
            sb.append("{");
            sb.append(this.rep.entry(i));
            sb.append(" - ");
            sb.append(this.rep.entry(i + 1));
            sb.append("}");
            if (i + 2 < this.rep.length()) {
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
