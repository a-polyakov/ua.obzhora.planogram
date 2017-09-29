package planograma.data;

/**
 * Тип товара на полке
 * Date: 12.05.12
 * Time: 14:34
 *
 * @author Alexandr Polyakov
 */
public enum TypeRackWares {
	/**
	 * Тип не определен
	 */
	NA;

	private TypeRackWares() {
	}

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
