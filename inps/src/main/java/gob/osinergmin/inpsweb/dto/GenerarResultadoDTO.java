/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 /**
 * Resumen 
 * Objeto				: GenerarResultadoDTO.java 
 * Descripción	        :
 * Fecha de Creación	: 
 * PR de Creación		: 
 * Autor				: 
 * ---------------------------------------------------------------------------------------------------
 * Modificaciones   Fecha             Nombre               Descripción
 * ---------------------------------------------------------------------------------------------------
 * OSINE_MAN_DSR_0022  | 18/06/2017     | Carlos Quijano Chavez::ADAPTER  | Agrega opciones de cierre parcial y definitivo
 */
package gob.osinergmin.inpsweb.dto;

import gob.osinergmin.mdicommon.domain.dto.ResultadoSupervisionDTO;

/**
 *
 * @author zchaupis
 */
public class GenerarResultadoDTO {
    private Long idSupervision;
    private String flagInfracciones;
    private String nroCarta;
    private String direccionOperativa;
    private String idDepartamento;
    private String idProvincia;
    private String idDistrito;
    private String fechaFin;
    private String horaFin;
	/* OSINE_MAN_DSR_0022 - Inicio */
	private String tipoCierre;
	/* OSINE_MAN_DSR_0022 - Fin */
    private String flagObstaculizados;
    private String identificadorEjecucionMedi;
    private String observacion;
    private ResultadoSupervisionDTO resultadoSupervisionInicialDTO;
    
    private String flagSupervisionInicial;
    private Long idTipoAsignacion;
    private ResultadoSupervisionDTO resultadoSupervisionFinalDTO;
    

    /**
     * @return the idSupervision
     */
    public Long getIdSupervision() {
        return idSupervision;
    }

    /**
     * @param idSupervision the idSupervision to set
     */
    public void setIdSupervision(Long idSupervision) {
        this.idSupervision = idSupervision;
    }

    /**
     * @return the flagInfracciones
     */
    public String getFlagInfracciones() {
        return flagInfracciones;
    }

    /**
     * @param flagInfracciones the flagInfracciones to set
     */
    public void setFlagInfracciones(String flagInfracciones) {
        this.flagInfracciones = flagInfracciones;
    }

    /**
     * @return the nroCarta
     */
    public String getNroCarta() {
        return nroCarta;
    }

    /**
     * @param nroCarta the nroCarta to set
     */
    public void setNroCarta(String nroCarta) {
        this.nroCarta = nroCarta;
    }

    /**
     * @return the direccionOperativa
     */
    public String getDireccionOperativa() {
        return direccionOperativa;
    }

    /**
     * @param direccionOperativa the direccionOperativa to set
     */
    public void setDireccionOperativa(String direccionOperativa) {
        this.direccionOperativa = direccionOperativa;
    }

    /**
     * @return the idDepartamento
     */
    public String getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * @param idDepartamento the idDepartamento to set
     */
    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * @return the idProvincia
     */
    public String getIdProvincia() {
        return idProvincia;
    }

    /**
     * @param idProvincia the idProvincia to set
     */
    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    /**
     * @return the idDistrito
     */
    public String getIdDistrito() {
        return idDistrito;
    }

    /**
     * @param idDistrito the idDistrito to set
     */
    public void setIdDistrito(String idDistrito) {
        this.idDistrito = idDistrito;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the horaFin
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the flagObstaculizados
     */
    public String getFlagObstaculizados() {
        return flagObstaculizados;
    }

    /**
     * @param flagObstaculizados the flagObstaculizados to set
     */
    public void setFlagObstaculizados(String flagObstaculizados) {
        this.flagObstaculizados = flagObstaculizados;
    }

    /**
     * @return the identificadorEjecucionMedi
     */
    public String getIdentificadorEjecucionMedi() {
        return identificadorEjecucionMedi;
    }

    /**
     * @param identificadorEjecucionMedi the identificadorEjecucionMedi to set
     */
    public void setIdentificadorEjecucionMedi(String identificadorEjecucionMedi) {
        this.identificadorEjecucionMedi = identificadorEjecucionMedi;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the resultadoSupervisionInicialDTO
     */
    public ResultadoSupervisionDTO getResultadoSupervisionInicialDTO() {
        return resultadoSupervisionInicialDTO;
    }

    /**
     * @param resultadoSupervisionInicialDTO the resultadoSupervisionInicialDTO to set
     */
    public void setResultadoSupervisionInicialDTO(ResultadoSupervisionDTO resultadoSupervisionInicialDTO) {
        this.resultadoSupervisionInicialDTO = resultadoSupervisionInicialDTO;
    }

    /**
     * @return the flagSupervisionInicial
     */
    public String getFlagSupervisionInicial() {
        return flagSupervisionInicial;
    }

    /**
     * @param flagSupervisionInicial the flagSupervisionInicial to set
     */
    public void setFlagSupervisionInicial(String flagSupervisionInicial) {
        this.flagSupervisionInicial = flagSupervisionInicial;
    }

    /**
     * @return the idTipoAsignacion
     */
    public Long getIdTipoAsignacion() {
        return idTipoAsignacion;
    }

    /**
     * @param idTipoAsignacion the idTipoAsignacion to set
     */
    public void setIdTipoAsignacion(Long idTipoAsignacion) {
        this.idTipoAsignacion = idTipoAsignacion;
    }

    /**
     * @return the resultadoSupervisionFinalDTO
     */
    public ResultadoSupervisionDTO getResultadoSupervisionFinalDTO() {
        return resultadoSupervisionFinalDTO;
    }

    /**
     * @param resultadoSupervisionFinalDTO the resultadoSupervisionFinalDTO to set
     */
    public void setResultadoSupervisionFinalDTO(ResultadoSupervisionDTO resultadoSupervisionFinalDTO) {
        this.resultadoSupervisionFinalDTO = resultadoSupervisionFinalDTO;
    }
	/* OSINE_MAN_DSR_0022 - Inicio */
	public String getTipoCierre() {
		return tipoCierre;
	}
	
	public void setTipoCierre(String tipoCierre) {
		this.tipoCierre = tipoCierre;
	}
	/* OSINE_MAN_DSR_0022 - Fin */   
    
}
