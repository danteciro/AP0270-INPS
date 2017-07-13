/**
* Resumen.
* Objeto            : MdiContratoEmpresaLocador.java.
* Descripción       : Domain con los atributos del ContratoEmpresaLocador.
* Fecha de Creación : 09/06/2016      
* PR de Creación    : OSINE_SFS-480            
* Autor             : Mario Dioses Fernandez.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          			Descripción
* --------------------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016    Mario Dioses Fernandez    Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).                                                       
*/

package gob.osinergmin.inpsweb.domain;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mdiosesf
 */
/* OSINE_SFS-480 - RSIS 11 - Inicio */
@Entity
@Table(name = "MDI_CONTRATO_EMPRESA_LOCADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiContratoEmpresaLocador.findAll", query = "SELECT m FROM MdiContratoEmpresaLocador m")
})
public class MdiContratoEmpresaLocador extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONTRATO_EMPRESA_LOCADOR")
    private Long idContratoEmpresaLocador;
    @Basic(optional = false)
    @Column(name = "NUMERO_CONTRATO")
    private String numeroContrato;
    @Column(name = "FECHA_INICIO_CONTRATO")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioContrato;
    @Column(name = "FECHA_FIN_CONTRATO")
    @Temporal(TemporalType.DATE)
    private Date fechaFinContrato;    
    @Column(name = "HONORARIO_TOTAL")
    private BigDecimal honorarioTotal;
    @Column(name = "MOTIVO_CONCLUSION")
    private Long motivoConclusion;
    @Column(name = "NUMERO_CARTA_NOTARIAL")
    private String numeroCartaNotarial;
    @Column(name = "FECHA_NOTIFICACION")
    @Temporal(TemporalType.DATE)
    private Date fechaNotificacion;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "FECHA_EFECTIVO")
    @Temporal(TemporalType.DATE)
    private Date fechaEfectivo;
    @Basic(optional = false)
    @Column(name = "ESTADO_PROCESO")
    private long estadoProceso;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "CATEGORIA_LOCADOR")
    private Long categoriaLocador;
    @Column(name = "MOTIVO_CONCLUSION_ESPECIFICA")
    private Long motivoConclusionEspecifica;
    @Column(name = "TIPO_MONEDA")
    private Long tipoMoneda;
    @Column(name = "FECHA_INICIO_INSCRIPCION")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioInscripcion;
    @Column(name = "FECHA_FIN_INSCRIPCION")
    @Temporal(TemporalType.DATE)
    private Date fechaFinInscripcion;
    @Column(name = "MODALIDAD_CONTRATO")
    private Long modalidadContrato;
    @JoinColumn(name = "ID_SUPERVISORA_EMPRESA", referencedColumnName = "ID_SUPERVISORA_EMPRESA")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiSupervisoraEmpresa idSupervisoraEmpresa;
    @JoinColumn(name = "ID_LOCADOR", referencedColumnName = "ID_LOCADOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiLocador idLocador;
    @JoinColumn(name = "ID_CONCURSO", referencedColumnName = "ID_CONCURSO")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiConcurso idConcurso;
    @OneToMany(mappedBy = "idContratoEmpresaLocador", fetch = FetchType.LAZY)
    private List<MdiLocadorDestaque> mdiLocadorDestaqueList;

    public MdiContratoEmpresaLocador() {
    }

    public MdiContratoEmpresaLocador(Long idContratoEmpresaLocador) {
        this.idContratoEmpresaLocador = idContratoEmpresaLocador;
    }

    public MdiContratoEmpresaLocador(Long idContratoEmpresaLocador, String numeroContrato, long estadoProceso, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idContratoEmpresaLocador = idContratoEmpresaLocador;
        this.numeroContrato = numeroContrato;
        this.estadoProceso = estadoProceso;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdContratoEmpresaLocador() {
        return idContratoEmpresaLocador;
    }

    public void setIdContratoEmpresaLocador(Long idContratoEmpresaLocador) {
        this.idContratoEmpresaLocador = idContratoEmpresaLocador;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public BigDecimal getHonorarioTotal() {
        return honorarioTotal;
    }

    public void setHonorarioTotal(BigDecimal honorarioTotal) {
        this.honorarioTotal = honorarioTotal;
    }

    public Long getMotivoConclusion() {
        return motivoConclusion;
    }

    public void setMotivoConclusion(Long motivoConclusion) {
        this.motivoConclusion = motivoConclusion;
    }

    public String getNumeroCartaNotarial() {
        return numeroCartaNotarial;
    }

    public void setNumeroCartaNotarial(String numeroCartaNotarial) {
        this.numeroCartaNotarial = numeroCartaNotarial;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaEfectivo() {
        return fechaEfectivo;
    }

    public void setFechaEfectivo(Date fechaEfectivo) {
        this.fechaEfectivo = fechaEfectivo;
    }

    public long getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(long estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCategoriaLocador() {
        return categoriaLocador;
    }

    public void setCategoriaLocador(Long categoriaLocador) {
        this.categoriaLocador = categoriaLocador;
    }

    public Long getMotivoConclusionEspecifica() {
        return motivoConclusionEspecifica;
    }

    public void setMotivoConclusionEspecifica(Long motivoConclusionEspecifica) {
        this.motivoConclusionEspecifica = motivoConclusionEspecifica;
    }

    public Long getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Long tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Date getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(Date fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public Date getFechaFinInscripcion() {
        return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(Date fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public Long getModalidadContrato() {
        return modalidadContrato;
    }

    public void setModalidadContrato(Long modalidadContrato) {
        this.modalidadContrato = modalidadContrato;
    }

    public MdiSupervisoraEmpresa getIdSupervisoraEmpresa() {
        return idSupervisoraEmpresa;
    }

    public void setIdSupervisoraEmpresa(MdiSupervisoraEmpresa idSupervisoraEmpresa) {
        this.idSupervisoraEmpresa = idSupervisoraEmpresa;
    }

    public MdiLocador getIdLocador() {
        return idLocador;
    }

    public void setIdLocador(MdiLocador idLocador) {
        this.idLocador = idLocador;
    }

    public MdiConcurso getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(MdiConcurso idConcurso) {
        this.idConcurso = idConcurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContratoEmpresaLocador != null ? idContratoEmpresaLocador.hashCode() : 0);
        return hash;
    }
    
    @XmlTransient
    public List<MdiLocadorDestaque> getMdiLocadorDestaqueList() {
        return mdiLocadorDestaqueList;
    }

    public void setMdiLocadorDestaqueList(List<MdiLocadorDestaque> mdiLocadorDestaqueList) {
        this.mdiLocadorDestaqueList = mdiLocadorDestaqueList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiContratoEmpresaLocador)) {
            return false;
        }
        MdiContratoEmpresaLocador other = (MdiContratoEmpresaLocador) object;
        if ((this.idContratoEmpresaLocador == null && other.idContratoEmpresaLocador != null) || (this.idContratoEmpresaLocador != null && !this.idContratoEmpresaLocador.equals(other.idContratoEmpresaLocador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiContratoEmpresaLocador[ idContratoEmpresaLocador=" + idContratoEmpresaLocador + " ]";
    }
}
/* OSINE_SFS-480 - RSIS 11 - Fin */
