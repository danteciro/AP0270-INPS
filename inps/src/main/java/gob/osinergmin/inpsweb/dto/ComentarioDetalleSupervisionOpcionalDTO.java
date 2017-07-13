/**
 * Resumen 
 * Objeto				: ComentarioDetalleSupervisionOpcionalDTO.java 
 * Descripción	        : DTO de comentario opcional. 
 * Fecha de Creación	: 19/06/2017.
 * PR de Creación		: OSINE_MAN_DSR_0037.
 * Autor				: Carlos Quijano Chavez::ADAPTER.
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 *
 */
package gob.osinergmin.inpsweb.dto;

public class ComentarioDetalleSupervisionOpcionalDTO {
	private Long idComentarioDetalleSupervisionOpcional;
	private Long idEscenarioIncumplido;
	private Long idDetalleSupervision;
	private String descripcion;
	
	public ComentarioDetalleSupervisionOpcionalDTO() {
		super();
	}
	public ComentarioDetalleSupervisionOpcionalDTO(Long idEscenarioIncumplido,Long idDetalleSupervision, String descripcion) {
		super();
		this.idEscenarioIncumplido = idEscenarioIncumplido;
		this.idDetalleSupervision = idDetalleSupervision;
		this.descripcion = descripcion;
	}
	public Long getIdEscenarioIncumplido() {
		return idEscenarioIncumplido;
	}
	public void setIdEscenarioIncumplido(Long idEscenarioIncumplido) {
		this.idEscenarioIncumplido = idEscenarioIncumplido;
	}
	public Long getIdDetalleSupervision() {
		return idDetalleSupervision;
	}
	public void setIdDetalleSupervision(Long idDetalleSupervision) {
		this.idDetalleSupervision = idDetalleSupervision;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getIdComentarioDetalleSupervisionOpcional() {
		return idComentarioDetalleSupervisionOpcional;
	}
	public void setIdComentarioDetalleSupervisionOpcional(
			Long idComentarioDetalleSupervisionOpcional) {
		this.idComentarioDetalleSupervisionOpcional = idComentarioDetalleSupervisionOpcional;
	}
}
