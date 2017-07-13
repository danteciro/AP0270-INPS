/**
* Resumen		
* Objeto			: DmUnidadSupervisada.java
* Descripción		: Clase del modelo de dominio DmUnidadSupervisada.
* Fecha de Creación	: 27/10/2016.
* PR de Creación	: OSINE_SFS-1344,
* Autor				: Julio Piro Gonzales.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/ 


package gob.osinergmin.inpsweb.domain;

import java.util.List;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "DM_UNIDAD_SUPERVISADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DmUnidadSupervisada.findAll", query = "SELECT d FROM DmUnidadSupervisada d")
})
public class DmUnidadSupervisada extends Auditoria{
    
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DM_UNIDAD_SUPERVISADA")
    private Long idDmUnidadSupervisada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "CODIGO_UNIDAD_SUPERVISADA")
    private String codigoUnidadSupervisada;
    @Size(max = 200)
    @Column(name = "NOMBRE_UNIDAD_SUPERVISADA")
    private String nombreUnidadSupervisada;
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_MINADO")
    private MdiMaestroColumna idTipoMinado;
    @ManyToOne
    @JoinColumn(name = "ID_ESTRATO")
    private MdiMaestroColumna idEstrato;
    @Size(max = 250)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_TITULAR_MINERO", referencedColumnName = "ID_TITULAR_MINERO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DmTitularMinero idTitularMinero;
    @JoinColumn(name = "ID_ACTIVIDAD", referencedColumnName = "ID_ACTIVIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiActividad idActividad;
    @OneToMany(mappedBy = "idDmUnidadSupervisada", fetch = FetchType.LAZY)
    private List<PghExpediente> pghExpedienteList;    
    
    public DmUnidadSupervisada() {
    }
    
    public DmUnidadSupervisada(Long idDmUnidadSupervisada,String codigoUnidadSupervisada,String nombreUnidadSupervisada,String nombreActividad,Long idActividad) {
        this.idDmUnidadSupervisada = idDmUnidadSupervisada;
        this.codigoUnidadSupervisada=codigoUnidadSupervisada;
        this.nombreUnidadSupervisada=nombreUnidadSupervisada;
        this.idActividad= new MdiActividad(idActividad,nombreActividad);

    }
    
    public DmUnidadSupervisada(Long idDmUnidadSupervisada,String codigoUnidadSupervisada,String nombreUnidadSupervisada,String nombreActividad,Long idActividad,
    		Long idTipoMinado,String nombreTipoMinado,Long idTipoEstrato,String nombreTipoEstrato) {
        this.idDmUnidadSupervisada = idDmUnidadSupervisada;
        this.codigoUnidadSupervisada=codigoUnidadSupervisada;
        this.nombreUnidadSupervisada=nombreUnidadSupervisada;
        this.idActividad= new MdiActividad(idActividad,nombreActividad);
        this.idEstrato = new MdiMaestroColumna(idTipoMinado,nombreTipoMinado); 
        this.idTipoMinado = new MdiMaestroColumna(idTipoEstrato,nombreTipoEstrato);		
    }
    
    public DmUnidadSupervisada(Long idDmUnidadSupervisada) {
        this.idDmUnidadSupervisada = idDmUnidadSupervisada;
    }

    public Long getIdDmUnidadSupervisada() {
        return idDmUnidadSupervisada;
    }

    public void setIdDmUnidadSupervisada(Long idDmUnidadSupervisada) {
        this.idDmUnidadSupervisada = idDmUnidadSupervisada;
    }

    public String getCodigoUnidadSupervisada() {
        return codigoUnidadSupervisada;
    }

    public void setCodigoUnidadSupervisada(String codigoUnidadSupervisada) {
        this.codigoUnidadSupervisada = codigoUnidadSupervisada;
    }

    public String getNombreUnidadSupervisada() {
        return nombreUnidadSupervisada;
    }

    public void setNombreUnidadSupervisada(String nombreUnidadSupervisada) {
        this.nombreUnidadSupervisada = nombreUnidadSupervisada;
    }

    public MdiMaestroColumna getIdTipoMinado() {
        return idTipoMinado;
    }

    public void setIdTipoMinado(MdiMaestroColumna idTipoMinado) {
        this.idTipoMinado = idTipoMinado;
    }

    public MdiMaestroColumna getIdEstrato() {
        return idEstrato;
    }

    public void setIdEstrato(MdiMaestroColumna idEstrato) {
        this.idEstrato = idEstrato;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DmTitularMinero getIdTitularMinero() {
        return idTitularMinero;
    }

    public void setIdTitularMinero(DmTitularMinero idTitularMinero) {
        this.idTitularMinero = idTitularMinero;
    }
    
    public MdiActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(MdiActividad idActividad) {
        this.idActividad = idActividad;
    }
    
    @XmlTransient
    public List<PghExpediente> getPghExpedienteList() {
        return pghExpedienteList;
    }

    public void setPghExpedienteList(List<PghExpediente> pghExpedienteList) {
        this.pghExpedienteList = pghExpedienteList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDmUnidadSupervisada != null ? idDmUnidadSupervisada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DmUnidadSupervisada)) {
            return false;
        }
        DmUnidadSupervisada other = (DmUnidadSupervisada) object;
        if ((this.idDmUnidadSupervisada == null && other.idDmUnidadSupervisada != null) || (this.idDmUnidadSupervisada != null && !this.idDmUnidadSupervisada.equals(other.idDmUnidadSupervisada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.DmUnidadSupervisada[ idDmUnidadSupervisada=" + idDmUnidadSupervisada + " ]";
    }
    
}
