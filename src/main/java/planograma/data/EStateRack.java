package planograma.data;

/**
 * Состояние стеллажа
 * Date: 01.04.12
 * Time: 20:45
 *
 * @author Alexandr Polyakov
 */
public enum EStateRack {
	/**
	 * Утвержден - редактирование окончено
	 */
	A,
	/**
	 * Черновик - стеллаж еще редактируется
	 */
	D,
	/**
	 * Выполнен - стеллаж соответвтвует стеллажу в магазине
	 */
	PC;

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
