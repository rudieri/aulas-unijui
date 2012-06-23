/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rogerio.pid;

/**
 *
 * @author rogerio
 */
public class ExemploPID4 implements iPID {

	public ExemploPID4(double p_factor, double i_factor, double d_factor) {
		p = p_factor;
		i = i_factor;
		d = d_factor;
		sumErrors = 0;
		previousValue = 0;
	}

	public double controller(double setPoint, double processValue) {
		double error = setPoint-processValue;
		double lerror = setPoint-previousValue;
		double ret = p * (error) + i * sumErrors + d * (error - lerror);

		previousValue = processValue;
		sumErrors += error;

		return ret;
	}

	private volatile double p;
	private volatile double i;
	private volatile double d;
	private volatile double previousValue;
	private volatile double sumErrors;

}
