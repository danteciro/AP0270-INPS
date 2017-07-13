/**
* Resumen.
* Objeto            : MdiConcurso.java.
* Descripción       : Domain con los atributos del Concurso.
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
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mdiosesf
 */
/* OSINE_SFS-480 - RSIS 11 - Inicio */
@Entity
@Table(name = "MDI_CONCURSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiConcurso.findAll", query = "SELECT m FROM MdiConcurso m")
})
public class MdiConcurso extends Auditoria {
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CONCURSO")
    private Long idConcurso;
    @Basic(optional = false)
    @Column(name = "NUMERO_CONCURSO")
    private String numeroConcurso;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CONCURSO")
    private String nombreConcurso;
    @Column(name = "DESCRIPCION_CONCURSO")
    private String descripcionConcurso;
    @Column(name = "NUMERO_PLAZAS")
    private BigInteger numeroPlazas;
    @Column(name = "FECHA_CONVOCATORIA")
    @Temporal(TemporalType.DATE)
    private Date fechaConvocatoria;
    @Column(name = "FECHA_PROPUESTA_TECNICA")
    @Temporal(TemporalType.DATE)
    private Date fechaPropuestaTecnica;
    @Column(name = "FECHA_PROPUESTA_ECONOMICA")
    @Temporal(TemporalType.DATE)
    private Date fechaPropuestaEconomica;
    @Column(name = "FECHA_EMISION_RESULTADO")
    @Temporal(TemporalType.DATE)
    private Date fechaEmisionResultado;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idConcurso", fetch = FetchType.LAZY)
    private List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList;

    public MdiConcurso() {
    }

    public MdiConcurso(Long idConcurso) {
        this.idConcurso = idConcurso;
    }

    public MdiConcurso(Long idConcurso, String numeroConcurso, String nombreConcurso, String estado, String usuarioCreacion, Date fechaCreacion, String terminalCreacion) {
        this.idConcurso = idConcurso;
        this.numeroConcurso = numeroConcurso;
        this.nombreConcurso = nombreConcurso;
        this.estado = estado;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.terminalCreacion = terminalCreacion;
    }

    public Long getIdConcurso() {
        return idConcurso;
    }

    public void setIdConcurso(Long idConcurso) {
        this.idConcurso = idConcurso;
    }

    public String getNumeroConcurso() {
        return numeroConcurso;
    }

    public void setNumeroConcurso(String numeroConcurso) {
        this.numeroConcurso = numeroConcurso;
    }

    public String getNombreConcurso() {
        return nombreConcurso;
    }

    public void setNombreConcurso(String nombreConcurso) {
        this.nombreConcurso = nombreConcurso;
    }

    public String getDescripcionConcurso() {
        return descripcionConcurso;
    }

    public void setDescripcionConcurso(String descripcionConcurso) {
        this.descripcionConcurso = descripcionConcurso;
    }

    public BigInteger getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(BigInteger numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public Date getFechaConvocatoria() {
        return fechaConvocatoria;
    }

    public void setFechaConvocatoria(Date fechaConvocatoria) {
        this.fechaConvocatoria = fechaConvocatoria;
    }

    public Date getFechaPropuestaTecnica() {
        return fechaPropuestaTecnica;
    }

    public void setFechaPropuestaTecnica(Date fechaPropuestaTecnica) {
        this.fechaPropuestaTecnica = fechaPropuestaTecnica;
    }

    public Date getFechaPropuestaEconomica() {
        return fechaPropuestaEconomica;
    }

    public void setFechaPropuestaEconomica(Date fechaPropuestaEconomica) {
        this.fechaPropuestaEconomica = fechaPropuestaEconomica;
    }

    public Date getFechaEmisionResultado() {
        return fechaEmisionResultado;
    }

    public void setFechaEmisionResultado(Date fechaEmisionResultado) {
        this.fechaEmisionResultado = fechaEmisionResultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<MdiContratoEmpresaLocador> getMdiContratoEmpresaLocadorList() {
        return mdiContratoEmpresaLocadorList;
    }

    public void setMdiContratoEmpresaLocadorList(List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList) {
        this.mdiContratoEmpresaLocadorList = mdiContratoEmpresaLocadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConcurso != null ? idConcurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiConcurso)) {
            return false;
        }
        MdiConcurso other = (MdiConcurso) object;
        if ((this.idConcurso == null && other.idConcurso != null) || (this.idConcurso != null && !this.idConcurso.equals(other.idConcurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiConcurso[ idConcurso=" + idConcurso + " ]";
    }
}
/* OSINE_SFS-480 - RSIS 11 - Fin */

