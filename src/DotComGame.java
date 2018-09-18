import java.util.*;

/**A class to store the battlefield
 *
 */
class BattleField {
    private boolean[] battleField; //An boolean array to store the battlefield, spot with the DOTCOM is true;

    /**Setter of one spot of the battlefield to be false.
     * <p>
     * Used when initialize the battlefield(map) and when it is hit.
     *
     * @param n set the #n of the battlefield to false
     */
    public void setFalse (int n) {
        this.battleField[n] = false;
    }

    /**Setter of one spot of the battlefield to be true;
     * <p>
     * Used when set where the DOTCOMs are.
     *
     * @param n set the #n of the battlefield to true
     */
    public void setTrue (int n) {
        this.battleField[n] = true;
    }

    /**Initialize the battlefield.
     * <p>
     * Set the length of the battlefield array and every element of it is false.
     *
     * @param n the length of the battlefield array
     */
    public void initBattleField(int n) {
        this.battleField = new boolean[n];
        for (int i = 0; i < n; i++) {
            this.setFalse(i);
        }
    }

    /**This is a method to return the #n of the battlefield array.
     *
     * @param n index of the array
     * @return the #n of the battlefield array
     */
    public boolean getTheNOfBattleField(int n) {
        return this.battleField[n];
    }

    /**This is a method to return the length of the battlefield array.
     *
     * @return the length of battlefield array
     */
    public int getLength() {
        return this.battleField.length;
    }
} //Close class.

/**This is the class where you will play the game.
 *
 */
public class DotComGame {
    static private Scanner in = new Scanner(System.in); //Input settings initialize.
    static private int boardInput; //Store what has been input.
    private int len; //The length of the battle field.
    private int comLen; //The length of the DOTCOM.

    public BattleField bF = new BattleField(); //A battle field for every game.
    public Stack<Integer> chosenSpot = new Stack<>(); //A stack to store which spot the player chose.

    /**This is a method to print all spots the player has chosen.
     * <p>
     * Use a for loop to see through the stack.
     */
    public void printChosenSpot() {
        for (int x: chosenSpot) {
            System.out.print(" " + x);
        }
        System.out.println(" ");
    }

    /**A getter of the length of the battlefield.
     *
     * @return the length of the battlefield
     */
    public int getLen() {
        return len;
    }

    /**A setter of the length of the battlefield.
     *
     * @param L what the length of the battlefield shall be
     */
    public void setLen(int L) {
        len = L;
    }

    /**A getter of the length of the DOTCOM.
     *
     * @return the length of the DOTCOM
     */
    public int getComLen() {
        return comLen;
    }

    /**A setter of the length of the DOTCOM.
     *
     * @param cL what the length of the DOTCOM shall be
     */
    public void setComLen(int cL) {
        comLen = cL;
    }

    /**This is a method to get one integer that users type on the keyboard.
     * <p>
     * If the input is not a integer, it will ask the user to type in again.
     *
     */
    public static void setFromKeyboard() {
        System.out.println("Input a natural number.");
        String str = in.next(); //Get the string from keyboard.
        int[] number = new int[str.length()]; //To store every character in the string.
        int i;
        for (i = 0; i < str.length(); i++) { //See through the string to see if they are all integers.
            number[i] = str.charAt(i);
            if (!(number[i] > 47 && number[i] < 58)) { //Find the one which is not integer, then break.
                break;
            }
        }
        if (i == str.length()) { //All characters in the string are integers.
            boardInput = Integer.parseInt(str); //Transfer the string to integer.
        } else {
            System.out.println("Input error! Please input again.");
            setFromKeyboard(); //Call this method again, until the user type in a integer.
        }
    }

    /**A getter of what is stored in the variable boardInput;
     *
     * @return the current value of boardInput
     */
    public static int getFromKeyboard() {
        return boardInput;
    }

