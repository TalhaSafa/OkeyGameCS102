public class Player 
{
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /**
     * removes and returns the tile in given index
     * 
     * @param index index of the tile that is going to be removed
     * @return Tile which is removed
     * @author Talha Safa Kucuk
     */
    public Tile getAndRemoveTile(int index) {
        Tile wantedTile = playerTiles[index];

        for(int i = index ; i < playerTiles.length - 1; i++)
        {
            playerTiles[i] = playerTiles[i+1];
        }

        playerTiles[playerTiles.length - 1] = null;
        numberOfTiles--;    //check whether working for numberOfTiles

        return wantedTile;
    }

    /**
     * adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     * 
     * @param t a tile to be added
     * @return
     * @author Talha Safa Kucuk
     */
    public void addTile(Tile t) {
        if(numberOfTiles < 15)
        {
            playerTiles[14] = t;
            orderTiles(); 
        }
    }

    /**
     * A method to order tiles using Tile.compareTo() method.
     * This method is written in order to order tiles again after adding a new tile.
     * @return
     * @author Talha Safa Kucuk
     */
    public void orderTiles()
    {
        for(int i = 0 ; i < numberOfTiles ; i++)
        {
            for(int j = 0 ; j < numberOfTiles - i - 1; j++)
            {
                if(playerTiles[j].compareTo(playerTiles[j+1]) > 0)
                {
                    Tile tempTile = playerTiles[j+1];
                    playerTiles[j+1] = playerTiles[j];
                    playerTiles[j] = tempTile;
                }
            }
        }

    }

    /** 
     * checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * 
     * @return boolean of result whether current player is winning or not
     * @author Talha Safa Kucuk
     */
    public boolean isWinningHand() {
        
        boolean result = false;
        int chainCounter = 0;

        for(int i = 0 ; i < numberOfTiles - 1; i++)
        {
            int chainMakerCounter = 0;
            while(playerTiles[i].compareTo(playerTiles[i+1]) == 0)
            {
                i++;
            }
            if(i != numberOfTiles - 1)
            {
                if(playerTiles[i].canFormChainWith(playerTiles[i+1]))
                {
                    chainMakerCounter++;
                    if(chainMakerCounter == 4)
                    {
                        chainCounter++;
                        chainMakerCounter = 0;
                    }
                }
                else
                {
                    chainMakerCounter = 0;
                }

            }
        }

        if(chainCounter == 3)
        {
            result = true;
        }
        return result;
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }    
}
