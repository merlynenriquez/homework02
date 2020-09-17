package cs5220stu08.hw2.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserResourcePk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "idUser")
	private Integer idUser;

	@Column(name = "idResource")
	private Integer idResource;

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdResource() {
		return idResource;
	}

	public void setIdResource(Integer idResource) {
		this.idResource = idResource;
	}

}
