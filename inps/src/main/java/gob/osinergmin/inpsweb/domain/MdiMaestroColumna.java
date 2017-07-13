/**
* Resumen		
* Objeto		: MdiMaestroColumna.java
* Descripción		: Clase del modelo de dominio MdiMaestroColumna
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     20/05/2016      Hernán Torres Saenz             Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* 
*/ 

package gob.osinergmin.inpsweb.domain;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_MAESTRO_COLUMNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiMaestroColumna.findAll", query = "SELECT m FROM MdiMaestroColumna m"),
    @NamedQuery(name = "MdiMaestroColumna.findByDominioAplicacion", query = "SELECT m FROM MdiMaestroColumna m where m.aplicacion=:aplicacion and m.dominio=:dominio and m.estado='1' ")
    /* OSINE_SFS-480 - RSIS 41 - Inicio */
    ,@NamedQuery(name = "MdiMaestroColumna.findByIdMaestroColumna", query = "SELECT m FROM MdiMaestroColumna m where m.idMaestroColumna=:idMaestroColumna "),
    /* OSINE_SFS-480 - RSIS 41 - Fin */
    @NamedQuery(name = "MdiMaestroColumna.findByDominioAplicacionCodigo", query = "SELECT mc FROM MdiMaestroColumna mc " +
            "WHERE mc.dominio = :dominio " +
            "and mc.aplicacion = :aplicacion " +
            "and mc.codigo= :codigo " +
            "and mc.estado='1' order by mc.descripcion "),
    @NamedQuery(name = "MdiMaestroColumna.findByDominioAplicacionDescripcion", query = "SELECT mc FROM MdiMaestroColumna mc " +
            "WHERE mc.dominio = :dominio " +
            "and mc.aplicacion = :aplicacion " +
            "and mc.descripcion= :descripcion " +
            "and mc.estado='1' order by mc.descripcion ")
})
public class MdiMaestroColumna extends Auditoria {
    @Size(max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 5)
    @Column(name = "CODIGO")
    private String codigo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MAESTRO_COLUMNA")
    private Long idMaestroColumna;
    @Size(max = 20)
    @Column(name = "DOMINIO")
    private String dominio;
    @Size(max = 25)
    @Column(name = "APLICACION")
    private String aplicacion;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idFiltro")
    private Collection<PghConfFiltroEmpSup> pghConfFiltroEmpSupCollection;

    public MdiMaestroColumna() {
    }   

    public MdiMaestroColumna(String descripcion, String codigo,
			Long idMaestroColumna, String dominio) {
		super();
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.idMaestroColumna = idMaestroColumna;
		this.dominio = dominio;
	}
    
    public MdiMaestroColumna(Long idMaestroColumna,String descripcion, String codigo) {
        this.idMaestroColumna = idMaestroColumna;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }
    
    public MdiMaestroColumna(Long idMaestroColumna,String descripcion, String codigo,String aplicacion) {
        this.idMaestroColumna = idMaestroColumna;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.aplicacion = aplicacion;
    }

    public MdiMaestroColumna(Long idMaestroColumna) {
        this.idMaestroColumna = idMaestroColumna;
    }

    public MdiMaestroColumna(Long idMaestroColumna,String descripcion) {
        this.idMaestroColumna = idMaestroColumna;
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdMaestroColumna() {
        return idMaestroColumna;
    }

    public void setIdMaestroColumna(Long idMaestroColumna) {
        this.idMaestroColumna = idMaestroColumna;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Collection<PghConfFiltroEmpSup> getPghConfFiltroEmpSupCollection() {
		return pghConfFiltroEmpSupCollection;
	}

	public void setPghConfFiltroEmpSupCollection(
			Collection<PghConfFiltroEmpSup> pghConfFiltroEmpSupCollection) {
		this.pghConfFiltroEmpSupCollection = pghConfFiltroEmpSupCollection;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaestroColumna != null ? idMaestroColumna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiMaestroColumna)) {
            return false;
        }
        MdiMaestroColumna other = (MdiMaestroColumna) object;
        if ((this.idMaestroColumna == null && other.idMaestroColumna != null) || (this.idMaestroColumna != null && !this.idMaestroColumna.equals(other.idMaestroColumna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiMaestroColumna[ idMaestroColumna=" + idMaestroColumna + " ]";
    }
    
}
