/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rogerio.pid;

/**
 *
 * @author rogerio
 */
public interface iPID {

	public double controller(double setPoint, double processValue);

}
