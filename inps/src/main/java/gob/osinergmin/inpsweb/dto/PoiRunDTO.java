/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

/**
 *
 * @author jpiro
 */
public class PoiRunDTO {
    private String text;
    private boolean bold=false;
    private UnderlinePatterns underline=UnderlinePatterns.NONE;
    private int breaks=0;
    private String fontFamily;
    private int fontSize=0;

    public PoiRunDTO() {
    }

    public PoiRunDTO(String text) {
        this.text = text;
    }
    public PoiRunDTO(String text,boolean bold,UnderlinePatterns underline) {
        this.text = text;
        this.bold=bold;
        this.underline=underline;
    }
    
    public PoiRunDTO(String text,boolean bold,UnderlinePatterns underline,String fontFamily,int fontSize) {
        this.text = text;
        this.bold=bold;
        this.underline=underline;
        this.fontFamily=fontFamily;
        this.fontSize=fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public UnderlinePatterns getUnderline() {
        return underline;
    }

    public void setUnderline(UnderlinePatterns underline) {
        this.underline = underline;
    }

    public int getBreaks() {
        return breaks;
    }

    public void setBreaks(int breaks) {
        this.breaks = breaks;
    }
    
}
