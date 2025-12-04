public abstract class PlaylistManagerSecondary implements Playlist {
    @Override
    public abstract void transferFrom(PlaylistManager source);
    @Override
    public void shuffle() {
        assert !this.isEmpty() : "Violation of: playlist is not empty";
        //Copy all songs into a temporary playlist
        PlaylistManager temp = this.newInstance();
        PlaylistManager copy = this.newInstance();
        copy.transferFrom(this);
        //Move songs between temp and this
        int count = 0;
        while (!copy.isEmpty()) {
            //Using mod to shuffle
            if (count % 2 == 0) {
                copy.playNext();
            } else {
                temp.transferFrom(copy);
            }
            count++;
        }
        //Transfer back so playlist isn’t empty
        this.transferFrom(temp);
    }
    @Override
    public void sortByArtist() {
        assert !this.isEmpty() : "Violation of: playlist is not empty";
        PlaylistManager sorted = this.newInstance();
        PlaylistManager temp = this.newInstance();
        //Move elements in ascending order
        while (!this.isEmpty()) {
            this.playNext();
            sorted.transferFrom(temp);
        }
        this.transferFrom(sorted);
    }
    @Override
    public void showPlaylist() {
        System.out.println(this.toString());
    }
    @Override
    public int size() {
        int start = 0;
        PlaylistManager temp = this.newInstance();
        temp.transferFrom(this);
        while (!temp.isEmpty()) {
            temp.playNext();
            start++;
        }
        return start;
    }
    @Override
    public String toString() {
        PlaylistManager temp = this.newInstance();
        temp.transferFrom(this);
        StringBuilder result = new StringBuilder("[ ");
        while (!temp.isEmpty()) {
            assert !temp.isEmpty() : "Violation of: temp is not empty";
            temp.playNext();
            result.append("(song) ");
        }
        result.append("]");
        return result.toString();
    }
}
