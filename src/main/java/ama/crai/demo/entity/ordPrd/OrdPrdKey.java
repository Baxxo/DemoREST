package ama.crai.demo.entity.ordPrd;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrdPrdKey implements Serializable {

    @Column(name = "id_ord")
    private Long idOrd;

    @Column(name = "name_prd")
    private String namePrd;

    public OrdPrdKey() {
    }

    public OrdPrdKey(Long idOrd, String namePrd) {
        this.idOrd = idOrd;
        this.namePrd = namePrd;
    }

    public Long getIdOrd() {
        return idOrd;
    }

    public void setIdOrd(Long idOrd) {
        this.idOrd = idOrd;
    }

    public String getNamePrd() {
        return namePrd;
    }

    public void setNamePrd(String namePrd) {
        this.namePrd = namePrd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrd != null ? idOrd.hashCode() : 0);
        hash += (namePrd != null ? namePrd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrdPrdKey)) {
            return false;
        }
        OrdPrdKey other = (OrdPrdKey) object;
        if ((this.idOrd == null && other.idOrd != null) || (this.idOrd != null && !this.idOrd.equals(other.idOrd))) {
            return false;
        }
        return (this.namePrd != null || other.namePrd == null) && (this.namePrd == null || this.namePrd.equals(other.namePrd));

    }

    @Override
    public String toString() {
        return "OrdPrdKey[ idOrd=" + idOrd + ", namePrd=" + namePrd + " ]";
    }
}
