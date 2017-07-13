/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

/**
 *
 * @author jpiro
 */
public class PoiCellDTO {
    private XWPFVertAlign vertAlign=XWPFVertAlign.TOP;
    private List<PoiParrafoDTO> listParrafo;

    public PoiCellDTO() {
    }
    
    public PoiCellDTO(XWPFVertAlign vertAlign) {
        this.vertAlign=vertAlign;
    }
    
    public XWPFVertAlign getVertAlign() {
        return vertAlign;
    }

    public void setVertAlign(XWPFVertAlign vertAlign) {
        this.vertAlign = vertAlign;
    }

    public List<PoiParrafoDTO> getListParrafo() {
        return listParrafo;
    }

    public void setListParrafo(List<PoiParrafoDTO> listParrafo) {
        this.listParrafo = listParrafo;
    }
    
}
