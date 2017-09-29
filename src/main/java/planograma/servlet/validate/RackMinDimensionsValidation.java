package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.constant.VerificationConst;
import planograma.constant.data.AbstractRackConst;
import planograma.data.*;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
 * Date: 09.01.13
 * Time: 14:45
 *
 * @author Alexandr Polyakov
 */
public class RackMinDimensionsValidation {
	/**
	 * Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
	 *
	 * @param fieldExceptionList список ошибок
	 * @param rack               стеллаж
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Rack rack, final int index) {
		validate(fieldExceptionList, rack, index, rack.getCode_rack(), rack.getType_rack() == ETypeRack.R);
	}


	/**
	 * Проверка параметров шаблонного стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
	 *
	 * @param fieldExceptionList список ошибок
	 * @param rackTemplate       шаблоный стеллаж
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final RackTemplate rackTemplate, final int index) {
		validate(fieldExceptionList, rackTemplate, index, rackTemplate.getCode_rack_template(), true);
	}

	private static void validate(final List<EntityFieldException> fieldExceptionList, final AbstractRack rack, final int index, final Integer code, boolean validateRealSize) {
		final float w = rack.getLength();
		final float rw = rack.getReal_length();
		final float h;
		final float rh;
		final float l;
		final float rl;
		if (rack.getLoad_side() == LoadSide.F) {
			h = rack.getHeight();
			rh = rack.getReal_height();
			l = rack.getWidth();
			rl = rack.getReal_width();
		} else {
			l = rack.getHeight();
			rl = rack.getReal_height();
			h = rack.getWidth();
			rh = rack.getReal_width();
		}
		if (h < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_HEIGHT_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.HEIGHT));
		}
		if (l < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_LENGTH_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.LENGTH));
		}
		if (w < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WIDTH_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.WIDTH));
		}
		if (validateRealSize) {
			if (rh < VerificationConst.MIN_RACK_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_HEIGHT_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.REAL_HEIGHT));
			}
			if (rl < VerificationConst.MIN_RACK_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_LENGTH_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.REAL_LENGTH));
			}
			if (rw < VerificationConst.MIN_RACK_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_WIDTH_TOO_LITTLE(), rack.getClass(), index, code, AbstractRackConst.REAL_WIDTH));
			}
		}
	}
}
