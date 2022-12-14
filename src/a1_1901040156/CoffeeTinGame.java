package a1_1901040156;

import java.util.Arrays;

import utils.TextIO;

/**
 * @author PHUC BUI
 * @overview A program that performs the coffee tin game on a
 * tin of beans and display result on the standard output
 */
public class CoffeeTinGame {
    /**
     * Constant value for the green bean
     */
    public static final char GREEN = 'G';
    /**
     * Constant value for the blue bean
     */
    public static final char BLUE = 'B';
    /**
     * Constant for remove bean
     */
    public static final char REMOVED = '-';
    /**
     * the null character
     */
    public static final char NULL = '\u0000';

    /**
     * initialise BeanBag
     */
    private static final char[] BeansBag;

    static {
        BeansBag = new char[300];
        int count = 0;
        while (count < BeansBag.length) {
            if(count < BeansBag.length / 3) {
                BeansBag[count] = BLUE;
            } else if(count < BeansBag.length * 2 / 3) {
                BeansBag[count] = GREEN;
            } else {
                BeansBag[count] = REMOVED;
            }
            count++;
        }
        }

    /**
     * the main procedure
     *
     * @effects initialise a coffee tin
     * {@link TextIO#putf(String, Object...)}: print the tin
     * content
     * {@link @tinGame(char[])}: perform the coffee tin game on
     * tin
     * {@link TextIO#putf(String, Object...)}: print the tin
     * content again
     * if last bean is correct
     * {@link TextIO#putf(String, Object...)}: print its colour
     * else
     * {@link TextIO#putf(String, Object...)}: print an error
     * message
     */

    public static void main(String[] args) {
        // initialise some bean
        char[][] tins = {
                {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN},
                {BLUE, GREEN, BLUE, BLUE, GREEN},
                {BLUE, GREEN, GREEN, BLUE},
                {BLUE, GREEN},
                {BLUE, GREEN, BLUE}
        };

        for (int i = 0; i < tins.length; i++) {
            char[] tin = tins[i];

            // count number of greens
            int greens = 0;
            for (char bean : tin) {
                if (bean == GREEN)
                    greens++;
            }

            final char last = (greens % 2 == 1) ? GREEN : BLUE;
            // p0 = green parity /\
            // (p0=1 -> last=GREEN) /\ (p0=0 -> last=BLUE)

            // print the content of tin before the game
            TextIO.putf("%nTIN (%d Gs): %s %n", greens, Arrays.toString(tin));

            // perform the game
            char lastBean = tinGame(tin);
            // lastBean = last \/ lastBean != last

            // print the content of tin and last bean
            TextIO.putf("tin after: %s %n", Arrays.toString(tin));

            // check if last bean as expected and print
            if (lastBean == last) {
                TextIO.putf("last bean: %c ", lastBean);
            } else {
                TextIO.putf("Oops, wrong last bean: %c (expected: %c)%n",
                        lastBean, last);
            }
        }
    }

    /**
     * Performs coffee tin game to determine the last bean.
     *
     * @requires tin neq null /\ tin.length > 0
     * @modifies tin
     * @effects <pre>
     *  repeat take out two beans from tin
     *      if same colour
     *          throw both away, put one blue bean back
     *      else
     *          put green bean back
     *  until tin has less than 2 beans left
     *  let p0 = initial number of green beans
     *  if p0 = 1
     *      result = ‘G’
     *  else
     *      result = ‘B’
     *      </pre>
     */
    public static char tinGame(char[] tin) {
        while (hasAtLeastTwoBeans(tin)) {
            // take two beans from tin
            char[] takeTwo = takeTwo(tin);
            // use update tin
            updateTin(tin, takeTwo);
        }
        return anyBean(tin);
    }

    /**
     * @effects if tin has at least two beans
     * return true
     * else
     * return false
     */
    private static boolean hasAtLeastTwoBeans(char[] tin) {
        int count = 0;
        for (char bean : tin) {
            if (bean != REMOVED) {
                count++;
            }
            if (count >= 2){  // enough bean
                return true;
            }
        }
        // not enough bean
        return false;
    }

    /**
     * @requires tin has at least 2 beans left
     * @modifies tin
     * @effects remove any two beans from tin and return them
     */
    private static char[] takeTwo(char[] tin) {
        char first = takeOne(tin);
        char second = takeOne(tin);
        return new char[]{first, second};
    }

    /**
     * @requires tin has at least one bean
     * @modifies tin
     * @effects remove any bean from tin and return it
     */
    public static char takeOne(char[] tin) {
        char bean = 0;
        for (int i = 0; i < tin.length; i++) {
            i = randInt(tin.length);
            bean = tin[i];
            if (bean != REMOVED) {   // found one
                tin[i] = REMOVED;
                break;
            }
        }
        // no bean left
        return bean;
    }

    /**
     * get a positive integer n
     *
     * @requires n is an integer number /\ n > 0
     * @effects return an integer number randomly selected from 0 to n
     * e.g.    n = 5 => return 2
     */
    public static int randInt(int n) {
        return (int) (Math.random() * n);
    }

    /**
     * get bean that are bean type
     *
     * @requires tin != null /\ bean type are blue type or green type
     * @effects return a randomly-selected bean that matches the bean type
     */
    public static char getBean(char[] tin, char beanType) {
        char bean = 0;
        for (int i = 0; i < tin.length; i++) {
            i = randInt(tin.length);
            bean = tin[i];
            if (bean == beanType) {
                tin[i] = REMOVED;
                break;
            }
        }
        return bean;
    }

    /**
     *  update tin accordingly
     * @requires    tin != null /\ twoBean != null
     * @effects     b1 = twoBean[0], b2 = twoBean[1]
     *              if b1 = b2 (GREEN or BLUE)
     *                  put B in bin
     *              else
     *                  put G in bin
     */
    private static void updateTin(char[] tin, char[] twoBeans) {
        // take beans from tin
        char b1 = getBean(BeansBag, twoBeans[0]);
        char b2 = getBean(BeansBag, twoBeans[1]);
        // process beans to update tin
        if(b1 == BLUE && b2 == BLUE) {
            // put B in bin
            putIn(tin, BLUE);
        } else if (b1 == GREEN && b2 == GREEN) {
            // put B in bin
            putIn(tin, BLUE);
        } else {
            // put G in bin
            putIn(tin, GREEN);
        }
    }

    /**
     * @requires tin has vacant positions for new beans
     * @modifies tin
     * @effects place bean into any vacant position in tin
     */
    private static void putIn(char[] tin, char bean) {
        for (int i = 0; i < tin.length; i++) {
            if (tin[i] == REMOVED) { // vacant position
                tin[i] = bean;
                break;
            }
        }
    }

    /**
     * @effects if there are beans in tin
     * return any such bean
     * else
     * return ’\u0000’ (null character)
     */
    private static char anyBean(char[] tin) {
        for (char bean : tin) {
            if (bean != REMOVED) {
                return bean;
            }
        }
        // no beans left
        return NULL;
    }
}
