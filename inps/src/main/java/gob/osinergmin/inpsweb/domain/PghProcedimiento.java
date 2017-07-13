/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.osinergmin.inpsweb.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "PGH_PROCEDIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghProcedimiento.findAll", query = "SELECT p FROM PghProcedimiento p"),
    @NamedQuery(name = "PghProcedimiento.findByIdProcedimiento", query = "SELECT p FROM PghProcedimiento p WHERE p.idProcedimiento = :idProcedimiento"),
    @NamedQuery(name = "PghProcedimiento.findByItem", query = "SELECT p FROM PghProcedimiento p WHERE p.item = :item"),
    @NamedQuery(name = "PghProcedimiento.findByDenominacion", query = "SELECT p FROM PghProcedimiento p WHERE p.denominacion = :denominacion"),
    @NamedQuery(name = "PghProcedimiento.findByBaseLegal", query = "SELECT p FROM PghProcedimiento p WHERE p.baseLegal = :baseLegal"),
    @NamedQuery(name = "PghProcedimiento.findByDerechoTramitacion", query = "SELECT p FROM PghProcedimiento p WHERE p.derechoTramitacion = :derechoTramitacion"),
    @NamedQuery(name = "PghProcedimiento.findByIdCalificacion", query = "SELECT p FROM PghProcedimiento p WHERE p.idCalificacion = :idCalificacion"),
    @NamedQuery(name = "PghProcedimiento.findByIdSilencioAdministrativo", query = "SELECT p FROM PghProcedimiento p WHERE p.idSilencioAdministrativo = :idSilencioAdministrativo"),
    @NamedQuery(name = "PghProcedimiento.findByPlazoResolver", query = "SELECT p FROM PghProcedimiento p WHERE p.plazoResolver = :plazoResolver"),
    @NamedQuery(name = "PghProcedimiento.findByIdInicioProcedimiento", query = "SELECT p FROM PghProcedimiento p WHERE p.idInicioProcedimiento = :idInicioProcedimiento"),
    @NamedQuery(name = "PghProcedimiento.findByIdAutoridadCompetente", query = "SELECT p FROM PghProcedimiento p WHERE p.idAutoridadCompetente = :idAutoridadCompetente"),
    @NamedQuery(name = "PghProcedimiento.findByIdApelacion", query = "SELECT p FROM PghProcedimiento p WHERE p.idApelacion = :idApelacion"),
    @NamedQuery(name = "PghProcedimiento.findByIdReconsideracion", query = "SELECT p FROM PghProcedimiento p WHERE p.idReconsideracion = :idReconsideracion"),
    @NamedQuery(name = "PghProcedimiento.findByIdAnexoRrh", query = "SELECT p FROM PghProcedimiento p WHERE p.idAnexoRrh = :idAnexoRrh"),
    @NamedQuery(name = "PghProcedimiento.findByIdValorUit", query = "SELECT p FROM PghProcedimiento p WHERE p.idValorUit = :idValorUit"),
    @NamedQuery(name = "PghProcedimiento.findByNotaProcedimiento", query = "SELECT p FROM PghProcedimiento p WHERE p.notaProcedimiento = :notaProcedimiento"),
    @NamedQuery(name = "PghProcedimiento.findByEstado", query = "SELECT p FROM PghProcedimiento p WHERE p.estado = :estado"),
    @NamedQuery(name = "PghProcedimiento.findByUsuarioCreacion", query = "SELECT p FROM PghProcedimiento p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "PghProcedimiento.findByFechaCreacion", query = "SELECT p FROM PghProcedimiento p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PghProcedimiento.findByUsuarioActualizacion", query = "SELECT p FROM PghProcedimiento p WHERE p.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "PghProcedimiento.findByFechaActualizacion", query = "SELECT p FROM PghProcedimiento p WHERE p.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "PghProcedimiento.findByTerminalActualizacion", query = "SELECT p FROM PghProcedimiento p WHERE p.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "PghProcedimiento.findByTerminalCreacion", query = "SELECT p FROM PghProcedimiento p WHERE p.terminalCreacion = :terminalCreacion")})
