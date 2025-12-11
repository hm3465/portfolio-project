import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Playlist.PlaylistManager1L;
import Playlist.PlaylistManagerKernel;

/**
 * Test cases for the PlaylistManagerKernel component.
 */
public class PlaylistManagerKernelTest {

    /**
     * Test isEmpty on a newly created playlist.
     */
    @Test
    public void testIsEmptyTrue() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        String expected = "[  ]";

        assertEquals(true, p.isEmpty());
        assertEquals(expected, p.toString());
    }

    /**
     * Test isEmpty on a non-empty playlist.
     */
    @Test
    public void testIsEmptyFalse() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("Title", "Artist");

        assertEquals(false, p.isEmpty());
    }

    /**
     * Test addSong with a single song.
     */
    @Test
    public void testAddSongOne() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("Sky", "Playboi Carti");

        String expected = "[ {Sky - Playboi Carti} ]";

        assertEquals(expected, p.toString());
    }

    /**
     * Test addSong with multiple songs to ensure order is preserved (FIFO).
     */
    @Test
    public void testAddSongMultiple() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("Song1", "Artist1");
        p.addSong("Song2", "Artist2");

        String expected = "[ {Song1 - Artist1}, {Song2 - Artist2} ]";

        assertEquals(expected, p.toString());
    }

    /**
     * Test removeSong removing the only song in the list.
     */
    @Test
    public void testRemoveSongLastOne() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("Sky", "Playboi Carti");

        p.removeSong("Sky");

        String expected = "[  ]";
        assertEquals(expected, p.toString());
        assertTrue(p.isEmpty());
    }

    /**
     * Test removeSong removing a song from the middle of the list.
     */
    @Test
    public void testRemoveSongMiddle() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("First", "A1");
        p.addSong("Second", "A2");
        p.addSong("Third", "A3");

        p.removeSong("Second");

        String expected = "[ {First - A1}, {Third - A3} ]";
        assertEquals(expected, p.toString());
    }

    /**
     * Test playNext. According to contract: removes the first song and "plays"
     * it.
     */
    @Test
    public void testPlayNext() {
        PlaylistManagerKernel p = new PlaylistManager1L();
        p.addSong("First", "A1");
        p.addSong("Second", "A2");

        p.playNext();

        String expected = "[ {Second - A2} ]";
        assertEquals(expected, p.toString());
    }
}
