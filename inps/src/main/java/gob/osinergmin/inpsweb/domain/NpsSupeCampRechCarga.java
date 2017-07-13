/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;


import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vubaldo
 */
@Entity
@Table(name = "NPS_SUPE_CAMP_RECH_CARGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NpsSupeCampRechCarga.findAll", query = "SELECT n FROM NpsSupeCampRechCarga n"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByIdSupeCampRechCarga", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.idSupeCampRechCarga = :idSupeCampRechCarga"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByFechaInicio", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByFechaFin", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.fechaFin = :fechaFin"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByNombreSupervisorEmpresa", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.nombreSupervisorEmpresa = :nombreSupervisorEmpresa"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByCargoSupervisorEmpresa", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.cargoSupervisorEmpresa = :cargoSupervisorEmpresa"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByNombreSupervisorOsinergmin", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.nombreSupervisorOsinergmin = :nombreSupervisorOsinergmin"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByCargoSupervisorOsinergmin", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.cargoSupervisorOsinergmin = :cargoSupervisorOsinergmin"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByIdUbigeo", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.idUbigeo = :idUbigeo"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByAjusteRele", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.ajusteRele = :ajusteRele"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByHabilitacionRele", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.habilitacionRele = :habilitacionRele"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByProtocoloRele", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.protocoloRele = :protocoloRele"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByReporteRele", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.reporteRele = :reporteRele"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByOtrasObservaciones", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.otrasObservaciones = :otrasObservaciones"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByNotasEmpresa", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.notasEmpresa = :notasEmpresa"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByNombreActaDoc", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.nombreActaDoc = :nombreActaDoc"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByIdZona", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.idZona = :idZona"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByNombreZona", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.nombreZona = :nombreZona"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByUsuarioCreacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByFechaCreacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByTerminalCreacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByUsuarioActualizacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByFechaActualizacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByTerminalActualizacion", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByFlgCerrado", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.flgCerrado = :flgCerrado"),
    @NamedQuery(name = "NpsSupeCampRechCarga.findByEstado", query = "SELECT n FROM NpsSupeCampRechCarga n WHERE n.estado = :estado")})
public class NpsSupeCampRechCarga extends Auditoria {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUPE_CAMP_RECH_CARGA")
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "NPS_SUPE_CAMP_RECH_CARGA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long idSupeCampRechCarga;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "NOMBRE_SUPERVISOR_EMPRESA")
    private String nombreSupervisorEmpresa;
    @Column(name = "CARGO_SUPERVISOR_EMPRESA")
    private String cargoSupervisorEmpresa;
    @Column(name = "NOMBRE_SUPERVISOR_OSINERGMIN")
    private String nombreSupervisorOsinergmin;
    @Column(name = "CARGO_SUPERVISOR_OSINERGMIN")
    private String cargoSupervisorOsinergmin;
    @Column(name = "ID_UBIGEO")
    private String idUbigeo;
    @Column(name = "AJUSTE_RELE")
    private String ajusteRele;
    @Column(name = "HABILITACION_RELE")
    private String habilitacionRele;
    @Column(name = "PROTOCOLO_RELE")
    private String protocoloRele;
    @Column(name = "REPORTE_RELE")
    private String reporteRele;
    @Column(name = "OTRAS_OBSERVACIONES")
    private String otrasObservaciones;
    @Column(name = "NOTAS_EMPRESA")
    private String notasEmpresa;
    @Column(name = "NOMBRE_ACTA_DOC")
    private String nombreActaDoc;
    @Column(name = "ID_ACTA_DOC")
    private String idActaDoc;
    @Column(name = "ID_ZONA")
    private String idZona;
    @Column(name = "NOMBRE_ZONA")
    private String nombreZona;
    @Column(name = "FLG_CERRADO")
    private String flgCerrado;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_SUPERVISION_RECH_CARGA", referencedColumnName = "ID_SUPERVISION_RECH_CARGA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private NpsSupervisionRechCarga idSupervisionRechCarga;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSupeCampRechCarga", fetch = FetchType.LAZY)
    private List<NpsDetaSupeCampRechCarga> npsDetaSupeCampRechCargaList;

    public NpsSupeCampRechCarga() {
    }

    public NpsSupeCampRechCarga(Long idSupeCampRechCarga) {
        this.idSupeCampRechCarga = idSupeCampRechCarga;
    }

    public NpsSupeCampRechCarga(Long idSupeCampRechCarga, String usuarioCreacion, Date fechaCreacion, String terminalCreacion, String estado) {
        this.idSupeCampRechCarga = idSupeCampRechCarga;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
        this.estado = estado;
    }

