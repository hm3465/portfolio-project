/**
 * A Use Case demonstrating the Playlist component in a direct application. * A
 * party hosts a list of requested songs, sorts them to check for duplicates,
 * shuffles them for variety, and then plays them.
 */
public class PartyPlaylist {

    public static void main(String[] args) {
        Playlist partyMix = new PlaylistManager1L();

        System.out.println("--- Setup Phase ---");

        partyMix.addSong("Humble", "Kendrick Lamar");
        partyMix.addSong("Sky", "Playboi Carti");
        partyMix.addSong("As It Was", "Harry Styles");
        partyMix.addSong("Dreams", "Fleetwood Mac");
        partyMix.addSong("Rich Flex", "Drake");
        System.out.println("Reviewing requests (Sorted by Artist):");
        partyMix.sortByArtist();
        partyMix.showPlaylist();
        System.out.println("\nShuffling for the party...");
        partyMix.shuffle();
        partyMix.showPlaylist();

        System.out.println("\n--- Party Started! ---");

        while (!partyMix.isEmpty()) {
            partyMix.playNext();
        }

        System.out.println("\nParty over! Playlist is empty.");
    }
}