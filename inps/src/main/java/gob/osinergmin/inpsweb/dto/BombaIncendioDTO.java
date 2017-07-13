package gob.osinergmin.inpsweb.dto;

public class BombaIncendioDTO {
	private Long capacidadNominal;
	private String descripcionTipoMotor;
	public Long getCapacidadNominal() {
		return capacidadNominal;
	}
	public void setCapacidadNominal(Long capacidadNominal) {
		this.capacidadNominal = capacidadNominal;
	}
	public String getDescripcionTipoMotor() {
		return descripcionTipoMotor;
	}
	public void setDescripcionTipoMotor(String descripcionTipoMotor) {
		this.descripcionTipoMotor = descripcionTipoMotor;
	}
}