    /**This is a method to find a random integer between two integers, inclusive
     *
     * @param m one of the given integer
     * @param n the other one of the given integer
     * @return a random integer between the two given integer m & n, may be m or n
     */
    public int findRandomIntBetween(int m, int n) {
        int random;
        int max = m > n ? m : n;
        int min = m > n ? n : m;
        Random rd = new Random();
        random = rd.nextInt(max) % (max - min + 1) + min;
        return random;
    }

    /**This is a important method of the game which aims at making a new battlefield(map).
     *
     */
    public void makeMap() {
        //Set the length of battlefield and DOTCOM using set and get fromKeyboard.
        System.out.println("Please input the length fo battlefield.");
        while (getFromKeyboard() == 0) {
            setFromKeyboard();
            if (getFromKeyboard() == 0) {
                System.out.println("You cannot input 0 for this part!");
            }
        }
        this.setLen(getFromKeyboard());
        System.out.println("Please input the length of the dotCom");
        boardInput = 0;
        while (getFromKeyboard() == 0 || getFromKeyboard() >= getLen()) {
            setFromKeyboard();
            if (getFromKeyboard() == 0) {
                System.out.println("You cannot input 0 for this part!");
            }
            if (getFromKeyboard() >= getLen()) {
                System.out.println("The length of dot com cannot be greater than or equal to the total length!");
            }
        }
        this.setComLen(getFromKeyboard());

        this.bF.initBattleField(this.getLen());
        int startPoint = findRandomIntBetween(0, this.getComLen() - 1); //Find the start point of the DOTCOM.
        for (int i = startPoint; i < startPoint + this.getComLen(); i++) {
            this.bF.setTrue(i);
        }
    }

    /**A method to check whether the game is finished
     *
     * @return is finished (true), not finished (false)
     */
    public boolean isFinished() {
        int i;
        //To see that if every element in the battlefield array is true.
        for (i = 0; i < getLen(); i++) {
            if (this.bF.getTheNOfBattleField(i)) {
                break;
            }
        }
            return i == getLen();
    }

    /**A method to check whether the spot where player fired is hit.
     *
     * @param spot where player fired
     * @return is hit(true) or not hit(false)
     */
    public boolean isHit(int spot) {
        return this.bF.getTheNOfBattleField(spot); //Call the method of battlefield the number n is true or false.
    }

    public int fireIt() {
        int i = -1;
        System.out.println("Input the position you want to fire on!");
        while (i < 0 || i >= this.getLen()) {
            setFromKeyboard();
            i = getFromKeyboard();
            if (i < 0 || i >= this.getLen()) {
                System.out.println("Wrong input! Input the position you want to fire on again!");
            }
        }
        return i;
    }

    /**The main body of the game.
     * <p>
     * In this part, A loop is provided to see whether the player has shoot all the DOTCOM.
     *
     * @param args never use it
     */
    public static void main(String args[]) {
        DotComGame game = new DotComGame();
        game.makeMap();
        System.out.println("Remember, the index of position starts from 0.");
        while (!game.isFinished()) {
            int i = game.fireIt();
            game.chosenSpot.push(i);
            if (game.isHit(i)) {
                game.bF.setFalse(i);
                System.out.println("Hit!");
            } else {
                System.out.println("Missed.:/");
            }
            System.out.print("You have hit on");
            game.printChosenSpot();
        }
        System.out.println("Congratulations! You win!");
    } //Close method.

} //Close class.

/**Test code.
 *
 */
class DotComGameTestDrive {
    static public void main (String[] args) {
        DotComGame test = new DotComGame();
        test.makeMap();
        long startTime = System.currentTimeMillis();
        while (!test.isFinished()) {
            int i = test.findRandomIntBetween(0,test.getLen()-1);
            test.chosenSpot.push(i);
            test.printChosenSpot();
            if (test.isHit(i)) {
                test.bF.setFalse(i);
            }
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.println("Congratulations! Test passed! Execution takes " + time + " second(s)");
    }
}