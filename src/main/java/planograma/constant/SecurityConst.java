package planograma.constant;

/**
 * список разграничений в приложении
 * select * from mz.security_list where parent_object_code = -139
 * <p/>
 * Date: 01.06.12
 * Time: 4:30
 *
 * @author Alexandr Polyakov
 */
public enum SecurityConst {
	/**
	 * SEV Модуль Планограммы ТП (WEB приложение)
	 */
	ACCESS_MODULE(-139),
	/**
	 * Доступ к редактированию шаблонов стелажей
	 */
	ACCESS_RACK_TEMPLATE_EDIT(-140),
	/**
	 * Доступ к редактированию стелажей
	 */
	ACCESS_RACK_EDIT(-141),
	/**
	 * Доступ к редактированию зала
	 */
	ACCESS_SECTOR_EDIT(-142),
	/**
	 * Доступ к расстановке товара
	 */
	ACCESS_RACK_WARES_PLACEMENT(-143),
	/**
	 * Доступ к очистке кеша изображений
	 */
	ACCESS_IMAGE_CLEAN_CACHE(-144),
	/**
	 * Доступ к глобальному утверждению стеллажей зала (A)
	 */
	ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A(-145),
	/**
	 * Доступ к глобальному выполнению стеллажей зала (РС)
	 */
	ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC(-155),
	/**
	 * Доступ к принудительному утверждению стеллажа
	 */
	ACCESS_RACK_STATE_SET_A(-146),
	/**
	 * Доступ к выполнению стеллажа
	 */
	ACCESS_RACK_STATE_SET_PC(-154),
	/**
	 * Доступ к принудительному утверждению стеллажа в зале
	 */
	ACCESS_RACK_STATE_IN_SECTOR_SET_A(-157),
	/**
	 * Доступ к выполнению стеллажа в зале
	 */
	ACCESS_RACK_STATE_IN_SECTOR_SET_PC(-156),
	/**
	 * Доступ к редактированию полок при расстановке товара
	 */
	ACCESS_RACK_WARES_PLACEMENT_AND_RACK_SHELF_EDIT(-150);

	private final int value;

	private SecurityConst(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
