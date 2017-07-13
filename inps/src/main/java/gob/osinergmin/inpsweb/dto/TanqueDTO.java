package gob.osinergmin.inpsweb.dto;

public class TanqueDTO {
	private Long numeroTanque;
	private String descripcionTipoInstalacion;
	private Long capacidadNominal;
	private String serie;
	
	public Long getNumeroTanque() {
		return numeroTanque;
	}
	public void setNumeroTanque(Long numeroTanque) {
		this.numeroTanque = numeroTanque;
	}
	public String getDescripcionTipoInstalacion() {
		return descripcionTipoInstalacion;
	}
	public void setDescripcionTipoInstalacion(String descripcionTipoInstalacion) {
		this.descripcionTipoInstalacion = descripcionTipoInstalacion;
	}
	public Long getCapacidadNominal() {
		return capacidadNominal;
	}
	public void setCapacidadNominal(Long capacidadNominal) {
		this.capacidadNominal = capacidadNominal;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
}
