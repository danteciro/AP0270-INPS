/**
* Resumen.
* Objeto            : MdiDirccionUnidadSuprvisada.java.
* Descripción       : Domain con los atributos de las direcciones de una Unidad Supervisada.
* Fecha de Creación : 12/05/2016             
* Autor             : Julio Piro.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          Descripción
* --------------------------------------------------------------------------------------------------------------
*                                                           
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_DIRCCION_UNIDAD_SUPRVISADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiDirccionUnidadSuprvisada.findAll", query = "SELECT m FROM MdiDirccionUnidadSuprvisada m")
})
public class MdiDirccionUnidadSuprvisada extends Auditoria {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "ID_DIRCCION_UNIDAD_SUPRVISADA")
    private Long idDirccionUnidadSuprvisada;
    @Column(name = "ETAPA")
    private String etapa;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "URBANIZACION")
    private String urbanizacion;
    @Column(name = "DIRECCION_COMPLETA")
    private String direccionCompleta;
    @JoinColumn(name = "ID_TIPO_DIRECCION")
    @ManyToOne(optional = false)
    private MdiMaestroColumna idTipoDireccion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ID_TIPO_VIA")
    private Long idTipoVia;
    @Column(name = "NUMERO_VIA")
    private String numeroVia;
    @Column(name = "NUMERO_DEPARTAMENTO")
    private String numeroDepartamento;
    @Column(name = "LOTE")
    private String lote;
    @Column(name = "MANZANA")
    private String manzana;
    @Column(name = "INTERIOR")
    private String interior;
    @Column(name = "KILOMETRO")
    private String kilometro;
    @Column(name = "BLOCK_CHALLET")
    private String blockChallet;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_VIA")
    private String descripcionVia;
    @JoinColumn(name = "ID_UNIDAD_SUPERVISADA", referencedColumnName = "ID_UNIDAD_SUPERVISADA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiUnidadSupervisada idUnidadSupervisada;
    @JoinColumns({
        @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID_DEPARTAMENTO"),
        @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_PROVINCIA"),
        @JoinColumn(name = "ID_DISTRITO", referencedColumnName = "ID_DISTRITO")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MdiUbigeo mdiUbigeo;
    @Column(name = "TELEFONO1")
    private String telefono1; 

    public MdiDirccionUnidadSuprvisada() {
    }

    public MdiDirccionUnidadSuprvisada(Long idDirccionUnidadSuprvisada) {
        this.idDirccionUnidadSuprvisada = idDirccionUnidadSuprvisada;
    }

    public MdiDirccionUnidadSuprvisada(Long idDirccionUnidadSuprvisada, Date fechaCreacion, String usuarioCreacion, String terminalCreacion, String estado, String descripcionVia) {
        this.idDirccionUnidadSuprvisada = idDirccionUnidadSuprvisada;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
        this.terminalCreacion = terminalCreacion;
//        this.idTipoDireccion = idTipoDireccion;
        this.estado = estado;
        this.descripcionVia = descripcionVia;
    }

    public Long getIdDirccionUnidadSuprvisada() {
        return idDirccionUnidadSuprvisada;
    }

    public void setIdDirccionUnidadSuprvisada(Long idDirccionUnidadSuprvisada) {
        this.idDirccionUnidadSuprvisada = idDirccionUnidadSuprvisada;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public MdiMaestroColumna getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(MdiMaestroColumna idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdTipoVia() {
        return idTipoVia;
    }

    public void setIdTipoVia(Long idTipoVia) {
        this.idTipoVia = idTipoVia;
    }

    public String getNumeroVia() {
        return numeroVia;
    }

    public void setNumeroVia(String numeroVia) {
        this.numeroVia = numeroVia;
    }

    public String getNumeroDepartamento() {
        return numeroDepartamento;
    }

    public void setNumeroDepartamento(String numeroDepartamento) {
        this.numeroDepartamento = numeroDepartamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getKilometro() {
        return kilometro;
    }

    public void setKilometro(String kilometro) {
        this.kilometro = kilometro;
    }

    public String getBlockChallet() {
        return blockChallet;
    }

    public void setBlockChallet(String blockChallet) {
        this.blockChallet = blockChallet;
    }

    public String getDescripcionVia() {
        return descripcionVia;
    }

    public void setDescripcionVia(String descripcionVia) {
        this.descripcionVia = descripcionVia;
    }

    public MdiUnidadSupervisada getIdUnidadSupervisada() {
        return idUnidadSupervisada;
    }

    public void setIdUnidadSupervisada(MdiUnidadSupervisada idUnidadSupervisada) {
        this.idUnidadSupervisada = idUnidadSupervisada;
    }

    public MdiUbigeo getMdiUbigeo() {
        return mdiUbigeo;
    }

    public void setMdiUbigeo(MdiUbigeo mdiUbigeo) {
        this.mdiUbigeo = mdiUbigeo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDirccionUnidadSuprvisada != null ? idDirccionUnidadSuprvisada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiDirccionUnidadSuprvisada)) {
            return false;
        }
        MdiDirccionUnidadSuprvisada other = (MdiDirccionUnidadSuprvisada) object;
        if ((this.idDirccionUnidadSuprvisada == null && other.idDirccionUnidadSuprvisada != null) || (this.idDirccionUnidadSuprvisada != null && !this.idDirccionUnidadSuprvisada.equals(other.idDirccionUnidadSuprvisada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inpsweb.domain.MdiDirccionUnidadSuprvisada[ idDirccionUnidadSuprvisada=" + idDirccionUnidadSuprvisada + " ]";
    }    
}