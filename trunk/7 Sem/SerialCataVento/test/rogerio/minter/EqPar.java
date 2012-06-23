/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rogerio.minter;

import minter.datatable.Getter;

/**
 *
 * @author rogerio
 */
public class EqPar implements Getter {

	public EqPar(int time) {
		this.time = time;
	}

	public double get(String variable) {
		//if(variable.equals("t")) {
			return time;
		//}
	}

	private int time;

}
