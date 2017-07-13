/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.dto;

import gob.osinergmin.mdicommon.domain.dto.ActividadDTO;
import gob.osinergmin.mdicommon.domain.dto.DetalleSupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.DireccionUnidadSupervisadaDTO;
import gob.osinergmin.mdicommon.domain.dto.DocumentoAdjuntoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaContactoDTO;
import gob.osinergmin.mdicommon.domain.dto.EmpresaSupDTO;
import gob.osinergmin.mdicommon.domain.dto.ExpedienteDTO;
import gob.osinergmin.mdicommon.domain.dto.LocadorDTO;
import gob.osinergmin.mdicommon.domain.dto.OrdenServicioDTO;
import gob.osinergmin.mdicommon.domain.dto.ProcesoDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisionDTO;
import gob.osinergmin.mdicommon.domain.dto.SupervisoraEmpresaDTO;
import gob.osinergmin.mdicommon.domain.dto.UbigeoDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadOrganicaDTO;
import gob.osinergmin.mdicommon.domain.dto.UnidadSupervisadaDTO;
import java.util.List;

/**
 *
 * @author jpiro
 */
public class DatoPlantillaDTO {
    private ActividadDTO idActividad;
    private ProcesoDTO idProceso;
    
    private SupervisionDTO supervision;
    private OrdenServicioDTO ordenServicio;
    //proceso
    private UnidadOrganicaDTO unidadOrganica;
    //actividad
    private EmpresaSupDTO empresaSupervisada;
    private UnidadSupervisadaDTO unidadSupervisada;
    private ExpedienteDTO expediente;
    private DireccionUnidadSupervisadaDTO dirUnidadSupervisada;
    private UbigeoDTO ubigeo;
    private EmpresaContactoDTO empresaContacto;
    private String departamento;
    private String provincia;
    private String distrito;
    private String descripcionDocumentosSupervision;
    private SupervisoraEmpresaDTO supervisoraEmpresa;
    private LocadorDTO locador;
    private String fechaSupervisionActual;
    private String fechaSupervisionAnterior;
    private String fechasVisitas;
    private List<DocumentoAdjuntoDTO> listaDocumentosSupervision;
    private List<DetalleSupervisionDTO> listaHallazgo;
    private String nroRegistroHidrocarburo;
    private String rucEmpresaSupervisada;
    private String cartaVisitaActual;
    private String cartaVisitaAnterior;
    private String cartasVisitas;
    private String cargoPersonaAtiende;
    private String nombrePersonaAtiende;
    private String motivoNoSupervision;
    private Long numeroHallazgos;

    
	public Long getNumeroHallazgos() {
		return numeroHallazgos;
	}

	public void setNumeroHallazgos(Long numeroHallazgos) {
		this.numeroHallazgos = numeroHallazgos;
	}

	public ActividadDTO getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(ActividadDTO idActividad) {
        this.idActividad = idActividad;
    }

    public ProcesoDTO getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(ProcesoDTO idProceso) {
        this.idProceso = idProceso;
    }

    public SupervisionDTO getSupervision() {
        return supervision;
    }

    public void setSupervision(SupervisionDTO supervision) {
        this.supervision = supervision;
    }

    public OrdenServicioDTO getOrdenServicio() {
        return ordenServicio;
    }

