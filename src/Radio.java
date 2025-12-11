/**
 * A Radio component that uses a Playlist as its underlying representation. *
 * Demonstrates using the Playlist component within another object to manage
 * state. The radio station manages its specific "rotation" while adding its own
 * identity.
 */
public class Radio {

    /**
     * The music rotation is represented by your Playlist component.
     */
    private Playlist rotation;

    /**
     * The name of the radio station.
     */
    private String stationName;

    /**
     * The frequency of the station.
     */
    private double frequency;

    /**
     * Constructor to initialize a new radio station.
     *
     *
     * @param name
     *             the station's call sign
     *
     * @param freq
     *            the broadcast frequency
     */
    public RadioStation(String name, double freq) {
        this.rotation = new PlaylistManager1L();
        this.stationName = name;
        this.frequency = freq;
    }

    /**
     * Adds a song to the station's upcoming lineup. This acts as a wrapper for
     * the component's addSong method.
     *
     * @param title
     *            song title
     * @param artist
     *            song artist
     */
    public void takeListenerRequest(String title, String artist) {
        System.out.println("[" + this.stationName + " " + this.frequency
                + "] Request received!");
        this.rotation.addSong(title, artist);
    }

    /**
     * Broadcasts the next track to the listeners. Handles the check for "dead
     * air" (empty playlist) internally.
     */
    public void broadcastNext() {
        if (this.rotation.isEmpty()) {
            System.out.println("...Dead Air... (The rotation is empty!)");
        } else {
            System.out.print("Now Broadcasting on " + this.frequency + ": ");
            this.rotation.playNext();
        }
    }

    /**
     * Randomizes the current rotation order. Useful for variety hours on the
     * radio.
     */
    public void mixItUp() {
        System.out.println(
                ">>> Shuffling the rotation for the Variety Hour! <<<");
        this.rotation.shuffle();
    }

    /**
     * Displays the current program schedule.
     */
    public void showSchedule() {
        System.out.println("--- Up Next on " + this.stationName + " ---");
        this.rotation.showPlaylist();
        System.out.println("----------------------------------");
    }
}