/**
* Resumen.
* Objeto            : MdiLocadorDestaque.java.
* Descripción       : Clase del modelo de dominio MdiLocadorDestaque.
* Fecha de Creación : 09/06/2016      
* PR de Creación    : OSINE_SFS-480      
* Autor             : Mario Dioses Fernandez.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          		Descripción
* --------------------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016    Mario Dioses Fernandez        Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).                                                       
*/

package gob.osinergmin.inpsweb.domain;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/* OSINE_SFS-480 - RSIS 11 - Inicio */
@Entity
@Table(name = "MDI_LOCADOR_DESTAQUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiLocadorDestaque.findAll", query = "SELECT m FROM MdiLocadorDestaque m")})
public class MdiLocadorDestaque extends Auditoria {
    @Column(name = "ENTIDAD_DESTAQUE")
    private Long entidadDestaque;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOCADOR_DESTAQUE")
    private Long idLocadorDestaque;
    @Column(name = "ID_DIVISION_DESTAQUE")
    private Long idDivisionDestaque;
    @Column(name = "ID_UNIDAD_DESTAQUE")
    private Long idUnidadDestaque;
    @Column(name = "FECHA_INICIO_DESTAQUE")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioDestaque;
    @Column(name = "FECHA_FIN_DESTAQUE")
    @Temporal(TemporalType.DATE)
    private Date fechaFinDestaque;
    @Column(name = "MOTIVO_DESTAQUE")
    private Long motivoDestaque;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "ID_MACRO_REGION", referencedColumnName = "ID_MACRO_REGION"),
        @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")})
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiMacroRegionXRegion mdiMacroRegionXRegion;
    @JoinColumn(name = "ID_CONTRATO_EMPRESA_LOCADOR")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiContratoEmpresaLocador idContratoEmpresaLocador;

    public MdiLocadorDestaque() {
    }

    public MdiLocadorDestaque(Long idLocadorDestaque) {
        this.idLocadorDestaque = idLocadorDestaque;
    }

    public MdiLocadorDestaque(Long idLocadorDestaque, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idLocadorDestaque = idLocadorDestaque;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getEntidadDestaque() {
        return entidadDestaque;
    }

    public void setEntidadDestaque(Long entidadDestaque) {
        this.entidadDestaque = entidadDestaque;
    }

    public Long getIdLocadorDestaque() {
        return idLocadorDestaque;
    }

    public void setIdLocadorDestaque(Long idLocadorDestaque) {
        this.idLocadorDestaque = idLocadorDestaque;
    }

    public Long getIdDivisionDestaque() {
        return idDivisionDestaque;
    }

    public void setIdDivisionDestaque(Long idDivisionDestaque) {
        this.idDivisionDestaque = idDivisionDestaque;
    }

    public Long getIdUnidadDestaque() {
        return idUnidadDestaque;
    }

    public void setIdUnidadDestaque(Long idUnidadDestaque) {
        this.idUnidadDestaque = idUnidadDestaque;
    }

    public Date getFechaInicioDestaque() {
        return fechaInicioDestaque;
    }

    public void setFechaInicioDestaque(Date fechaInicioDestaque) {
        this.fechaInicioDestaque = fechaInicioDestaque;
    }

    public Date getFechaFinDestaque() {
        return fechaFinDestaque;
    }

    public void setFechaFinDestaque(Date fechaFinDestaque) {
        this.fechaFinDestaque = fechaFinDestaque;
    }

    public Long getMotivoDestaque() {
        return motivoDestaque;
    }

    public void setMotivoDestaque(Long motivoDestaque) {
        this.motivoDestaque = motivoDestaque;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public MdiMacroRegionXRegion getMdiMacroRegionXRegion() {
        return mdiMacroRegionXRegion;
    }

    public void setMdiMacroRegionXRegion(MdiMacroRegionXRegion mdiMacroRegionXRegion) {
        this.mdiMacroRegionXRegion = mdiMacroRegionXRegion;
    }
    
    public MdiContratoEmpresaLocador getIdContratoEmpresaLocador() {
        return idContratoEmpresaLocador;
    }

    public void setIdContratoEmpresaLocador(MdiContratoEmpresaLocador idContratoEmpresaLocador) {
        this.idContratoEmpresaLocador = idContratoEmpresaLocador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocadorDestaque != null ? idLocadorDestaque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiLocadorDestaque)) {
            return false;
        }
        MdiLocadorDestaque other = (MdiLocadorDestaque) object;
        if ((this.idLocadorDestaque == null && other.idLocadorDestaque != null) || (this.idLocadorDestaque != null && !this.idLocadorDestaque.equals(other.idLocadorDestaque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiLocadorDestaque[ idLocadorDestaque=" + idLocadorDestaque + " ]";
    }
    
}
/* OSINE_SFS-480 - RSIS 11 - Fin */