/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package rubiksrace;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JPanel;
import static rubiksrace.GameBoard.tileDeck;

/**
 *
 * @author kjhuggins
 */
class SolutionBoard extends JPanel {
    private final Integer DIMENSION = 3;
    
    GameTile[][] tileSolution = new GameTile[3][3];
    GameTile[][] tileFiver = new GameTile[5][5];
            
    static ArrayList<GameTile> tileSolutionDeck = null;
    
    private ArrayList<Color> backColors = new ArrayList<>(Arrays.asList(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.RED, Color.RED,Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.BLACK));
    
    SolutionBoard() {
        setLayout(new GridLayout(DIMENSION, DIMENSION));
        initializeCards();
        // if tileSolution contains empty tile, redo solution
    }

    private void initializeCards() {
        tileSolutionDeck = new ArrayList();
        
        for (int i = 0; i < ((DIMENSION*DIMENSION)); i++) {
            GameTile newCard = new GameTile("");
            newCard.setColor(backColors.get(i));
            tileSolutionDeck.add(newCard);
        }
        
         // Shuffles tiles and adds to ui
        Collections.shuffle(tileSolutionDeck);
        for (int i = 0; i < tileSolutionDeck.size(); i++) {
            add(tileSolutionDeck.get(i));
        }
        
        int q = 0;
        for (int i = 0; i < DIMENSION; i++) {
            for (int c = 0; c < DIMENSION; c++) {
                tileFiver[i][c] = tileSolutionDeck.get(q);
                tileSolutionDeck.get(q).setCoordinate(new Coordinate(i, c));
                q++;
            }
        }
        
        convertSolution();
    }
    
    private void convertSolution() {
        // translates the 5x5 into the 3x3
        for (int i = 1; i < 3; i++) {
            for (int c = 1; c < 3; c++) {
                tileSolution[i-1][c-1] = tileFiver[i][c];
            }
        }
    }
    
    public ArrayList<GameTile> getSolution() {
        ArrayList<GameTile> temp = null;
        
        for (int i = 0; i < 3; i++) {
            for (int c = 0; c < 3; c++) {
                temp.add(tileSolution[i][c]);
            }
        }
        
        return temp;
    }
    
    public void setSolution(GameTile[][] q) {
        tileSolution = q;
    }
    
    public void newSolution() {
        Collections.shuffle(tileSolutionDeck);

        int q = 0;
        for (int i = 0; i < 5; i++) {
            for (int c = 0; c < 5; c++) {
                tileFiver[i][c] = tileSolutionDeck.get(q);
                tileSolutionDeck.get(q).setCoordinate(new Coordinate(i, c));
                if (tileSolutionDeck.get(q).getColor() == Color.BLACK) {
                    tileSolutionDeck.get(q).setEmpty(true);
                }
                
                q++;
            }
        }
        
        // Parent should now call convertSolution
    }
}