public class PghProcedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCEDIMIENTO")
    private Long idProcedimiento;
    @Size(max = 10)
    @Column(name = "ITEM")
    private String item;
    @Size(max = 500)
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Size(max = 2000)
    @Column(name = "BASE_LEGAL")
    private String baseLegal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DERECHO_TRAMITACION")
    private BigDecimal derechoTramitacion;
    @Column(name = "ID_CALIFICACION")
    private Long idCalificacion;
    @Column(name = "ID_SILENCIO_ADMINISTRATIVO")
    private Long idSilencioAdministrativo;
    @Column(name = "PLAZO_RESOLVER")
    private BigDecimal plazoResolver;
    @Column(name = "ID_INICIO_PROCEDIMIENTO")
    private Long idInicioProcedimiento;
    @Column(name = "ID_AUTORIDAD_COMPETENTE")
    private Long idAutoridadCompetente;
    @Column(name = "ID_APELACION")
    private Long idApelacion;
    @Column(name = "ID_RECONSIDERACION")
    private Long idReconsideracion;
    @Column(name = "ID_ANEXO_RRH")
    private Long idAnexoRrh;
    @Column(name = "ID_VALOR_UIT")
    private Long idValorUit;
    @Size(max = 4000)
    @Column(name = "NOTA_PROCEDIMIENTO")
    private String notaProcedimiento;
    @Column(name = "ESTADO")
    private Character estado;
    @Size(max = 38)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 38)
    @Column(name = "USUARIO_ACTUALIZACION")
    private String usuarioActualizacion;
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Size(max = 38)
    @Column(name = "TERMINAL_ACTUALIZACION")
    private String terminalActualizacion;
    @Size(max = 38)
    @Column(name = "TERMINAL_CREACION")
    private String terminalCreacion;
    @OneToMany(mappedBy = "idProcedimiento", fetch = FetchType.LAZY)
    private List<PghProcedimientoTramite> pghProcedimientoTramiteList;

    public PghProcedimiento() {
    }

    public PghProcedimiento(Long idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public Long getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(Long idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getBaseLegal() {
        return baseLegal;
    }

    public void setBaseLegal(String baseLegal) {
        this.baseLegal = baseLegal;
    }

    public BigDecimal getDerechoTramitacion() {
        return derechoTramitacion;
    }

    public void setDerechoTramitacion(BigDecimal derechoTramitacion) {
        this.derechoTramitacion = derechoTramitacion;
    }

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Long getIdSilencioAdministrativo() {
        return idSilencioAdministrativo;
    }

    public void setIdSilencioAdministrativo(Long idSilencioAdministrativo) {
        this.idSilencioAdministrativo = idSilencioAdministrativo;
    }

    public BigDecimal getPlazoResolver() {
        return plazoResolver;
    }

    public void setPlazoResolver(BigDecimal plazoResolver) {
        this.plazoResolver = plazoResolver;
    }

    public Long getIdInicioProcedimiento() {
        return idInicioProcedimiento;
    }

    public void setIdInicioProcedimiento(Long idInicioProcedimiento) {
        this.idInicioProcedimiento = idInicioProcedimiento;
    }

    public Long getIdAutoridadCompetente() {
        return idAutoridadCompetente;
    }

    public void setIdAutoridadCompetente(Long idAutoridadCompetente) {
        this.idAutoridadCompetente = idAutoridadCompetente;
    }

    public Long getIdApelacion() {
        return idApelacion;
    }

    public void setIdApelacion(Long idApelacion) {
        this.idApelacion = idApelacion;
    }

    public Long getIdReconsideracion() {
        return idReconsideracion;
    }

    public void setIdReconsideracion(Long idReconsideracion) {
        this.idReconsideracion = idReconsideracion;
    }

    public Long getIdAnexoRrh() {
        return idAnexoRrh;
    }

    public void setIdAnexoRrh(Long idAnexoRrh) {
        this.idAnexoRrh = idAnexoRrh;
    }

    public Long getIdValorUit() {
        return idValorUit;
    }

    public void setIdValorUit(Long idValorUit) {
        this.idValorUit = idValorUit;
    }

    public String getNotaProcedimiento() {
        return notaProcedimiento;
    }

    public void setNotaProcedimiento(String notaProcedimiento) {
        this.notaProcedimiento = notaProcedimiento;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
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

    public String getTerminalCreacion() {
        return terminalCreacion;
    }

    public void setTerminalCreacion(String terminalCreacion) {
        this.terminalCreacion = terminalCreacion;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghProcedimientoTramite> getPghProcedimientoTramiteList() {
        return pghProcedimientoTramiteList;
    }

    public void setPghProcedimientoTramiteList(List<PghProcedimientoTramite> pghProcedimientoTramiteList) {
        this.pghProcedimientoTramiteList = pghProcedimientoTramiteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcedimiento != null ? idProcedimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghProcedimiento)) {
            return false;
        }
        PghProcedimiento other = (PghProcedimiento) object;
        if ((this.idProcedimiento == null && other.idProcedimiento != null) || (this.idProcedimiento != null && !this.idProcedimiento.equals(other.idProcedimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.PghProcedimiento[ idProcedimiento=" + idProcedimiento + " ]";
    }
    
}
