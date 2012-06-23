/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rogerio.pid;

/**
 *
 * @author rogerio
 */
public class ExemploPID0 implements iPID {

    public ExemploPID0(double p_factor, double i_factor, double d_factor) {
        // Start values for ExemploPID0 controller
        sumError = 0;
        lastProcessValue = 0;
        // Tuning constants for ExemploPID0 loop
        P_Factor = p_factor;
        I_Factor = i_factor;
        D_Factor = d_factor;
        // Limits to avoid overflow
        maxError = MAX_INT / (P_Factor + 1);
        maxSumError = MAX_I_TERM / (I_Factor + 1);
    }

    public double controller(double setPoint, double processValue) {
        double error, p_term, d_term;
        double i_term, ret, temp;

        error = setPoint - processValue;

        // Calculate Pterm and limit error overflow
        if (error > maxError) {
            p_term = MAX_INT;
        } else if (error < -maxError) {
            p_term = -MAX_INT;
        } else {
            p_term = P_Factor * error;
        }

        // Calculate Iterm and limit integral runaway
        temp = sumError + error;
        if (temp > maxSumError) {
            i_term = MAX_I_TERM;
            sumError = maxSumError;
        } else if (temp < -maxSumError) {
            i_term = -MAX_I_TERM;
            sumError = -maxSumError;
        } else {
            sumError = temp;
            i_term = I_Factor * sumError;
        }

        // Calculate Dterm
        d_term = D_Factor * (lastProcessValue - processValue);

        lastProcessValue = processValue;

        ret = (p_term + i_term + d_term); /// SCALING_FACTOR;
        if (ret > MAX_INT) {
            ret = MAX_INT;
        } else if (ret < -MAX_INT) {
            ret = -MAX_INT;
        }

        return (ret);
    }
    //! Last process value, used to find derivative of process value.
    double lastProcessValue;
    //! Summation of errors, used for integrate calculations
    double sumError;
    //! The Proportional tuning constant, multiplied with SCALING_FACTOR
    double P_Factor;
    //! The Integral tuning constant, multiplied with SCALING_FACTOR
    double I_Factor;
    //! The Derivative tuning constant, multiplied with SCALING_FACTOR
    double D_Factor;
    //! Maximum allowed error, avoid overflow
    double maxError;
    //! Maximum allowed sumerror, avoid overflow
    double maxSumError;

    final double MAX_INT = Integer.MAX_VALUE;
    final double MAX_I_TERM = Long.MAX_VALUE;
}