    public void setOrdenServicio(OrdenServicioDTO ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    public UnidadOrganicaDTO getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(UnidadOrganicaDTO unidadOrganica) {
        this.unidadOrganica = unidadOrganica;
    }

    public EmpresaSupDTO getEmpresaSupervisada() {
        return empresaSupervisada;
    }

    public void setEmpresaSupervisada(EmpresaSupDTO empresaSupervisada) {
        this.empresaSupervisada = empresaSupervisada;
    }

    public UnidadSupervisadaDTO getUnidadSupervisada() {
        return unidadSupervisada;
    }

    public void setUnidadSupervisada(UnidadSupervisadaDTO unidadSupervisada) {
        this.unidadSupervisada = unidadSupervisada;
    }

    public ExpedienteDTO getExpediente() {
        return expediente;
    }

    public void setExpediente(ExpedienteDTO expediente) {
        this.expediente = expediente;
    }

    public DireccionUnidadSupervisadaDTO getDirUnidadSupervisada() {
        return dirUnidadSupervisada;
    }

    public void setDirUnidadSupervisada(DireccionUnidadSupervisadaDTO dirUnidadSupervisada) {
        this.dirUnidadSupervisada = dirUnidadSupervisada;
    }

    public UbigeoDTO getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(UbigeoDTO ubigeo) {
        this.ubigeo = ubigeo;
    }

    public EmpresaContactoDTO getEmpresaContacto() {
        return empresaContacto;
    }

    public void setEmpresaContacto(EmpresaContactoDTO empresaContacto) {
        this.empresaContacto = empresaContacto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDescripcionDocumentosSupervision() {
        return descripcionDocumentosSupervision;
    }

    public void setDescripcionDocumentosSupervision(String descripcionDocumentosSupervision) {
        this.descripcionDocumentosSupervision = descripcionDocumentosSupervision;
    }

    public SupervisoraEmpresaDTO getSupervisoraEmpresa() {
        return supervisoraEmpresa;
    }

    public void setSupervisoraEmpresa(SupervisoraEmpresaDTO supervisoraEmpresa) {
        this.supervisoraEmpresa = supervisoraEmpresa;
    }

    public LocadorDTO getLocador() {
        return locador;
    }

    public void setLocador(LocadorDTO locador) {
        this.locador = locador;
    }

    public String getFechaSupervisionActual() {
        return fechaSupervisionActual;
    }

    public void setFechaSupervisionActual(String fechaSupervisionActual) {
        this.fechaSupervisionActual = fechaSupervisionActual;
    }

    public String getFechaSupervisionAnterior() {
        return fechaSupervisionAnterior;
    }

    public void setFechaSupervisionAnterior(String fechaSupervisionAnterior) {
        this.fechaSupervisionAnterior = fechaSupervisionAnterior;
    }

    public String getFechasVisitas() {
        return fechasVisitas;
    }

    public void setFechasVisitas(String fechasVisitas) {
        this.fechasVisitas = fechasVisitas;
    }

    public List<DocumentoAdjuntoDTO> getListaDocumentosSupervision() {
        return listaDocumentosSupervision;
    }

    public void setListaDocumentosSupervision(List<DocumentoAdjuntoDTO> listaDocumentosSupervision) {
        this.listaDocumentosSupervision = listaDocumentosSupervision;
    }

    public List<DetalleSupervisionDTO> getListaHallazgo() {
        return listaHallazgo;
    }

    public void setListaHallazgo(List<DetalleSupervisionDTO> listaHallazgo) {
        this.listaHallazgo = listaHallazgo;
    }

	public String getNroRegistroHidrocarburo() {
		return nroRegistroHidrocarburo;
	}

	public void setNroRegistroHidrocarburo(String nroRegistroHidrocarburo) {
		this.nroRegistroHidrocarburo = nroRegistroHidrocarburo;
	}

	public String getRucEmpresaSupervisada() {
		return rucEmpresaSupervisada;
	}

	public void setRucEmpresaSupervisada(String rucEmpresaSupervisada) {
		this.rucEmpresaSupervisada = rucEmpresaSupervisada;
	}

	public String getCartasVisitas() {
		return cartasVisitas;
	}

	public void setCartasVisitas(String cartasVisitas) {
		this.cartasVisitas = cartasVisitas;
	}

	public String getCartaVisitaActual() {
		return cartaVisitaActual;
	}

	public void setCartaVisitaActual(String cartaVisitaActual) {
		this.cartaVisitaActual = cartaVisitaActual;
	}

	public String getCartaVisitaAnterior() {
		return cartaVisitaAnterior;
	}

	public void setCartaVisitaAnterior(String cartaVisitaAnterior) {
		this.cartaVisitaAnterior = cartaVisitaAnterior;
	}

	public String getCargoPersonaAtiende() {
		return cargoPersonaAtiende;
	}

	public void setCargoPersonaAtiende(String cargoPersonaAtiende) {
		this.cargoPersonaAtiende = cargoPersonaAtiende;
	}

	public String getNombrePersonaAtiende() {
		return nombrePersonaAtiende;
	}

	public void setNombrePersonaAtiende(String nombrePersonaAtiende) {
		this.nombrePersonaAtiende = nombrePersonaAtiende;
	}

	public String getMotivoNoSupervision() {
		return motivoNoSupervision;
	}

	public void setMotivoNoSupervision(String motivoNoSupervision) {
		this.motivoNoSupervision = motivoNoSupervision;
	} 
	
    
}
