/**
 * Abstract secondary class for PlaylistManager.
 *
 * Since the kernel interface's playNext() is destructive, we cannot implement
 * non-destructive operations at the secondary level. All such operations must
 * be implemented in concrete classes where we have access to the internal representation.
 */
public abstract class PlaylistManagerSecondary implements Playlist {

    /**
     * Must be implemented by concrete classes
     */
    @Override
    public abstract void transferFrom(PlaylistManager source);

    /**
     * Must be implemented by concrete classes
     */
    public abstract PlaylistManager newInstance();


    @Override
    public abstract void shuffle();

    @Override
    public abstract void sortByArtist();

    @Override
    public abstract int size();

    @Override
    public abstract String toString();

    /**
     * Shows the playlist by delegating to toString()
     */
    @Override
    public void showPlaylist() {
        System.out.println(this.toString());
    }
}