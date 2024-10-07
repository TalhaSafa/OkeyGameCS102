import java.util.ArrayList;
import java.util.Random;

public class OkeyGame 
{
    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;
    int currentIndexOfTilesInTheMiddle = 57;   //To keep track of tiles in the middle

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /* Eren sucuoğlu, değiştirebilirsiniz.
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int k = 0;

        for(int i = 0; i <= 56;i++){
            if(i <= 14){

                players[currentPlayerIndex].playerTiles[k] = tiles[i];
                k++;
                if(k== 14){
                    players[currentPlayerIndex].numberOfTiles = k;
                    players[currentPlayerIndex].orderTiles();
                    k = 0;
                    currentPlayerIndex++;
                }
            }
            else if( i > 14 && i <= 28){

                players[currentPlayerIndex].playerTiles[k] = tiles[i];
                k++;
                if(k == 13){
                    players[currentPlayerIndex].numberOfTiles = k;
                    players[currentPlayerIndex].orderTiles();
                    k = 0;
                    currentPlayerIndex++;
                }
            }
            else if( i < 28 && i <= 42){

                players[currentPlayerIndex].playerTiles[k] = tiles[i];
                k++;
                if(k == 13){
                    players[currentPlayerIndex].numberOfTiles = k;
                    players[currentPlayerIndex].orderTiles();
                    k = 0;
                    currentPlayerIndex++;
                }
            }
            else{
                players[currentPlayerIndex].playerTiles[k] = tiles[i];
                k++;

                if(k == 13){
                    players[currentPlayerIndex].numberOfTiles = k;
                    players[currentPlayerIndex].orderTiles();
                    k = 0;
                    currentPlayerIndex = 0;
                }
            }
        }
    }

    /*
     * Burak Yılmaz
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() 
    {
        String result = String.valueOf(lastDiscardedTile.getColor()) + String.valueOf(lastDiscardedTile.getValue());

        return result;
    }

    /*
     * Burak Yılmaz
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() 
    {
        String result = tiles[currentIndexOfTilesInTheMiddle].toString();

        currentIndexOfTilesInTheMiddle++;

        return result;
    }

    /* 
     * Cagan Aksoy
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {

        //Copying Tiles List
        Tile[] copyTile = new Tile[tiles.length];
        for(int i = 0; i < tiles.length; i++){
            copyTile[i] = tiles[i];
        }
        
        //Randomly sorted indexes 0-112
        int[] RandomIndexList = arrayCreatorRandom(tiles.length);

        //Shuffles the original tiles list with the random indexes
        for(int n = 0; n < tiles.length; n++){
            tiles[n] = copyTile[RandomIndexList[n]];
        }


    }

    /*  
     *  Cagan Aksoy
     *  Creates an array with random sort with the length of the number we enter
     */
    public int[] arrayCreatorRandom(int n){
        ArrayList<Integer> usedNumbers = new ArrayList<>(); 
        Random index = new Random();
        int[] randomIndexList = new int[n];
            for(int i = 0; i < 112; i++){
                int randomNumber = index.nextInt(0,n);
                if(!usedNumbers.contains(randomNumber)){
                    randomIndexList[i] = randomNumber;
                }
                else{
                    i--;
                }
                usedNumbers.add(randomNumber);
            }
            return randomIndexList;
    }

    /*
     *Burak Yılmaz
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     * 
     */
    public boolean didGameFinish() 
    {
        boolean result = false;

        Player currentPlayer = players[currentPlayerIndex];

        if(currentPlayer.isWinningHand() == true)
        {
            result = true;
        }

        return result;
    }

    /* 
     * Eren Sucuoğlu
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

        boolean control = true;

        int j = 0;

        for(int i = 0; i < 14; i++){

            if(lastDiscardedTile.compareTo(players[currentPlayerIndex].getTiles()[i]) == 0){

                getTopTile();

                control = false;

                break;
            }
        }

        while(control){

            if(lastDiscardedTile.canFormChainWith(players[currentPlayerIndex].getTiles()[j])){

                getLastDiscardedTile();

                break;
            }
            else{

                j++;
            }
        }

        if(j == 14){

            getTopTile();
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     * Eren Sucuoğlu
     */
    public void discardTileForComputer() {
        boolean control = true;

        for(int i = 0; i < 15;i++){
            

            int counter = -1;

            for(int j = 0; j < 15;j++){

                if(players[currentPlayerIndex].playerTiles[i].compareTo(players[currentPlayerIndex].playerTiles[j]) == 0){

                    counter++;
                }
            }
            
            if(counter > 0){

                discardTile(i);
                control = false;

                break;
            }
        } 

        while(control){

            ArrayList <Integer> countArray = countOfChain(players[currentPlayerIndex].playerTiles);
            int min = 15;
            int countForIndex = 0;

            for(int k = 0; k < countArray.size(); k++){
                if( min > countArray.get(k)){

                    min = countArray.get(k);
                }
            }
            for(int i = 0; i < countArray.size(); i++){
                if(countArray.get(i) == min){

                    System.out.println("The tile " + players[currentPlayerIndex].playerTiles[countForIndex] + "has been discarded.");
                   
                    discardTile(countForIndex);
                    
                }

                countForIndex += countArray.get(i);
            }

        }   
    }

    //Only made for easy application of discardTileForComputer,Eren Sucuoğlu.
    public ArrayList<Integer> countOfChain(Tile [] array){
        
        ArrayList<Integer> countArray = new ArrayList<>();

        for(int i = 0; i < 14;i++){
            int counter = 0;

            if(array[i].canFormChainWith(array[i+1])){

                for(int j = i+1; j < 14; j++){
                    if(array[i].canFormChainWith(array[j])){
                        counter++;
                    }
                }
            }

            countArray.add(counter+1);
            i += counter;
        }  

        return countArray;
    }

    /*
     * Cagan Aksoy
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }    
}
