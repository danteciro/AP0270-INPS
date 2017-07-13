/**
* Resumen		
* Objeto			: PghObliTipoUnidOrga.java
* Descripción		: Clase del modelo de dominio PghObliTipoUnidOrga.
* Fecha de Creación	: 02/11/2016.
* PR de Creación	: OSINE_SFS-1344,
* Autor				: Julio Piro Gonzales.
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
*
*/ 
package gob.osinergmin.inpsweb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gvillanueva
 */
@Entity
@Table(name = "PGH_OBLI_TIPO_UNID_ORGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PghObliTipoUnidOrga.findAll", query = "SELECT p FROM PghObliTipoUnidOrga p")
})
public class PghObliTipoUnidOrga extends Auditoria {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OBLI_TIPO_UNID_ORGA")
    private Long idObliTipoUnidOrga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "ID_OBLIGACION_TIPO", referencedColumnName = "ID_OBLIGACION_TIPO")
    @ManyToOne(fetch = FetchType.LAZY)
    private PghObligacionTipo idObligacionTipo;
    @JoinColumn(name = "ID_UNIDAD_ORGANICA", referencedColumnName = "ID_UNIDAD_ORGANICA")
    @ManyToOne(fetch = FetchType.LAZY)
    private MdiUnidadOrganica idUnidadOrganica;

    public PghObliTipoUnidOrga() {
    }

    public PghObliTipoUnidOrga(Long idObliTipoUnidOrga) {
        this.idObliTipoUnidOrga = idObliTipoUnidOrga;
    }

    public Long getIdObliTipoUnidOrga() {
        return idObliTipoUnidOrga;
    }

    public void setIdObliTipoUnidOrga(Long idObliTipoUnidOrga) {
        this.idObliTipoUnidOrga = idObliTipoUnidOrga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PghObligacionTipo getIdObligacionTipo() {
        return idObligacionTipo;
    }

    public void setIdObligacionTipo(PghObligacionTipo idObligacionTipo) {
        this.idObligacionTipo = idObligacionTipo;
    }

    public MdiUnidadOrganica getIdUnidadOrganica() {
        return idUnidadOrganica;
    }

    public void setIdUnidadOrganica(MdiUnidadOrganica idUnidadOrganica) {
        this.idUnidadOrganica = idUnidadOrganica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObliTipoUnidOrga != null ? idObliTipoUnidOrga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PghObliTipoUnidOrga)) {
            return false;
        }
        PghObliTipoUnidOrga other = (PghObliTipoUnidOrga) object;
        if ((this.idObliTipoUnidOrga == null && other.idObliTipoUnidOrga != null) || (this.idObliTipoUnidOrga != null && !this.idObliTipoUnidOrga.equals(other.idObliTipoUnidOrga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.PghObliTipoUnidOrga[ idObliTipoUnidOrga=" + idObliTipoUnidOrga + " ]";
    }
    
}
