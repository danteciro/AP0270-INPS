/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import java.util.List;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 *
 * @author jpiro
 */
public class PoiParrafoDTO {
    private ParagraphAlignment align=ParagraphAlignment.LEFT;
    private String estilo;
    private List<PoiRunDTO> listRun;   

    public PoiParrafoDTO() {
    }
    
    public PoiParrafoDTO(ParagraphAlignment align) {
        this.align=align;
    }

    public ParagraphAlignment getAlign() {
        return align;
    }

    public void setAlign(ParagraphAlignment align) {
        this.align = align;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public List<PoiRunDTO> getListRun() {
        return listRun;
    }

    public void setListRun(List<PoiRunDTO> listRun) {
        this.listRun = listRun;
    }
    
}