    public Long getIdSupeCampRechCarga() {
        return idSupeCampRechCarga;
    }

    public void setIdSupeCampRechCarga(Long idSupeCampRechCarga) {
        this.idSupeCampRechCarga = idSupeCampRechCarga;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombreSupervisorEmpresa() {
        return nombreSupervisorEmpresa;
    }

    public void setNombreSupervisorEmpresa(String nombreSupervisorEmpresa) {
        this.nombreSupervisorEmpresa = nombreSupervisorEmpresa;
    }

    public String getCargoSupervisorEmpresa() {
        return cargoSupervisorEmpresa;
    }

    public void setCargoSupervisorEmpresa(String cargoSupervisorEmpresa) {
        this.cargoSupervisorEmpresa = cargoSupervisorEmpresa;
    }

    public String getNombreSupervisorOsinergmin() {
        return nombreSupervisorOsinergmin;
    }

    public void setNombreSupervisorOsinergmin(String nombreSupervisorOsinergmin) {
        this.nombreSupervisorOsinergmin = nombreSupervisorOsinergmin;
    }

    public String getCargoSupervisorOsinergmin() {
        return cargoSupervisorOsinergmin;
    }

    public void setCargoSupervisorOsinergmin(String cargoSupervisorOsinergmin) {
        this.cargoSupervisorOsinergmin = cargoSupervisorOsinergmin;
    }

    public String getIdUbigeo() {
        return idUbigeo;
    }

    public void setIdUbigeo(String idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    public String getAjusteRele() {
        return ajusteRele;
    }

    public void setAjusteRele(String ajusteRele) {
        this.ajusteRele = ajusteRele;
    }

    public String getHabilitacionRele() {
        return habilitacionRele;
    }

    public void setHabilitacionRele(String habilitacionRele) {
        this.habilitacionRele = habilitacionRele;
    }

    public String getProtocoloRele() {
        return protocoloRele;
    }

    public void setProtocoloRele(String protocoloRele) {
        this.protocoloRele = protocoloRele;
    }

    public String getReporteRele() {
        return reporteRele;
    }

    public void setReporteRele(String reporteRele) {
        this.reporteRele = reporteRele;
    }

    public String getOtrasObservaciones() {
        return otrasObservaciones;
    }

    public void setOtrasObservaciones(String otrasObservaciones) {
        this.otrasObservaciones = otrasObservaciones;
    }

    public String getNotasEmpresa() {
        return notasEmpresa;
    }

    public void setNotasEmpresa(String notasEmpresa) {
        this.notasEmpresa = notasEmpresa;
    }

    public String getNombreActaDoc() {
        return nombreActaDoc;
    }

    public void setNombreActaDoc(String nombreActaDoc) {
        this.nombreActaDoc = nombreActaDoc;
    }
    
    public String getIdActaDoc() {
		return idActaDoc;
	}

	public void setIdActaDoc(String idActaDoc) {
		this.idActaDoc = idActaDoc;
	}
    
    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTerminalActualizacion() {
        return terminalActualizacion;
    }

    public void setTerminalActualizacion(String terminalActualizacion) {
        this.terminalActualizacion = terminalActualizacion;
    }

    public String getFlgCerrado() {
        return flgCerrado;
    }

    public void setFlgCerrado(String flgCerrado) {
        this.flgCerrado = flgCerrado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public NpsSupervisionRechCarga getIdSupervisionRechCarga() {
        return idSupervisionRechCarga;
    }

    public void setIdSupervisionRechCarga(NpsSupervisionRechCarga idSupervisionRechCarga) {
        this.idSupervisionRechCarga = idSupervisionRechCarga;
    }

    @XmlTransient
    public List<NpsDetaSupeCampRechCarga> getNpsDetaSupeCampRechCargaList() {
        return npsDetaSupeCampRechCargaList;
    }

    public void setNpsDetaSupeCampRechCargaList(List<NpsDetaSupeCampRechCarga> npsDetaSupeCampRechCargaList) {
        this.npsDetaSupeCampRechCargaList = npsDetaSupeCampRechCargaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSupeCampRechCarga != null ? idSupeCampRechCarga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NpsSupeCampRechCarga)) {
            return false;
        }
        NpsSupeCampRechCarga other = (NpsSupeCampRechCarga) object;
        if ((this.idSupeCampRechCarga == null && other.idSupeCampRechCarga != null) || (this.idSupeCampRechCarga != null && !this.idSupeCampRechCarga.equals(other.idSupeCampRechCarga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.siged.com.NpsSupeCampRechCarga[ idSupeCampRechCarga=" + idSupeCampRechCarga + " ]";
    }
    
}
