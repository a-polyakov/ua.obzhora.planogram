package planograma.data;

/**
 * тип стеллажа
 * Date: 28.04.12
 * Time: 5:56
 *
 * @author Alexandr Polyakov
 */
public enum ETypeRack {
	/**
	 * Стеллаж
	 */
	R,
	/**
	 * Касса
	 */
	WP,
	/**
	 * Мертвая зона
	 */
	DZ;

	private ETypeRack() {

	}

	private String desc;
	private String color;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
