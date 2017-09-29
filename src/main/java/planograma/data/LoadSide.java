package planograma.data;

/**
 * Сторона загрузки
 * Date: 01.04.12
 * Time: 20:52
 *
 * @author Alexandr Polyakov
 */
public enum LoadSide {
	/**
	 * С переди
	 */
	F,
	/**
	 * Сверху
	 */
	U;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
