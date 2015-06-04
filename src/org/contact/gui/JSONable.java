/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contact.gui;

/**
 *
 * @author Oyebc
 */
public interface JSONable {
   String toJSON();
   void fromJSON(String JSONstring);
}
