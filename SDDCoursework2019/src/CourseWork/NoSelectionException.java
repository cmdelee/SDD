/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseWork;

/**
 *
 * @author cmdel
 */
public class NoSelectionException extends Exception {

    /**
     * shows error if selection is coded out of the bounds of the database (set
     * to 11)
     */
    public NoSelectionException() {
        System.err.println("No valid selection");
    }
}
