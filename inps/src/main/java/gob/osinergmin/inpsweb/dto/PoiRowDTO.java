/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import java.util.List;

/**
 *
 * @author jpiro
 */
public class PoiRowDTO {
    private List<PoiCellDTO> listCell;

    public List<PoiCellDTO> getListCell() {
        return listCell;
    }

    public void setListCell(List<PoiCellDTO> listCell) {
        this.listCell = listCell;
    }
    
}
