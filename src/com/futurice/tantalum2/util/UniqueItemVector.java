/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.futurice.tantalum2.util;

import java.util.Vector;

/**
 *Allows the vector to have only unique instances
 * of an object. 
 * @author Oyebc
 */
public class UniqueItemVector extends Vector {
    
    public UniqueItemVector()
    {
        super();
    }
    
    public UniqueItemVector(final int length)
    {
        super(length);
    }
    
    public synchronized void addElement(Object o)
    {
        removeElement(o);
        super.addElement(o);
    }
}
