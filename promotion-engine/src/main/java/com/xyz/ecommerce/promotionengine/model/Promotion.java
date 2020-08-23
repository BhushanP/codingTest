package com.xyz.ecommerce.promotionengine.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Promotion {

	@Id	
	private String promoCode;

	private String description;

	private String discountType;

	private float discountPrice;

	private String codeCriteria;

	private float discountPercentage;

	public Promotion(String promoCode, String description, List<Item> itemsOnDiscount, String discountType,
			float discountPrice, String codeCriteria, float discountPercentage) {
		super();
		this.promoCode = promoCode;
		this.description = description;
		this.discountType = discountType;
		this.discountPrice = discountPrice;
		this.codeCriteria = codeCriteria;
		this.discountPercentage = discountPercentage;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getCodeCriteria() {
		return codeCriteria;
	}

	public void setCodeCriteria(String codeCriteria) {
		this.codeCriteria = codeCriteria;
	}

	public float getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(float discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	@Override
	public String toString() {
		return "Promotion [promoCode=" + promoCode + ", description=" + description
				+ ", discountType=" + discountType + ", discountPrice=" + discountPrice
				+ ", codeCriteria=" + codeCriteria + ", discountPercentage=" + discountPercentage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeCriteria == null) ? 0 : codeCriteria.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(discountPercentage);
		result = prime * result + Float.floatToIntBits(discountPrice);
		result = prime * result + ((promoCode == null) ? 0 : promoCode.hashCode());
		result = prime * result + ((discountType == null) ? 0 : discountType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promotion other = (Promotion) obj;
		if (codeCriteria == null) {
			if (other.codeCriteria != null)
				return false;
		} else if (!codeCriteria.equals(other.codeCriteria))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(discountPercentage) != Float.floatToIntBits(other.discountPercentage))
			return false;
		if (Float.floatToIntBits(discountPrice) != Float.floatToIntBits(other.discountPrice))
			return false;
		if (promoCode == null) {
			if (other.promoCode != null)
				return false;
		} else if (!promoCode.equals(other.promoCode))
			return false;
		if (discountType == null) {
			if (other.discountType != null)
				return false;
		} else if (!discountType.equals(other.discountType))
			return false;
		return true;
	}

	public Promotion() {
	}

}
