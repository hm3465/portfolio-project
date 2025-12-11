import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlaylistTest {

    /**
     * Test size on empty playlist.
     */
    @Test
    public void testSizeEmpty() {
        Playlist p = new PlaylistManager1L();
        assertEquals(0, p.size());
    }

    /**
     * Test size with songs.
     */
    @Test
    public void testSizeNonEmpty() {
        Playlist p = new PlaylistManager1L();
        p.addSong("A", "B");
        p.addSong("C", "D");

        assertEquals(2, p.size());
    }

    /**
     * Test sortByArtist. Input: "B_Artist", "A_Artist", "C_Artist" Expected:
     * "A_Artist", "B_Artist", "C_Artist"
     */
    @Test
    public void testSortByArtist() {
        Playlist p = new PlaylistManager1L();
        p.addSong("Song1", "Drake"); // Middle
        p.addSong("Song2", "Adele"); // First
        p.addSong("Song3", "The Weeknd"); // Last

        p.sortByArtist();

        // Expected order: Adele, Drake, The Weeknd
        String expected = "[ {Song2 - Adele}, {Song1 - Drake}, {Song3 - The Weeknd} ]";
        assertEquals(expected, p.toString());
    }

    /**
     * Test sortByArtist when already sorted. Ensures method doesn't corrupt
     * data.
     */
    @Test
    public void testSortByArtistAlreadySorted() {
        Playlist p = new PlaylistManager1L();
        p.addSong("A", "ArtistA");
        p.addSong("B", "ArtistB");

        // Create copy to verify state didn't change
        String expected = p.toString();

        p.sortByArtist();

        assertEquals(expected, p.toString());
    }

    /**
     * Test shuffle. Hard to test randomness, but we can verify: 1. Size is
     * unchanged. 2. Songs are not lost (Permutation property).
     */
    @Test
    public void testShufflePreservesData() {
        Playlist p = new PlaylistManager1L();
        p.addSong("S1", "A1");
        p.addSong("S2", "A2");
        p.addSong("S3", "A3");

        int initialSize = p.size();

        p.shuffle();

        // 1. Verify size is maintained
        assertEquals(initialSize, p.size());

        // 2. Verify all strings are still present in the representation
        // (Simple existence check since order is unpredictable)
        String rep = p.toString();
        assertTrue(rep.contains("S1 - A1"));
        assertTrue(rep.contains("S2 - A2"));
        assertTrue(rep.contains("S3 - A3"));
    }

    /**
     * Test showPlaylist. Since this prints to stdout and modifies nothing, we
     * verify that the state remains exactly the same.
     */
    @Test
    public void testShowPlaylistPreservesState() {
        Playlist p = new PlaylistManager1L();
        p.addSong("Song", "Artist");

        String expected = p.toString();

        p.showPlaylist(); // visual check only

        assertEquals(expected, p.toString());
    }
}