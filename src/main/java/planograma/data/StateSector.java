package planograma.data;

/**
 * Состояния секторов планограм
 * Date: 01.04.12
 * Time: 20:37
 *
 * @author Alexandr Polyakov
 */
public enum StateSector {
	/**
	 * Активен
	 */
	A,
	/**
	 * Черновик
	 */
	D,
	/**
	 * Стеллажы соответствует положению в зале магазина
	 */
	PC;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
