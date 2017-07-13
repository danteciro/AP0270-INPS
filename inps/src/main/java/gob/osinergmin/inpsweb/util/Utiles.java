/**
* Resumen		
* Objeto		: Utiles.java
* Descripción		: Conjunto de clases, que poseen una serie de métodos y atributos
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     21/05/2016      Julio Piro Gonzales         Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
* 
*/

package gob.osinergmin.inpsweb.util;

import gob.osinergmin.inpsweb.dto.PoiCellDTO;
import gob.osinergmin.inpsweb.dto.PoiParrafoDTO;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import gob.osinergmin.inpsweb.dto.PoiPictureDTO;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import gob.osinergmin.inpsweb.dto.PoiRowDTO;
import gob.osinergmin.inpsweb.dto.PoiRunDTO;
import gob.osinergmin.inpsweb.dto.PoiTableDTO;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.BreakType;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import org.apache.poi.xwpf.usermodel.Document;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
/* OSINE_SFS-480 - RSIS 01 - Fin */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jpiro
 */
public class Utiles {
    private static final Logger LOG = LoggerFactory.getLogger(Utiles.class);
    
    public static Date sumarFechasDias(Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }
    public static String padRight(String s, Integer n, char r) {
        //return String.format("%1$-" + n + "s", s);  
        return String.format("%-"+n.toString()+"s", s).replace(' ', r);
    }

    public static String padLeft(String s, Integer n, char r) {
        //return String.format("%1$" + n + "s", s);  
        return String.format("%"+n.toString()+"s", s).replace(' ', r);
    }
    
