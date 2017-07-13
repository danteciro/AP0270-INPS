/**
 * Resumen 
 * Objeto				: ObligacionDsrUpdtDTO.java 
 * Descripción	        : DTO de obligacion DSR con codigo Infraccion. 
 * Fecha de Creación	: 19/06/2017.
 * PR de Creación		: OSINE_MAN_DSR_0025.
 * Autor				: Carlos Quijano Chavez::ADAPTER.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */
package gob.osinergmin.inpsweb.dto;

public class ObligacionDsrUpdtDTO {
	
	private String comentarioObstaculizado;
	
	private Long idDetalleSupervision;
   
    private String descripcionInfraccion;
    
    private String clasecss;
    
    private String descripcionResSup;
    
    private Long prioridad;
    
    private Long codigoResSup;
   
    private Long idDetalleSupervisionAnt;
   
    private Long iteracion;
    
    private String codigoInfraccion;

	public ObligacionDsrUpdtDTO() {
		super();
	}

	public ObligacionDsrUpdtDTO(Long idDetalleSupervision,
			String descripcionInfraccion, String clasecss, String descripcion,
			Long prioridad, Long codigoResultadoSupervision,
			Long idDetalleSupervisionAnt, Long iteracion,
			String codigoInfraccion,String comentarioObstaculizado) {
		super();
		this.idDetalleSupervision = idDetalleSupervision;
		this.descripcionInfraccion = descripcionInfraccion;
		this.clasecss = clasecss;
		this.descripcionResSup = descripcion;
		this.prioridad = prioridad;
		this.codigoResSup = codigoResultadoSupervision;
		this.idDetalleSupervisionAnt = idDetalleSupervisionAnt;
		this.iteracion = iteracion;
		this.codigoInfraccion = codigoInfraccion;
		this.comentarioObstaculizado=comentarioObstaculizado;
	}

	public Long getIdDetalleSupervision() {
		return idDetalleSupervision;
	}

	public void setIdDetalleSupervision(Long idDetalleSupervision) {
		this.idDetalleSupervision = idDetalleSupervision;
	}

	public String getDescripcionInfraccion() {
		return descripcionInfraccion;
	}

	public void setDescripcionInfraccion(String descripcionInfraccion) {
		this.descripcionInfraccion = descripcionInfraccion;
	}

	public String getClasecsso() {
		return clasecss;
	}

	public void setClasecss(String clasecss) {
		this.clasecss = clasecss;
	}

	public String getDescripcionResSup() {
		return descripcionResSup;
	}

	public void setDescripcionResSup(String descripcion) {
		this.descripcionResSup = descripcion;
	}

	public Long getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}

	public Long getCodigoResSup() {
		return codigoResSup;
	}

	public void setCodigoResSup(Long codigoResultadoSupervision) {
		this.codigoResSup = codigoResultadoSupervision;
	}

	public Long getIdDetalleSupervisionAnt() {
		return idDetalleSupervisionAnt;
	}

	public void setIdDetalleSupervisionAnt(Long idDetalleSupervisionAnt) {
		this.idDetalleSupervisionAnt = idDetalleSupervisionAnt;
	}

	public Long getIteracion() {
		return iteracion;
	}

	public void setIteracion(Long iteracion) {
		this.iteracion = iteracion;
	}

	public String getCodigoInfraccion() {
		return codigoInfraccion;
	}

	public void setCodigoInfraccion(String codigoInfraccion) {
		this.codigoInfraccion = codigoInfraccion;
	}

	public String getComentarioObstaculizado() {
		return comentarioObstaculizado;
	}

	public void setComentarioObstaculizado(String comentarioObstaculizado) {
		this.comentarioObstaculizado = comentarioObstaculizado;
	}
    
    
	
}
