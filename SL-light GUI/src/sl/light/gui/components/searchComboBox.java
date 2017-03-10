/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sl.light.gui.components;

import java.util.List;
import javafx.scene.control.ComboBox;

/**
 *
 * @author MARBRO02
 */
public class searchComboBox extends ComboBox{
    public searchComboBox(List<String> options){
        super();
        options.sort((s1, s2) -> s1.compareTo(s2));
        getItems().addAll(options);
        
        
    }
}