    public static boolean validaFormatoPermitido(String[] extenciones, String nombre) {
    	boolean permitida = false;
    	int lastIndexOf = nombre.lastIndexOf(".");
    	String formato = nombre.substring(lastIndexOf);

    	for (int i = 0; i < extenciones.length; i++) {
    	    if (formato.equalsIgnoreCase(extenciones[i])) {
	    		permitida = true;
	    		break;
    	    }
    	}
    	return permitida;
    }
    public static String DateToString(Date fecha, String formato){
        if (fecha==null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(formato);
        return df.format(fecha);
    }
    
    /**
     * Convierte fecha cadena a fecha Date
     *
     * @param fecha
     * @param formato
     * @return
     * @throws Exception
     */
    public static Date stringToDate(String fecha, String formato){
        if(fecha==null || fecha.equals("")){
            return null;
        }        
        GregorianCalendar gc = new GregorianCalendar();
        try {
            SimpleDateFormat df = new SimpleDateFormat(formato);
            gc.setTime(df.parse(fecha));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gc.getTime();
    }
    
    public static boolean compararCadenas(String cadena1, String cadena2){
    	boolean bandera = false;
    	if((cadena1!=null && !Constantes.CONSTANTE_VACIA.equals(cadena1)) &&
    			(cadena2!=null && !Constantes.CONSTANTE_VACIA.equals(cadena2))){
    		if(cadena1.trim().toUpperCase().equals(cadena2.trim().toUpperCase())){
    			bandera = true;
    		}
    	}
    	return bandera;
    }
    //POI UTILES
    public static class Poi{
        public static final String LISTADO_ETIQUETAS="listadoEtiquetas";
        public static final String LISTADO_TABLAS="listadoTablas";
        public static final String LISTADO_LISTAS="listadoListas";
        /* OSINE_SFS-480 - RSIS 01 - Inicio */
        public static final String LISTADO_IMAGENES="listadoImagenes";
        /* OSINE_SFS-480 - RSIS 01 - Fin */
        
        public static XWPFDocument actualizaPlantilla(XWPFDocument document,Map<String, Map<String, Object>> datos){
            if(datos.containsKey(LISTADO_ETIQUETAS)) document=reemplazaEtiquetas(document,datos.get(LISTADO_ETIQUETAS));
            if(datos.containsKey(LISTADO_TABLAS)) document=creaTablasListado(document,datos.get(LISTADO_TABLAS));
            if(datos.containsKey(LISTADO_LISTAS)) document=reemplazaListas(document,datos.get(LISTADO_LISTAS));
            /* OSINE_SFS-480 - RSIS 01 - Inicio */
            if(datos.containsKey(LISTADO_IMAGENES)) document=reemplazaImagenes(document,datos.get(LISTADO_IMAGENES));
            /* OSINE_SFS-480 - RSIS 01 - Fin */
            return document;
        }
        /* OSINE_SFS-480 - RSIS 01 - Inicio */
        public static XWPFDocument reemplazaImagenes(XWPFDocument document,Map<String, Object> listadoImagenes){
            try{
                if(listadoImagenes!=null){
                    Set<Map.Entry<String, Object>> rawParameters = listadoImagenes.entrySet();
                    for (Map.Entry<String, Object> entry : rawParameters) {
                        List<IBodyElement> elementsBody=document.getBodyElements();
                        for(IBodyElement elemBody : elementsBody){
                            if(elemBody instanceof XWPFParagraph){
                                elemBody=reemplazaImgEnParrafos((XWPFParagraph)elemBody,entry,document);
                            }
                        } 
                    }
                }
            }catch(Exception e){
                LOG.error("Error en reemplazaImagenes.",e);
            }
            return document;
        }
        
        public static XWPFParagraph reemplazaImgEnParrafos(XWPFParagraph p,Map.Entry<String, Object> entry,XWPFDocument document){
            try{
                if(!p.getText().isEmpty()){
                    for (XWPFRun rg : p.getRuns()) {
                        String text = rg.getText(0);
                        if (text != null) {
                            if (text.contains(entry.getKey())) {
                                LOG.info("etiqueta img encontrada-->"+entry.getKey());
                                rg.setText("", 0);
                                List<PoiPictureDTO> imgs = (List<PoiPictureDTO>) entry.getValue();
                                if(imgs.size() != 0){
                                	for(PoiPictureDTO reg : imgs){
                                        if(reg.getPictureType()!=0){
                                            //file
                                            File someFile = new File(reg.getNombre());
                                            if (reg.getImagen()!=null) {
                                                FileOutputStream fos = new FileOutputStream(someFile);
                                                fos.write(reg.getImagen());
                                                fos.flush();
                                                fos.close();
                                            }   

                                            String blipId = document.addPictureData(new FileInputStream(someFile), reg.getPictureType());

                                            XWPFRun r = p.createRun();
                                            CTInline inline = r.getCTR().addNewDrawing().addNewInline();
                                            createPicture(blipId,document.getNextPicNameNumber(reg.getPictureType()), inline,reg.getWidth(), reg.getHeight());
                                            r.addBreak();

                                            XWPFRun rt = p.createRun();
                                            rt.setText(reg.getDescripcion());
                                            rt.addBreak();rt.addBreak();
                                        }
                                    }
                                	XWPFParagraph para = document.getLastParagraph();
                                    
                                    XWPFRun run = para.createRun(); 
                                    run.addBreak(BreakType.PAGE);
                                    
                                    run.addBreak();
                                    run.addBreak();
                                    run.addBreak();
                                    run.addBreak();
                                    run.addBreak();
                                    run.addBreak();
                                    run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                	run.addBreak();
                                    XWPFTable table = document.createTable(2, 1);
                                    table.getCTTbl().getTblPr().unsetTblBorders();
                                    XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
                                    XWPFRun r1 = p1.createRun();
                                    r1.setBold(true);
                                    r1.setText("                                                                               Anexo 2");
                                    r1.setItalic(false);
                                    r1.setFontFamily("Calibri");
                                    r1.setTextPosition(0);
                                    table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
                                    
                                    XWPFParagraph p2 = table.getRow(1).getCell(0).getParagraphs().get(0);
                                    XWPFRun r2 = p2.createRun();
                                    r2.setBold(true);
                                    r2.setText("                                                             Acta de Visita de Supervisión ");
                                    r2.setItalic(false);
                                    r2.setFontFamily("Calibri");
                                    r2.setTextPosition(0);
                                    table.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
                                }else{
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                	rg.addBreak();
                                    XWPFTable table = document.createTable(2, 1);
                                    table.getCTTbl().getTblPr().unsetTblBorders();
                                    XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
                                    XWPFRun r1 = p1.createRun();
                                    r1.setBold(true);
                                    r1.setText("                                                                               Anexo 2");
                                    r1.setItalic(false);
                                    r1.setFontFamily("Calibri");
                                    r1.setTextPosition(0);
                                    table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
                                    
                                    XWPFParagraph p2 = table.getRow(1).getCell(0).getParagraphs().get(0);
                                    XWPFRun r2 = p2.createRun();
                                    r2.setBold(true);
                                    r2.setText("                                                             Acta de Visita de Supervisión ");
                                    r2.setItalic(false);
                                    r2.setFontFamily("Calibri");
                                    r2.setTextPosition(0);
                                    table.getRow(1).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(9000));
                                    
                                }
                                break;
                            }
                        }
                    } 
                }
            }catch(Exception e){
                LOG.error("error en reemplazaImgEnParrafos",e);
            }
            return p;
        }
        /* OSINE_SFS-480 - RSIS 01 - Fin */
        public static XWPFDocument reemplazaListas(XWPFDocument document,Map<String, Object> listadoListas){
            LOG.info("reemplazaListas");
            try{
                if(listadoListas!=null){
                    Set<Map.Entry<String, Object>> rawParameters = listadoListas.entrySet();
                    for (Map.Entry<String, Object> entry : rawParameters) {
                        String etiqueta=entry.getKey();
                        boolean flagRecorrio=false;
                        while(!flagRecorrio){
                            List<IBodyElement> elementsBody=document.getBodyElements();
                            for(IBodyElement elemBody : elementsBody){
                                if(elemBody instanceof XWPFParagraph){
                                    XWPFParagraph p=(XWPFParagraph)elemBody;
                                    if(!p.getText().isEmpty()){
                                        if(p.getText().contains(etiqueta)){
                                            List<PoiParrafoDTO> lisParrafos=(List<PoiParrafoDTO>)entry.getValue();
                                            //invertirList
                                            Collections.reverse(lisParrafos);
                                                                                        
                                            int posOfParagraph=document.getPosOfParagraph(p);
                                            XmlCursor cursor = p.getCTP().newCursor();//cursor de parrafo encontrado
                                            for(PoiParrafoDTO parrafo : lisParrafos){
                                                posOfParagraph++;
                                                XWPFParagraph parnew = document.insertNewParagraph(cursor);//INSERTO nuevo parrafo
                                                parnew.setIndentationLeft(555);;
                                                parnew.setStyle(parrafo.getEstilo());
                                                if(parrafo.getListRun()!=null){
                                                    for(PoiRunDTO run : parrafo.getListRun()){
                                                        XWPFRun r = parnew.createRun();
                                                        r.setText(run.getText());
                                                        r.setBold(run.isBold());
                                                        r.setUnderline(run.getUnderline());
                                                        if(run.getFontFamily()!=null) r.setFontFamily(run.getFontFamily());
                                                        if(run.getFontSize()>0) r.setFontSize(run.getFontSize());
                                                        if(run.getBreaks()>0){
                                                            for(int i=0;i<run.getBreaks();i++){
                                                                r.addBreak();
                                                            }
                                                        }
                                                    }
                                                }
                                                cursor = parnew.getCTP().newCursor();//cursor de parrafo encontrada
                                            }
                                            //elimina contenido del parrafo
                                            for(int i=0;i<p.getRuns().size();i++){
                                                p.removeRun(i);
                                            }
                                            document.removeBodyElement(posOfParagraph);
                                            //cortar el for pq loas posiciones han cambiado XD
                                            break;
                                        }
                                    }
                                }
                                flagRecorrio=true;
                            }  
                        }
                    }
                }
            }catch(Exception e){
                LOG.error("error en reemplazaListas",e);
            }
            return document;
        }

        public static void setTableAlignment(XWPFTable table, STJc.Enum justification) {
            CTTblPr tblPr = table.getCTTbl().getTblPr();
            CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
            jc.setVal(justification);
        }
        
        public static XWPFDocument creaTablasListado(XWPFDocument document,Map<String, Object> tablasListado){
            LOG.info("creaTablasListado");
            try{
                if(tablasListado!=null){
                    Set<Map.Entry<String, Object>> rawParameters = tablasListado.entrySet();
                    //POIBUG tiene un bug en cuanto al mapeo en xml de tablas modificadas, este atributo servira para no volver a tocar las tablas YA modificadas
                    int[] posTablasTrabajadas=new int[document.getTables().size()];
                    int contTablasTrabajadas=0;
                            
                    for (Map.Entry<String, Object> entry : rawParameters) {
                        String etiqueta=entry.getKey();
                        PoiTableDTO tabla=(PoiTableDTO)entry.getValue();
                        LOG.info((tabla.getListRow()!=null)?etiqueta+",tabla :"+tabla.getListRow().size():etiqueta+",tabla: listrow es nulo");
                        boolean recorrioTablas=false;//si recorrioTablas, quiere decir que ya barrio todas las tablas
                        int posOfTable=-1;//inicia en -1 porque la primera tabla tiene posicion 0, para cuando el sgte recorrido continue en la tabla donde se quedo
                        
                        for(int i : posTablasTrabajadas){
                            LOG.info("posTablasTrabajadas-->"+i);
                        }                        
                        //busca mientras todas las tablas no estes revisadas
                        while(!recorrioTablas){
                            boolean existEtiqueta=false;
                            for (XWPFTable tbl : document.getTables()) {//inicio recorrer todas las tablas del documento
                                if(posOfTable<document.getPosOfTable(tbl)){//para continuar de la ultima tabla donde se quedo
                                    LOG.info("buscando en tabla posicion--->"+document.getPosOfTable(tbl));
                                    //POIBUG verificar que tabla no haya sino trabajada
                                    boolean tablaTrabajada=false;
                                    for(int i : posTablasTrabajadas){
                                        if(document.getPosOfTable(tbl)==i){tablaTrabajada=true;break;}
                                    } 
                                    LOG.info("flag tablaTrabajada-->"+tablaTrabajada);
                                    if(!tablaTrabajada){
                                        //verificar si existeEtiqueta?
                                        for (XWPFTableRow row : tbl.getRows()) {
                                            if(existEtiqueta){break;}
                                            for (XWPFTableCell cell : row.getTableCells()) {
                                                if(existEtiqueta){break;}
                                                for (XWPFParagraph p : cell.getParagraphs()) {
                                                    LOG.info("etiqueta buscada-->"+etiqueta);
                                                    if(p!=null && p.getText()!=null && !p.getText().isEmpty() && p.getText().contains(etiqueta)){
                                                        LOG.info("etiqueta encontrada--> text:"+p.getText()+",etiqueta:"+etiqueta);
                                                        existEtiqueta=true;break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //fin verificar si existeEtiqueta?
                                    if(existEtiqueta){
                                        //POIBUG almacenando tabla trabajada
                                        posTablasTrabajadas[contTablasTrabajadas]=document.getPosOfTable(tbl);
                                        contTablasTrabajadas++;
                                                
                                        posOfTable = document.getPosOfTable(tbl);//ubicacion de tabla encontrada
                                        if(tabla.getListRow().size()>0){//si hay filas q ingresar
                                            posOfTable++;

                                            XWPFTableRow row=tbl.getRow(0);//obtengo cabecera de tabla encontrada
                                            XmlCursor cursor = tbl.getCTTbl().newCursor();//cursor de tabla encontrada
                                            //inserta cabecera
                                            XWPFTable tblnew = document.insertNewTbl(cursor);//INSERTO nueva tabla
                                            tblnew.addRow(row);//le coloco la cabecera de la tabla encontrada, se generara una 2da fila
                                            tblnew.removeRow(0);//elimino la 1ra fila, para que la 2da fila pase a ser la nueva cabecera

                                            for(PoiRowDTO fila : tabla.getListRow()){
                                                XWPFTableRow tableRow = tblnew.createRow();
                                                int cell=0;
                                                for(PoiCellDTO celda : fila.getListCell()){
                                                    tableRow.getCell(cell).setVerticalAlignment(celda.getVertAlign());
                                                    int contParrafo=1;
                                                    for(PoiParrafoDTO parrafo : celda.getListParrafo()){
                                                        XWPFParagraph p;
                                                        if(contParrafo==1){
                                                            if(tableRow.getCell(cell).getParagraphs().get(0)!=null){
                                                                p=tableRow.getCell(cell).getParagraphs().get(0);
                                                            }else{
                                                                p = tableRow.getCell(cell).addParagraph();
                                                            }
                                                        }else{
                                                            p = tableRow.getCell(cell).addParagraph();
                                                        }
                                                        p.setAlignment(parrafo.getAlign());
                                                        if(parrafo.getListRun()!=null){
                                                            for(PoiRunDTO run : parrafo.getListRun()){
                                                                XWPFRun r = p.createRun();
                                                                String data = run.getText();
                                                                if (data.contains("\n")) {
                                                                    String[] lines = data.split("\n");
                                                                    r.setText(lines[0], 0); // set first line into XWPFRun
                                                                    for(int i=1;i<lines.length;i++){
                                                                        // add break and insert new text
                                                                        r.addBreak();
                                                                        r.setText(lines[i]);
                                                                    }
                                                                } else {
                                                                    r.setText(data, 0);
                                                                }
                                                                //r.setText(run.getText());
                                                                r.setBold(run.isBold());
                                                                r.setUnderline(run.getUnderline());
                                                                if(run.getFontFamily()!=null) r.setFontFamily(run.getFontFamily());
                                                                if(run.getFontSize()>0) r.setFontSize(run.getFontSize());
                                                                if(run.getBreaks()>0){
                                                                    for(int i=0;i<run.getBreaks();i++){
                                                                        r.addBreak();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        contParrafo++;
                                                    }
                                                    cell++;
                                                }
                                            }
                                            CTTbl table        = tblnew.getCTTbl();
                                            CTTblPr pr         = table.getTblPr();
                                            CTTblWidth  tblW = pr.getTblW();
                                            tblW.setW(BigInteger.valueOf(4690));
                                            tblW.setType(STTblWidth.PCT);
                                            pr.setTblW(tblW);
                                            table.setTblPr(pr);
                                            CTJc jc = pr.addNewJc();        
                                            jc.setVal(STJc.RIGHT);
                                            pr.setJc(jc);
                                        }
                                        //elimina tabla que era de Plantilla
                                        document.removeBodyElement(posOfTable);
                                        break;
                                    }
                                }
                            }
                            if(!existEtiqueta){//si ya no encontro la etiqueta es porque ya reviso en todas
                                recorrioTablas=true;
                            }
                        }

                    }
                }
            }catch(Exception e){
                LOG.error("Error en creaTablasListado",e);        
            }

            return document;
        }
        public static XWPFDocument reemplazaEtiquetas(XWPFDocument document,Map<String, Object> parametros){
            LOG.info("reemplazaEtiquetas");
            try{
                if(parametros!=null){
                    Set<Map.Entry<String, Object>> rawParameters = parametros.entrySet();
                    for (Map.Entry<String, Object> entry : rawParameters) {
                        LOG.info("buscando etiqueta-->"+entry.getKey());
                        List<IBodyElement> elementsBody=document.getBodyElements();
                        for(IBodyElement elemBody : elementsBody){
                            if (elemBody instanceof XWPFTable){
                                elemBody=reemplazaEnTablas((XWPFTable)elemBody,entry);
                            }else if(elemBody instanceof XWPFParagraph){
                                elemBody=reemplazaEnParrafos((XWPFParagraph)elemBody,entry);
                            }
                        }        
                    }
                }
            }catch(Exception e){
                LOG.error("error en reemplazaEtiquetas",e);
            }
            return document;
        }

        public static XWPFTable reemplazaEnTablas(XWPFTable tbl,Map.Entry<String, Object> entry){
            try{
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            p=reemplazaEnParrafos(p,entry);
                        }
                    }
                }
            }catch(Exception e){
                LOG.error("error en reemplazaEnTablas",e);
            }
            return tbl;
        }
        public static XWPFParagraph reemplazaEnParrafos(XWPFParagraph p,Map.Entry<String, Object> entry){
            try{
                if(!p.getText().isEmpty()){
                    for (XWPFRun r : p.getRuns()) {
                        String text = r.getText(0);
                        if (text != null) {
                            if (text.contains(entry.getKey())) {
                                LOG.info("etiqueta encontrada-->"+entry.getKey());
                                text = text.replace(entry.getKey(),entry.getValue().toString());
                                r.setText(text, 0);
                            }
                        }
                    } 
                }
            }catch(Exception e){
                LOG.error("error en reemplazaEnParrafos",e);
            }
            return p;
        }
        
        /* OSINE_SFS-480 - RSIS 01 - Inicio */
        public static void createPicture(String blipId,int id,CTInline inline, int width, int height){
            final int EMU = 9525;
            width *= EMU;
            height *= EMU;

            String picXml = "" +
                    "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                    "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                    "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                    "         <pic:nvPicPr>" +
                    "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                    "            <pic:cNvPicPr/>" +
                    "         </pic:nvPicPr>" +
                    "         <pic:blipFill>" +
                    "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                    "            <a:stretch>" +
                    "               <a:fillRect/>" +
                    "            </a:stretch>" +
                    "         </pic:blipFill>" +
                    "         <pic:spPr>" +
                    "            <a:xfrm>" +
                    "               <a:off x=\"0\" y=\"0\"/>" +
                    "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                    "            </a:xfrm>" +
                    "            <a:prstGeom prst=\"rect\">" +
                    "               <a:avLst/>" +
                    "            </a:prstGeom>" +
                    "         </pic:spPr>" +
                    "      </pic:pic>" +
                    "   </a:graphicData>" +
                    "</a:graphic>";

            //CTGraphicalObjectData graphicData = inline.addNewGraphic().addNewGraphicData();
            XmlToken xmlToken = null;
            try
            {
                xmlToken = XmlToken.Factory.parse(picXml);
            }
            catch(XmlException xe)
            {
                xe.printStackTrace();
            }
            inline.set(xmlToken);
            //graphicData.set(xmlToken);

            inline.setDistT(0);
            inline.setDistB(0);
            inline.setDistL(0);
            inline.setDistR(0);

            CTPositiveSize2D extent = inline.addNewExtent();
            extent.setCx(width);
            extent.setCy(height);

            CTNonVisualDrawingProps docPr = inline.addNewDocPr();
            docPr.setId(id);
            docPr.setName("Picture " + id);
            docPr.setDescr("Generated");
        }
        public static int obtenerPictureType(String extension){
            extension=extension.toUpperCase();
            int retorno=0;
            if(extension.equals(Constantes.TIPO_ARCHIVO_JPG) || extension.equals(Constantes.TIPO_ARCHIVO_JPEG)){
                retorno=Document.PICTURE_TYPE_JPEG;
            }else if(extension.equals(Constantes.TIPO_ARCHIVO_PNG)){
                retorno=Document.PICTURE_TYPE_PNG;
            }else if(extension.equals(Constantes.TIPO_ARCHIVO_GIF)){
                retorno=Document.PICTURE_TYPE_GIF;
            }
            return retorno;
        }
        /* OSINE_SFS-480 - RSIS 01 - Fin */
    }
    public static String obtenerMes(int mes){
    	String var="";
    	switch (mes) {
			case 1:var="Enero";break;
			case 2:var="Febrero";break;
			case 3:var="Marzo";break;
			case 4:var="Abril";break;
			case 5:var="Mayo";break;
			case 6:var="Junio";break;
			case 7:var="Julio";break;
			case 8:var="Agosto";break;
			case 9:var="Setiembre";break;
			case 10:var="Octubre";break;
			case 11:var="Noviembre";break;
			case 12:var="Diciembre";break;
			default:var="";break;
		}
    	return var;
    }
}
