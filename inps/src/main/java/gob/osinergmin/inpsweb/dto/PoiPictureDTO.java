/**
* Resumen
* Objeto		: PoiPictureDTO.java
* Descripción		: Objeto que transporta datos de imagenes a utilizar con libreria POI
* Fecha de Creación	: 21/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     21/05/2016      Julio Piro Gonzales         Insertar imágenes de medios probatorios en plantillas de Informe de Supervisión
* 
*/

package gob.osinergmin.inpsweb.dto;
/* OSINE_SFS-480 - RSIS 01 - Inicio */
public class PoiPictureDTO {
    private byte[] imagen;
    private String nombre;
    private String extension;
    private String Descripcion;
    private int pictureType;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getPictureType() {
        return pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
}
/* OSINE_SFS-480 - RSIS 01 - Fin */