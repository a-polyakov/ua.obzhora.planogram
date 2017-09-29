package planograma.constant.data;

/**
 * Date: 21.03.12
 * Time: 0:16
 *
 * @author Alexandr Polyakov
 */
public interface RackConst extends AbstractRackConst {
	public static final String TABLE_NAME = "EUGENE_SAZ.SEV_PL_RACK_IN_SECTOR";

	public static final String CODE_SECTOR = "code_sector";
	public static final String CODE_RACK = "code_rack";
	public static final String NAME_RACK = "name_rack";
	public static final String RACK_BARCODE = "rack_barcode";
	public static final String X_COORD = "x_coord";
	public static final String Y_COORD = "y_coord";
	public static final String ANGLE = "angle";
	public static final String CODE_RACK_TEMPLATE = "code_rack_template";
	public static final String LOCK_SIZE = "lock_size";
	public static final String LOCK_MOVE = "lock_move";
	public static final String TYPE_RACK = "type_rack";
	public static final String ALLOW_INTERSECT = "allow_intersect";

	public static final int STATE_ALL_PART_STATE_TYPE_RACK = -68;
}
